package com.residents.dubaiassetmanagement.notifications_dashboard;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.residents.dubaiassetmanagement.notifications_dashboard.models.NotificationData;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends FragmentStatePagerAdapter {
    private Context ctx;
    private ArrayList<NotificationData> data;
    private Fragment[] fragments;
    private LayoutInflater inflater;
    String photo_url, isSeen;
    int count=0;
    public NotificationAdapter(Context ctx, FragmentManager fm, ArrayList<NotificationData> data) {
        super(fm);
        this.ctx = ctx;
        this.data = data;
        fragments = new Fragment[data.size()];
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        String description = data.get(position).description;
        String title = data.get(position).title;
         photo_url = data.get(position).photo_url;
         isSeen = data.get(position).isSeen;
    count++;
    DashboardNotificationsFragment dinamisFragment = new DashboardNotificationsFragment();
    dinamisFragment.setDetail(description);
    dinamisFragment.setTitle(title + count);
    dinamisFragment.setPhotoUrl(photo_url);

    fragment = dinamisFragment;


    if (fragments[position] == null) {
        fragments[position] = fragment;
    }
    return fragments[position];

    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }
}