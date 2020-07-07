package com.advancedandroid.quanlysach_newui.HomeMenuFragment.BillFragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advancedandroid.quanlysach_newui.DatabaseDAO.DatabaseDAO;
import com.advancedandroid.quanlysach_newui.R;
import com.advancedandroid.quanlysach_newui.RecyclerViewCustom.BillDetailAdapter;
import com.advancedandroid.quanlysach_newui.mData.mBillDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillDetailFragment extends Fragment {

    private View view;
    String code;
    protected List<mBillDetail> mBillDetailList;
    mBillDetail m;
    DatabaseDAO db;
    BillDetailAdapter adapter;
    RecyclerView recyclerView;
    Button btnAddBillDetail, btnShowBillDetail;
    EditText edt1, edt2;
    TextView txtTotalAmount, btnShowBillDetailList;

    private final static String TAG = "BillDetailFragment";

    public BillDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bill_detail, container, false);
        code = getArguments().getString("CODE");

        initView();

        btnShowBillDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBillDetailToCart();
            }
        });
        btnAddBillDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addListToDatabase();
            }
        });
        btnShowBillDetailList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new ShowBillDetailListFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack("billfragment").commit();
                Bundle bundle=new Bundle();
                bundle.putString("CODE",code);
                myFragment.setArguments(bundle);
            }
        });

        return view;
    }

    private void addListToDatabase(){
        db = new DatabaseDAO(getActivity());
        for (int i = 0; i < mBillDetailList.size(); i++){
            m = mBillDetailList.get(i);
            if (db.addBillDetail(m) == false){
                Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }else {

                //Get book Amount in inventory
                int sumBookAmountInInventory = db.getAmountBookByBookCodeInInventory(mBillDetailList.get(i).getBookCode());

                //Get book amount in cart
                int bookAmountInCart = 0;
                bookAmountInCart = bookAmountInCart + m.getAmount();

                //Set book amount after pay
                int amountBookAfterPay = sumBookAmountInInventory - bookAmountInCart;

                if (amountBookAfterPay >= 0){
                    Toast.makeText(getActivity(), "Thanh toán thành công", Toast.LENGTH_SHORT).show();

                    //update book amount
                    db.updateAmountBook(m.getBookCode(), amountBookAfterPay);
                }else {
                    Toast.makeText(getActivity(), "Mã sách "+m.getBookCode()+" tồn kho "+sumBookAmountInInventory+"\nGiảm số lượng hoặc đặt mã sách khác", Toast.LENGTH_LONG).show();
                }

            }
        }

        removeAllItem(0, mBillDetailList.size());
        txtTotalAmount.setText("0 VNĐ");
    }

    private void addBillDetailToCart() {

        if (edt1.getText().toString().isEmpty() || edt2.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
        }else if (Integer.parseInt(edt2.getText().toString()) < 1){
            Toast.makeText(getActivity(), "Số lượng sách ít nhất là 1 quyển", Toast.LENGTH_SHORT).show();
        }else {
            String bookCode = edt1.getText().toString();
            int bookAmount = Integer.parseInt(edt2.getText().toString());
            double unitPrice = db.getBookPriceByCode(bookCode);
            double totalAmount = bookAmount*unitPrice;

            int sumBookAmountInInventory = db.getAmountBookByBookCodeInInventory(bookCode);
//            int bookAmountInBillDetail = db.getAmountBookByBookCodeInBillDetail(bookCode);
//            int bookAmountInCart = 0;
//            int n = 0;
//            while(n < mBillDetailList.size()){
//                bookAmountInCart = bookAmountInCart + mBillDetailList.get(n).getAmount();
//                n++;
//            }

            if ((db.checkExist(bookCode)) == false){
                Toast.makeText(getActivity(), "Mã sách không tồn tại.", Toast.LENGTH_SHORT).show();
            }else if (bookAmount > sumBookAmountInInventory){
                Toast.makeText(getActivity(), "Sách tồn kho của mã sách "+bookCode+" là "+sumBookAmountInInventory+".\nVui lòng kiểm tra lại", Toast.LENGTH_LONG).show();
            }else {
                int check = checkMaSach(mBillDetailList, bookCode);
                if (check >= 0){

                    //If book code already exists in list then excute this method
                    int soLuong = mBillDetailList.get(check).getAmount();
                    mBillDetail m = new mBillDetail();
                    m.setBillCode(code);
                    m.setBookCode(bookCode);
                    m.setAmount(soLuong + bookAmount);
                    m.setUnitPrice(unitPrice);

                    double totalAmount2 = m.getUnitPrice()*m.getAmount();

                    m.setTotalAmount(totalAmount2);
                    mBillDetailList.set(check, m);

                    adapter = new BillDetailAdapter(mBillDetailList, getActivity());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(layoutManager);

                    //Set total amount to TextView
                    double totalAmountToTextView = 0;
                    for (int i = 0; i < mBillDetailList.size(); i++){
                        totalAmountToTextView = totalAmountToTextView + mBillDetailList.get(i).getTotalAmount();
                        txtTotalAmount.setText(totalAmountToTextView + " VNĐ");
                    }

                    edt1.getText().clear();
                    edt2.getText().clear();

                }else {

                    //else excute here
                    m = new mBillDetail(code, bookCode, bookAmount, unitPrice, totalAmount);
                    mBillDetailList.add(m);
                    adapter = new BillDetailAdapter(mBillDetailList, getActivity());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(layoutManager);

                    //Set total amount to textview
                    double totalAmountToTextView = 0;
                    for (int i = 0; i < mBillDetailList.size(); i++){
                        totalAmountToTextView = totalAmountToTextView + mBillDetailList.get(i).getTotalAmount();
                        txtTotalAmount.setText(totalAmountToTextView + " VNĐ");
                    }

                    edt1.getText().clear();
                    edt2.getText().clear();
                }


            }

        }

    }



    private int checkMaSach(List<mBillDetail> checkList, String bookCode){
        int pos = -1;
        for (int i = 0; i < checkList.size(); i++){
            mBillDetail hd = checkList.get(i);
            if (hd.getBookCode().equalsIgnoreCase(bookCode)){
                pos = i;
                break;
            }
        }
        Log.e(TAG, "checkMaSach: "+pos);
        return pos;
    }

    private void initView(){

        btnShowBillDetailList = view.findViewById(R.id.btnBillDetail_Show);
        txtTotalAmount = view.findViewById(R.id.txtTotalAmount);
        edt1 = view.findViewById(R.id.edtBillDetail_bookCode);
        edt2 = view.findViewById(R.id.edtBillDetail_bookAmount);
        btnAddBillDetail = view.findViewById(R.id.btnAddBillDetail);
        btnShowBillDetail = view.findViewById(R.id.btnShowBillDetail);

        recyclerView = view.findViewById(R.id.recyclerView_BillDetail);
        mBillDetailList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new BillDetailAdapter(mBillDetailList, getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BillDetailAdapter.OnItemClickListener() {
            @Override
            public void onDeleteItemClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xóa Loại Sách")
                        .setMessage("Bạn chắc chắn muốn xóa Loại sách này?")
                        .setPositiveButton("Chấp nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeItem(position);
                                adapter.notifyDataSetChanged();

                                double totalAmount = 0;
                                for (int j = 0; j < mBillDetailList.size(); j++){
                                    mBillDetail m = mBillDetailList.get(j);
                                    totalAmount = totalAmount + m.getTotalAmount();
                                }
                                txtTotalAmount.setText(String.valueOf(totalAmount));

                            }
                        }).setNegativeButton("Bỏ qua", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

    }

    private void removeItem(int position) {
        mBillDetailList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private void removeAllItem(int position, int listSize) {
        mBillDetailList.clear();
        adapter.notifyItemRangeChanged(position, listSize);
    }

    @Override
    public void onResume() {
        super.onResume();
        db = new DatabaseDAO(getActivity());
        mBillDetailList.clear();
        adapter.updateData(mBillDetailList);
    }

}
