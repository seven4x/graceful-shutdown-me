package com.example.stopme.app;

import lombok.SneakyThrows;

public class LongTimeJob2 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Long job2: isInterrupted" + System.currentTimeMillis());
                break;
            }
            System.out.println("Long job2:" + System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                //假如不抛异常，？
//                throw new RuntimeException(e);

                return;
            }
        }
    }

}
