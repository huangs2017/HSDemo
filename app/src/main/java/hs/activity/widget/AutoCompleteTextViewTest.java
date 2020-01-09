package hs.activity.widget;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import hs.activity.R;

public class AutoCompleteTextViewTest extends AppCompatActivity {

    AutoCompleteTextView actv;
    MultiAutoCompleteTextView mauto;
    String[] books = new String[]{"aaJava讲义", "aAjax讲义", "acXML讲义", "ddWorkflow讲义"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocomplete_main);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, books);
        actv = findViewById(R.id.auto);
        actv.setAdapter(aa);
        mauto = findViewById(R.id.mauto);
        mauto.setAdapter(aa);
        mauto.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}