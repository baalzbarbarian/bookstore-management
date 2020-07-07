package com.advancedandroid.quanlysach_newui.RecyclerViewCustom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.advancedandroid.quanlysach_newui.DatabaseDAO.DatabaseDAO;
import com.advancedandroid.quanlysach_newui.R;
import com.advancedandroid.quanlysach_newui.mData.mBillDetail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShowBillAdapter extends RecyclerView.Adapter<ShowBillAdapter.ViewHolder> implements Filterable, View.OnClickListener{

    private List<mBillDetail> mUserList;
    private List<mBillDetail> mUserListFull;
    private OnItemClickListener listener;
    Context context;
    View view;
    DatabaseDAO db;
    String totalAmount;

    public interface OnItemClickListener{
        void onDeleteItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener mListener){
        listener = mListener;
    }

    public ShowBillAdapter(List<mBillDetail> mUserList, Context context) {
        this.mUserList = mUserList;
        this.context = context;
        mUserListFull = new ArrayList<>(mUserList);
    }

    @Override
    public Filter getFilter() {
        return mUserFilter;
    }

    private Filter mUserFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<mBillDetail> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(mUserListFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (mBillDetail item : mUserListFull){
                    if (item.getBillCode().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mUserList.clear();
            mUserList.addAll((Collection<? extends mBillDetail>) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.recylerview_custom_billdetail, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        db = new DatabaseDAO(context);
        holder.txtBookCode.setText(mUserList.get(position).getBookCode());
        holder.txtBookAmount.setText(String.valueOf(mUserList.get(position).getAmount()));
        holder.txtBookPrice.setText(String.valueOf(mUserList.get(position).getUnitPrice()));
        holder.txtTotalAmount.setText(String.valueOf(mUserList.get(position).getTotalAmount()));

        holder.imgDelete.setImageResource(R.drawable.ic_delete);
//        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Xóa Loại Sách")
//                        .setMessage("Bạn chắc chắn muốn xóa Loại sách này?")
//                        .setPositiveButton("Chấp nhận", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                DatabaseDAO databaseDAO = new DatabaseDAO(context);
//                                mUserList = databaseDAO.getAllBillDetailToDelete();
//
//                                try {
//                                    mBillDetail m = mUserList.get(position);
//                                    int result = databaseDAO.deleteBillDetail(m);
//
//                                    if (result > 0){
//                                        notifyItemRemoved(position);
//                                        notifyItemChanged(position);
//                                        updateData(databaseDAO.getAllBillDetailToDelete());
//                                        Toast.makeText(context,
//                                                "Đã xóa. ",Toast.LENGTH_SHORT).show();
//
//                                        totalAmount = String.valueOf(db.getTotalAmountBillDetail(m.getBillCode()));
//
//                                    }else {
//                                        Toast.makeText(context,
//                                                "Xóa thất bại",Toast.LENGTH_SHORT).show();
//
//                                    }
//                                }catch (IndexOutOfBoundsException e){
//                                    Toast.makeText(context,
//                                            "Dữ liệu không tồn tại. \nVui lòng làm mới ứng dụng!!",Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//                        }).setNegativeButton("Bỏ qua", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                }).show();
//
//            }
//        });
//
//        //==== EDIT USER ===//
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                LayoutInflater inflater = LayoutInflater.from(context);
//                View dialogView = inflater.inflate(R.layout.dialog_custom_edit_user, null);
//                builder.setView(dialogView);
//                final AlertDialog dialog = builder.create();
//                dialog.show();
//
//
//                final TextView txtUsernameEdit = dialogView.findViewById(R.id.txtUsernameEdit);
//                final EditText edtNewphonenumber = dialogView.findViewById(R.id.edtEditUserPhone);
//                final EditText edtNewFullname = dialogView.findViewById(R.id.edtEditUserFullName);
//
//                final DatabaseDAO db = new DatabaseDAO(context);
//                mUserList = db.getAllTypes();
//
//                try{
//                    m = mUserList.get(position);
//                    txtUsernameEdit.setText(m.getTypesCode());
//                    edtNewphonenumber.setText(m.getTypesName());
//                    edtNewFullname.setText(m.getTypesLocation());
//                }catch (IndexOutOfBoundsException e){
//                    Toast.makeText(context,
//                            "Dữ liệu đã bị xóa hoặc không tồn tại. " +
//                                    "\nVui lòng làm mới ứng dụng",Toast.LENGTH_LONG).show();
//                }
//
//                final Button btnEdit = dialogView.findViewById(R.id.btnEditUserInfor_Save);
//                btnEdit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View vieww) {
//                        String code = txtUsernameEdit.getText().toString();
//                        String NewPhonenumber = edtNewphonenumber.getText().toString();
//                        String NewFullname = edtNewFullname.getText().toString();
//
//                        db.editTypes(code, NewPhonenumber, NewFullname);
//                        mUserList = db.getAllTypes();
//
//                        holder.txtPhonenumber.setText(mUserList.get(position).getTypesLocation());
//                        holder.txtFullname.setText(mUserList.get(position).getTypesName());
//
//                        notifyItemChanged(position);
////                        Toast.makeText(context, "Dữ liệu đã được thay đổi", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//
//
//                    }
//                });
//                Button btnCancle = dialogView.findViewById(R.id.btnEditUserInfor_Cancel);
//                btnCancle.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtBookCode;
        public TextView txtBookAmount;
        public TextView txtBookPrice;
        public TextView txtTotalAmount;

        public ImageView imgDelete;
        public CardView cardView;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView_BillDetail);
            txtBookCode = itemView.findViewById(R.id.txtBillDetail_bookCode);
            txtBookAmount = itemView.findViewById(R.id.txtBillDetail_Amount);
            txtBookPrice = itemView.findViewById(R.id.txtBillDetail_bookPrice);
            txtTotalAmount = itemView.findViewById(R.id.txtBillDetail_totalAmount);
            imgDelete = itemView.findViewById(R.id.imgBillDetail_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        final int position = getLayoutPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteItemClick(imgDelete, position);

                        }
                    }
                }
            });

        }
    }


    public void updateData(List<mBillDetail> mUsers) {
        if (mUsers == null) {
            return;
        }
        mUserList.clear();
        mUserList.addAll(mUsers);
        notifyDataSetChanged();
    }

    public void clearAdapter() {
        int size = mUserList.size();
        mUserList.clear();
        notifyItemRangeRemoved(0, size);
    }

}
