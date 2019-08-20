package com.cstary.currency;


import java.util.concurrent.CountDownLatch;
import java.util.Random;

import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
public class ExchangeRate {

    private static ZooKeeper zk = null;
    private static final String host = "10.0.0.2:2181";
    private static final Double[] currencyRate = new Double[]{2.0, 12.0, 0.15, 9.0};
    private static String[] currencyType = new String[]{"RMB", "USD", "JPY", "EUR"};
    private static String PATH = "/Currency";


    private static byte[] double2Bytes(double d) {
        long value = Double.doubleToRawLongBits(d);
        byte[] byteRet = new byte[8];
        for (int i = 0; i < 8; i++) {
            byteRet[i] = (byte) ((value >> 8 * i) & 0xff);
        }
        return byteRet;
    }

    public static void initrate() throws Exception {
        zk.create(PATH+"/RMB", double2Bytes(2.0),Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create(PATH+"/USD", double2Bytes(12.0),Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create(PATH+"/JPY", double2Bytes(0.15),Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create(PATH+"/EUR", double2Bytes(9.0),Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public String getData(String path, Boolean watch) throws KeeperException, InterruptedException{
        byte[] data = zk.getData(path, watch, null);
        return new String(data);
    }


    public static void main(String[] args) throws InterruptedException,Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        initrate();
        zk = new ZooKeeper(host, 50000, new Watcher() {

            public void process(WatchedEvent watchedEvent) {
                try {
                    for (int i = 0; i < 4; i++) {
                        Stat stat = zk.exists("/Currency/" + currencyType[i], false);
                        if (stat == null) {
                            zk.create("/Currency/" + currencyType[i], double2Bytes(currencyRate[i]), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                        }
                        final int index = i;
                        final long t = 30000;
                        new Thread(() -> {
                            try {
                                while (true) {
                                    Random rand = new Random();
                                    double floatRate = rand.nextInt(50) / 100.0 + 0.75;
                                    double newCurrency = floatRate * currencyRate[index];
                                    zk.setData("/Currency/" + currencyType[index], double2Bytes(newCurrency), -1);
                                    byte[] data = zk.getData("/Currency/" + currencyType[index], false, null);
                                    Thread.sleep(t);
                                }

                            } catch (Exception e) {

                            }
                        }).start();
                    }
                } catch (Exception e) {

                }
            }
        });
        latch.await();

    }
}