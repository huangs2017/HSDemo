package 笔试题.sort;

public class SelectSort {


    public static void selectionSort(int[] a, int length) {
        for (int i = 0; i < length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < length; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            //交换操作
            int temp = a[min];
            a[min] = a[i];
            a[i] = temp;
        }
    }


}

/*
从无序区中找到最小元素，与无序区的起始元素交换位置

*/
