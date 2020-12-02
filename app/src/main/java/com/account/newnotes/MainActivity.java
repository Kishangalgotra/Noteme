package com.account.newnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {
    ListView listview;
    static ArrayAdapter adaptor;
    static ArrayList<String> array;
    static ArrayList<String> arrayTITLE;
    static SharedPreferences Obj;

    //MENU ATTACHMENT
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menu1 = getMenuInflater();
        menu1.inflate(R.menu.menu_file,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //FOR ADDING NEW NOTE
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
       if(item.getItemId() == R.id.ADD) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("ADD");
            final EditText editText = new EditText(this);
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            alert.setView(editText);
            alert.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (editText.getText().toString().trim().length() != 0) {
                                Intent intent1 = new Intent(getApplicationContext(), NOTEActivity.class);
                                intent1.putExtra("TITLE", editText.getText().toString());
                                startActivity(intent1);
                                CustomIntent.customType(MainActivity.this, "left-to-right");
                    }
                }
            });
             alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.show();
          }
         return true;
    }

    //STARTING OF APPLICATION
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = findViewById(R.id.ListView);
        Obj = this.getSharedPreferences("com.account.newnotes", Context.MODE_PRIVATE);

        //FETCHING NOTES DATA
        HashSet<String> hash = (HashSet<String>) Obj.getStringSet("notes",new HashSet<String>());
        array = new ArrayList(hash);

        //FETCHING NOTES TITLES AND POPULATING IN LISTVIEW
            HashSet<String> hashTITLE = (HashSet<String>) Obj.getStringSet("TITLEDATA",new HashSet<String>());
            arrayTITLE = new ArrayList(hashTITLE);
            adaptor = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayTITLE);
            listview.setAdapter(adaptor);

        //CODE FOR LISTVIEW ITEM SELECTION
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this,NOTEActivity.class);
                    intent.putExtra("ListData",position);
                    startActivity(intent);
                    CustomIntent.customType(MainActivity.this,"left-to-right");
                }
            });

        //FOR DELETING DATA FROM LIST AND ARRAY OF TITLE AND NOTE DATA
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               final int index = position;
                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("REALLY")
                        .setMessage("ARE You sure You want to delete this ?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try{
                                   array.remove(index);
                                   arrayTITLE.remove((index));
                                   adaptor.notifyDataSetChanged();}
                               catch(Exception e){
                                   Log.i("Error while deleting", "ERROR");
                               }
                               //FOR TITLE ARRAY
                                HashSet<String> Titlehash = new HashSet(MainActivity.arrayTITLE);
                                MainActivity.Obj.edit().putStringSet("TITLEDATA",Titlehash).apply();
                               //FOR NOTE DATA ARRAY
                                HashSet<String> notehash = new HashSet(MainActivity.array);
                                MainActivity.Obj.edit().putStringSet("notes",notehash).apply();
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                return true;
            }
        });
    }
}