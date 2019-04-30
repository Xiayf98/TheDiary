package com.example.a15850.thediary;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a15850.thediary.database.Diary;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity {
    public String currentUserEmail;
    private boolean query=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(0);

        if(!query){
            query=getIntent().getBooleanExtra("queryDiaryAgain",false);
        }

        //显式1
        Button button_MainToEdit = (Button) findViewById(R.id.editButton_main);
        button_MainToEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_main_edit = new Intent(MainActivity.this,EditActivity.class);
                startActivity(intent_main_edit);
            }
        });

        //Floating Button
        FloatingActionButton button_MainToHome = (FloatingActionButton) findViewById(R.id.homeButton_main);
        button_MainToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_main_home = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent_main_home);
            }
        });

        FloatingActionButton button_MainToShare = (FloatingActionButton) findViewById(R.id.shareButton_main);
        button_MainToShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_main_share = new Intent(MainActivity.this,ShareActivity.class);
                startActivity(intent_main_share);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(query){
            queryPersonalDiary();//为Home界面显示个人日记提前查询
            query=false;
        }

    }

    //为Home界面显示个人日记提前查询,并且根据日记编辑情况保持更新
    public void queryPersonalDiary(){
        DiaryContent.ITEMS.clear();
        currentUserEmail= (String) BmobUser.getObjectByKey(MainActivity.this,"email");
        BmobQuery<Diary> queryPersonalDiary=new BmobQuery<Diary>();
        queryPersonalDiary.addWhereEqualTo("email",currentUserEmail);//only for test
        //queryPersonalDiary.addWhereEqualTo("email","dzwblue@163.com");//only for test
        queryPersonalDiary.setLimit(15);
        queryPersonalDiary.findObjects(MainActivity.this, new FindListener<Diary>() {
            @Override
            public void onSuccess(List<Diary> list) {
                Toast.makeText(MainActivity.this, list.size()+"篇个人日记查询成功", Toast.LENGTH_SHORT).show();
                int size=list.size();
                int count=0;
                if(size!=0){
                    for(Diary diary:list){
                        ++count;
                        DiaryContent.DiaryItem diaryItem= new DiaryContent.DiaryItem(String.valueOf(count),
                                diary.getTitle(), diary.getBody(),diary.getObjectId());
                        DiaryContent.ITEMS.add(diaryItem);
                    }
                }else{
                    for(int i=0;i<7;++i){
                        DiaryContent.DiaryItem diaryItem= new DiaryContent.DiaryItem("0",
                                "日记为空:)", "无内容0",null);
                        DiaryContent.ITEMS.add(diaryItem);
                    }

                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(MainActivity.this, "个人日记查询失败", Toast.LENGTH_SHORT).show();
                DiaryContent.DiaryItem diaryItem= new DiaryContent.DiaryItem("0",
                        "日记为空:)", "无内容0",null);
                DiaryContent.ITEMS.add(diaryItem);

            }
        });
    }
}




