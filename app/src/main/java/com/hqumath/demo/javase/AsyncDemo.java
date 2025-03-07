package com.hqumath.demo.javase;

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2025/3/7 17:08
 * 文件描述: 异步转同步
 * 注意事项:
 * ****************************************************************
 */
public class AsyncDemo {

    //private Random random = new Random(System.currentTimeMillis());
    private final Object lock = new Object();

    public static void main(String[] args) {
        System.out.println("发起调用");
        AsyncDemo demo = new AsyncDemo();
        demo.asyncCall();
        System.out.println("主线程等待");
        synchronized (demo.lock) {
            try {
                demo.lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("主线程结束");
    }

    /**
     * 异步函数
     */
    public void asyncCall() {
        new Thread(() -> {
            //long res = random.nextInt(10);
            System.out.println("子线程开始 3s");
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程结束,唤醒主线程");
            synchronized (lock) {
                lock.notifyAll();
            }
        }).start();
    }
}