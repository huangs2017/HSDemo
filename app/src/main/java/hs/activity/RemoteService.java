package hs.activity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hunter.BinderTest;
import hunter.Book;
import hunter.Student;
import util.Log;

//进程间的通讯，可以采取Aidl，Messenger，ContentProvider，Broadcast等方式。
public class RemoteService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder(); ////////
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public class MyBinder extends BinderTest.Stub{
        @Override
        public int callAdd(int a, int b) throws RemoteException {
            return add(a, b);
        }

        @Override
        public List<Book> getBooks(Student student) throws RemoteException {
            Map<Student, List<Book>> map = new HashMap<>();

            Student student1 = new Student("张三", 21);
            List<Book> bookList1 = new ArrayList<>();
            bookList1.add(new Book("语文", 10));
            bookList1.add(new Book("数学", 20)) ;
            map.put(student1, bookList1);

            Student student2 = new Student("李四", 22);
            List<Book> bookList2 = new ArrayList<>();
            bookList2.add(new Book("物理", 30));
            bookList2.add(new Book("化学", 40));
            map.put(student2, bookList2);


            Log.i("--------" + student);
            Log.i("--------" + student1);
            Log.i("========" + map.get(student));
            Log.i("========" + map.get(student1));
            Log.i("~~~~~~~~" + student.equals(student1));

            return map.get(student2);
        }

        @Override
        public void getStudent(Student student) throws RemoteException {
            Log.i("服务器out： " + student);
            student.name = "王五";
            student.age = 23;
        }

        @Override
        public void getStudentWithInOutTag(Student student) throws RemoteException {
            Log.i("服务器inout： " + student);
            student.name = "小六";
            student.age = 24;
        }
    }

    public int add(int a, int b) {
        return a + b;
    }

}
