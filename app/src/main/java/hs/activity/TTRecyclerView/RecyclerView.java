package hs.activity.TTRecyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import hs.activity.R;

public class RecyclerView extends ViewGroup {

    // 最小滑动距离(通过此值判断滑动事件是否传递到子View)
    private int touchSlop;

    // 当前显示的View集合(无论后续上划，下划，最终展示使用的几个View集合)
    private List<View> viewList;

    // 初始化  第一屏最慢
    private boolean needRelayout;
    private int scrollY;      // y偏移量
    private int[] heights;    // item 高度之和
    private int width;
    private int height;
    private int rowCount;    // 行数
    private int firstRow;    // view的第一行  是占内容的几行

    private Adapter adapter;
    Recycler recycler;

    public RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.touchSlop = configuration.getScaledTouchSlop();
        this.viewList = new ArrayList<>();
        this.needRelayout = true;
    }

    /**
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int h;
        if (adapter != null) {
            this.rowCount = adapter.getCount();
            heights = new int[rowCount];
            for (int i = 0; i < heights.length; i++) {
                heights[i] = adapter.getHeight(i);
            }
        }
        // 数据的高度(假设所有数据都滑动展示时，所有子ItemView高度之和)
        int tmpH = sumArray(heights, 0, heights.length);
        h = Math.min(heightSize, tmpH);

        // 设置RecycleView的最终高度
        // 在onMeasure方法中最终调用 setMeasuredDimension 方法来确定控件的大小
        setMeasuredDimension(widthSize, h);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    // 初始化
    @Override
    protected void onLayout(boolean changed, int mLeft, int mTop, int mRight, int mBottom) {
        if (needRelayout || changed) {
            needRelayout = false;
            viewList.clear();
            removeAllViews();
            if (adapter != null) {
                // 摆放所有RecycleView的Item的实际高度和宽度
                width = mRight - mLeft;
                height = mBottom - mTop;
                int top = 0, bottom;
                for (int i = 0; i < rowCount && top < height; i++) {
                    bottom = top + heights[i];
                    // 生成一个View
                    View view = makeAndStep(i, 0, top, width, bottom);
                    viewList.add(view);
                    top = bottom; // 循环摆放需要展示的几个Item View
                }
            }
        }
    }

    /**
     * @param row
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    private View makeAndStep(int row, int left, int top, int right, int bottom) {
        View view = obtainView(row, right - left, bottom - top);
        view.layout(left, top, right, bottom);
        return view;
    }

    private View obtainView(int row, int width, int height) {
        int itemType = adapter.getItemViewType(row);
        // 通过指定 position 位置的View类型去回收池查找View，存在则复用，不存在则调用 onCreateViewHolder 创建
        View recycleView = recycler.get(itemType);
        View view;
        if (recycleView == null) {
            view = adapter.onCreateViewHolder(row, recycleView, this);
            if (view == null) {
                throw new RuntimeException("onCreateViewHolder  必须填充布局");
            }
        } else {
            view = adapter.onBinderViewHolder(row, recycleView, this);
        }
        view.setTag(R.id.tag_type_view, itemType);
        view.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        addView(view, 0);
        return view;
    }

    // -------------------------------------------------------------------------

    // 当前滑动的y值
    private int currentY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                currentY = (int) event.getRawY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                // 当手指在屏幕上滑动距离大于 touchSlop 最小滑动距离时，则继承ViewGroup的 RecyclerView 拦截事件
                int y2 = Math.abs(currentY - (int) event.getRawY());
                if (y2 > touchSlop) {
                    intercept = true;
                }
            }
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                int y2 = (int) event.getRawY(); // 移动的距离，y方向
                int diffY = currentY - y2;      // 大于0表示上滑，小于0表示下滑
                scrollBy(0, diffY);             // 画布移动，并不影响子控件的位置
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void scrollBy(int x, int y) {
        // scrollY表示 第一个可见Item的左上顶点 距离屏幕的左上顶点的距离
        scrollY += y;
        scrollY = scrollBounds(scrollY);
        // scrollY
        if (scrollY > 0) {
            // 上滑正  下滑负  边界值
            while (scrollY > heights[firstRow]) {
                // 1上滑移除  2上划加载  3下滑移除  4下滑加载
                removeView(viewList.remove(0));
                scrollY -= heights[firstRow];
                firstRow++;
            }
            // 上滑添加
            while (getFillHeight() < height) {
                int addLast = firstRow + viewList.size();
                View view = obtainView(addLast, width, heights[addLast]);
                viewList.add(viewList.size(), view);
            }
        } else if (scrollY < 0) {
            // 下滑加载
            while (scrollY < 0) {
                int firstAddRow = firstRow - 1;
                View view = obtainView(firstAddRow, width, heights[firstAddRow]);
                viewList.add(0, view);
                firstRow--;
                scrollY += heights[firstRow + 1];
            }
            // 下滑移除
            while (sumArray(heights, firstRow, viewList.size()) - scrollY - heights[firstRow + viewList.size() - 1] >= height) {
                removeView(viewList.remove(viewList.size() - 1));
            }
        }
        repositionViews();
    }

    private int scrollBounds(int scrollY) {
        // 上滑
        if (scrollY > 0) {
        } else {
            // 极限值  会取零  非极限值的情况下   scroll
            scrollY = Math.max(scrollY, -sumArray(heights, 0, firstRow));
        }
        return scrollY;
    }

    /**
     * @return 数据的高度 -scrollY
     */
    private int getFillHeight() {
        return sumArray(heights, firstRow, viewList.size()) - scrollY;
    }

    /**
     * @param array
     * @param firstIndex
     * @param count
     * @return 计算所有组合Item的总高度 与 RecycleView高度对比
     */
    private int sumArray(int array[], int firstIndex, int count) {
        int sum = 0;
        count += firstIndex;
        for (int i = firstIndex; i < count; i++) {
            sum += array[i];
        }
        return sum;
    }

    private void repositionViews() {
        int top, bottom, i;
        top = -scrollY;
        i = firstRow;
        for (View view : viewList) {
            bottom = top + heights[i++];
            view.layout(0, top, width, bottom);
            top = bottom;
        }
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);
        int key = (int) view.getTag(R.id.tag_type_view);
        recycler.put(view, key);
    }

    // 1-------------------------------------------------------------------------
    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        if (adapter != null) {
            // 保存复用RecycleView的Item回收池
            recycler = new Recycler(adapter.getViewTypeCount());
            scrollY = 0;
            firstRow = 0;
            needRelayout = true;
            requestLayout(); // 1 onMeasure   2 onLayout
        }
    }

    interface Adapter {
        View onCreateViewHolder(int position, View convertView, ViewGroup parent);
        View onBinderViewHolder(int position, View convertView, ViewGroup parent);
        int getItemViewType(int row);   // Item的类型
        int getViewTypeCount();         // Item的类型数量
        int getCount();
        int getHeight(int index);
    }
    // 1-------------------------------------------------------------------------

}

