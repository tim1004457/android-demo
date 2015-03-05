package com.tencent.timluo.demo.manager;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by timluo on 2014/8/30.
 */
public class ReferenceModel<T> {
    private ReferenceQueue<T> queue = null;
    private List<Reference<T>> referenceList = null;
    private int referenceMode = 0;
    public static final int REFERENCE_TYPE_SOFT = 0;
    public static final int REFERENCE_TYPE_WEAK = 1;
    private Object LOCK = new Object();

    public ReferenceModel() {
        this(REFERENCE_TYPE_SOFT);
    }

    public ReferenceModel(int referenceMode) {
        referenceMode = referenceMode;
        queue = new ReferenceQueue<T>();
        referenceList = new ArrayList<Reference<T>>();
    }

    public void regiestReference(T reference) {
        if (null == reference) {
            return;
        }
        synchronized (LOCK) {
            Reference deleteReference = null;
            while ((deleteReference = queue.poll()) != null) {
                referenceList.remove(deleteReference);
            }
            Reference<T> addReferenceItem;
            if (REFERENCE_TYPE_SOFT == referenceMode) {
                addReferenceItem = new SoftReference<T>(reference, queue);
            } else {
                addReferenceItem = new WeakReference<T>(reference, queue);
            }
            if (!referenceList.contains(addReferenceItem))
            referenceList.add(addReferenceItem);
        }
    }

    public void unregiestReference(T reference) {
        if (null == reference)
            return;
        synchronized (LOCK) {
            referenceList.remove(reference);
        }
    }

    public List<T> getReference() {
        List<T> resultList = new ArrayList<T>(referenceList.size());
        synchronized (LOCK) {
            for (Reference<T> item : referenceList) {
                if (null != item.get()) {
                    resultList.add(item.get());
                }
            }
        }
        return resultList;
    }
}
