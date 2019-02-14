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

public class HouseHelpAdapter extends RecyclerView.Adapter<HouseHelpAdapter.ViewHolder> {

    private List<HouseHelp> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private HouseAdapterInterface editListener;
    private DeleteHouseHelp deleteHouseHelp;
    ArrayList<OccupantDetail> maidList = new ArrayList<>();
private Context ctx;

    // data is passed into the constructor
    HouseHelpAdapter(Context context, List<HouseHelp> data, HouseAdapterInterface editListeners, DeleteHouseHelp deleteHouseHelp) {
        this.editListener = editListeners;
        this.deleteHouseHelp = deleteHouseHelp;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.ctx = context;
    }
    // data is passed into the constructor
    HouseHelpAdapter(Context context, ArrayList<OccupantDetail> data, HouseAdapterInterface editListeners, DeleteHouseHelp deleteHouseHelp) {
        this.editListener = editListeners;
        this.deleteHouseHelp = deleteHouseHelp;
        this.mInflater = LayoutInflater.from(context);
        this.maidList = data;
        this.ctx = context;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_house_help, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


    /*    if (mData.get(position).getPosition() == position) {
            holder.tv_name.setText(mData.get(position).getName()+" "+ mData.get(position).getLastName());
            holder.tv_contact.setText(mData.get(position).getContact());
            holder.tv_occupantId.setText(mData.get(position).getOccupanId());

        } else {
            if (mData.size() > 0) {
                holder.tv_name.setText(mData.get(position).getName()+" "+ mData.get(position).getLastName());
                holder.tv_contact.setText(mData.get(position).getContact());
                holder.tv_occupantId.setText(mData.get(position).getOccupanId());

            }
        }*/

        holder.tv_name.setText(maidList.get(position).getOccupantName());
        holder.tv_contact.setText(maidList.get(position).getOccupantMobile());
        holder.tv_occupantId.setText(maidList.get(position).getOccupantId());

        holder.iv_edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullName = maidList.get(position).getOccupantName();
                String lastName = fullName.substring(fullName.lastIndexOf(" ")+1);
                String firstName= fullName.replaceAll(lastName,"");

if (firstName.equalsIgnoreCase(" ")){
    firstName=lastName;
}
                editListener.editPressedHouse(firstName,lastName, maidList.get(position).getOccupantMobile(),position,maidList.get(position).getOccupantId());

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
                String fullName = maidList.get(position).getOccupantName();
                String lastName = fullName.substring(fullName.lastIndexOf(" ")+1);
                String firstName= fullName.replaceAll(lastName,"");

                deleteHouseHelp.deletePressedHouse(firstName, lastName, maidList.get(position).getOccupantMobile(), position,maidList.get(position).getOccupantId());
                    removeAt(position);
                            }
                        }).show();

                if (maidList.size() < 1) {
                        mClickListener.onItemClick(v, position);
                    }
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return maidList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private EditText tv_name, tv_contact,tv_occupantId;
        private ImageView iv_edit_button, iv_delete_button;

        ViewHolder(View itemView) {
            super(itemView);
            tv_name = (EditText) itemView.findViewById(R.id.tv_name);
            tv_contact = (EditText) itemView.findViewById(R.id.tv_contact);
            iv_edit_button = (ImageView) itemView.findViewById(R.id.iv_edit_button);
            iv_delete_button = (ImageView) itemView.findViewById(R.id.iv_delete_button);
            tv_occupantId = (EditText) itemView.findViewById(R.id.tv_occupantId);
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

    public interface HouseAdapterInterface {
        public void editPressedHouse(String firstName,String lastName, String contact, int pos,String occupantId);
    }
    public interface DeleteHouseHelp {
        public void deletePressedHouse(String firstName,String lastName, String contact, int pos,String occupantId);
    }

    public void removeAt(int position) {
        maidList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, maidList.size());
    }

}