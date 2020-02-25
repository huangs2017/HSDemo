package 笔试题.sort;

public class QuickSort {

    public void quickSort(int a[], int l, int r) {
        if (l >= r)
            return;

        int i = l;
        int j = r;
        int key = a[l]; // 选择第一个数为key

        while (i < j) {
            while (i < j && a[j] >= key) //从右向左找第一个小于key的值
                j--;
            if (i < j) {
                a[i] = a[j];
                i++;
            }

            while (i < j && a[i] < key) //从左向右找第一个大于key的值
                i++;
            if (i < j) {
                a[j] = a[i];
                j--;
            }
        }
        //i == j
        a[i] = key;
        quickSort(a, l, i - 1); // 将左半部分排序
        quickSort(a, i + 1, r); // 将右半部分排序
    }

}







/*
快速排序的基本思想是:
    通过一趟排序算法把数组分成两部分，
    其中，一部分的元素<=另一部分的元素，
    然后通过递归对左右两部分排序。
*/