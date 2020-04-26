package com.github.chengzhx76.jdk.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author admin
 * @Date 2020/4/26 11:45
 * @Version 3.0
 */
// https://blog.csdn.net/new_abc/article/details/28642189
public class TicketSeller {

    static int i = 1;
    final static int totalTicket = 8;
    final static List<Integer> tickets = new ArrayList<>(totalTicket);

    public static void main(String[] args) {

        new Thread(new Producer()).start();
//        new Thread(new Producer()).start();
//        new Thread(new Producer()).start();
        new Thread(new Consumer(), "seller-1").start();
        new Thread(new Consumer(), "seller-2").start();
//        new Thread(new Consumer(), "seller-3").start();
//        new Thread(new Consumer(), "seller-4").start();
//        new Thread(new Consumer(), "seller-5").start();

    }


    void create() throws InterruptedException {
        while (true) {
            if (tickets.size() < TicketSeller.totalTicket) {
                System.out.println("制作票-> " + i);
                tickets.add(i);
                i++;
                TimeUnit.SECONDS.sleep(1);
            } else {
                System.out.println("等待卖出票，显存票-> " + tickets.size());
                wait();
            }
        }
    }

    synchronized void sell() throws InterruptedException {
        while (true) {
            if (tickets.size() > 0) {
                int i = tickets.remove(0);
                System.out.println(Thread.currentThread().getName() + " 卖出票-> " + i);
                TimeUnit.SECONDS.sleep(3);
            } else {
                notify();
            }
        }
    }

}


class Producer implements Runnable  {
    private TicketSeller seller = new TicketSeller();
    @Override
    public void run() {
        try {
            seller.create();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable  {
    private TicketSeller seller = new TicketSeller();
    @Override
    public void run() {
        try {
            seller.sell();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}