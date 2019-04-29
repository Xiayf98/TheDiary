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

import cn.bmob.v3.BmobUser;


public class EditActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextBody;
    String inputTextTitle;
    String inputTextBody;
    String diaryNum;//日记篇数


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setElevation(0);

        //TickerView点击显示当前日记篇数
        //获取日记篇数传入diaryNum
        //diaryNum="13";//测试用
        final TextView diaryNumView = findViewById(R.id.diaryNumView);
        BmobUser a_bmobUser=BmobUser.getCurrentUser(EditActivity.this);
        if(a_bmobUser!=null)
        {
            diaryNum=a_bmobUser.getObjectByKey(EditActivity.this, "diaryNum").toString();
            diaryNumView.setText(diaryNum);
        }else{
            diaryNumView.setText("查询失败");
        }
        //tickerView.setCharacterLists(TickerUtils.provideNumberList());
        /*tickerView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });*/

        //EditText
        editTextTitle = (EditText) findViewById(R.id.editText_editTitle);
        editTextBody=(EditText)findViewById(R.id.editText_editBody);
        //Floating Button
        FloatingActionButton button_EditToMain = (FloatingActionButton) findViewById(R.id.mainButton_edit);
        FloatingActionButton button_UndoToMain = (FloatingActionButton) findViewById(R.id.mainButton_undo);

        //发送键的响应函数，分别将标题和正文传入inputTextTitle和inputTextBody
        button_EditToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputTextTitle = editTextTitle.getText().toString();
                inputTextBody = editTextBody.getText().toString();
                // Toast.makeText(EditActivity.this,inputText,Toast.LENGTH_SHORT).show(); //测试EditText内容传入了inputText//测试成功
                BmobUser bmobUser=BmobUser.getCurrentUser(EditActivity.this);
                int newDiaryNum=(int)bmobUser.getObjectByKey(EditActivity.this, "diaryNum")+1;
                if(bmobUser!=null)
                {
                    sendNewDiary(bmobUser,inputTextTitle,inputTextBody);
                    diaryNum=String.valueOf(newDiaryNum);
                    diaryNumView.setText(diaryNum);

                    Toast.makeText(EditActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(EditActivity.this,"获取当前用户信息失败，发送失败",Toast.LENGTH_SHORT).show();
                    //暂时没有写暂存本地的功能......
                }

                /*Intent intent_edit_main = new Intent(EditActivity.this,MainActivity.class);
                startActivity(intent_edit_main);*/
            }

        });
        button_UndoToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_edit_main = new Intent(EditActivity.this,MainActivity.class);
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
}

