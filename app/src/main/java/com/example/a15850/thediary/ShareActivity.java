package com.example.a15850.thediary;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class ShareActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    String inputTextTitle;//日记标题
    String inputTextbody;//日记正文
    int cardNum;//公开日记的数量

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
        //从数据库中获取公开日记的篇数传入cardNum
        cardNum=1;
        for(int i=0;i<cardNum;i++){
            //从数据库中获取第i篇公开日记的标题和正文
            //分别传入inputTextTitle和inputTextBody中
            //这里先给两个字符串赋值进行测试
            inputTextTitle="Spring";
            inputTextbody="Have a good day!";

            mCardAdapter.addCardItem(new CardItem(inputTextTitle,inputTextbody));
        }
        mCardShadowTransformer=new ShadowTransformer(mViewPager,mCardAdapter);
        mCardShadowTransformer.enableScaling(true);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }
}
