package com.residents.dubaiassetmanagement.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OnboardingAdapter extends PagerAdapter {


    private int[] layouts;
    private LayoutInflater layoutInflator;
    private Context context;

    public OnboardingAdapter(int[] layouts, Context context){
        this.layouts= layouts;
        this.context = context;
        layoutInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }



    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = layoutInflator.inflate(layouts[position], container, false);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view =(View)object;
        container.removeView(view);
    }
}
