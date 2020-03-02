package component.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

// 最终的广播接收者，不需要在清单文件配置
public class FinalReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String content = getResultData();
        Toast.makeText(context, "报告习大大：" + content, Toast.LENGTH_SHORT).show();
    }

}
