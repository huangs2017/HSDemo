package 笔试题.算法;

import Util.Log;

// 字符串反转
public class StringReverse {

    public static void main(String[] args) {
        String str = "abcda";
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length / 2; i++) {
            char temp = chars[i];
            chars[i] = chars[chars.length - i - 1];
            chars[chars.length - i - 1] = temp;
        }
        Log.i(new String(chars));
    }

}
