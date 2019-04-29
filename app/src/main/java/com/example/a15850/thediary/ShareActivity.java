package com.example.a15850.thediary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.a15850.thediary.database.Diary;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class ShareActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    String inputTextTitle;//日记标题
    String inputTextbody;//日记正文
    private int cardNum;//公开日记的数量
    private static List<CardItem> cardItemList=new ArrayList<>();

//    public void setmCardAdapter(CardItem cardItem){
//        mCardAdapter.addCardItem(cardItem);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

//
//        Button giveALikeBtn = (Button) findViewById(R.id.give_a_like_btn);
//        giveALikeBtn.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction()==MotionEvent.ACTION_DOWN) {
//                    //change to the pressed_background
//                    v.setBackgroundResource(R.mipmap.ic_give_a_like_pressed);
//                }
//                else if(event.getAction()==MotionEvent.ACTION_UP){
//                    //change to the unpressed_background
//                    v.setBackgroundResource(R.mipmap.ic_give_a_like_unpressed);
//                }
//                return false;
//            }
//        });

        mCardAdapter=new CardPagerAdapter();
        collectDiaryAndSetAdapter();
    }

    public void dealFollowingTasks(){
        cardNum=cardItemList.size();
        if(cardNum==0){
            mCardAdapter.addCardItem(new CardItem("无标题0","无内容0",null));
        }else{
            for(int i=0;i<cardNum;i++){
                //从数据库中获取第i篇公开日记的标题和正文
                //分别传入inputTextTitle和inputTextBody中
                //这里先给两个字符串赋值进行测试
//                inputTextTitle="Spring";
//                inputTextbody="Have a good day!";

                CardItem mItem=cardItemList.get(i);
                mCardAdapter.addCardItem(mItem);
                //mCardAdapter.addCardItem(new CardItem(inputTextTitle,inputTextbody,null));
            }
        }


        mCardShadowTransformer=new ShadowTransformer(mViewPager,mCardAdapter);
        mCardShadowTransformer.enableScaling(true);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

    }
    public void setCardItemList(CardItem cardItem){
        this.cardItemList.add(cardItem);
    }
    public void collectDiaryAndSetAdapter(){
        BmobQuery<Diary> diaryBmobQuery=new BmobQuery<Diary>();
        diaryBmobQuery.addWhereEqualTo("open",true);
        //diaryBmobQuery.setLimit(1);
        diaryBmobQuery.findObjects(this, new FindListener<Diary>() {
            @Override
            public void onSuccess(List<Diary> list) {//查询成功
                Toast.makeText(ShareActivity.this,"查询到"+list.size()+"条数据",Toast.LENGTH_SHORT).show();
                int list_size=list.size();
                int temp_count=0;
                if(list_size==0){
                    Toast.makeText(ShareActivity.this,"没有分享内容",Toast.LENGTH_SHORT).show();
                }else if(list_size>0){
                    while(temp_count<list_size){
                        Diary a_diary=list.get(temp_count);
                        if(a_diary!=null){
                            CardItem item=new CardItem(a_diary.getTitle(),a_diary.getBody(),a_diary.getObjectId());
                            setCardItemList(item);
                            ++temp_count;
                        }
                    }
                }else{
                    Toast.makeText(ShareActivity.this,"系统出错",Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(ShareActivity.this,"收集完毕",Toast.LENGTH_SHORT).show();
                dealFollowingTasks();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(ShareActivity.this,"日记收集失败",Toast.LENGTH_SHORT).show();
                //setCollectFinished(true);
            }
        });
    }
}
