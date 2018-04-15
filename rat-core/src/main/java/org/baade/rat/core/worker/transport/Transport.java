package org.baade.rat.core.worker.transport;

import java.util.UUID;

public class Transport {

    private String id;
    private long createTime;

    private Transfer receiver;

    private Transfer sender;

    private boolean isCallback = false;

    private boolean isDone  = false;

    public Transport() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }


    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Transfer getReceiver() {
        return receiver;
    }

    public void setReceiver(Transfer receiver) {
        this.receiver = receiver;
    }

    public Transfer getSender() {
        return sender;
    }

    public void setSender(Transfer sender) {
        this.sender = sender;
    }

    public boolean isCallback() {
        return isCallback;
    }

    public void setCallback(boolean callback) {
        isCallback = callback;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
