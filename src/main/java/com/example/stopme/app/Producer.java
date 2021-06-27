package com.example.stopme.app;

import lombok.extern.java.Log;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * @author seven
 */
@Log
public class Producer implements Runnable {
    private LinkedBlockingQueue<String> queue;
    private volatile boolean exit;

    public Producer(LinkedBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (; ; ) {
            if (exit) {
                log.info("product grace exit");
                break;
            }
            String info = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            log.info("product:" + info);
            System.out.println("sys:product:" + info);
            LockSupport.parkNanos(1000000000);
            try {
                queue.put(info);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
