package com.thread_demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class MoleculePrinter {

    private final ExecutorService executor;

    public MoleculePrinter() {
        this.executor = Executors.newFixedThreadPool(20);
    }

    public static void main(String[] args) {
        MoleculePrinter mp = new MoleculePrinter();
        //mp.printH2SO4(10);
        mp.printPropane(5);
        //System.out.printf("~~~~~~~~~~~~~~~~~~~~~~~~");
        // Shutdown executor and wait for tasks to complete
        mp.executor.shutdown();
        try {
            mp.executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printPropane(int count) {
        PropaneC3H6 propane = new PropaneC3H6();
        int carbonCount = 3, hydrogenCount = 6;
        for(int i = 0; i < count; i++) {
            for(int cc = 0; cc < carbonCount; cc++) {
                this.executor.submit(() -> {
                    try {
                        propane.carbon(() -> System.out.printf("C"));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            for(int hc = 0; hc < hydrogenCount; hc++) {
                this.executor.submit(() -> {
                    try {
                        propane.hydrogen(() -> System.out.printf("H"));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }

    private class PropaneC3H6 {
        private final Semaphore semC;
        private final Semaphore semH;

        private final CyclicBarrier barrier;

        public PropaneC3H6() {
            this.semH = new Semaphore(6);
            this.semC = new Semaphore(3);
            this.barrier = new CyclicBarrier(9);


        }

        public void carbon(Runnable releaseCarbonAtom) throws InterruptedException {
            this.semC.acquire();
            releaseCarbonAtom.run();
            try {
                this.barrier.await();
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            this.semC.release();
        }

        public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
            this.semH.acquire();
            releaseHydrogen.run();

            try {
                this.barrier.await();
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            this.semH.release();
        }
    }

    private void printH2SO4(int count) {
        H2SO4 h2SO4 = new H2SO4();
        for(int i = 0; i < count; i++) {
            executor.submit(() -> {
                try {
                    h2SO4.hydrogen(() -> System.out.printf("H"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            executor.submit(() -> {
                try {
                    h2SO4.hydrogen(() -> System.out.printf("H"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            executor.submit(() -> {
                try {
                    h2SO4.oxygen(() -> System.out.printf("O"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            executor.submit(() -> {
                try {
                    h2SO4.sulphur(() -> System.out.printf("S"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            executor.submit(() -> {
                try {
                    h2SO4.sulphur(() -> System.out.printf("S"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            executor.submit(() -> {
                try {
                    h2SO4.sulphur(() -> System.out.printf("S"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            executor.submit(() -> {
                try {
                    h2SO4.sulphur(() -> System.out.printf("S"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            //System.out.printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        }
    }
    


    public class H2SO4 {
        private final Semaphore semH;
        private final Semaphore semO;
        private final Semaphore semS;
        private final CyclicBarrier cyclicBarrier;

        public H2SO4() {
            this.semH = new Semaphore(2);
            this.semO = new Semaphore(1);
            this.semS = new Semaphore(4);
            this.cyclicBarrier = new CyclicBarrier(7);

        }

        public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
            this.semH.acquire();
            releaseHydrogen.run();

            try {
                this.cyclicBarrier.await();
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            this.semH.release();
        }

        public void oxygen(Runnable releaseOxygen) throws InterruptedException {
            this.semO.acquire();

            releaseOxygen.run();

            try {
                this.cyclicBarrier.await();
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            this.semO.release();
        }

        public void sulphur(Runnable releaseSulphur) throws InterruptedException{
            this.semS.acquire();
            releaseSulphur.run();
            try {
                this.cyclicBarrier.await();
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            this.semS.release();
        }
    }
}
