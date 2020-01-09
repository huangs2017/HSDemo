package JavaDemo.TTArrayList;

// 自定义ArrayList
public class TTArrayList<E> {

    E[] elementData;
    int size;

    public TTArrayList() {
        elementData = (E[]) new Object[10];
    }

    public TTArrayList(int capacity) {
        elementData = (E[]) new Object[capacity];
    }

    public void add(E e) {
        if (size == elementData.length) { // 扩容
            int newLength = elementData.length + (elementData.length>>1); // 10-->10+10/2
            E[] newArray = (E[]) new Object[newLength];
//            arraycopy(Object src, int srcPos, Object dest, int destPos, int length);
            System.arraycopy(elementData, 0, newArray, 0, elementData.length);
            elementData = newArray;
        }
        elementData[size++] = e;
    }

    public E get(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
        return elementData[index];
    }

    public E set(int index, E e) {
        if (index >= size)
            throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
        E oldValue = (E) elementData[index];
        elementData[index] = e;
        return oldValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i=0; i<size; i++) {
            sb.append(elementData[i] + ",");
        }
        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }

}
