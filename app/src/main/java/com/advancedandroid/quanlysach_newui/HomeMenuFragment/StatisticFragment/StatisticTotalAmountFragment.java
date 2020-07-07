package com.advancedandroid.quanlysach_newui.HomeMenuFragment.StatisticFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.advancedandroid.quanlysach_newui.DatabaseDAO.DatabaseDAO;
import com.advancedandroid.quanlysach_newui.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticTotalAmountFragment extends Fragment {

    TextView txtDoanhThuNgay, txtDoanhThuThang, txtDoanhThuNam;

    public StatisticTotalAmountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic_total_amount, container, false);

        txtDoanhThuNgay = view.findViewById(R.id.statisticTotalAmount_day);
        txtDoanhThuThang = view.findViewById(R.id.statisticTotalAmount_month);
        txtDoanhThuNam = view.findViewById(R.id.statisticTotalAmount_year);

        DatabaseDAO db = new DatabaseDAO(getActivity());
        txtDoanhThuNgay.setText(String.valueOf(db.getDoanhThuTheoNgay()));
        txtDoanhThuThang.setText(String.valueOf(db.getDoanhThuTheoThang()));
        txtDoanhThuNam.setText(String.valueOf(db.getDoanhThuTheoNam()));

        return view;
    }

}
