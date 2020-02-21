package hs.activity;

import android.content.Context;
import android.util.ArrayMap;
import android.util.SparseArray;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import util.Log;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("hs.activity", appContext.getPackageName());
    }


    @Test
    public void myTest() {
        SparseArray array = new SparseArray();
        SparseArray<Object> sparseArray = new SparseArray<>();
        array.append(1, "a");
        array.append(2, "b");
        array.put(2, "d");
        Log.i(array);

        ArrayMap map = new ArrayMap();
//        map.put()
//        public V put(K key, V value) {
    }

}
