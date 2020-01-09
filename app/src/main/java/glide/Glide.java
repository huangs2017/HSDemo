package glide;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class Glide {

    public static BitmapRequest with(FragmentActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        LifecycleFragment fragment = (LifecycleFragment) fm.findFragmentByTag("abc");
        if (fragment == null) {
            fragment = new LifecycleFragment();
            fm.beginTransaction().add(fragment, "abc").commitAllowingStateLoss();
            fm.executePendingTransactions();  // 加上这行会立即提交，否则new Fragment()会执行多次
        }
        return new BitmapRequest(activity);
    }

}

/*
    Glide：快速高效的Android图片加载框架，性能高，流式API;
    优点:
    1. 配置度高
    2. 支持多种数据源:本地，网络，assets，并且支持gif
    3. 支持memory和disk图片缓存
    4. BitmapPool复用Bitmap，支持高效处理Bitmap
    5. 图片加载过程可监听
    6. 支持生命周期


    分析LinkedHashMap中的LRU:
    一提到LRU，就应该想到LinkedHashMap。LRU是通过双向链表来实现的。
    当某个位置的数据被命中，通过调整该数据的位置，将其移动至尾部。新插入的元素也是直接放入尾部(尾插法)。
    这样一来，最近被命中的元素就向尾部移动，那么链表的头部就是最近最少使用的元素所在的位置。
    accessOrder-->true基于访问排序，false基于插入排序
    LinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder) {}

    HashMap，jdk1.8之前是头插法，在jdk1.8中是尾插法

*/

