package com.advancedandroid.quanlysach_newui.HomeMenuFragment.UserFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advancedandroid.quanlysach_newui.DatabaseDAO.DatabaseDAO;
import com.advancedandroid.quanlysach_newui.R;
import com.advancedandroid.quanlysach_newui.RecyclerViewCustom.UserRecyclerViewAdapter;
import com.advancedandroid.quanlysach_newui.mData.mUser;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements SearchView.OnQueryTextListener {

    View view;
    RecyclerView recyclerView;
    public static List<mUser> mUserList;
    UserRecyclerViewAdapter adapter;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);
        setRecyclerView();

        SearchView searchView = view.findViewById(R.id.seachView_UserLogin);
        searchView.setOnQueryTextListener(this);
        return view;
    }

    private void setRecyclerView(){
        DatabaseDAO db;
        recyclerView = view.findViewById(R.id.recyclerView_UserLogin);
        db = new DatabaseDAO(getActivity());
        mUserList = db.getAllUser();
        adapter = new UserRecyclerViewAdapter(mUserList, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(
                        getActivity(),
                        ((LinearLayoutManager)
                                layoutManager).getOrientation()
                );
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }

}
