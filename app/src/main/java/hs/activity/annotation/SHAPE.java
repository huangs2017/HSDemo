package hs.activity.annotation;

public class SHAPE {

    public static final int RECTANGLE = 0;
    public static final int TRIANGLE = 1;
    public static final int SQUARE = 2;
    public static final int CIRCLE = 3;

    private @Model
    int value = RECTANGLE;

    public void setShape(@Model int value) {
        this.value = value;
    }

    @Model
    public int getShape() {
        return this.value;
    }

}


/*
    内存优化，枚举优化
    每一个枚举值都是一个单例对象，使用它时会增加额外的内存消耗


    public enum SHAPE {
        RECTANGLE,
        TRIANGLE,
        SQUARE,
        CIRCLE
    }
*/


