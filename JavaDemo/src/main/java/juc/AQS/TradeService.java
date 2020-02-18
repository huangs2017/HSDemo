package juc.AQS;

import Util.Log;

public class TradeService {

    MyLock lock = new MyLock();
    private static Integer stock = 5;    //商品库存

    //减库存
    public String decStock() {
        lock.lock();
        if (stock <= 0) {
            Log.i("下单失败，没有库存了");
            lock.lock();
            return "下单失败，没有库存了";
        }
        stock--;
        Log.i("下单成功，当前剩余产品数: " + stock);
        lock.lock();
        return "下单成功，当前剩余产品数: " + stock;
    }

}
