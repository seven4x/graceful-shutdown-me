package com.example.stopme.app;

import java.util.concurrent.LinkedBlockingQueue;

public class BlockJob implements Runnable{
    @Override
    public void run() {
        System.out.println("BlockJob start");
        try {
            Object take = new LinkedBlockingQueue<>().take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return ;
        }
        System.out.println("BlockJob end");

    }
}
