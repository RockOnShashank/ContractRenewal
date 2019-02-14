package com.residents.dubaiassetmanagement.change_password;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeFragment;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePasswordFragment extends Fragment implements ResponseCallback {
    private View view;
    private EditText et_password,et_password1,et_confirm;
    private Button submit;
    private SavePreference mSavePreference;
    private TextView text_view_error_message,fragmentTitle;
    private String response = null;
    private ImageView iv_1,iv_2,iv_3;
    private RelativeLayout iv_bell_icon;
    private BottomNavigationView bottomNavigation;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_change_pass, container, false);
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("CHANGE PASSWORD");


        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.GONE);
        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);
        et_confirm = (EditText) view.findViewById(R.id.et_confirm);
        et_password = (EditText) view.findViewById(R.id.et_password);
        et_password1 = (EditText) view.findViewById(R.id.et_password1);
        text_view_error_message = (TextView) view.findViewById(R.id.text_view_error_message);

        et_password1.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });

        et_confirm.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });

        iv_1 = (ImageView)  view.findViewById(R.id.iv_1);
        iv_2 = (ImageView) view.findViewById(R.id.iv_2);
        iv_3 = (ImageView) view.findViewById(R.id.iv_3);
submit = (Button) view.findViewById(R.id.button_login);

mSavePreference = SavePreference.getInstance(getActivity());
        ((DrawerLocker)getActivity()).setDrawerEnabled(true);

        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // getFragmentManager().popBackStack();
                loadFragment(new HomeFragment());
            }
        });
submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

if (et_password1.getText().toString().equalsIgnoreCase(et_confirm.getText().toString())) {
    if (!et_password.getText().toString().equalsIgnoreCase(et_password1.getText().toString())) {
        if (Password_Validation(et_password1.getText().toString())) {
            text_view_error_message.setVisibility(View.GONE);

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("tCode", "" + mSavePreference.getString(IpreferenceKey.TCODE));
                jsonObject.put("OldPassword", et_password.getText().toString());
                jsonObject.put("NewPassword", et_confirm.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new RequestService(getActivity(), ChangePasswordFragment.this).postRequest("/MyDocuments/ChangePassword", jsonObject);
        } else {
            Toast.makeText(getActivity(), "Password Policy does not match.", Toast.LENGTH_LONG).show();

        }
    } else {
        text_view_error_message.setText("New password cannot be same as the old password");

        text_view_error_message.setVisibility(View.VISIBLE);
    }
}else {
    text_view_error_message.setVisibility(View.VISIBLE);

}

    }
});
iv_1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        ViewPassword();
    }
});

iv_2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        ViewPassword1();
    }
});

        iv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPassword2();
            }
        });


        return  view;
    }
    // Toggle View Password

    public void ViewPassword() {


        String uri = "@drawable/ic_cancel_view_password";
        String uri2 = "@drawable/viewpass_login";

        int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());
        int imageResourse2 = getResources().getIdentifier(uri2, null, getActivity().getPackageName());

        Drawable res = getResources().getDrawable(imageResource);
        Drawable res2 = getResources().getDrawable(imageResourse2);


        if (et_password.getInputType() != InputType.TYPE_CLASS_TEXT) {

            et_password.setInputType(InputType.TYPE_CLASS_TEXT);

            et_password.setSelection(et_password.getText().length());

            iv_1.setImageDrawable(res2);

        } else {

            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            et_password.setSelection(et_password.getText().length());

            iv_1.setImageDrawable(res);

        }

    }
    public void ViewPassword1() {


        String uri = "@drawable/ic_cancel_view_password";
        String uri2 = "@drawable/viewpass_login";

        int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());
        int imageResourse2 = getResources().getIdentifier(uri2, null, getActivity().getPackageName());

        Drawable res = getResources().getDrawable(imageResource);
        Drawable res2 = getResources().getDrawable(imageResourse2);


        if (et_password1.getInputType() != InputType.TYPE_CLASS_TEXT) {

            et_password1.setInputType(InputType.TYPE_CLASS_TEXT);

            et_password1.setSelection(et_password1.getText().length());

            iv_2.setImageDrawable(res2);

        } else {

            et_password1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            et_password1.setSelection(et_password1.getText().length());

            iv_2.setImageDrawable(res);

        }

    }

    public void ViewPassword2() {


        String uri = "@drawable/ic_cancel_view_password";
        String uri2 = "@drawable/viewpass_login";

        int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());
        int imageResourse2 = getResources().getIdentifier(uri2, null, getActivity().getPackageName());

        Drawable res = getResources().getDrawable(imageResource);
        Drawable res2 = getResources().getDrawable(imageResourse2);


        if (et_confirm.getInputType() != InputType.TYPE_CLASS_TEXT) {

            et_confirm.setInputType(InputType.TYPE_CLASS_TEXT);

            et_confirm.setSelection(et_confirm.getText().length());

            iv_3.setImageDrawable(res2);

        } else {

            et_confirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            et_confirm.setSelection(et_confirm.getText().length());

            iv_3.setImageDrawable(res);

        }

    }
    @Override
    public void onSuccess(String response) {

    }

    @Override
    public void onSuccessHome(String response) {

    }

    @Override
    public void onSuccessNotificationCount(String response) {

    }

    @Override
    public void onSuccessSecond(String response) {

    }


    @Override
    public void onPostSuccess(String responses, String sessionId) {
        response=responses;
        try {
            JSONObject jsonObject =  new JSONObject(responses);
         String status  =  jsonObject.getString("Status");
         if (status.equalsIgnoreCase("Success")){
             launchSuccessDialog("");
         }else {
             launchSuccessDialog(status);

             //Toast.makeText(getActivity(),"Server Error", Toast.LENGTH_LONG);
         }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void launchSuccessDialog(final String title) {

        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_change_password);
        dialog.setCanceledOnTouchOutside(true);

        TextView tv_message = (TextView) dialog.findViewById(R.id.tv_message);
        if (title.equalsIgnoreCase("")){
            tv_message.setText("You have successfully changed your password");
        }else {
            tv_message.setText("You have entered incorrect current password");
        }
        TextView ok = (TextView) dialog.findViewById(R.id.ok);
        //srid.setText("SR ID #"+srID);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.equalsIgnoreCase("")) {
                    getFragmentManager().popBackStack();
                }else {

                }
                    dialog.dismiss();
                dialog.cancel();
            }
        });


        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }
    private void loadFragment(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public static boolean Password_Validation(String password)
    {

        if(password.length()>=8)
        {
            Pattern letter = Pattern.compile("[a-zA-Z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
            //Pattern eight = Pattern.compile (".{8}");


            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetter.find() && hasDigit.find() && hasSpecial.find();

        }
        else
            return false;

    }
}
