package com.example.a15850.thediary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gao on 2016/7/1.
 *
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PlateViewHolder> {

    private List<String> list;
    private List<Boolean> listCheck;
    private Context context;
    boolean isShow=false;

    public RecyclerAdapter(List<String> list, Context context, List<Boolean> listCheck){
        this.list=list;
        this.context=context;
        this.listCheck=listCheck;
    }


    @Override
    public PlateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new PlateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlateViewHolder holder, int position) {
        holder.position=position;
        holder.textView.setText(position+"");
        holder.checkBox.setChecked(listCheck.get(position));
        if(isShow){
            holder.checkBox.setVisibility(View.VISIBLE);
        }else {
            holder.checkBox.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PlateViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener,CompoundButton.OnCheckedChangeListener{
        private TextView textView;
        private CheckBox checkBox;
        private LinearLayout rootView;
        private int position;
        public PlateViewHolder(View itemView) {
            super(itemView);
            rootView= (LinearLayout) itemView.findViewById(R.id.item_root);
            textView= (TextView) itemView.findViewById(R.id.item_text);
            checkBox= (CheckBox) itemView.findViewById(R.id.item_checkbox);

            checkBox.setOnCheckedChangeListener(this);
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            if(onItemClickListener!=null){
                return onItemClickListener.setOnItemLongClick(position);
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListener!=null){
                if(checkBox.isChecked()){
                    checkBox.setChecked(false);
                    onItemClickListener.setOnItemClick(position,false);
                }else {
                    checkBox.setChecked(true);
                    onItemClickListener.setOnItemClick(position,true);
                }
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(onItemClickListener!=null){
                onItemClickListener.setOnItemCheckedChanged(position,isChecked);
            }
        }
    }

    public interface OnItemClickListener {
        void setOnItemClick(int position, boolean isCheck);
        boolean setOnItemLongClick(int position);
        void setOnItemCheckedChanged(int position, boolean isCheck);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}
