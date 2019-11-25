package com.qzw.demo.algorithm.多元线性回归;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;

import java.lang.ref.WeakReference;


/**
 * Created by Administrator on 2019/3/18 0018.
 */
public class A_虚引用 {

    public static void main(String[] args) {
        ThreadLocalMy threadLocalMy = new ThreadLocalMy();
        Entry entry = new Entry(threadLocalMy, 1);
        MapContainer mapContainer = new MapContainer();
        mapContainer.setEntry(entry);
        System.out.println(mapContainer.getEntry());
        //
        System.out.println("threadLocal设置为空");
        threadLocalMy = null;
        //
        System.out.println(mapContainer.getEntry());
        //
        System.out.println("FULL GC发生");
        System.gc();
        //
        System.out.println(mapContainer.getEntry());
    }

    static class Entry extends WeakReference<ThreadLocalMy> {
        int value;

        public Entry(ThreadLocalMy referent, int value) {
            super(referent);
            this.value = value;
        }
        @Override
        public String toString() {
            return "Entry{" +
                    "value=" + value +
                    ", " +
                    "reference=" + String.valueOf(super.get()) +
                    '}';
        }
    }
    static class ThreadLocalMy {

    }
    static class MapContainer {
        private Entry entry;

        public void setEntry(Entry entry) {
            this.entry = entry;
        }

        public Entry getEntry() {
            return entry;
        }
    }
}


