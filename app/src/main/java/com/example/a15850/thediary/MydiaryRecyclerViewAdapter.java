package com.example.a15850.thediary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a15850.thediary.DiaryFragment.OnListFragmentInteractionListener;
import com.example.a15850.thediary.DiaryContent.DiaryItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DiaryItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MydiaryRecyclerViewAdapter extends
        RecyclerView.Adapter<MydiaryRecyclerViewAdapter.ViewHolder> {
    private final List<DiaryItem> mValues;
    private final OnListFragmentInteractionListener mListener;


    // Provide a suitable constructor
    public MydiaryRecyclerViewAdapter(List<DiaryItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_diary, parent, false);
        return new ViewHolder(view);
    }

    /*The adapter binds the view holders to their data.
    It does this by assigning the view holder to a position,
    and calling the adapter's onBindViewHolder() method.*/
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).title);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /*The views in the list are represented by view holder objects.
        These objects are instances of a class you define by extending RecyclerView.ViewHolder.*/
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public DiaryItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

}
