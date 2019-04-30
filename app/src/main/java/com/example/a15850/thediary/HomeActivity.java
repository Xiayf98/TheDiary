package com.example.a15850.thediary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;



import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private List<Boolean> listCheck;
    RecyclerAdapter adapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        listCheck = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add(i + "");
            listCheck.add(false);
        }

        adapter = new RecyclerAdapter(list, this, listCheck);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClick(int position, boolean isCheck) {
                listCheck.set(position, isCheck);
            }

            @Override
            public boolean setOnItemLongClick(int position) {
                adapter.isShow = true;
                adapter.notifyDataSetChanged();
                return true;
            }
            @Override
            public void setOnItemCheckedChanged(int position, boolean isCheck) {
                listCheck.set(position,isCheck);
            }
        });
    }


    @Override
    public boolean onCabItemClicked(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home_delete:
                int length=listCheck.size();
                for (int i = length-1; i>=0; i--) {
                    if (listCheck.get(i)) {
                        list.remove(i);
                        listCheck.remove(i);
                        adapter.notifyItemRemoved(i);
                        adapter.notifyItemRangeChanged(0,list.size());
                    }
                }
                break;
            case R.id.home_select_all:
                for (int i = 0; i < listCheck.size(); i++) {
                    listCheck.set(i, true);
                }
                adapter.notifyDataSetChanged();
                break;
        }

        return true;
    }
}
