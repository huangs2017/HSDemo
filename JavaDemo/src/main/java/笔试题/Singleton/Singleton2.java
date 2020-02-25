package 笔试题.Singleton;

// 懒汉式 + 双重检测锁
public class Singleton2 {

    private static volatile Singleton2 instance; // 加volatile对instance的所有操作不会指令重排

    private Singleton2() {

    }

    public static Singleton2 getInstance() {
        if (instance == null) {
            synchronized (Singleton2.class) {
                if (instance == null) {
                    instance = new Singleton2(); // 可能指令重排
                }
            }
        }
        return instance;
    }

}

/*
    instance = new Singleton2()会执行如下操作:
    (1) 分配对象内存空间
    (2) 初始化对象
    (3) instance指向(1)中分配的空间

    在某些编译器上，可能出现指令重排:
    (1) 分配对象内存空间
    (2) instance指向(1)中分配的空间(但此时对象没有初始化)
    (3) 初始化对象
*/



