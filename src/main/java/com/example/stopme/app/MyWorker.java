package com.example.stopme.app;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import sun.misc.Unsafe;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * 优雅：producer生产的所有key 需要consumer消费完成后退出
 * actuator/shutdown
 * kill -2 signal ,jvm hook, spring callback,stop task ,stop executor
 */
@Component
@Log
public class MyWorker {

    ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 2,
            TimeUnit.MINUTES, new LinkedBlockingQueue<>(),
            new ThreadPoolExecutor.CallerRunsPolicy());
    Producer producer;
    Consumer consumer;
    LinkedBlockingQueue<String> queue;

    @PostConstruct
    public void start() {
        log.info("worker is starting");
        queue = new LinkedBlockingQueue<>(10);
        producer = new Producer(queue);
        consumer = new Consumer(queue);
        executor.execute(producer);
        executor.execute(consumer);
        executor.execute(new LongTimeJob());
        executor.execute(new LongTimeJob2());
        executor.execute(new BlockJob());

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                log.info("runtime shutdown hook");
                System.out.println("sys: runtime shutdown hook");
            }
        });
    }


    @PreDestroy
    public void clean() {
        log.info("worker is cleaned");
        System.out.println("sys: worker is cleaned");
        stop();
    }


    public void stop() {
        System.out.println("setting exit ture");
        producer.setExit(true);
        consumer.setExit(true);
        //如果业务不做控制，consumer无法完整消费
        while (!queue.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("shutdown  executor");
        //如果不退出，或者shutdown ?
        executor.shutdown();
//       executor.shutdownNow();
    }
}
