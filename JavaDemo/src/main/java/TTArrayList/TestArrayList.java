package TTArrayList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TestArrayList {

    public static void main(String[] args) {
        TTArrayList list = new TTArrayList(20);
        for (int i = 0; i < 100; i++) {
            list.add("TT" + i);
        }
//        System.out.println(list);
        System.out.println(list.get(21));
        list.set(99, "aa");
        System.out.println(list);
    }


    // 遍历集合的方法总结
    public void iteratorTest() {
        ArrayList<String> list = new ArrayList();
        HashSet<String> set = new HashSet();
        HashMap<String, String> map = new HashMap();

        // 遍历List_1：普通for循环
        for (int i = 0; i < list.size(); i++) {//list为集合的对象名
            String s = list.get(i);
            System.out.println(s);
        }
        // 遍历List_2：增强for循环
        for (String s : list) {
            System.out.println(s);
        }
        // 遍历List_3：Iterator迭代器
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            String s = (String) it.next();
            it.remove();//如果遍历时，要删除集合中的元素，建议使用这种方式
            System.out.println(s);
        }

        // 遍历Set_1：增强for循环
        for (String temp : set) {
            System.out.println(temp);
        }
        // 遍历Set_2：Iterator迭代器
        for (Iterator it = set.iterator(); it.hasNext(); ) {
            String temp = (String) it.next();
            System.out.println(temp);
        }

        //遍历Map_1：根据key获取value
        Set<String> keySet = map.keySet();
        for (String s : keySet) {
            System.out.println(map.get(s));
        }
        //遍历Map_2：使用entrySet
        Set<Map.Entry<String, String>> ss = map.entrySet();
        for (Iterator it = ss.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            System.out.println(entry.getKey() + "--" + entry.getValue());
        }
    }

}
