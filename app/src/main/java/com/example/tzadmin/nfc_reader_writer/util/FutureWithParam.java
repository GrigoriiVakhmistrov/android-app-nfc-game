package com.example.tzadmin.nfc_reader_writer.util;

import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @param <T> future type
 * @param <P> param type
 */
public class FutureWithParam<T, P> implements FutureCallback<T> {
    private static final List<FutureWithParam> cache;
    private static final FutureReaper reaper;

    static {
        List<FutureWithParam> cacheInit = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            cacheInit.add(new FutureWithParam());
        }
        cache = Collections.synchronizedList(cacheInit);
        reaper = new FutureReaper();
        reaper.start();
    }

    private Future<T> future;
    private P param;
    private List<Callback<T, P>> callbacks;

    private FutureWithParam() {}

    public FutureWithParam(Future<T> future, P param) {
        this.future = future;
        this.param = param;
        this.callbacks = new ArrayList<>();
        init();
    }

    private void init() {
        future.setCallback(this);
    }

    public static <T, P> FutureWithParam<T, P> fromCache(Future<T> future, P param) {
        if(cache.isEmpty())
            return new FutureWithParam<>(future, param);
        FutureWithParam entry = cache.remove(0);
        entry.future = future;
        entry.param = param;
        entry.callbacks = new ArrayList();
        entry.init();
        return entry;
    }

    public static void giveToCache(FutureWithParam future) {
        if(cache.size() > 10)//Do not store more than 10 cache futures
            return;
        future.param = null;
        future.future = null;
        cache.add(future);
    }

    public static void addFutureToCheck(FutureWithParam futureWithParam) {
        reaper.put(futureWithParam);
    }

    public void setCallback(Callback<T, P> callback) {
        callbacks.add(callback);
    }

    public T tryGet() {
        return future.tryGet();
    }

    public Exception tryGetException() {
        return future.tryGetException();
    }

    public boolean isDone() {
        return future.isDone();
    }

    public T get() throws InterruptedException, ExecutionException {
        return future.get();
    }

    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return future.get(timeout, unit);
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return future.cancel(mayInterruptIfRunning);
    }

    public boolean isCancelled() {
        return future.isCancelled();
    }

    public boolean cancel() {
        return future.cancel();
    }

    public P getParam() {
        return param;
    }

    @Override
    public void onCompleted(Exception e, T result) {
        for(Callback<T, P> callback : callbacks) {
            callback.completed(e, result, param);
        }
    }

    public interface Callback<T, P> {
        void completed(Exception e, T result, P param);
    }
}
