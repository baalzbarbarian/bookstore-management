package com.advancedandroid.quanlysach_newui.HomeMenuFragment.BillFragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advancedandroid.quanlysach_newui.DatabaseDAO.DatabaseDAO;
import com.advancedandroid.quanlysach_newui.Interface.FragmentCommunication;
import com.advancedandroid.quanlysach_newui.R;
import com.advancedandroid.quanlysach_newui.RecyclerViewCustom.BillAdapter;
import com.advancedandroid.quanlysach_newui.Validation.CheckDate;
import com.advancedandroid.quanlysach_newui.mData.mBill;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillFragment extends Fragment implements SearchView.OnQueryTextListener {

    List<mBill> mBillList;
    DatabaseDAO db;
    BillAdapter adapter;
    private View view;
    RecyclerView recyclerView;

    public BillFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bill, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_Bill);
        setRecyclerView();

        androidx.appcompat.widget.SearchView searchView = view.findViewById(R.id.seachView_bill);
        searchView.setOnQueryTextListener(this);

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
        View view = inflater.inflate(R.layout.dialog_custom_add_bill, null);
        builder.setView(view);
        final Dialog dialog = builder.create();
        dialog.show();

        final EditText code = dialog.findViewById(R.id.addBill_txt1);
        final EditText date = dialog.findViewById(R.id.addBill_txt2);

        dialog.findViewById(R.id.addBill_btnDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(calendar.DATE);
                int month = calendar.get(calendar.MONTH);
                int year = calendar.get(calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                calendar.set(i, i1, i2);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                date.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        dialog.findViewById(R.id.addBill_btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseDAO db = new DatabaseDAO(getActivity());

                if (code.getText().toString().isEmpty() || date.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Các trường không được trống", Toast.LENGTH_SHORT).show();
                }else if (!CheckDate.isValidDate(date.getText().toString())){
                    Toast.makeText(getActivity(), "Ngày tháng định dạng: dd/mm/YYYY", Toast.LENGTH_SHORT).show();
                }else {
                    if (db.addBill(code.getText().toString(), date.getText().toString()) == true){
                        mBillList = db.getAllBill();
                        adapter = new BillAdapter(mBillList, getActivity(),communication);
                        Toast.makeText(getContext(), "Thêm Bill thành công", Toast.LENGTH_SHORT).show();
                        recyclerView.setAdapter(adapter);
                        setRecyclerView();
                    }else {
                        adapter.notifyDataSetChanged();
                    }
                }

                code.getText().clear(); date.getText().clear();

            }
        });

        dialog.findViewById(R.id.addBill_btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void setRecyclerView(){
        DatabaseDAO db;
        db = new DatabaseDAO(getActivity());
        mBillList = db.getAllBill();
        adapter = new BillAdapter(mBillList, getActivity(), communication);
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

    FragmentCommunication communication = new FragmentCommunication() {
        @Override
        public void respond(int position, String code) {
            BillDetailFragment fragment = new BillDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("CODE", code);
            fragment.setArguments(bundle);
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_fragment, fragment).addToBackStack("billfragment").commit();
        }
    };

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
