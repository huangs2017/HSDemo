package 笔试题.sort;

public class BubbleSort {

    public void bubbleSort(int[] a, int length) {
        // 排序趟数，最后一个元素不用比较所以是 (n-1) 趟
        for (int i = 0; i < length - 1; i++) {
            // 每趟比较的次数，第 i 趟比较 (n-1-i) 次
            for (int j = 0; j < length - 1 - i; j++) {
                // 比较相邻元素，若逆序则交换
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                }
            }
        }
    }

}


/*


比较相邻的元素，如果反序则交换，
数组元素分为有序区和无序区，初始时所有元素都在无序区，
经过第一趟后就能找出最大的元素，然后重复即可

第一个for循环是遍历所有元素，
第二个for循环是对无序区的相邻两个元素进行依次比较，若反序则交换

*/
