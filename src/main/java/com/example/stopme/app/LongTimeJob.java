package com.example.stopme.app;

import lombok.SneakyThrows;

import java.util.concurrent.locks.LockSupport;

public class LongTimeJob implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            System.out.println("Long job:" + System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
//                throw new RuntimeException(e);
                return;
            }

        }
    }

}
