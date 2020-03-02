package 笔试题.算法;


import java.util.HashMap;
import java.util.Map;

import Util.Log;

// 找出字符串中出现次数最多的字符
public class FindCharTimes {

    public static void main(String[] args) {
        String str = "abcda";
        char res = str.charAt(0);
        int max = 0; //最多出现了多少次
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            Integer count = map.get(c);
            if (count == null) { //字符没有出现过
                count = 1;
            } else {
                count++;
            }
            map.put(c, count);
            if (count > max) {
                res = c;
                max = count;
            }
        }
        Log.i(res + "出现次数最多:" + max);
    }

}