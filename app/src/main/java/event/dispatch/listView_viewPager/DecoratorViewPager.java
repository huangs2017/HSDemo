package event.dispatch.listView_viewPager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

/**
 * ViewPager ListView 相互冲突 将父view ViewPager 里面
 * 
 * 使用父类的方法 parent.requestDisallowInterceptTouchEvent(true);
 * 
 * 当 requestDisallowInterceptTouchEvent 为true的时候，表示父view不拦截子view的touch事件
 * 
 * 这个方法只是改变flag
 */
public class DecoratorViewPager extends ViewPager {
	private ViewGroup parent;

	public DecoratorViewPager(Context context) {
		super(context);
	}

	public DecoratorViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setNestedParent(ViewGroup parent) {
		this.parent = parent;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (parent != null) {
			parent.requestDisallowInterceptTouchEvent(true);
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (parent != null) {
			parent.requestDisallowInterceptTouchEvent(true);
		}
		return super.onInterceptTouchEvent(arg0);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (parent != null) {
			parent.requestDisallowInterceptTouchEvent(true);
		}
		return super.onTouchEvent(arg0);
	}

}
