package org.example;
import static java.lang.Math.*;
import static java.lang.System.out;

public class LabWork1_4 {
    public static void main(String[] args) throws InterruptedException {
        class Sync {
            volatile double x = 1d;
            volatile boolean phase = true;
        }
        final Sync sync = new Sync();

        Thread t0 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                synchronized (sync) {
                    while(!sync.phase) {
                        try {
                            sync.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                        sync.x = sin(sync.x);
                        out.println(sync.x);
                        sync.phase = !sync.phase;
                        sync.notify();
                }
            }
        });


        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                synchronized (sync) {
                    while (sync.phase) {
                        try {
                            sync.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                        sync.x = asin(sync.x);
                        out.println(sync.x);
                        sync.phase = !sync.phase;
                        sync.notify();
                }
            }
        });
        t0.start();
        t1.start();
    }
}

