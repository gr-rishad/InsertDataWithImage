package com.example.rishad.imagesaving;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameEditText,priceEditText,shortEditText;
    private Button btnAdd, btnChoose,btnShow;
    ImageView imageView;
    final int REQUEST_CODE_GALLERY = 999;
    InfoDetails  infoDetails ;

    SQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteHelper = new SQLiteHelper(this);
        SQLiteDatabase sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        nameEditText = (EditText) findViewById(R.id.nameEditTextId);
        priceEditText= (EditText) findViewById(R.id.priceEditTextId);
        shortEditText=(EditText) findViewById(R.id.shortNameEditTextId);
        btnAdd= (Button) findViewById(R.id.buttonAddId);
        btnChoose = (Button) findViewById(R.id.buttonChooseId);
        btnShow = (Button) findViewById(R.id.buttonShowId);
        imageView = (ImageView) findViewById(R.id.imageViewId);

        btnChoose.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnShow.setOnClickListener(this);

        //object create in info details;
     infoDetails = new InfoDetails();
    }

    @Override
    public void onClick(View v) {

        String name = nameEditText.getText().toString().trim();
        String price = priceEditText.getText().toString().trim();
        String shortName = shortEditText.getText().toString().trim();
        byte[] picture = imageViewToByte(imageView);


        if(v.getId()==R.id.buttonChooseId){
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_GALLERY);
        }else if(v.getId()== R.id.buttonAddId){

            infoDetails.setName(name);
            infoDetails.setPrice(price);
            infoDetails.setShortName(shortName);
            infoDetails.setImage(picture);
            try {
                long rowId=  sqLiteHelper.insertData(infoDetails);
                if(rowId==-1){
                    Toast.makeText(getApplicationContext(),"Unsuccessfull",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Data is Successfully Inserted.",Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Exception is : "+e,Toast.LENGTH_SHORT).show();
            }
        }else if(v.getId()== R.id.buttonShowId){

            Toast.makeText(getApplicationContext(),"Hello Show",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,ListDataActivity.class);
            startActivity(intent);
        }
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }




    public String getPath(Uri uri){
        String[] filePathColumn={MediaStore.Images.Media.DATA};

        Cursor cursor=getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex=cursor.getColumnIndex(filePathColumn[0]);

        return cursor.getString(columnIndex);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }else {
                Toast.makeText(getApplicationContext(),"You don't have permission to access file location!",Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            //String path=getPath(uri);
            //Toast.makeText(getApplicationContext(),"Path is : "+path,Toast.LENGTH_SHORT).show();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
