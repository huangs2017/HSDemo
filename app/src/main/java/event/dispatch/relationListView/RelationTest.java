package event.dispatch.relationListView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import hs.activity.R;

// 联动的ListView
public class RelationTest extends Activity {
	private RelationListView mListView1;
	private RelationListView mListView2;
	
	private String[] mData1 = new String[] { "lv1", "lv1", "lv1", "lv1", "lv1", "lv1", "lv1", "lv1", "lv1",
			"lv1", "lv1", "lv1", "lv1", "lv1", "lv1", "lv1", "lv1", "lv1", "lv1", "lv1", "lv1", "lv1"};
	private String[] mData2 = new String[] { "lv2", "lv2", "lv2", "lv2", "List2", "lv2", "lv2", "lv2", "lv2",
			"lv2", "lv2", "lv2", "lv2", "lv2", "lv2", "lv2", "lv2", "lv2", "lv2", "lv2", "lv2", "lv2",};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relation_test);
		
		mListView1 = findViewById(R.id.listView1);
		mListView2 = findViewById(R.id.listView2);
		
		mListView1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mData1));
		mListView2.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mData2));
		mListView1.setRelatedListView(mListView2);
//		mListView2.setRelatedListView(mListView1);
	}
}
