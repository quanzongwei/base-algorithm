import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.invoke.VolatileCallSite;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.Pack200;

/**
 * Created by Administrator on 2019/3/1 0001.
 */
public class MainTest {


    public static void main(String[] args) {
        double x = 0.99;
        double y = 0.41;
        System.out.println(x * 26468 + y * 37774);
        System.out.println(y * 69334 + x * 36005);
    }

    @Test
    public void listAddTime() {
        long begin = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        Long max = null;
        Long min = null;
        for (int i = 0; i < 1000 * 1000; i++) {
            long maxBegin = System.currentTimeMillis();
            list.add(new Integer(1));
            long maxEnd = System.currentTimeMillis();
            long time = maxEnd - maxBegin;
            if (max == null) {
                max = time;
                min = time;
            }
            if (time > max) {
                System.out.println(time);
                System.out.println(list.size());
                max = time;
            }
            if (time < min) {
                min = time;
            }
        }
        System.out.println("max:" + max);
        System.out.println("min:" + min);
        System.out.println((System.currentTimeMillis() - begin));
    }

    //** 指数增长
    @Test
    public void listAddTime2() {
        int SIZE = 1024 * 6000;
        long begin = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        Long max = null;
        Long min = null;
        for (int i = 0; i < SIZE; i++) {
            long maxBegin = System.currentTimeMillis();
            list.add(new Integer(1));
            long maxEnd = System.currentTimeMillis();
            long time = maxEnd - maxBegin;
            if (max == null) {
                max = time;
                min = time;
            }
            if (time > max) {
//                System.out.println(time);
//                System.out.println(list.size());
                max = time;
            }
            if (time < min) {
                min = time;
            }
        }
        System.out.println("max:" + max);
        System.out.println("min:" + min);
        System.out.println((System.currentTimeMillis() - begin));
    }


    @Test
    public void removeAll() {
        List list = new ArrayList();
        list.add("1");
        list.add("1");
        list.add("1");
        list.remove("1");
        List c = new ArrayList<>();
        c.add("1");
        c.removeAll(c);
        System.out.println(list);
        System.out.println(c);
    }

    @Test
    public void hashMap() {
        new Runnable() {

            @Override
            public void run() {
                System.out.println(" hi");
            }
        }.run();

    }

    volatile boolean start = false;

    @Test
    public void hashMap2() throws InterruptedException {

        final HashMapMy<Integer, Integer> map = new HashMapMy<>();
        map.put(16, 16);
        map.put(32, 32);
        map.put(1, 1);
        map.put(2, 1);
        map.put(3, 1);
        map.put(4, 1);
        map.put(5, 1);
        map.put(6, 1);
        map.put(7, 1);
        map.put(8, 1);
        map.put(9, 1);
        map.put(10, 1);
        //12个值
        map.put(11, 1);
        start = true;
        Thread ta = new Thread(new Runnable() {
            @Override
            public void run() {
                map.put(27, 1);

            }
        });
        ta.setName("线程A");
        Thread tb = new Thread(new Runnable() {
            @Override
            public void run() {
                map.put(43, 1);
            }
        });
        tb.setName("线程B");

        ta.start();
        tb.start();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        ta.join();
        Integer integer = map.get(48);
        System.out.println(integer);

    }

    @Test
    public void testCircle() {


        while (true) {

        }
    }

    @Test
    public void bitMove() {
        int i = -1;
        System.out.println(i >>> 1);
        System.out.println((4 & 1) == 0);
        Map map = new HashMap();
        map.put("1", 1);

    }

    @Test
    public void intgerTest(
    ) {
        System.out.println(Integer.numberOfLeadingZeros(1 << 1));
        System.out.println(2 << 31);
        ConcurrentHashMap map = new ConcurrentHashMap();
        map.put(1, 1);
        System.out.println();

        System.out.println();
    }


    @Test
    public void unsafe() throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe)theUnsafe.get(null);
        System.out.println(unsafe.arrayBaseOffset(int[].class));
        System.out.println(unsafe.arrayIndexScale(int[].class));

        System.out.println(unsafe.arrayBaseOffset(long[].class));
        System.out.println(unsafe.arrayIndexScale(long[].class));
    }


    @Test
    public void test8ConcurrentHashMap() {
        int initialCapacity = 33;
        System.out.println(tableSizeFor(initialCapacity + (initialCapacity >>> 1) + 1));
    }
    private static final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ?1: n + 1;
    }


    @Test
    public void testJIT() {
        while (true) {
            tableSizeFor(1);
        }
//        while (true) {
//            i++;
//        }
    }

    @Test
    public void testAOM() {

    }
}
