package 笔试题.算法;

import java.util.HashSet;
import java.util.Set;

import Util.Log;

// 找出第一个重复出现的字符
public class FindRepeatChar {

    public static void main(String[] args) {
        String str = "abcda";
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < str.length(); i++) {
            if (!set.add(str.charAt(i))) { // 已经存在了
                Log.i(str.charAt(i));
                break;
            }
        }
    }
//    第一个只出现一次的字符
}
