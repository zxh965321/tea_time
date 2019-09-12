package com.zky.tea_time.utils;

/**
 * @author zky
 * @description:
 * @date : 2019-09-09 10:34
 */
public class AlgorithmUtils {
    //单利AlgorithmUtils 变量
    private static volatile AlgorithmUtils instanse = null;

    //私有化构造方法
    private AlgorithmUtils() {
    }

    //获取单利对象
    public static AlgorithmUtils getInstanse() {
        if (instanse == null) {
            synchronized (AlgorithmUtils.class) {
                if (instanse == null) {
                    instanse = new AlgorithmUtils();
                }
            }
        }
        return instanse;
    }

    /**
     * 希尔算法
     *
     * @param array
     */
    public void sort(int[] array) {
        //希尔排序的增量
        int d = array.length;
        while (d > 1) {
            //使用希尔增量的方式，即每次折半
            d = d / 2;
            for (int x = 0; x < d; x++) {
                for (int i = x + d; i < array.length; i = i + d) {
                    int temp = array[i];
                    int j;
                    for (j = i - d; j >= 0 && array[j] > temp; j = j - d) {
                        array[j + d] = array[j];
                    }
                    array[j + d] = temp;
                }
            }
        }
    }





}
