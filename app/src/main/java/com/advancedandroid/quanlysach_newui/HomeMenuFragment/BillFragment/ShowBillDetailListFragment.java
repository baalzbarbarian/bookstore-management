package com.advancedandroid.quanlysach_newui.HomeMenuFragment.BillFragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advancedandroid.quanlysach_newui.DatabaseDAO.DatabaseDAO;
import com.advancedandroid.quanlysach_newui.R;
import com.advancedandroid.quanlysach_newui.RecyclerViewCustom.BillDetailAdapter;
import com.advancedandroid.quanlysach_newui.mData.mBillDetail;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowBillDetailListFragment extends Fragment {

    BillDetailAdapter adapter;
    protected List<mBillDetail> mBillDetailList;
    DatabaseDAO db;
    mBillDetail m;
    RecyclerView recyclerView;
    String code;
    public ShowBillDetailListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        code = getArguments().getString("CODE");
        recyclerView = view.findViewById(R.id.recyclerView_BillDetail_Show);
        db = new DatabaseDAO(getActivity());
        mBillDetailList = db.getBillDetailByBillCode(code);
        adapter = new BillDetailAdapter(mBillDetailList, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setOnItemClickListener(new BillDetailAdapter.OnItemClickListener() {
            @Override
            public void onDeleteItemClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xóa Loại Sách")
                        .setMessage("Bạn chắc chắn muốn xóa Loại sách này?")
                        .setPositiveButton("Chấp nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                DatabaseDAO databaseDAO = new DatabaseDAO(getActivity());
                                mBillDetailList = databaseDAO.getAllBillDetailToDelete();

                                try {
                                    mBillDetail m = mBillDetailList.get(position);
                                    int result = databaseDAO.deleteBillDetail(m);
                                    Log.e(null, "result ShowBill: "+result);
                                    if (result > 0){
                                        Toast.makeText(getActivity(),
                                                "Đã xóa. ",Toast.LENGTH_SHORT).show();
                                        onResume();
                                    }else {
                                        Toast.makeText(getActivity(),
                                                "Xóa thất bại",Toast.LENGTH_SHORT).show();
                                    }
                                }catch (IndexOutOfBoundsException e){
                                    Toast.makeText(getActivity(),
                                            "Dữ liệu không tồn tại. \nVui lòng làm mới ứng dụng!!",Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).setNegativeButton("Bỏ qua", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        db = new DatabaseDAO(getActivity());
        mBillDetailList.clear();
        mBillDetailList = db.getBillDetailByBillCode(code);
        adapter.updateData(mBillDetailList);

    }

    private void removeItem(int position) {
        mBillDetailList.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
