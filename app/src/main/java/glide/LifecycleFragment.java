package glide;

import android.content.Context;
import androidx.fragment.app.Fragment;
import glide.cache.DoubleLruCache;

public class LifecycleFragment extends Fragment {

    int activityCode;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityCode = context.hashCode();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        DoubleLruCache.getInstance().remove(activityCode);
    }

}
