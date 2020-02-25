package 笔试题.Singleton;

public class Singleton1 {

    private static Singleton1 instance = new Singleton1();

    private Singleton1() {

    }

    public static Singleton1 getInstance() {
        return instance;
    }

}



/*

什么是单例:
单例类在整个程序中只能有一个实例，这个类负责创建自己的对象。

要点:
a) 私有构造器
b) 持有该类的属性
c) 对外提供获取实例的静态方法


●常见的单例实现方式
    一主要：
        饿汉式（调用效率高，不能延时加载）
        懒汉式（调用效率不高，可以延时加载）
    一其他：
        静态内部类式（调用效率高，可以延时加载）
        枚举式（调用效率高，不能延时加载。并且可以天然的防止反射和反序列化漏洞）
●如何选用？
    一不需要延时加载，单例对象占用资源少：
        枚举式好于饿汉式
    一需要延时加载，单例对象占用资源大：
        静态内部类式好于懒汉式
*/
