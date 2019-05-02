package com.example.a15850.thediary;
//主Activity，应用的入口点，构建和运行应用时，系统会启动此 Activity 的实例并加载其布局。
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
//import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.NavigationView;
import android.widget.Toast;

import com.example.a15850.thediary.database.Diary;
import com.example.a15850.thediary.database.MyBmobUser;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
//import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    public String currentUserEmail;
    private boolean query=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //把Activity添加到集合里面
        CommonAction.getInstance().addActivity(this);

        setContentView(R.layout.activity_main);



        //跳转至Edit界面
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

        //设置ToolBar
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);

        //设置抽屉DrawerLayout
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close);

        mDrawerToggle.syncState();//初始化状态
        mDrawerLayout.addDrawerListener(mDrawerToggle);


        //设置导航栏NavigationView的点击事件
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navigation_main);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_notifications:
                        break;
                    case R.id.navigation_settings:
                        break;
                    case R.id.navigation_logout:
                        MyBmobUser.logOut(MainActivity.this);
                        Toast.makeText(MainActivity.this, "已退出登录", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                        CommonAction.getInstance().OutSign();
                        break;
                }
                menuItem.setChecked(true);//点击了把它设为选中状态
                mDrawerLayout.closeDrawers();
                //menuItem.setChecked(false);//点击了把它设为取消选中状态
                return true;
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(!query){
            query=getIntent().getBooleanExtra("queryMyDiaryAgain",false);
        }
        if(!query){
            query=getIntent().getBooleanExtra("queryMyDiaryAgain2",false);
        }
        if(query){
            queryPersonalDiary();//为Home界面显示个人日记提前查询/重新查询刷新
            query=false;
        }
    }

    //为Home界面显示个人日记提前查询,并且根据日记编辑情况保持更新
    public void queryPersonalDiary(){
        DiaryContent.ITEMS.clear();
        DiaryContent.CHECKS.clear();

        currentUserEmail= (String) MyBmobUser.getObjectByKey(MainActivity.this,"email");
        BmobQuery<Diary> queryPersonalDiary=new BmobQuery<Diary>();
        queryPersonalDiary.addWhereEqualTo("email",currentUserEmail);
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
                                diary.getTitle(), diary.getBody(),diary.getObjectId(),false);
                        DiaryContent.ITEMS.add(diaryItem);
                        DiaryContent.CHECKS.add(false);
                    }
                }else{

                        DiaryContent.DiaryItem diaryItem= new DiaryContent.DiaryItem("0",
                                "日记为空:)", "无内容0",null,false);
                        DiaryContent.ITEMS.add(diaryItem);
                        DiaryContent.CHECKS.add(false);


                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(MainActivity.this, "个人日记查询失败", Toast.LENGTH_SHORT).show();
                DiaryContent.DiaryItem diaryItem= new DiaryContent.DiaryItem("0",
                        "日记为空:)", "无内容0",null,false);
                DiaryContent.ITEMS.add(diaryItem);
                DiaryContent.CHECKS.add(false);
            }
        });
    }
}


