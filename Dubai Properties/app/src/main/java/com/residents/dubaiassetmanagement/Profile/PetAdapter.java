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

import com.residents.dubaiassetmanagement.Model.PetDetail;
import com.residents.dubaiassetmanagement.R;

import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> {

    private List<PetDetail> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private PetAdapterInterface editListener;
    private DeletePetInterface deletePetInterface;
    public static int t=0;
    Context ctx;
String full1;

    // data is passed into the constructor
    PetAdapter(Context context, List<PetDetail> data, PetAdapterInterface editListeners, DeletePetInterface deletePetInterface) {
        this.editListener = editListeners;
        this.deletePetInterface = deletePetInterface;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.ctx = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_pets, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {



            if (mData.size() > 0) {
                holder.tv_name.setText(mData.get(position).getPetName());
                String full = mData.get(position).getPetType();


                if (full.contains("?")){

                    for (int i=0;i<full.length();i++){
                         full=full.replace("?","");

                    }
                }


                String breed = full.substring(full.lastIndexOf("-")+1);



                String type= full.replaceAll(breed,"");

                holder.tv_pet_type.setText(type.replace("-",""));
                holder.tv_breed.setText(breed);
                holder.petId.setText(mData.get(position).getPetId());

            }

        holder.iv_edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editListener.editPressedPets(mData.get(position).getPetName(), mData.get(position).getPetType(),mData.get(position).getPetBreed(), position, mData.get(position).getPetId());
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
                                deletePetInterface.deletePressedPets(mData.get(position).getPetName(), mData.get(position).getPetType(), mData.get(position).getPetBreed(), position, mData.get(position).getPetId());
                                removeAt(holder.getAdapterPosition());

                            }
                            }).show();

                    if (mData.size() < 1) {
                        mClickListener.onItemClick(v, position);
                    }


            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private EditText tv_name, tv_pet_type,tv_breed,petId;
        private ImageView iv_edit_button, iv_delete_button;

        ViewHolder(View itemView) {
            super(itemView);
            tv_name = (EditText) itemView.findViewById(R.id.tv_name);
            tv_pet_type = (EditText) itemView.findViewById(R.id.tv_pet_type);
            tv_breed = (EditText) itemView.findViewById(R.id.tv_breed);
            petId = (EditText) itemView.findViewById(R.id.petId);
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

    public interface PetAdapterInterface {
        public void editPressedPets(String name, String petType, String breed, int pos, String petId);
    }

    public interface DeletePetInterface {
        public void deletePressedPets(String name, String petType, String breed, int pos, String petId);
    }
    public void removeAt(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size());
    }


}
