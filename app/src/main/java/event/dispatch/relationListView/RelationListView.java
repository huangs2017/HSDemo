package event.dispatch.relationListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class RelationListView extends ListView {
    private RelationListView mListView;

    public RelationListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RelationListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setRelatedListView(RelationListView listView) {
        mListView = listView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (null != mListView) {
            mListView.onTouch(ev);
        }
        return super.onTouchEvent(ev);
    }

    public void onTouch(MotionEvent ev) {
        super.onTouchEvent(ev);
    }
}
