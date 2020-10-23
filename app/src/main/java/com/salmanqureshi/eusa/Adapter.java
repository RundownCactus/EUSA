package com.salmanqureshi.eusa;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class Adapter extends PagerAdapter {
    private List<Models> models;
    private LayoutInflater layoutInflater;
    private Context context;


    public Adapter(List<Models> models, Context context) {
        this.models = models;
        this.context = context;
    }


    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.newitem,container,false);

        ImageView imageView;
        TextView textView;
        imageView=view.findViewById(R.id.newimage);
        textView=view.findViewById(R.id.newtitle);

        imageView.setImageResource(models.get(position).getImage());
        textView.setText(models.get(position).getTitle());
        container.addView(view,0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,models.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,ServiceProvidersListView.class);
                context.startActivity(intent);

            }
        });

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}