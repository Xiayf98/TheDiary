package com.example.a15850.thediary;
//主Activity，应用的入口点，构建和运行应用时，系统会启动此 Activity 的实例并加载其布局。
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.NavigationView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //把Activity添加到集合里面
        CommonAction.getInstance().addActivity(this);

        setContentView(R.layout.activity_main);



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
                    case R.id.navigation_portrait:
                        showChoosePicDialog();
                        break;
                    case R.id.navigation_id:
                        break;
                    case R.id.navigation_password:
                        break;
                    case R.id.navigation_logout:
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

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static Uri tempUri;
    private String mCurrentPhotoPath;

    /**
     * 显示修改头像的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if(takePictureIntent.resolveActivity(getPackageManager() )!= null) {
                            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA).format(new Date()) + ".png";
                            File file = new File(Environment.getExternalStorageDirectory(), filename);
                            mCurrentPhotoPath = file.getAbsolutePath();

                            Uri fileUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.a15850.thediary.fileProvider", file);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            startActivityForResult(takePictureIntent, TAKE_PICTURE);
                        }
                        break;
                }
            }
        });
        builder.create().show();
    }

    //关闭相机后自动调用
    //onActivityResult

}


