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
        mCardAdapter.addCardItem(new CardItem(R.string.title_1,R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_2,R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_3,R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_4,R.string.text_1));

        mCardShadowTransformer=new ShadowTransformer(mViewPager,mCardAdapter);
        mCardShadowTransformer.enableScaling(true);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }
}
