package TTHashMap;

import java.util.HashMap;

// 自定义HashSet
public class TTHashSet<E> {

    HashMap<E, Object> map;
    private static final Object PRESENT = new Object();

    public TTHashSet() {
        map = new HashMap<>();
    }

    public boolean add(E e) {
        return map.put(e, PRESENT)==null;
    }

    public int size() {
        return map.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (E e : map.keySet()) {
            sb.append(e + ",");
        }
        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }

}
