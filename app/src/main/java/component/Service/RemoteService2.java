package component.Service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import component.Broadcast.FinalReceiver;

public class RemoteService2 extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        FinalReceiver myBroadcastReceiver = new FinalReceiver();
        registerReceiver(myBroadcastReceiver, intentFilter);

        Intent intent = new Intent("android.net.conn.CONNECTIVITY_CHANGE");
        sendBroadcast(intent); // 发送广播

        unregisterReceiver(myBroadcastReceiver);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    private Messenger messenger = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message clientMsg) {
            Message replyMsg = Message.obtain();
            replyMsg.arg1 = clientMsg.arg1 + clientMsg.arg2; // 返回给客户端的消息
            try {
                clientMsg.replyTo.send(replyMsg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    });

}
