package com.example.a19dh1100266_phamngocphu.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a19dh1100266_phamngocphu.R;
import com.example.a19dh1100266_phamngocphu.model.Basket;
import com.example.a19dh1100266_phamngocphu.model.FoodBasket;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodBasketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    public interface OnFoodBasketItemClickListener {
        void onFoodBasketItemListener(FoodBasket foodBasket);
    }
    ArrayList<FoodBasket> baskets;
    public FoodBasketAdapter(ArrayList<FoodBasket> baskets )
    {
        this.baskets = baskets;
    }
    public class ViewHolderFoodBasket extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvOpenHour, tvID, tvDate, tvUserName;
        ImageView ivImage;

        public ViewHolderFoodBasket(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvOpenHour = itemView.findViewById(R.id.tvOpenHour);
            tvID = itemView.findViewById(R.id.tvID);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvUserName = itemView.findViewById(R.id.tvUserName);
        }
    }

    private List<FoodBasket> foodBaskets;
    private OnFoodBasketItemClickListener onFoodBasketItemClickListener;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_food_basket, parent, false);
        return new ViewHolderFoodBasket(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FoodBasket foodBasket = foodBaskets.get(position);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        FoodBasketAdapter.ViewHolderFoodBasket viewHolderFoodBasket = (FoodBasketAdapter.ViewHolderFoodBasket) holder;
        StorageReference profileRef = storageReference.child("foodBaskets/"+ foodBasket.getImage());
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(viewHolderFoodBasket.ivImage);
            }
        });


        viewHolderFoodBasket.tvName.setText(foodBasket.getName());
        viewHolderFoodBasket.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFoodBasketItemClickListener.onFoodBasketItemListener(foodBasket);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodBaskets.size();
    }
}
