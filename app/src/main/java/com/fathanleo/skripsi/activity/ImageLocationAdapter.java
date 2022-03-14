package com.fathanleo.skripsi.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fathanleo.skripsi.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageLocationAdapter extends RecyclerView.Adapter<ImageLocationAdapter.ImageLocationViewHolder> {

    private List<ImageLocation> imageLocationList;

    public ImageLocationAdapter(List<ImageLocation> imageLocationList) {
        this.imageLocationList = imageLocationList;
    }

    @NonNull
    @Override
    public ImageLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageLocationViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_versi_panjang,parent,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ImageLocationViewHolder holder, int position) {
        holder.setLocationData(imageLocationList.get(position));
    }

    @Override
    public int getItemCount() {

        return imageLocationList.size();
    }

    static class ImageLocationViewHolder extends RecyclerView.ViewHolder {

        private KenBurnsView gambarTerjadi;
        private TextView textTitle, textHarga;

        public ImageLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            gambarTerjadi = itemView.findViewById(R.id.gambarCC);
            textTitle = itemView.findViewById(R.id.bulanCC);
            textHarga = itemView.findViewById(R.id.hargaCC);
        }

        void setLocationData(ImageLocation imageLocation){
            Picasso.get().load(imageLocation.imageUrl).into(gambarTerjadi);
            textTitle.setText(imageLocation.title);
            textHarga.setText(imageLocation.harga);
        }
    }
}
