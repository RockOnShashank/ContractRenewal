package com.residents.dubaiassetmanagement.Profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.residents.dubaiassetmanagement.Model.OccupantDetail;
import com.residents.dubaiassetmanagement.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<FamilyInfo> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private AdapterInterface editListener;
    private DeleteOccupant deleteOccupant;
    private Context ctx;
    ArrayList<OccupantDetail> tenantDetailsList = new ArrayList<>();
    private int size;

    // for adding
    MyRecyclerViewAdapter(Context context, List<FamilyInfo> data, AdapterInterface editListeners, DeleteOccupant deleteOccupant) {
        this.editListener = editListeners;
        this.deleteOccupant = deleteOccupant;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.size = mData.size();
        this.ctx = context;
    }
    // at starting
    MyRecyclerViewAdapter(Context context, ArrayList<OccupantDetail> data, AdapterInterface editListeners, DeleteOccupant deleteOccupant) {
        this.editListener = editListeners;
        this.deleteOccupant = deleteOccupant;
        this.tenantDetailsList = data;
        this.mInflater = LayoutInflater.from(context);
        this.ctx = context;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_family_dependants, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

/*if (mData!=null) {
    if (mData.get(position).getPos() == position) {


            holder.tv_full_name.setText(mData.get(position).getFirstName() + " " + mData.get(position).getLastName());
            holder.tv_relationship.setText(mData.get(position).getRelationship());
            holder.tv_email.setText(mData.get(position).getEmail());
            holder.tv_phone_number.setText(mData.get(position).getMobileNumber());
            holder.occupantId.setText(mData.get(position).getOccupantId());

    } else {
        if (mData.size() > 0) {

                holder.tv_full_name.setText(mData.get(position).getFirstName() + " " + mData.get(position).getLastName());
                holder.tv_relationship.setText(mData.get(position).getRelationship());
                holder.tv_email.setText(mData.get(position).getEmail());
                holder.tv_phone_number.setText(mData.get(position).getMobileNumber());
                holder.occupantId.setText(mData.get(position).getOccupantId());
            }

    }
}else {*/


            holder.tv_relationship.setText(tenantDetailsList.get(position).getOccupantRelation());
            holder.tv_full_name.setText(tenantDetailsList.get(position).getOccupantName());
            if (tenantDetailsList.get(position).getOccupantEmail() != null) {
                holder.tv_email.setText(tenantDetailsList.get(position).getOccupantEmail());
            }
            holder.tv_phone_number.setText(tenantDetailsList.get(position).getOccupantMobile());
            holder.occupantId.setText(tenantDetailsList.get(position).getOccupantId());



        holder.iv_edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullName = tenantDetailsList.get(position).getOccupantName();
                String lastName = fullName.substring(fullName.lastIndexOf(" ")+1);

if (lastName.contains(")")|| lastName.contains("(") || lastName.contains("'")){
lastName = replaceString(lastName);
}
                String firstName= fullName.replaceAll(lastName,"");
                if (firstName.equalsIgnoreCase(" ")){
                    firstName = lastName;
                }
                editListener.editPressed(firstName, lastName, tenantDetailsList.get(position).getOccupantRelation(), tenantDetailsList.get(position).getOccupantEmail(), tenantDetailsList.get(position).getOccupantMobile(), position,tenantDetailsList.get(position).getOccupantId());
                removeAt(holder.getAdapterPosition());
            }
        });
        holder.iv_delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
                dialog.setTitle( "" )
                        .setMessage("Are you sure you want to delete?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }})
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                String fullName = tenantDetailsList.get(position).getOccupantName();
                String lastName = fullName.substring(fullName.lastIndexOf(" ")+1);
                String firstName= fullName.replaceAll(lastName,"");
//delete
        deleteOccupant.deletePressed(firstName,lastName,tenantDetailsList.get(position).getOccupantRelation(), tenantDetailsList.get(position).getOccupantEmail(),tenantDetailsList.get(position).getOccupantMobile(), position,tenantDetailsList.get(position).getOccupantId());

        //
                 removeAt(holder.getAdapterPosition());

                            }
                        }).show();

                //removeAt(holder.getAdapterPosition());
                    if (tenantDetailsList.size() < 1) {
                        mClickListener.onItemClick(v, position);
                    }

            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {

            return tenantDetailsList.size();

        }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private EditText tv_full_name, tv_relationship, tv_email, tv_phone_number,occupantId;
        private ImageView iv_edit_button, iv_delete_button;

        ViewHolder(View itemView) {
            super(itemView);
            tv_full_name = (EditText) itemView.findViewById(R.id.tv_full_name);
            tv_relationship = (EditText) itemView.findViewById(R.id.tv_relationship);
            tv_email = (EditText) itemView.findViewById(R.id.tv_email);
            tv_phone_number = (EditText) itemView.findViewById(R.id.tv_phone_number);
            occupantId = (EditText) itemView.findViewById(R.id.occupantId);
            iv_edit_button = (ImageView) itemView.findViewById(R.id.iv_edit_button);
            iv_delete_button = (ImageView) itemView.findViewById(R.id.iv_delete_button);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    /*String getItem(int id) {
        return mData.get(id);
    }*/

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface AdapterInterface {
        public void editPressed(String firstName, String lastName, String relationship, String email, String phoneNumber, int pos, String occupantId);
    }
    public interface DeleteOccupant {
        public void deletePressed(String firstName, String lastName, String relationship, String email, String phoneNumber, int pos, String occupantId);
    }
    public void removeAt(int position) {
        tenantDetailsList.remove(position);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tenantDetailsList.size());
    }
    String replaceString(String string) {
        return string.replaceAll("\\(|\\)","");
    }
}