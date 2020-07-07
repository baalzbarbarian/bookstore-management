package com.advancedandroid.quanlysach_newui.HomeMenuFragment.BookFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advancedandroid.quanlysach_newui.DatabaseDAO.DatabaseDAO;
import com.advancedandroid.quanlysach_newui.R;
import com.advancedandroid.quanlysach_newui.RecyclerViewCustom.BookRecyclerView;
import com.advancedandroid.quanlysach_newui.mData.mBook;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends Fragment implements SearchView.OnQueryTextListener {

    List<mBook> mBookList;
    BookRecyclerView adapter;
    RecyclerView recyclerView;

    public BookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_Book);
        setRecyclerView();

        final FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new addBookFragment());
            }
        });

        SearchView searchView = view.findViewById(R.id.seachView_book);
        searchView.setOnQueryTextListener(this);
        return view;
    }

    private void changeFragment(Fragment targetFragment){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "addBookFragment")
                .addToBackStack(null)
                .commit();
    }

    private void setRecyclerView(){
        DatabaseDAO db;
        db = new DatabaseDAO(getActivity());
        mBookList = db.getAllBook();
        adapter = new BookRecyclerView(mBookList, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

//        DividerItemDecoration dividerItemDecoration =
//                new DividerItemDecoration(
//                        getActivity(),
//                        ((LinearLayoutManager)
//                                layoutManager).getOrientation()
//                );
//        recyclerView.addItemDecoration(dividerItemDecoration);

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
