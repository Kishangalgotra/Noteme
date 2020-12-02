package com.account.newnotes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;
import java.util.Arrays;
import java.util.HashSet;
import maes.tech.intentanim.CustomIntent;

public class NOTEActivity extends AppCompatActivity {
        TextView Notedatatext ;
        TextView title;
        private int data;
        private String stringdata = "";
        //private int i;
     @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_o_t_e);
        Notedatatext = findViewById(R.id.Notedatatext);
        title= findViewById(R.id.title);
        Intent intent = getIntent();

        //FETCHING INDEX OF TITLE
        data = intent.getIntExtra("ListData", -1);

        //FOR NEW NOTE
        if(data == -1) {
            stringdata = intent.getStringExtra("TITLE");

            title.setText(stringdata);
            MainActivity.array.add("");
            data = MainActivity.array.size() - 1;
            MainActivity.arrayTITLE.add(stringdata);
            HashSet<String> hashTitle = new HashSet(MainActivity.arrayTITLE);
            MainActivity.Obj.edit().putStringSet("TITLEDATA",hashTitle).apply();
        }

        //FOR OLDER NOTE      FACING ISSUE HERE
        if(data != -1){
            String arraydat = MainActivity.array.get(data);
            Log.i("Title array",MainActivity.arrayTITLE.toString());
            String titlearray = MainActivity.arrayTITLE.toString();
            Log.i("ArrayTitile",MainActivity.arrayTITLE.get(data));
            title  = findViewById(R.id.title);
            title.setText(MainActivity.arrayTITLE.get(data));
            Notedatatext.setText(MainActivity.array.get(data));
            //str += MainActivity.array.get(data);
         }

         // CODE FOR ADDING NOTE TO ARRAY WHILE MAKING NOTES
         Notedatatext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.array.set(data, String.valueOf(s));
                MainActivity.adaptor.notifyDataSetChanged();
                System.out.print(MainActivity.array.toString());
                Log.i("Mesaage",Arrays.toString(MainActivity.array.toArray()));
                Log.i("Mesaage",MainActivity.array.get(data));
                HashSet<String> hash = new HashSet(MainActivity.array);
                MainActivity.Obj.edit().putStringSet("notes",hash).apply();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void finish() {
        // MainActivity.array.add(Notedatatext.get);
        super.finish();
        CustomIntent.customType(NOTEActivity.this,"right-to-left");
    }
}