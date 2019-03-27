package com.example.rishad.imagesaving;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {

    ListView listView;
    private SQLiteHelper listSqLiteHelper;
    InfoDetails infoDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        listView = (ListView) findViewById(R.id.listViewId);
        listSqLiteHelper = new SQLiteHelper(this);
        infoDetails= new InfoDetails();

    }

    public void loadData(){

        ArrayList<String> listData = new ArrayList<>();
        Cursor cursor= listSqLiteHelper.showAllData();

        if(cursor.getCount()== 0){
            Toast.makeText(getApplicationContext(),"No Database is avilable in database",Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                listData.add(cursor.getString(1));
            }
        }


    }
}
