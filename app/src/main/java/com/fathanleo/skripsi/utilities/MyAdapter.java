package com.fathanleo.skripsi.utilities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.fathanleo.skripsi.R;
import com.fathanleo.skripsi.activity.ContributeActivity;
import com.fathanleo.skripsi.activity.HistoryActivity;
import com.fathanleo.skripsi.activity.HomeActivity;
import com.flaviofaria.kenburnsview.KenBurnsView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<MyModel> modelArrayList;

    public MyAdapter(Context context, ArrayList<MyModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }


    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_container_location, container, false);

        ImageView imageFirst = view.findViewById(R.id.gambarC);
        TextView bulanPaket = view.findViewById(R.id.bulanC);
        TextView hargaPaket = view.findViewById(R.id.hargaC);

        MyModel model = modelArrayList.get(position);
        String title = model.getTitle();
        String harga = model.getHarga();
        int image = model.getImage();

        imageFirst.setImageResource(image);
        bulanPaket.setText(title);
        hargaPaket.setText(harga);


        container.addView(view, position);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
