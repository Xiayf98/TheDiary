package com.example.a15850.thediary;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {
    public List<CardView> mViews;
    public List<CardItem> mData;
    public float mBaseElevation;
    public boolean isLiked = false;
    public int likes;//点赞数


    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.viewpager_share, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        giveALike(mData.get(position),view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(CardItem item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getText());
    }

    //点击改变点赞状态
    public void giveALike(CardItem item,View view){
        final CardView cardView = (CardView) view.findViewById(R.id.cardView);
        cardView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v,MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN && !isLiked){
                    //change to the pressed_background
                    cardView.setCardBackgroundColor(android.graphics.Color.parseColor("#F3DBCF"));
                    isLiked=true;
                    likes++;
                }
                else{
                    //change to the unpressed_background
                    cardView.setCardBackgroundColor(Color.WHITE);
                    isLiked=false;
                    likes--;
                }
                return false;
            }
        });
    }
}
