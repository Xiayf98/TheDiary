package com.example.a15850.thediary;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;

public class HomeActivity extends AppCompatActivity
        implements DiaryFragment.OnListFragmentInteractionListener {
    //private DiaryFragment.OnListFragmentInteractionListener onListFragmentInteractionListener;
    private ImageButton backToMainPageButton;//返回main界面的按钮
    private ToggleButton editOrDoneButton;//编辑按钮（用于多选删除或多选分享）
    private Button selectAllButton;
    private Button shareButton;
    private Button deleteButton;

    //private DiaryFragment diaryFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);//设置要显示的视图

        //
        backToMainPageButton = (ImageButton) findViewById(R.id.backToMain);//找到要编辑的按钮（这是返回main界面的按钮）
        backToMainPageButton.setOnClickListener(new backToMainPageButtonListener());

        //
        editOrDoneButton = (ToggleButton) findViewById(R.id.EditOrDone);//找到要编辑的按钮（这是返回编辑或确定按钮）
        editOrDoneButton.setOnCheckedChangeListener(new editOrDoneButtonListener());

        //
        selectAllButton=(Button)findViewById(R.id.selectAllButton);
        selectAllButton.setOnClickListener(new selectAllButtonListener());

        //
        shareButton=(Button)findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new shareButtonListener());
        //
        deleteButton=(Button)findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new deleteButtonListener());

        //
        //diaryFragment=new DiaryFragment();
    }


    //返回main界面按钮的响应设置
    private class backToMainPageButtonListener implements OnClickListener {
        //
        public void onClick(View v) {
            Intent intent = new Intent();
                /*通过将 Intent 传递给 startActivity()来
                启动新的 Activity 实例。Intent 描述了要启动的 Activity，
                并携带了任何必要的数据。*/
            intent.setClass(HomeActivity.this, MainActivity.class);
            //这里从HomeActivity跳至另一个界面，请将‘MainActivity’换成需要跳转的Activity（例如个人主页）：）

            //把一个值写入到Intent中
            intent.putExtra("Text", "测试值");
            //启动另一个activity
            HomeActivity.this.startActivity(intent);

        }
    }

    //编辑按钮（用于多选删除或分享）的响应设置
    private class editOrDoneButtonListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton button,boolean isChecked)
        {
            //添加Edit按钮的响应(出现全选、删除、分享的按钮)，并且使日记列表的状态变为可多选的状态
            if(isChecked) {
                selectAllButton.setVisibility(View.VISIBLE);
                shareButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
            }
            else{
                selectAllButton.setVisibility(View.INVISIBLE);
                shareButton.setVisibility(View.INVISIBLE);
                deleteButton.setVisibility(View.INVISIBLE);
            }

        }




    }


    //
    private class selectAllButtonListener implements OnClickListener {

        public void onClick(View v) {
            //多选功能尚未实现
            //这里需要后端函数

        }
    }

    private  class shareButtonListener implements OnClickListener{
        public void onClick(View v) {
            //后端
        }
    }

    private  class deleteButtonListener implements OnClickListener{
        public void onClick(View v) {
            //后端
        }
    }

    //使用含有日记列表的Fragment需要实现这个响应接口
    @Override
    public void onListFragmentInteraction(DiaryContent.DiaryItem item) {
        //这里实现点击其中的一项转到相应的查看界面，
        // 查看的具体内容由item的信息提供（之后须将item信息加入Intent），启动edit界面时需要接收这个信息
        Intent readThisDiary=new Intent(HomeActivity.this,EditActivity.class);
        //请将‘MainActivity’改成需要查看的Activity（例如edit界面）：）

        startActivity(readThisDiary);

    }
}
