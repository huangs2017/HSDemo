package util;


public class Log {

	public static void i(String msg) {
		android.util.Log.i("Hunter", msg);
	}

	public static void e(String msg) {
		android.util.Log.e("Hunter", msg);
	}

}
