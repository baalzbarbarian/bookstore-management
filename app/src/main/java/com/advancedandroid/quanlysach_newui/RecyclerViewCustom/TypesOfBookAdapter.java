package com.advancedandroid.quanlysach_newui.RecyclerViewCustom;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.advancedandroid.quanlysach_newui.DatabaseDAO.DatabaseDAO;
import com.advancedandroid.quanlysach_newui.R;
import com.advancedandroid.quanlysach_newui.mData.mTypesOfBook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TypesOfBookAdapter extends RecyclerView.Adapter<TypesOfBookAdapter.ViewHolder> implements Filterable, View.OnClickListener {

    private List<mTypesOfBook> mUserList;
    private List<mTypesOfBook> mUserListFull;
    Context context;
    mTypesOfBook m;
    View view;
    private AdapterView.OnItemClickListener itemClickListener;

    public interface OnItemCLicked{
        void onItemCLick(int position);
    }

    public TypesOfBookAdapter(List<mTypesOfBook> mUserList, Context context) {
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
            List<mTypesOfBook> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(mUserListFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (mTypesOfBook item : mUserListFull){
                    if (item.getTypesName().toLowerCase().contains(filterPattern)){
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
            mUserList.addAll((Collection<? extends mTypesOfBook>) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.recyclerview_custom_add_types, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtFullname.setText(mUserList.get(position).getTypesName());
        holder.txtUsername.setText("@"+mUserList.get(position).getTypesCode());
        holder.txtPhonenumber.setText(mUserList.get(position).getTypesLocation());

        holder.imgUserDelete.setImageResource(R.drawable.ic_delete);
        holder.imgUserDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa Loại Sách !")
                        .setMessage("Xóa loại sách sẽ xóa tất cả các sách cùng loại. Vẫn xóa ?")
                        .setPositiveButton("Chấp nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                DatabaseDAO databaseDAO = new DatabaseDAO(context);
                                mUserList = databaseDAO.getAllTypesToDelete();

                                try {
                                    mTypesOfBook m = mUserList.get(position);
                                    int result = databaseDAO.deleteTypes(m);

                                    if (result > 0){
                                        notifyItemRemoved(position);
                                        notifyItemChanged(position);
                                        updateData(databaseDAO.getAllTypes());
                                        databaseDAO.deleteBookWithTypesName(m.getTypesName());
                                        Toast.makeText(context,
                                                "Đã xóa. ",Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(context,
                                                "Xóa thất bại",Toast.LENGTH_SHORT).show();

                                    }
                                }catch (IndexOutOfBoundsException e){
                                    Toast.makeText(context,
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

        //==== EDIT USER ===//
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.dialog_custom_edit_user, null);
                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();
                dialog.show();


                final TextView txtUsernameEdit = dialogView.findViewById(R.id.txtUsernameEdit);
                final EditText edtNewphonenumber = dialogView.findViewById(R.id.edtEditUserPhone);
                final EditText edtNewFullname = dialogView.findViewById(R.id.edtEditUserFullName);

                final DatabaseDAO db = new DatabaseDAO(context);
                mUserList = db.getAllTypes();

                try{
                    m = mUserList.get(position);
                    txtUsernameEdit.setText(m.getTypesCode());
                    edtNewphonenumber.setText(m.getTypesName());
                    edtNewFullname.setText(m.getTypesLocation());
                }catch (IndexOutOfBoundsException e){
                    Toast.makeText(context,
                            "Dữ liệu đã bị xóa hoặc không tồn tại. " +
                                    "\nVui lòng làm mới ứng dụng",Toast.LENGTH_LONG).show();
                }

                final Button btnEdit = dialogView.findViewById(R.id.btnEditUserInfor_Save);
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View vieww) {
                        String code = txtUsernameEdit.getText().toString();
                        String NewPhonenumber = edtNewphonenumber.getText().toString();
                        String NewFullname = edtNewFullname.getText().toString();

                        db.editTypes(code, NewPhonenumber, NewFullname);
                        mUserList = db.getAllTypes();

                        holder.txtPhonenumber.setText(mUserList.get(position).getTypesLocation());
                        holder.txtFullname.setText(mUserList.get(position).getTypesName());

                        notifyItemChanged(position);
//                        Toast.makeText(context, "Dữ liệu đã được thay đổi", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();


                    }
                });
                Button btnCancle = dialogView.findViewById(R.id.btnEditUserInfor_Cancel);
                btnCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtUsername;
        TextView txtFullname;
        //        TextView txtEmail;
        TextView txtPhonenumber;
        ImageView imgUserDelete;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.itemUser);
            txtFullname = itemView.findViewById(R.id.txtUser_Fullname);
            txtUsername = itemView.findViewById(R.id.txtUser_username);
            txtPhonenumber = itemView.findViewById(R.id.txtUser_phonenumber);
//            txtEmail = itemView.findViewById(R.id.txtUser_email);
            imgUserDelete = itemView.findViewById(R.id.imgUser_delete);
        }
    }


    public void updateData(List<mTypesOfBook> mUsers) {
        if (mUsers == null) {
            return;
        }
        mUserList.clear();
        mUserList.addAll(mUsers);
        notifyDataSetChanged();
    }


}
