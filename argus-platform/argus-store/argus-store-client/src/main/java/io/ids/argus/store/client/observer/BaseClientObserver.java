package io.ids.argus.store.client.observer;

import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public abstract class BaseClientObserver<C,S> implements StreamObserver<S> {
    protected StreamObserver<C> sender;
    protected boolean closeSession = false;
    private final ReentrantLock closedLock = new ReentrantLock();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void setSender(StreamObserver<C> sender){
        this.sender = sender;
    }

    @Override
    public void onError(Throwable throwable) {
        sender.onError(throwable);
    }

    @Override
    public void onCompleted() {
        sender.onCompleted();
    }

    public void close() {
        synchronized (closedLock) {
            if (closeSession) {
                return;
            }
            closeSession = true;
        }
    }
    protected void signal(){
        condition.signal();
    }
    protected boolean await() throws InterruptedException {
        return condition.await(60, TimeUnit.SECONDS);
    }
    protected void lock(){
        lock.lock();
    }
    protected void unlock(){
        lock.unlock();
    }
}
