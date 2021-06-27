package com.example.stopme.app;

import lombok.extern.java.Log;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

@Log
public class Consumer implements Runnable {
    private LinkedBlockingQueue<String> queue;
    private volatile boolean exit;

    public Consumer(LinkedBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (; ; ) {
            if (exit && queue.isEmpty()) {
                log.info("product grace exit");
                break;
            }
            String peek = null;
            LockSupport.parkNanos(2000000000);

            try {
                peek = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("consume:" + peek);
            System.out.println("sys:consume:" + peek);
        }

    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
