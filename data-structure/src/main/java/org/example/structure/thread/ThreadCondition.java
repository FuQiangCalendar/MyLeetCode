package org.example.structure.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Name ThreadCondition
 * @Description
 * @Author qfu1
 * @Date 2023-02-14
 */
public class ThreadCondition {
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    public static void main(String[] args) throws InterruptedException {
//        extracted() ;
        print(3);
    }

    private static void extracted() throws InterruptedException {
        new Thread(() ->{
            try {
                lock.lock();
                System.out.println("静茹等待");
                condition.await();
                System.out.println("被唤醒继续执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();
        TimeUnit.SECONDS.sleep(4);
        lock.lock();
        condition.signal();
        lock.unlock();
    }

    public static void print (int num) {

        for (int i = 31; i >= 0; i--) {
            System.out.println("i>>" + (1 << i));
            System.out.print((num & (1 << i)) == 0  ? "0" : "1");
        }
        System.out.println();
    }
}
