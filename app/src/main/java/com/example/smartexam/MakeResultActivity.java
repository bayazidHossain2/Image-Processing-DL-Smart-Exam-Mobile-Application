package com.example.smartexam;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smartexam.Adapters.AnswerAdapter;
import com.example.smartexam.Adapters.CroppedImageAdapter;
import com.example.smartexam.Model.Option_Image;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.ArrayList;

public class MakeResultActivity extends AppCompatActivity {
    Button selectImage,addPapper,save;
    EditText roll,skip;
    ImageView mainImage;
    RecyclerView result;
    Bitmap bitmap,temp;
    ArrayList<Option_Image> image_list;
    boolean isLoded,sop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_result);

        mainImage = findViewById(R.id.iv_main_image);
        selectImage = findViewById(R.id.btn_select);
        addPapper = findViewById(R.id.btn_add_paper);
        save = findViewById(R.id.btn_save_all);
        roll = findViewById(R.id.et_roll);
        skip = findViewById(R.id.et_skip);
        result = findViewById(R.id.rv_result);

        image_list = new ArrayList<>();

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Uri uri = (result.getData()).getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap((MakeResultActivity.this).getContentResolver(), uri);
                                bitmap = Bitmap.createScaledBitmap(bitmap, 625, 810, true);
                                mainImage.setImageBitmap(bitmap);

                                int initial = Integer.parseInt(skip.getText().toString());

                                for(int i=0;i<5;i++){
                                    for(int j=0;j<4;j++){
                                        temp = Bitmap.createBitmap(bitmap, 20, initial, 60, 25);
                                        String snum = "Q "+String.valueOf(i+1)+" Opt "+String.valueOf(j+1);
                                        image_list.add( new Option_Image(snum,temp));
                                        initial += 22;
                                    }
                                    initial += 20;
                                }
                                isLoded = true;
                                Log.d("abc","Image Opening success --------------- ");
                            }catch (IOException e){
                                Log.d("abc","Image Opening Error : "+e.getMessage());
                            }
                        }
                    }
                });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_list.clear();
                isLoded = false;
                sop = false;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(!sop){
                            if(isLoded){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CroppedImageAdapter imageAdapter = new CroppedImageAdapter(MakeResultActivity.this,image_list);
                                        result.setAdapter(imageAdapter);
                                        LinearLayoutManager layoutManager = new LinearLayoutManager(MakeResultActivity.this);
                                        result.setLayoutManager(layoutManager);
                                    }
                                });
                                sop = true;
                            }
                            Log.d("abc","thread running------");
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                someActivityResultLauncher.launch(intent);
            }
        });


    }
}