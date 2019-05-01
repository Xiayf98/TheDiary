package com.example.a15850.thediary;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class HomeActivity extends AppCompatActivity
        implements DiaryFragment.OnListFragmentInteractionListener {
    private DiaryFragment.OnListFragmentInteractionContronller onListFragmentInteractionContronller;
    private FloatingActionButton backToMainPageButton;//返回main界面的按钮
    private ToggleButton editOrDoneButton;//编辑按钮（用于多选删除或多选分享）
    private Button selectAllButton;
    private Button shareButton;
    private Button deleteButton;
    private CheckBox checkBox;
//    public boolean flage;

    private DiaryFragment diaryFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);//设置要显示的视图

        //全选按钮
        selectAllButton=(Button)findViewById(R.id.selectAllButton);
        selectAllButton.setOnClickListener(new selectAllButtonListener());
        //分享按钮
        shareButton=(Button)findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new shareButtonListener());
        //删除按钮
        deleteButton=(Button)findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new deleteButtonListener());
        //复选框
        checkBox=(CheckBox)findViewById(R.id.check_state);
        //diaryFragment=new DiaryFragment();
    }

    //创建Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.home_title,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //菜单栏中按钮响应
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home_to_main://跳转至main界面
                Intent intent_home_main = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent_home_main);
                return true;
            case R.id.home_edit:
                selectAllButton.setVisibility(View.VISIBLE);
                shareButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
//                diaryFragment.showCheckBox();
                diaryFragment=(DiaryFragment)getSupportFragmentManager().findFragmentById(R.id.diary_fragment);
                int size1=DiaryContent.ITEMS.size();
                for(int i=0;i<size1;++i){
                    DiaryContent.ITEMS.get(i).setEdit(true);
                }
                diaryFragment.updateRecyclerViewState();
//                if(onListFragmentInteractionContronller!=null){
//                    onListFragmentInteractionContronller.onListFragmentController(true);
//                }
//                flage=true;
                return true;
            case R.id.home_edit_finish:
                selectAllButton.setVisibility(View.INVISIBLE);
                shareButton.setVisibility(View.INVISIBLE);
                deleteButton.setVisibility(View.INVISIBLE);
//                diaryFragment.hideCheckBox();
                diaryFragment=(DiaryFragment)getSupportFragmentManager().findFragmentById(R.id.diary_fragment);
                int size2=DiaryContent.ITEMS.size();
                for(int i=0;i<size2;++i){
                    DiaryContent.ITEMS.get(i).setEdit(false);
                }
                diaryFragment.updateRecyclerViewState();
//                if(onListFragmentInteractionContronller!=null){
//                    onListFragmentInteractionContronller.onListFragmentController(false);
//                }
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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
