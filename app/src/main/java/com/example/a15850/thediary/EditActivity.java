package com.example.a15850.thediary;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a15850.thediary.database.Diary;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.Calendar;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;


public class EditActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextBody;
    String inputTextTitle;
    String inputTextBody;
    String diaryNum;//日记篇数
    private String [] fromHome=null;//来自Home的消息


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setElevation(0);

        //TickerView点击显示当前日记篇数
        //获取日记篇数传入diaryNum
        //diaryNum="13";//测试用
        final TickerView tickerView = findViewById(R.id.day_tickerView);
        tickerView.setCharacterLists(TickerUtils.provideNumberList());
//        tickerView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                tickerView.setText(diaryNum);
//            }
//        });

        //日记篇数查询显示
        BmobUser a_bmobUser=BmobUser.getCurrentUser(EditActivity.this);
        if(a_bmobUser!=null) {
            if (a_bmobUser.getObjectByKey(EditActivity.this, "diaryNum") != null) {
                diaryNum = a_bmobUser.getObjectByKey(EditActivity.this, "diaryNum").toString();
                tickerView.setText(diaryNum);
            } else {
                tickerView.setText("0");
            }
        }
        //EditText
        editTextTitle = (EditText) findViewById(R.id.editText_editTitle);
        editTextBody=(EditText)findViewById(R.id.editText_editBody);
        //Floating Button
        FloatingActionButton button_EditToMain = (FloatingActionButton) findViewById(R.id.mainButton_edit);
        FloatingActionButton button_UndoToMain = (FloatingActionButton) findViewById(R.id.mainButton_undo);

        //阅读日记时接收的来自Home的消息
        fromHome=getIntent().getStringArrayExtra("willBeRead");
        if(fromHome!=null){
            editTextTitle.setText(fromHome[1], TextView.BufferType.EDITABLE);
            editTextBody.setText(fromHome[2],TextView.BufferType.EDITABLE);
            Toast.makeText(this, fromHome[0]+"阅读对象:"+fromHome[3], Toast.LENGTH_SHORT).show();
        }


        //发送键的响应函数，分别将标题和正文传入inputTextTitle和inputTextBody
        button_EditToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputTextTitle = editTextTitle.getText().toString();
                inputTextBody = editTextBody.getText().toString();

                BmobUser bmobUser=BmobUser.getCurrentUser(EditActivity.this);
                int newDiaryNum=(int)bmobUser.getObjectByKey(EditActivity.this, "diaryNum")+1;


                //判断是写日记还是修改
                if(fromHome!=null){//修改日记
                    updateDiary(inputTextTitle,inputTextBody,fromHome[3]);
                }else if(bmobUser!=null)
                {//写日记
                    sendNewDiary(bmobUser,inputTextTitle,inputTextBody);
                    diaryNum=String.valueOf(newDiaryNum);
                    tickerView.setText(diaryNum);

                    Toast.makeText(EditActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(EditActivity.this,"无法获取当前用户信息，编辑失败",Toast.LENGTH_SHORT).show();
                    //没有写暂存本地的功能
            }
//                Intent intent_edit_main = new Intent(EditActivity.this,MainActivity.class);
//                startActivity(intent_edit_main);
            }

        });


        button_UndoToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_edit_main = new Intent(EditActivity.this,MainActivity.class);
                intent_edit_main.putExtra("queryMyDiaryAgain",true);
                startActivity(intent_edit_main);
            }

        });
    }


    public void sendNewDiary(BmobUser bmobUser,String inputTextTitle,String inputTextBody){
        Diary diary=new Diary();
        diary.setEmail(bmobUser.getEmail());
        //diary.setNickname(" ");
        diary.setTitle(inputTextTitle);
        diary.setBody(inputTextBody);
        diary.save(EditActivity.this);
        bmobUser.increment("diaryNum");
        bmobUser.update(EditActivity.this);
    }

    public void updateDiary(String inputTextTitle,String inputTextBody,String diaryID){
        Diary diary=new Diary();
        diary.setTitle(inputTextTitle);
        diary.setBody(inputTextBody);
        diary.update(EditActivity.this, diaryID, new UpdateListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(EditActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(EditActivity.this, "修改失败", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

