package com.example.a15850.thediary;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */

public class DiaryFragment extends Fragment {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private MydiaryRecyclerViewAdapter mAdapter;
    private RecyclerView recyclerView;
    private boolean updateAdapter=true;
    //private RecyclerView.LayoutManager layoutManager;//The view will be connected to a layout manager

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DiaryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    /*public static DiaryFragment newInstance(int columnCount) {
        DiaryFragment fragment = new DiaryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化(unused)
        // diaryContent = new DiaryContent();//日记内容
//        mListener=new OnListFragmentInteractionListener() {
//            @Override
//            public void onListFragmentInteraction(DiaryItem item) {
//
//            }
//        };
        //mAdapter=new MydiaryRecyclerViewAdapter(diaryContent.ITEMS,mListener);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary_list, container, false);
        /*recyclerView = (RecyclerView) getView().findViewById(R.id.list);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);//connect the RecyclerView to a layout manager
        recyclerView.setAdapter(mAdapter);*/

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            recyclerView.setHasFixedSize(true);

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));//The view will be connected to a layout manager
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));//The view will be connected to a layout manager
            }
            mAdapter=new MydiaryRecyclerViewAdapter(DiaryContent.ITEMS,DiaryContent.CHECKS,mListener);
            recyclerView.setAdapter(mAdapter);
            Context temp=getActivity();
            Toast.makeText(temp,"适配器更新成功1",Toast.LENGTH_SHORT).show();
            updateAdapter=false;
        }
        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        if(updateAdapter){
            mAdapter=new MydiaryRecyclerViewAdapter(DiaryContent.ITEMS,DiaryContent.CHECKS,mListener);
            recyclerView.setAdapter(mAdapter);
            Context temp=getActivity();
            Toast.makeText(temp,"适配器更新成功2",Toast.LENGTH_SHORT).show();
        }else{
            updateAdapter=true;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
//    public void showCheckBox(){
//        mAdapter.showCheckBox();
//    }
//    public void hideCheckBox(){
//        mAdapter.hideCheckBox();
//    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public void updateRecyclerViewState(){
        mAdapter.notifyDataSetChanged();
    }
    public void deleteOnAdapter(int position,int startPosition,int itemCount){
        mAdapter.notifyItemRemoved(position);
        mAdapter.notifyItemRangeChanged(startPosition,itemCount);
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DiaryContent.DiaryItem item);
    }
    public interface OnListFragmentInteractionContronller {
        // TODO: Update argument type and name
        void onListFragmentController(boolean show);
    }
}

