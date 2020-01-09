package JavaDemo.TTHashMap;

/*
    HashMap
    TreeMap
    Hashtable
    Properties
*/

import java.util.TreeMap;

// 自定义HashMap
public class TTHashMap<K,V> {

    Node[] table;   // 位桶数组，bucket array
    int size;       // 存放的键值对的个数

    public TTHashMap() {
        table = new Node[16]; // 长度一般定义成2的整数幂
    }


    public void put(K key, V value) {
        Node newNode = new Node();
        newNode.hash = hash(key);
        newNode.key = key;
        newNode.value = value;
        newNode.next = null;

        Node temp = table[newNode.hash];
        if (temp == null) {
            // 数组里每个链表中的第一个值
            table[newNode.hash] = newNode;
            size++;
        } else {
            while (temp != null) {
                if (temp.key.equals(key)) {
                    // key如果重复，则覆盖
                    temp.value = value;
                    break;
                } else {
                    // key不重复，则遍历下一个
                    if (temp.next == null) {
                        // 遍历到最后一个
                        temp.next = newNode;
                        size++;
                        break;
                    } else {
                        temp = temp.next;
                    }
                }
            }
        }
        // 如果要完善，还需要考虑数组扩容的问题
    }


    public V get(K key) {
        V value = null;
        int hash = hash(key);
        Node temp = table[hash];
        while (temp != null) {
            if (temp.key.equals(key)) {
                value = (V) temp.value;
                break;
            } else {
                temp = temp.next;
            }
        }
        return value;
    }


    public int hash(K key) {
        return key.hashCode() & (table.length - 1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < table.length; i++) {
            Node temp = table[i];
            while (temp != null) {
                sb.append(temp.key + ":" + temp.value + ",");
                temp = temp.next;
            }
        }
        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }


}
