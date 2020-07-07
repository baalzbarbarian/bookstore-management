package com.advancedandroid.quanlysach_newui.HomeMenuFragment.TypesOfBookFragment;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advancedandroid.quanlysach_newui.DatabaseDAO.DatabaseDAO;
import com.advancedandroid.quanlysach_newui.R;
import com.advancedandroid.quanlysach_newui.RecyclerViewCustom.TypesOfBookAdapter;
import com.advancedandroid.quanlysach_newui.mData.mTypesOfBook;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypesOfBookFragment extends Fragment {

    TypesOfBookAdapter adapter;
    List<mTypesOfBook> mTypesOfBookList;
    RecyclerView recyclerView;
    View view;
    DatabaseDAO db;

    public TypesOfBookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_book_gender, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_TypesOfBook);

        setRecyclerView();

        final FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });
        return view;
    }

    private void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_custom_add_types_of_book, null);
        builder.setView(view);
        final Dialog dialog = builder.create();
        dialog.show();

        final EditText code = dialog.findViewById(R.id.addTypes_edt1);
        final EditText name = dialog.findViewById(R.id.addTypes_edt2);
        final EditText location = dialog.findViewById(R.id.addTypes_edt3);
        final EditText description = dialog.findViewById(R.id.addTypes_ed4);

        dialog.findViewById(R.id.addStudents_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseDAO db = new DatabaseDAO(getActivity());

                if (db.checkTypesCodeExist(code.getText().toString()) == true || db.checkTypesNameExist(name.getText().toString()) == true){
                    Toast.makeText(getActivity(), "Mã loại hoặc tên loại đã tồn tại.", Toast.LENGTH_SHORT).show();
                }else {
                    mTypesOfBook m = new mTypesOfBook(
                            code.getText().toString(),
                            name.getText().toString(),
                            location.getText().toString(),
                            description.getText().toString()
                    );

                    if (db.addTypesOfBook(m) == true){
                        onResume();
                        Toast.makeText(getContext(), "Thêm Loại Thành Công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Thêm Loại Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                    code.getText().clear(); name.getText().clear(); location.getText().clear(); description.getText().clear();
                }

            }
        });

        dialog.findViewById(R.id.addStudents_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void setRecyclerView(){

        db = new DatabaseDAO(getActivity());
        mTypesOfBookList = db.getAllTypes();
        adapter = new TypesOfBookAdapter(mTypesOfBookList, getActivity());
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
    public void onResume() {
        super.onResume();
        db = new DatabaseDAO(getActivity());
        mTypesOfBookList.clear();
        mTypesOfBookList = db.getAllTypes();
        adapter.updateData(mTypesOfBookList);
    }

}
