package com.residents.dubaiassetmanagement.Profile;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.R;

import java.util.ArrayList;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> Name1= new ArrayList<>();
    private ArrayList<String> Name2= new ArrayList<>();
    private ArrayList<String> Name3= new ArrayList<>();
    private ArrayList<String> Name4= new ArrayList<>();
    private DependantsFragment mContext;

    public recyclerViewAdapter(ArrayList<String> name1, ArrayList<String> name2, ArrayList<String> name3, ArrayList<String> name4, DependantsFragment mContext) {
        Name1 = name1;
        Name2 = name2;
        Name3 = name3;
        Name4 = name4;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dependents, viewGroup, false);
        ViewHolder holder= new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.i(TAG, "onBindViewHolder called");
        viewHolder.Name.setText(Name1.get(i));
        viewHolder.Relationship.setText(Name2.get(i));
        viewHolder.Mobile.setText(Name3.get(i));
        viewHolder.Email.setText(Name4.get(i));

    }

    @Override
    public int getItemCount() {
        return Name1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

         TextView Name;
         TextView Relationship;
         TextView Email;
         TextView Mobile;
         RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.dependants_textView_name);
            Relationship=itemView.findViewById(R.id.dependants_textView_Relationship);
            Email=itemView.findViewById(R.id.dependants_textView_Email);
            Mobile=itemView.findViewById(R.id.dependants_textView_mobile);
            relativeLayout=itemView.findViewById(R.id.parent_listItem);
        }
    }

}
