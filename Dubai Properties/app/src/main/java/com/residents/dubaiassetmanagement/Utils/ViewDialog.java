package com.residents.dubaiassetmanagement.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.Profile.MeFragment;
import com.residents.dubaiassetmanagement.R;

public class ViewDialog implements View.OnClickListener {


private LinearLayout ll_camera,ll_removePhoto,ll_gallery;
private TextView tv_cancel,tv_ok;

public void showDialog(Activity activity,String message){
    final Dialog dialog = new Dialog(activity);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.profilepic_dialog);
    ll_camera = (LinearLayout) dialog.findViewById(R.id.ll_camera);
    ll_removePhoto = (LinearLayout) dialog.findViewById(R.id.ll_removePhoto);
    ll_gallery = (LinearLayout) dialog.findViewById(R.id.ll_gallery);

    tv_cancel = (TextView) dialog.findViewById(R.id.tv_cancel);
    tv_ok = (TextView) dialog.findViewById(R.id.tv_ok);


    tv_cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
        }
    });
    ll_gallery.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


        }
    });

    dialog.show();
}

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.ll_gallery:

            break;

        case R.id.ll_camera:


            break;

        case R.id.ll_removePhoto:

            break;
    }



    }

}
