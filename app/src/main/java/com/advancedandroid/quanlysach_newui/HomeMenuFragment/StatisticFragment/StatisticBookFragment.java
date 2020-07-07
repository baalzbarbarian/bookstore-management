package com.advancedandroid.quanlysach_newui.HomeMenuFragment.StatisticFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advancedandroid.quanlysach_newui.DatabaseDAO.DatabaseDAO;
import com.advancedandroid.quanlysach_newui.R;
import com.advancedandroid.quanlysach_newui.RecyclerViewCustom.StatisticBookAdapter;
import com.advancedandroid.quanlysach_newui.mData.mBook;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticBookFragment extends Fragment{

    RecyclerView recyclerView;
    TextInputEditText textInputEditText;
    StatisticBookAdapter adapter;
    Button btnGetData;
    public StatisticBookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        btnGetData = view.findViewById(R.id.btnGetData);
        recyclerView = view.findViewById(R.id.recyclerView_Statistics);
        textInputEditText = view.findViewById(R.id.searchView_EnterMonthToGetInfo);

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseDAO db = new DatabaseDAO(getActivity());
                List<mBook> mBookList = db.getTheBestSellingBook(textInputEditText.getText().toString());
                adapter = new StatisticBookAdapter(mBookList, getActivity());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        });
        return view;
    }

}
