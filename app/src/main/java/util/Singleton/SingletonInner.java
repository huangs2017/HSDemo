package util.Singleton;

// 静态内部类实现方式（可延时加载）
public class SingletonInner {

    private static class SingletonHolder {
        private static SingletonInner instance = new SingletonInner();
    }

    private SingletonInner() {

    }

    public static SingletonInner getInstance() {
        return SingletonHolder.instance;
    }

}




