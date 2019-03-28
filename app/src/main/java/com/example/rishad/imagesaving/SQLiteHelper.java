package com.example.rishad.imagesaving;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SQLiteHelper  extends SQLiteOpenHelper {


    private static final String DATABASE_NAME= "food.db";
    private static final String TABLE_NAME= "food_image";
    //Column Name
    private static final String ID= "id";
    private static final String NAME= "name";
    private static final String IMAGE= "image";
    private static final String PRICE= "price";
    private static final String SHORT_NAME= "short_name";
    private static final int VERSION_NUMBER= 4;
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+NAME+" VARCHAR(255)," +
            " "+PRICE+" VARCHAR(255), " +
            ""+IMAGE+" BLOB ," +
            ""+SHORT_NAME+" VARCHAR(255));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

    private Context context;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            Toast.makeText(context,"onCreate is Called",Toast.LENGTH_SHORT).show();
            db.execSQL(CREATE_TABLE);
        }catch (Exception e){
            Toast.makeText(context,"Exception : "+e,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (Exception e){

        }
    }

    public long insertData(InfoDetails infoDetails){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(NAME,infoDetails.getName());
        contentValues.put(PRICE, infoDetails.getPrice());
        contentValues.put(IMAGE, infoDetails.getImage());
        contentValues.put(SHORT_NAME, infoDetails.getShortName());

      long rowId=  sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
      return rowId;
    }

    public Cursor showAllData(){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursor;
    }
}
