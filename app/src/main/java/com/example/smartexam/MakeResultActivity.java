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
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartexam.Adapters.CroppedImageAdapter;
import com.example.smartexam.Adapters.ResultAdapter;
import com.example.smartexam.Model.AnswerModel;
import com.example.smartexam.Model.AnswerSetModel;
import com.example.smartexam.Model.Option_Image;
import com.example.smartexam.Model.ResultModel;
import com.example.smartexam.Model.ResultSetModel;
import com.example.smartexam.ml.CheckMarkDetectorModel;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class MakeResultActivity extends AppCompatActivity {
    Button selectImage,addPapper,save,saveAll;
    EditText roll,examName,skip;
    ImageView mainImage;
    RecyclerView result;
    Bitmap bitmap,temp;
    ArrayList<Option_Image> image_list;
    TextView errorMsg;
    boolean isLoded,sop;
    MyDatabaseHelper databaseHelper;
    AnswerSetModel answerSetModel;
    int minResultId=Integer.MAX_VALUE,maxResultId=0;
    int questionNumber;
    ResultAdapter resultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_result);

        mainImage = findViewById(R.id.iv_main_image);
        selectImage = findViewById(R.id.btn_select);
        addPapper = findViewById(R.id.btn_add_paper);
        save = findViewById(R.id.btn_save);
        saveAll = findViewById(R.id.btn_finish_result);
        roll = findViewById(R.id.et_roll);
        examName = findViewById(R.id.et_exam);
        result = findViewById(R.id.rv_result);
        skip = findViewById(R.id.et_skip);
        errorMsg = findViewById(R.id.tv_result_error);
        answerSetModel = null;
        resultAdapter = new ResultAdapter(MakeResultActivity.this,new ArrayList<>());

        databaseHelper = new MyDatabaseHelper(MakeResultActivity.this);

        image_list = new ArrayList<>();

        mainImage.setImageDrawable(getResources().getDrawable(R.drawable.place_holder));

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

                                int initial = 90;
                                try{
                                    initial = Integer.parseInt(skip.getText().toString());
                                }catch (Exception e){
                                    Toast.makeText(MakeResultActivity.this,
                                            "Skip value must be an Integer.", Toast.LENGTH_SHORT).show();
                                }
                                int questionLimit = Integer.min(6,(answerSetModel.getMaxId()-
                                        answerSetModel.getMinId()+1)-questionNumber);
                                for(int i=0;i<questionLimit;i++){
                                    for(int j=0;j<4;j++){
                                        temp = Bitmap.createBitmap(bitmap, 20, initial, 60, 25);
                                        String snum = "Q "+String.valueOf(i+1+questionNumber)+" Opt "+String.valueOf(j+1);
                                        image_list.add( new Option_Image(snum,temp,predict(temp)));
                                        initial += 22;
                                    }
                                    initial += 20;
                                }questionNumber += questionLimit;
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
                if(answerSetModel == null){
                    if(examName.getText().toString().equals("")){
                        errorMsg.setVisibility(View.VISIBLE);
                        errorMsg.setText("Write a exam name, which already set on the answer. Or first set the answer.");
                        return;
                    }
                    if(databaseHelper.isExist(examName.getText().toString())){
                        answerSetModel = databaseHelper.getAnswerSet(examName.getText().toString());
                        errorMsg.setVisibility(View.GONE);
                    }else{
                        errorMsg.setVisibility(View.VISIBLE);
                        errorMsg.setText("This exam name does not contain answer. First set the answer for this exam.");
                        return;
                    }
                }else{
                    errorMsg.setVisibility(View.GONE);
                }
                questionNumber = 0;
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

        addPapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(roll.getText().toString().equals("")){
                    errorMsg.setVisibility(View.VISIBLE);
                    errorMsg.setText("Write Roll Number on Roll section.");
                    return;
                }else{
                    errorMsg.setVisibility(View.GONE);
                }
                int QN = 1;
                int min_ans_ind=Integer.MAX_VALUE,max_ans_ind=0;
                for(int i=0;(i+3)<image_list.size();i+=4){
                    boolean pa,pb,pc,pd;
                    pa = binaryPredict(image_list.get(i).getBitmap());
                    pb = binaryPredict(image_list.get(i+1).getBitmap());
                    pc = binaryPredict(image_list.get(i+2).getBitmap());
                    pd = binaryPredict(image_list.get(i+3).getBitmap());

                    String res="";
                    if(pa){
                        res +="A";
                    }
                    if(pb){
                        res +="B";
                    }
                    if(pc){
                        res +="C";
                    }
                    if(pd){
                        res +="D";
                    }
                    AnswerModel answerModel = new AnswerModel(0,String.valueOf(new Date().getTime()),
                            QN,res);
                    int id = databaseHelper.insertAnswer(answerModel);
                    min_ans_ind = Integer.min(min_ans_ind,id);
                    max_ans_ind = Integer.max(max_ans_ind,id);
                }
                ArrayList<AnswerModel> currentlist = databaseHelper.getAnswer(min_ans_ind,max_ans_ind);
                ArrayList<AnswerModel> solutionlist = databaseHelper.getAnswer(answerSetModel.getMinId(),answerSetModel.getMaxId());
                int count = 0;
                for(int i=0;i<currentlist.size();i++){
                    if(currentlist.get(i).getOption().equals(solutionlist.get(i).getOption())){
                        count++;
                    }
                }
                ResultModel resultModel = new ResultModel(0,String.valueOf(new Date().getTime()),
                        Integer.parseInt(roll.getText().toString()),min_ans_ind,max_ans_ind,count);
                int rid = databaseHelper.insertResult(resultModel);
                minResultId = Integer.min(minResultId,rid);
                maxResultId = Integer.max(maxResultId,rid);
                roll.setText(String.valueOf(resultModel.getRoll()+1));
                resultAdapter.add(resultModel);
                result.setAdapter(resultAdapter);
                mainImage.setImageDrawable(getResources().getDrawable(R.drawable.place_holder));
            }
        });

        saveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.insertResultSet(new ResultSetModel(examName.getText().
                        toString()+String.valueOf(new Date().getTime()), minResultId, maxResultId));
                finish();
            }
        });
    }

    private String predict(Bitmap bitmapPred){
        String pred="E";

        try {
            CheckMarkDetectorModel model = CheckMarkDetectorModel.newInstance(MakeResultActivity.this);

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 25, 60, 3}, DataType.FLOAT32);

            Log.d("----shape",bitmapPred.getHeight()+" "+bitmapPred.getWidth());
            Log.d("----shape",inputFeature0.getBuffer().toString());
            TensorImage ti = new TensorImage(DataType.FLOAT32);
            ti.load(bitmapPred);

            Log.d("----shape",ti.getDataType()+" D");
            inputFeature0.loadBuffer(ti.getBuffer());

            // Runs model inference and gets result.
            CheckMarkDetectorModel.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            pred = "P "+String.valueOf(outputFeature0.getFloatArray()[0])+" "+String.valueOf(outputFeature0.getFloatArray()[1]);

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
        return pred;
    }

    private boolean binaryPredict(Bitmap bitmapPred){
        boolean res = false;
        try {
            CheckMarkDetectorModel model = CheckMarkDetectorModel.newInstance(MakeResultActivity.this);

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 25, 60, 3}, DataType.FLOAT32);
            TensorImage ti = new TensorImage(DataType.FLOAT32);
            ti.load(bitmapPred);

            inputFeature0.loadBuffer(ti.getBuffer());

            // Runs model inference and gets result.
            CheckMarkDetectorModel.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            if(outputFeature0.getFloatArray()[0] < outputFeature0.getFloatArray()[1]){
                res = true;
            }
            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
        return res;
    }

}