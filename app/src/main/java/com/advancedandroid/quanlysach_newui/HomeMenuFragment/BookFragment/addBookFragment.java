package com.advancedandroid.quanlysach_newui.HomeMenuFragment.BookFragment;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.advancedandroid.quanlysach_newui.DatabaseDAO.DatabaseDAO;
import com.advancedandroid.quanlysach_newui.R;
import com.advancedandroid.quanlysach_newui.mData.mBook;
import com.advancedandroid.quanlysach_newui.mData.mTypesOfBook;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class addBookFragment extends Fragment {
    Spinner spinner;
    View view;
    EditText edtBookCode, edtBookName, edtAuthor, edtPrice, edtAmount;
    Button btnClear, btnAdd, btnShow;
    ImageView imgAddTypes;
    List<mBook> mBookList;
    
    public addBookFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_book, container, false);
        initView();
        setSpinner();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        imgAddTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImgAddTypes();
            }
        });
        return view;
    }

    private String getCallerFragment(){
        FragmentManager fm = getFragmentManager();
        int count = getFragmentManager().getBackStackEntryCount();
        return fm.getBackStackEntryAt(count - 2).getName();
    }

    private void clear(){
        edtAmount.getText().clear();
        edtPrice.getText().clear();
        edtAuthor.getText().clear();
        edtBookName.getText().clear();
        edtBookCode.getText().clear();
    }

    private void setSpinner(){
        DatabaseDAO db = new DatabaseDAO(getContext());
        List<String> list = db.getTypesName();
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
    }

    private void initView(){
        edtBookCode = view.findViewById(R.id.addBook_edt1);
        edtBookName = view.findViewById(R.id.addBook_edt2);
        edtAuthor = view.findViewById(R.id.addBook_edt3);
        edtPrice = view.findViewById(R.id.addBook_edt5);
        edtAmount = view.findViewById(R.id.addBook_edt6);

        btnAdd = view.findViewById(R.id.addBook_btn2);
        btnClear = view.findViewById(R.id.addBook_btn1);
        btnShow = view.findViewById(R.id.addBook_btn3);

        spinner = view.findViewById(R.id.addBook_spinner);
        imgAddTypes = view.findViewById(R.id.imgAddTypesInAddBook);
    }

    private void setImgAddTypes(){
        dialog();
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

                if (db.checkTypesCodeExist(code.getText().toString()) == true){
                    Toast.makeText(getActivity(), "Mã loại sách đã tồn tại.", Toast.LENGTH_SHORT).show();
                }else {
                    if (db.checkTypesNameExist(name.getText().toString()) == true){
                        Toast.makeText(getActivity(), "Tên loại sách đã tồn tại.", Toast.LENGTH_SHORT).show();
                    }else {
                        mTypesOfBook m = new mTypesOfBook(code.getText().toString(), name.getText().toString(), location.getText().toString(), description.getText().toString());
                        if (db.addTypesOfBook(m) == true){
                            Toast.makeText(getContext(), "Thêm Loại Thành Công", Toast.LENGTH_SHORT).show();
                            setSpinner();
                        }else {
                            Toast.makeText(getContext(), "Thêm Loại Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                        code.getText().clear(); name.getText().clear(); location.getText().clear(); description.getText().clear();
                    }
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

    private void addBook(){
        DatabaseDAO db = new DatabaseDAO(getActivity());
        
        String code = edtBookCode.getText().toString();
        String name = edtBookName.getText().toString();
        String author = edtAuthor.getText().toString();
        
        if (db.checkExist(code) == false){
            double price = Double.parseDouble(edtPrice.getText().toString());
            double amount = Double.parseDouble(edtAmount.getText().toString());
            String typesOfBook = spinner.getSelectedItem().toString();

            mBook m = new mBook(code, name, author, typesOfBook, price, amount);

            if (db.addBook(m) == false){
                Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();

            }
        }else {
            Toast.makeText(getActivity(), "Mã sách đã tồn tại.", Toast.LENGTH_SHORT).show();
        }
        
        
    }

}
