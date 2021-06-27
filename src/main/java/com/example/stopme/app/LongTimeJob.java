package com.example.stopme.app;

import lombok.SneakyThrows;

import java.util.concurrent.locks.LockSupport;

public class LongTimeJob implements Runnable {

    @SneakyThrows
    @Override
    public void run() {
        for (int i = 0; i < 600000; i++) {
            System.currentTimeMillis();
            Thread.sleep(1000);
        }
    }

}
