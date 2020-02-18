package TTHashMap;

import java.util.HashSet;

public class TestHashMap {

    public static void main(String[] args) {

        // TTHashMap测试
        TTHashMap<Integer, String> map = new TTHashMap();
        map.put(10, "aa");
        map.put(20, "bb");
        map.put(30, "cc");
        map.put(20, "ss");

        map.put(53, "gg");
        map.put(69, "hh");
        map.put(85, "kk");
//        System.out.println(map);


        // TTHashSet测试
        TTHashSet<String> set = new TTHashSet();
        set.add("aa");
        set.add("bb");
        System.out.println(set);

        HashSet<String> set2 = new HashSet<>();
        for (String s : set2) {

        }
    }

}
