package com.example.a19dh1100266_phamngocphu.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.a19dh1100266_phamngocphu.OrderActivity;
import com.example.a19dh1100266_phamngocphu.R;
import com.example.a19dh1100266_phamngocphu.RestaurantDetailActivity;
import com.example.a19dh1100266_phamngocphu.adapter.FoodBasketAdapter;
import com.example.a19dh1100266_phamngocphu.model.Basket;
import com.example.a19dh1100266_phamngocphu.model.FoodBasket;

import java.util.ArrayList;

public class BasketDialogFragment extends DialogFragment implements  View.OnClickListener {
    public TextView tvTotal;
    public RecyclerView rvFoods;
    public Basket basket;
    public  FoodBasketAdapter adapter;
    public Button btnPlaceOrder;



    public BasketDialogFragment() {
    }

    @SuppressLint("ValidFragment")
    public BasketDialogFragment(Basket basket) {
        this.basket = basket;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basket_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTotal = view.findViewById(R.id.tvTotal);
        tvTotal.setText(basket.getTotalPrice()+"");
        rvFoods = view.findViewById(R.id.rvFoods);
        // dang fix
       adapter = new FoodBasketAdapter(new ArrayList<FoodBasket>(basket.foods.values()));
        rvFoods.setAdapter(adapter);
        rvFoods.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        btnPlaceOrder = view.findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(this);


    }

    @Override
    public void onResume() {
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().setCancelable(true);
        super.onResume();
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPlaceOrder) {
            if (basket.getTotalItem() > 0) {
                Intent intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra("basket", basket);
                startActivity(intent);
                getActivity().finish();
                getDialog().dismiss();
            } else {
                getDialog().dismiss();
            }
        }


        int id = v.getId();


    }


    @Override
    public void onCancel(DialogInterface dialog) {
        ((RestaurantDetailActivity) getActivity()).updateBasket();


        super.onCancel(dialog);
    }
}
