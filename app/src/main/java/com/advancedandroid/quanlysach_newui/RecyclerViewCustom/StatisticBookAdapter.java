package com.advancedandroid.quanlysach_newui.RecyclerViewCustom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.advancedandroid.quanlysach_newui.R;
import com.advancedandroid.quanlysach_newui.mData.mBook;

import java.util.ArrayList;
import java.util.List;

public class StatisticBookAdapter extends RecyclerView.Adapter<StatisticBookAdapter.ViewHolder>{

    private List<mBook> mUserList;
    private List<mBook> mUserListFull;
//    FragmentCommunication mCommunicator;
//    FragmentCommunication mCommunication;
    Context context;
    View view;


    public StatisticBookAdapter(List<mBook> mUserList, Context context) {
        this.mUserList = mUserList;
        this.context = context;
//        this.mCommunicator = mCommunication;
        mUserListFull = new ArrayList<>(mUserList);
    }

//    @Override
//    public Filter getFilter() {
//        return mUserFilter;
//    }
//
//    private Filter mUserFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<mBook> filteredList = new ArrayList<>();
//
//            if (constraint == null || constraint.length() == 0){
//                filteredList.addAll(mUserListFull);
//            }else {
//                String filterPattern = constraint.toString().toLowerCase().trim();
//                for (mBook item : mUserListFull){
//                    if (item.getBillCode().toLowerCase().contains(filterPattern)){
//                        filteredList.add(item);
//                    }
//                }
//            }
//
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            mUserList.clear();
//            mUserList.addAll((Collection<? extends mBill>) results.values);
//            notifyDataSetChanged();
//        }
//    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.recyclerview_custom_statistic_book, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txt1.setText(mUserList.get(position).getBookCode());
        holder.txt2.setText(String.valueOf(mUserList.get(position).getBookAmount()));

//        holder.imgBillDelete.setImageResource(R.drawable.ic_delete);
//        holder.imgBillDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Xóa hóa đơn")
//                        .setMessage("Xóa Bill sẽ xóa tất cả Bill Detail cùng mã.\nVẫn xóa?")
//                        .setPositiveButton("Chấp nhận", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                DatabaseDAO databaseDAO = new DatabaseDAO(context);
//                                mUserList = databaseDAO.getAllBillToDelete();
//
//                                try {
//                                    mBill m = mUserList.get(position);
//                                    int result = databaseDAO.deleteBill(m);
//                                    int result2 = databaseDAO.deleteBillWithCode(m.getBillCode());
//
//                                    if (result > 0 && result2 > 0){
//                                        notifyItemRemoved(position);
//                                        notifyItemChanged(position);
//                                        updateData(databaseDAO.getAllBillToDelete());
//                                        Toast.makeText(context,
//                                                "Đã xóa. ",Toast.LENGTH_SHORT).show();
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
//
//                    }
//                }).show();
//            }
//        });
//
//
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                Fragment myFragment = new BillDetailFragment();
////                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack("billfragment").commit();
//                activity.getSupportFragmentManager().beginTransaction().commit();
//                mBill m = mUserList.get(position);
//                String code = m.getBillCode();
//                Log.e(null, "codeAdapter: "+code );
//
//                mCommunication.respond(holder.getAdapterPosition(),mUserList.get(holder.getAdapterPosition()).getBillCode());
//
//                Bundle bundle=new Bundle();
//                bundle.putString("CODE",code);
//                myFragment.setArguments(bundle);
////
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt1;
        TextView txt2;
//        ImageView imgBillDelete;
//        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            cardView = itemView.findViewById(R.id.cardView_Bill);
            txt1 = itemView.findViewById(R.id.txt1);
            txt2 = itemView.findViewById(R.id.txt2);
//            imgBillDelete = itemView.findViewById(R.id.imgDelete);
//            mCommunication = mCommunicator;
        }
    }


//    void updateData(List<mBill> mUsers) {
//        if (mUsers == null) {
//            return;
//        }
//        mUserList.clear();
//        mUserList.addAll(mUsers);
//        notifyDataSetChanged();
//    }

}

