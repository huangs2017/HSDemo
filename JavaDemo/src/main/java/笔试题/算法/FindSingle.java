package 笔试题.算法;

import Util.Log;

public class FindSingle {

    static int[] a = {10, 2, 1, 2, 5, 4, 4, 8, 10, 8, 1};

    public static void main(String[] args) {
        Log.i(findSingle(a, 11));
    }

    public static int findSingle(int[] A, int length) {
        int res = 0;
        for (int i = 0; i < length; i++) {
            res = res ^ A[i];
        }
        return res;
    }

}


/*

一个数组中有2n+1个整数，其中有n个数是成对出现的，只有一个是不成对的，如何尽快找到这个数

例如{10, 2, 1, 2, 5, 4, 4, 8, 10, 8, 1}输出结果是5


原理：异或操作（^）相同为0，相异为1
0与任何数^等于自己，相等的两个数^等于0
0^0=0, 1^1=0, 0^1=1, 1^0=1
*/