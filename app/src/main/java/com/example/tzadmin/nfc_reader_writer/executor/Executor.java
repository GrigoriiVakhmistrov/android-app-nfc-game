package com.example.tzadmin.nfc_reader_writer.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

public final class Executor {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("ExecutorWorker");
            thread.setDaemon(true);
            return thread;
        }
    });

    private Executor() {
        throw new UnsupportedOperationException();
    }

    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return executor.submit(task);
    }

    /**
     * Use this only on application exit
     */
    public static void shutdown() {
        executor.shutdown();
    }
}
