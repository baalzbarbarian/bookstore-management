package com.advancedandroid.quanlysach_newui.RecyclerViewCustom;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.advancedandroid.quanlysach_newui.DatabaseDAO.DatabaseDAO;
import com.advancedandroid.quanlysach_newui.R;
import com.advancedandroid.quanlysach_newui.mData.mBook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookRecyclerView extends RecyclerView.Adapter<BookRecyclerView.ViewHolder> implements Filterable, View.OnClickListener {

    private List<mBook> mUserList;
    private List<mBook> mUserListFull;
    Context context;
    mBook m;
    View view;
    Spinner spinner;

    private AdapterView.OnItemClickListener itemClickListener;

    public interface OnItemCLicked{
        void onItemCLick(int position);
    }

    public BookRecyclerView(List<mBook> mUserList, Context context) {
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
            List<mBook> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(mUserListFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (mBook item : mUserListFull){
                    if (item.getBookName().toLowerCase().contains(filterPattern)){
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
            mUserList.addAll((Collection<? extends mBook>) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.recyclerview_custom_book, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtBookName.setText(mUserList.get(position).getBookName());
        holder.txtTypesBook.setText(mUserList.get(position).getTypesOfBook());
        holder.txtBookCode.setText("@"+mUserList.get(position).getBookCode());
        holder.txtBookPrice.setText(String.valueOf(mUserList.get(position).getBookPrice()));
        holder.txtBookAmount.setText(String.valueOf(mUserList.get(position).getBookAmount()));

        holder.imgUserDelete.setImageResource(R.drawable.ic_delete);
        holder.imgUserDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa Loại Sách")
                        .setMessage("Bạn chắc chắn muốn xóa Loại sách này?")
                        .setPositiveButton("Chấp nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                DatabaseDAO databaseDAO = new DatabaseDAO(context);
                                mUserList = databaseDAO.getAllBookToDelete();

                                try {
                                    mBook m = mUserList.get(position);
                                    int result = databaseDAO.deleteBook(m);

                                    if (result > 0){
                                        notifyItemRemoved(position);
                                        notifyItemChanged(position);
                                        updateData(databaseDAO.getAllBookToDelete());
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
                View dialogView = inflater.inflate(R.layout.dialog_custom_edit_book, null);
                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();
                dialog.show();


                spinner = dialog.findViewById(R.id.editBook_spinner);
                final EditText edtBookCode = dialogView.findViewById(R.id.editBook_edt1);
                final EditText edtBookName = dialogView.findViewById(R.id.editBook_edt2);
                final EditText edtBookAuthor = dialogView.findViewById(R.id.editBook_edt3);
                final EditText edtBookPrice = dialogView.findViewById(R.id.editBook_edt5);
                final EditText edtBookAmount = dialogView.findViewById(R.id.editBook_edt6);
                setSpinner();

                final DatabaseDAO db = new DatabaseDAO(context);
                mUserList = db.getAllBook();
                m = mUserList.get(position);

                try{
                    edtBookCode.setText(m.getBookCode());
                    edtBookName.setText(m.getBookName());
                    edtBookAuthor.setText(m.getBookAuthor());
                    edtBookPrice.setText(String.valueOf(m.getBookPrice()));
                    edtBookAmount.setText(String.valueOf(m.getBookAmount()));

                }catch (IndexOutOfBoundsException e){
                    Toast.makeText(context,
                            "Dữ liệu đã bị xóa hoặc không tồn tại. " +
                                    "\nVui lòng làm mới ứng dụng",Toast.LENGTH_LONG).show();
                }

                final Button btnEdit = dialogView.findViewById(R.id.editBook_btn2);
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View vieww) {

                        String code = m.getBookCode();
                        String name = edtBookName.getText().toString();
                        String author = edtBookAuthor.getText().toString();
                        String typesOfBook = spinner.getSelectedItem().toString();
                        String price = (edtBookPrice.getText().toString());
                        String amount = (edtBookAmount.getText().toString());

                        if (db.editBook(code, name, author, typesOfBook, price, amount) < 1){
                            Toast.makeText(context, "Thay đổi thất bại", Toast.LENGTH_SHORT).show();
                        }else {
                            mUserList = db.getAllBook();

                            holder.txtBookName.setText(mUserList.get(position).getBookName());
                            holder.txtBookCode.setText("@"+mUserList.get(position).getBookCode());
                            holder.txtBookPrice.setText(String.valueOf(mUserList.get(position).getBookPrice()));
                            holder.txtBookAmount.setText(String.valueOf(mUserList.get(position).getBookAmount()));
                            notifyItemChanged(position);
                            Toast.makeText(context, "Dữ liệu đã được thay đổi", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();

                    }
                });
                Button btnCancle = dialogView.findViewById(R.id.editBook_btn1);
                btnCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    private void setSpinner(){
        DatabaseDAO db = new DatabaseDAO(context);
        List<String> list = db.getTypesName();
        ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtBookName;
        TextView txtTypesBook;
        TextView txtBookCode;
        TextView txtBookPrice;
        TextView txtBookAmount;
        ImageView imgUserDelete;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView_book);
            txtBookName = itemView.findViewById(R.id.txtBook_BookName);
            txtTypesBook = itemView.findViewById(R.id.txtBook_typesBook);
            txtBookCode = itemView.findViewById(R.id.txtBook_BookCode);
            txtBookPrice = itemView.findViewById(R.id.txtBook_BookPrice);
            txtBookAmount = itemView.findViewById(R.id.txtBook_BookAmount);
            imgUserDelete = itemView.findViewById(R.id.imgBook_icDelete);
        }
    }


    void updateData(List<mBook> mUsers) {
        if (mUsers == null) {
            return;
        }
        mUserList.clear();
        mUserList.addAll(mUsers);
        notifyDataSetChanged();
    }


}
