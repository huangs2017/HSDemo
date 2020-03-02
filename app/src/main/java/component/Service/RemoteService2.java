package component.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class RemoteService2 extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
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
