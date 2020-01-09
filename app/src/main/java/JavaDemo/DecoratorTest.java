package JavaDemo;

/*
    1. 抽象组件: 被装饰的抽象对象(接口或抽象父类)
    2. 具体组件:
    3. 抽象装饰类: 持有抽象组件的引用,含有装饰类共有的方法
    4. 具体装饰类:
*/
public class DecoratorTest {
    static Drink sugar;
    static Drink milk;
    public static void main(String[] args) {
        Drink coffee = new Coffee();
        sugar = new Sugar(coffee);
        System.out.println(sugar.info() + "-->" + sugar.cost());
        milk = new Milk(sugar);
        System.out.println(milk.info() + "-->" + milk.cost());
    }
}


//抽象组件
interface Drink {
    String info();
    double cost();
}


//具体组件
class Coffee implements Drink {

    @Override
    public String info() {
        return "原味咖啡";
    }

    @Override
    public double cost() {
        return 10;
    }
}


//抽象装饰类
abstract class Decorate implements Drink {
    //对抽象组件的引用
    private Drink drink;

    public Decorate(Drink drink) {
        this.drink = drink;
    }

    @Override
    public String info() {
        return drink.info();
    }

    @Override
    public double cost() {
        return drink.cost();
    }
}


//具体装饰类1
class Sugar extends Decorate {

    public Sugar(Drink drink) {
        super(drink);
    }

    @Override
    public String info() {
        return super.info() + ":加糖";
    }

    @Override
    public double cost() {
        return super.cost() * 2;
    }
}


//具体装饰类2
class Milk extends Decorate {

    public Milk(Drink drink) {
        super(drink);
    }

    @Override
    public String info() {
        return super.info() + ":加牛奶";
    }

    @Override
    public double cost() {
        return super.cost() * 4;
    }
}