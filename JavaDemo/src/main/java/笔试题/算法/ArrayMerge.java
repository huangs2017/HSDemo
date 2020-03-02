package 笔试题.算法;


//两个有序数组 合成一个有序数组
public class ArrayMerge {

    public static void main(String[] args) {
        //定义两个数组下标，遍历并记录index使用；
        int indexM = 0;
        int indexN = 0;
        int[] arrayM = new int[]{1, 4, 6, 7, 8};
        int[] arrayN = new int[]{2, 3, 5, 9, 11};

        int index = 0;    //定义汇总数组的index；
        int[] res = new int[arrayM.length + arrayN.length];    //定义一个汇总数组

        //使用while循环遍历；当indexM或者indexN中有任意一个值为M或者N时，则表示当前某一个数组遍历到尾部
        while (indexM < arrayM.length && indexN < arrayN.length) {
            if (arrayM[indexM] <= arrayN[indexN]) {
                res[index] = arrayM[indexM];
                indexM++;
            } else {
                res[index] = arrayN[indexN];
                indexN++;
            }
            index++;
        }

        // 判断哪一个数组先遍历完，将另一个数组添加到汇总数组中；
        if (indexM != arrayM.length) {
            for (int i = indexM; i < arrayM.length; i++) {
                res[index] = arrayM[i];
                index++;
            }
        } else {
            for (int i = indexN; i < arrayN.length; i++) {
                res[index] = arrayN[i];
                index++;
            }
        }

    }


}

// 时间复杂度为O（m+n）