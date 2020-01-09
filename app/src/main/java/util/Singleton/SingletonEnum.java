package util.Singleton;

// 使用枚举实现单例模式
public enum SingletonEnum {

    // 定义一个枚举元素，该元素天生为单例。
    INSTANCE {
        @Override
        void doSomething() {

        }
    };

    abstract void doSomething();

}

// 测试
//    public static void main(String[] args) {
//        SingletonEnum s1 = SingletonEnum.INSTANCE;
//        SingletonEnum s2 = SingletonEnum.INSTANCE;
//        System.out.println(s1 == s2);
//    }


/*
    优点:
        实现简单
        枚举本身就是单例模式。避免通过反射和反序列化的漏洞
    缺点:
        无延迟加载
*/