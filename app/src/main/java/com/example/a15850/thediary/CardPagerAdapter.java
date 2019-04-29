package com.example.a15850.thediary;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a15850.thediary.database.Diary;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {
    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    private boolean isLiked = false;

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

    public void giveALike(final CardItem item, View view){
        final CardView cardView = (CardView) view.findViewById(R.id.cardView);
        final CardItem mItem=item;
        cardView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v,MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN && !isLiked){

                    //更新点赞数+
                    updateLikesRecord_increase(mItem.diaryID,cardView);
                }
                else{

                    //更新点赞数-
                    updateLikesRecord_decrease(mItem.diaryID,cardView);
                }
                return false;
            }
        });
    }

    public void updateLikesRecord_increase(String diary_id, final CardView cardView){
        Diary diary=new Diary();
        diary.increment("likes_record");
        diary.update(cardView.getContext(), diary_id, new UpdateListener() {
            @Override
            public void onSuccess() {
                //change to the pressed_background
                cardView.setCardBackgroundColor(android.graphics.Color.parseColor("#F3DBCF"));
                isLiked=true;
                Toast.makeText(cardView.getContext(), "谢谢你的称赞:)", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(cardView.getContext(), "点赞失败", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void updateLikesRecord_decrease(String diary_id, final CardView cardView) {
        Diary diary = new Diary();
        diary.increment("likes_record",-1);
        diary.update(cardView.getContext(), diary_id, new UpdateListener() {
            @Override
            public void onSuccess() {
                //change to the unpressed_background
                cardView.setCardBackgroundColor(Color.WHITE);
                isLiked=false;
                Toast.makeText(cardView.getContext(), "点赞已取消", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(cardView.getContext(), "取消点赞失败", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
