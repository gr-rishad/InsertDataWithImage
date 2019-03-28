package com.example.rishad.imagesaving;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {

    ListView listView;
    private SQLiteHelper listSqLiteHelper;
    ArrayList<InfoDetails> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        listView = (ListView) findViewById(R.id.listViewId);
        listSqLiteHelper = new SQLiteHelper(this);

        loadData();
    }

    public void loadData(){

        listData = new ArrayList<>();
        Cursor cursor= listSqLiteHelper.showAllData();

        if(cursor.getCount()== 0){
            Toast.makeText(getApplicationContext(),"No Database is avilable in database",Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){

                            String name = cursor.getString(1);
                            String price = cursor.getString(2);
                            byte[] image = cursor.getBlob(3);
                            String shortName = cursor.getString(4);

                            listData.add(new InfoDetails(name,price,image,shortName));
            }

            CustomAdapter adapter = new CustomAdapter(this,R.layout.sample_view,listData);
            listView.setAdapter(adapter);
        }

    }
}
