package com.example.tzadmin.nfc_reader_writer.util;

import java.util.concurrent.LinkedBlockingQueue;

final class FutureReaper extends Thread {
    private final LinkedBlockingQueue<FutureWithParam> queue = new LinkedBlockingQueue<>();

    public FutureReaper() {
        setDaemon(true);
        setName("FutureReaper");
        setPriority(Thread.MIN_PRIORITY + 1);
    }

    public void run() {
        try {
            while(isAlive()) {
                FutureWithParam futureWithParam = queue.take();
                if(futureWithParam.isDone())
                    FutureWithParam.giveToCache(futureWithParam);
                else
                    queue.put(futureWithParam);

                Thread.sleep(500);//Give futures to complete
            }
        } catch (InterruptedException e) {
            //Shutdown thread
        }
    }

    public void put(FutureWithParam futureWithParam) {
        if(queue.size() > 10)
            return;//Fixed problem with queue garbage overflow
        queue.add(futureWithParam);
    }
}
