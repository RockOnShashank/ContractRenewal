package com.residents.dubaiassetmanagement.my_documents;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.Helper.UrlHelper;
import com.residents.dubaiassetmanagement.Model.TenantDetails;
import com.residents.dubaiassetmanagement.Profile.MyRecyclerViewAdapter;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.Utils.VolleySingleton;
import com.residents.dubaiassetmanagement.my_documents.models.DPGAttachmentDetailsV;
import com.residents.dubaiassetmanagement.my_documents.models_status.ServiceRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MyDocumentsAdapter extends RecyclerView.Adapter<MyDocumentsAdapter.ViewHolder> {

    private ArrayList<DPGAttachmentDetailsV> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context con;
    private AdapterInterface editListener;

    private int a,b,c,d,e,f,g,h,i=0;
    private TenantDetails tenantDetails;
    private ArrayList<ServiceRequest> documentstatusList;
    private String date,dateEmirates,dateEmiratesid;

    // data is passed into the constructor
    MyDocumentsAdapter(Context context, ArrayList<DPGAttachmentDetailsV> data,ArrayList<ServiceRequest> documentstatus, AdapterInterface editListeners ) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.con = context;
        this.documentstatusList = documentstatus;
        this.editListener = editListeners;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_mydocuments, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        tenantDetails = VolleySingleton.getInstance().getTenantDetails();


    if (position == 0) {

        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).getAttachmentType().equalsIgnoreCase("Ejari Document")) {

                h = 1;
                holder.ll_upload.setVisibility(View.GONE);
                holder.ll_download.setVisibility(View.VISIBLE);
                holder.tv_subname.setText(mData.get(i).getFileName());
                holder.tv_displayname.setText("Ejari Document");
                holder.tv_attach.setText(mData.get(i).getAttachmentType());
                holder.tv_haAttach.setText(mData.get(i).getHAttachment());
            } else if (h==0){
                holder.ll_download.setVisibility(View.GONE);
                holder.ll_upload.setVisibility(View.GONE);
            }

        }
    }
    if (position == 1) {
        for (int x = 0; x < mData.size(); x++) {
            if (mData.get(x).getAttachmentType().equalsIgnoreCase("Memorandum of Understanding")) {
                i = 1;
                holder.ll_upload.setVisibility(View.GONE);
                holder.ll_download.setVisibility(View.VISIBLE);
                holder.tv_subname.setText(mData.get(x).getFileName());
                holder.tv_displayname.setText("Lease Agreement Document");
                holder.tv_attach.setText(mData.get(x).getAttachmentType());
                holder.tv_haAttach.setText(mData.get(x).getHAttachment());
                holder.tv_expiry_date.setVisibility(View.VISIBLE);
                try {
                    date = getMonth(tenantDetails.getTenantDetails().get(0).getLeaseEndDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                holder.tv_expiry_date.setText("Expiry date : "+date.replaceAll("-"," "));
            } else if (i==0){
                holder.ll_download.setVisibility(View.GONE);
                holder.ll_upload.setVisibility(View.GONE);
            }
        }
    }
    if (position == 2) {

for (int y=0;y<mData.size();y++) {

    if (mData.get(y).getAttachmentType().equalsIgnoreCase("Passport Copy of Tenant")) {
        holder.ll_upload.setVisibility(View.GONE);
        holder.ll_download.setVisibility(View.VISIBLE);
        holder.tv_displayname.setText(mData.get(y).getDisplayName());
        holder.tv_subname.setText(mData.get(y).getFileName());
        holder.tv_expiry_date.setVisibility(View.VISIBLE);
        try {
            date = getMonth(tenantDetails.getTenantDetails().get(0).getTenantPassportExpDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tv_expiry_date.setText("Expiry date : "+date.replaceAll("-"," "));
        holder.tv_attach.setText(mData.get(y).getAttachmentType());
        holder.tv_haAttach.setText(mData.get(y).getHAttachment());
a=1;
    } else {
        /*holder.ll_upload.setVisibility(View.VISIBLE);
        holder.ll_download.setVisibility(View.GONE);
        holder.tv_document_name.setText("Upload Passport Document");*/

    }
}
    }
    if (position == 3) {

        for (int a=0;a<mData.size();a++) {

            if (mData.get(a).getAttachmentType().equalsIgnoreCase("Emirates ID Copy")) {
b=1;
                holder.ll_upload.setVisibility(View.GONE);
                holder.ll_download.setVisibility(View.VISIBLE);
                holder.tv_displayname.setText(mData.get(a).getDisplayName());
                holder.tv_subname.setText(mData.get(a).getFileName());
                holder.tv_expiry_date.setVisibility(View.VISIBLE);
                if (tenantDetails.getTenantDetails().get(0).getTenantEmiratesidExpDate() != null) {

                    try {
                        dateEmirates = getMonth(tenantDetails.getTenantDetails().get(0).getTenantEmiratesidExpDate());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    holder.tv_expiry_date.setText("Expiry date : "+dateEmirates);
                }
                holder.tv_attach.setText(mData.get(a).getAttachmentType());
                holder.tv_haAttach.setText(mData.get(a).getHAttachment());
            } else {
               /* holder.ll_upload.setVisibility(View.VISIBLE);
                holder.ll_download.setVisibility(View.GONE);
                holder.tv_document_name.setText("Upload Emirates ID Document");
*/
            }
        }

    }
    if (position == 4) {

        for (int b=0;b<mData.size();b++) {


            if (mData.get(b).getAttachmentType().equalsIgnoreCase("Resident Visa Copy")) {
                holder.ll_upload.setVisibility(View.GONE);
                holder.ll_download.setVisibility(View.VISIBLE);
                holder.tv_displayname.setText(mData.get(b).getDisplayName());
                holder.tv_subname.setText(mData.get(b).getFileName());
                c=1;
                holder.tv_expiry_date.setVisibility(View.VISIBLE);
                holder.tv_attach.setText(mData.get(b).getAttachmentType());
                holder.tv_haAttach.setText(mData.get(b).getHAttachment());
                if (tenantDetails.getTenantDetails().get(0).getTenantVisaExpDate() != null) {

                    try {
                        dateEmiratesid = getMonth(tenantDetails.getTenantDetails().get(0).getTenantVisaExpDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    holder.tv_expiry_date.setText("Expiry date : "+dateEmiratesid.replaceAll("-"," "));
                }

            } else {
               /* holder.ll_upload.setVisibility(View.VISIBLE);
                holder.ll_download.setVisibility(View.GONE);
                holder.tv_document_name.setText("Upload VISA Document");

*/
        }}

    }
    if (position == 5) {

        for (int c=0;c<mData.size();c++) {


            if (mData.get(c).getAttachmentType().equalsIgnoreCase("DEWA Registration Receipt")) {
                holder.ll_upload.setVisibility(View.GONE);
                holder.ll_download.setVisibility(View.VISIBLE);
                holder.tv_displayname.setText(mData.get(c).getDisplayName());
                holder.tv_subname.setText(mData.get(c).getFileName());
                d=1;
                holder.tv_attach.setText(mData.get(c).getAttachmentType());
                holder.tv_haAttach.setText(mData.get(c).getHAttachment());

            } else {
              /*  holder.ll_upload.setVisibility(View.VISIBLE);
                holder.ll_download.setVisibility(View.GONE);
                holder.tv_document_name.setText("Upload Final DIWA Request");

*/
            }
        }

    }
    if (position == 6) {


        for (int d=0;d<mData.size();d++) {

            if (mData.get(d).getAttachmentType().equalsIgnoreCase("Empower Registration Receipt")) {
                holder.ll_upload.setVisibility(View.GONE);
                holder.ll_download.setVisibility(View.VISIBLE);
                holder.tv_displayname.setText(mData.get(d).getDisplayName());
                holder.tv_subname.setText(mData.get(d).getFileName());
                holder.tv_attach.setText(mData.get(d).getAttachmentType());
                holder.tv_haAttach.setText(mData.get(d).getHAttachment());
                e=1;
            } else {
                /*holder.ll_upload.setVisibility(View.VISIBLE);
                holder.ll_download.setVisibility(View.GONE);
                holder.tv_document_name.setText("Upload Final EMPOWER request");

*/
        }}

    }
    if (position == 7) {

        for (int e=0;e<mData.size();e++) {


            if (mData.get(e).getAttachmentType().equalsIgnoreCase("Final DEWA Bill")) {
                holder.ll_upload.setVisibility(View.GONE);
                holder.ll_download.setVisibility(View.VISIBLE);
                holder.tv_displayname.setText(mData.get(e).getDisplayName());
                holder.tv_subname.setText(mData.get(e).getFileName());
                    f=1;
                holder.tv_attach.setText(mData.get(e).getAttachmentType());
                holder.tv_haAttach.setText(mData.get(e).getHAttachment());
            } else {
              /*  holder.ll_upload.setVisibility(View.VISIBLE);
                holder.ll_download.setVisibility(View.GONE);
                holder.tv_document_name.setText("Upload Final DEWA Bill");*/
            }

        }

    }

    if (position == 8) {

        for (int f=0;f<mData.size();f++) {

            if (mData.get(f).getAttachmentType().equalsIgnoreCase("Final Empower Bill")) {
                holder.ll_upload.setVisibility(View.GONE);
                holder.ll_download.setVisibility(View.VISIBLE);
                holder.tv_displayname.setText(mData.get(f).getDisplayName());
                holder.tv_subname.setText(mData.get(f).getFileName());
                g=1;
                holder.tv_attach.setText(mData.get(f).getAttachmentType());
                holder.tv_haAttach.setText(mData.get(f).getHAttachment());

            } else {
                /*holder.ll_upload.setVisibility(View.VISIBLE);
                holder.ll_download.setVisibility(View.GONE);
                holder.tv_document_name.setText("Upload Final EMPOWER Bill");*/

            }
        }
    }
// if no document


        if (position == 2 && a==0 ){
            holder.ll_upload.setVisibility(View.VISIBLE);
            holder.ll_download.setVisibility(View.GONE);
            holder.tv_document_name.setText("Upload Passport");


        }
        if (position == 3 && b==0 ){
            holder.ll_upload.setVisibility(View.VISIBLE);
            holder.ll_download.setVisibility(View.GONE);
            holder.tv_document_name.setText("Upload Emirates ID");

        }
        if (position == 4 && c==0 ){
            holder.ll_upload.setVisibility(View.VISIBLE);
            holder.ll_download.setVisibility(View.GONE);
            holder.tv_document_name.setText("Upload Visa");

        }
        if (position == 5 && d==0 ){
            holder.ll_upload.setVisibility(View.VISIBLE);
            holder.ll_download.setVisibility(View.GONE);
            holder.tv_document_name.setText("Upload DEWA Registration Receipt");

        }
        if (position == 6 && e==0 ){
            holder.ll_upload.setVisibility(View.VISIBLE);
            holder.ll_download.setVisibility(View.GONE);
            holder.tv_document_name.setText("Upload Empower Registration Receipt");

        }
        if (position == 7 && f==0 ){
            holder.ll_upload.setVisibility(View.VISIBLE);
            holder.ll_download.setVisibility(View.GONE);
            holder.tv_document_name.setText("Upload Final DEWA Bill");

        }
        if (position == 8 && g==0 ){
            holder.ll_upload.setVisibility(View.VISIBLE);
            holder.ll_download.setVisibility(View.GONE);
            holder.tv_document_name.setText("Upload Final Empower Bill");

        }


//status

        if (position == 0 ){

                        holder.tv_upload.setEnabled(false);
                        holder.iv_upload.setAlpha((float) 0.255);
                        holder.tv_upload.setAlpha((float) 0.255);
                        holder.tv_status.setVisibility(View.GONE);
                        holder.tv_status_upload.setVisibility(View.GONE);

        }
        if (position == 1 ){

            holder.tv_upload.setEnabled(false);
            holder.iv_upload.setAlpha((float) 0.255);
            holder.tv_upload.setAlpha((float) 0.255);
            holder.tv_status.setVisibility(View.GONE);
            holder.tv_status_upload.setVisibility(View.GONE);

        }

       /* if (position == 2 ){

            holder.tv_upload.setEnabled(false);
            holder.iv_upload.setAlpha((float) 0.255);
            holder.tv_upload.setAlpha((float) 0.255);
            holder.tv_status.setVisibility(View.GONE);
            holder.tv_status_upload.setVisibility(View.GONE);

        }
*/
//a==1
        if (position == 2 ){
            for (int a=0;a<documentstatusList.size();a++) {
              //  if (documentstatusList.get(a).getSubCategory().equalsIgnoreCase("Passport")) {
                    if (documentstatusList.get(a).getSubCategory().equalsIgnoreCase("Passport")) {

                        if (documentstatusList.get(a).getStatus().equalsIgnoreCase("In Progress") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Pending Approval")
                                ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Scheduled") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Resident Appointment")
                                ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Request Reassignment") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Approved") ||
                                documentstatusList.get(a).getStatus().equalsIgnoreCase("Parts Pending") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Call")  ) {
                        holder.tv_status.setVisibility(View.VISIBLE);
                        holder.tv_upload.setEnabled(false);
                        holder.iv_upload.setAlpha((float) 0.255);
                        holder.tv_upload.setAlpha((float) 0.255);
                        holder.tv_status_upload.setVisibility(View.VISIBLE);
                            holder.ll_upload.setEnabled(false);
                            holder.ll_upload.setAlpha((float) 0.50);

                        }else {


                            holder.tv_status_upload.setTextColor(ContextCompat.getColor(con, R.color.red));
                            holder.tv_status.setTextColor(ContextCompat.getColor(con, R.color.red));

                            holder.tv_status_upload.setVisibility(View.VISIBLE);
                        holder.tv_status_upload.setText("Your document was rejected. Kindly upload valid document.");
                            holder.tv_status.setText("Your document was rejected. Kindly upload valid document.");

                            holder.tv_status.setVisibility(View.VISIBLE);

                            if (documentstatusList.get(a).getStatus().equalsIgnoreCase("Work Completed")){
                                holder.tv_status.setVisibility(View.GONE);
                            }

                    }
                }

                }
        }
        //b==1
        if (position == 3  ){
            for (int a=0;a<documentstatusList.size();a++) {
               // if (documentstatusList.get(a).getSubCategory().equalsIgnoreCase("Emirates ID Copy")) {
                if (documentstatusList.get(a).getSubCategory().equalsIgnoreCase("Emirates_ID")) {

                    if (documentstatusList.get(a).getStatus().equalsIgnoreCase("In Progress") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Pending Approval") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Scheduled") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Resident Appointment") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Request Reassignment") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Approved") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Parts Pending") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Call")  ) {
                        holder.tv_status.setVisibility(View.VISIBLE);
                        holder.tv_upload.setEnabled(false);
                        holder.tv_upload.setAlpha((float) 0.255);
                        holder.iv_upload.setAlpha((float) 0.255);
                        holder.tv_status_upload.setVisibility(View.VISIBLE);
                        holder.ll_upload.setEnabled(false);
                        holder.ll_upload.setAlpha((float) 0.50);
                    }else {
                        holder.tv_status_upload.setTextColor(ContextCompat.getColor(con, R.color.red));
                        holder.tv_status.setTextColor(ContextCompat.getColor(con, R.color.red));
                        holder.tv_status_upload.setVisibility(View.VISIBLE);
                        holder.tv_status_upload.setText("Your document was rejected. Kindly upload valid document.");
                        holder.tv_status.setText("Your document was rejected. Kindly upload valid document.");
                        holder.tv_status.setVisibility(View.VISIBLE);
                        if (documentstatusList.get(a).getStatus().equalsIgnoreCase("Work Completed")){
                            holder.tv_status.setVisibility(View.GONE);
                        }
                    }
                }

            }

        }

        //c==1
        if (position == 4 ){
            for (int a=0;a<documentstatusList.size();a++) {
               // if (documentstatusList.get(a).getSubCategory().equalsIgnoreCase("Resident Visa Copy")) {
                    if (documentstatusList.get(a).getSubCategory().equalsIgnoreCase("Visa")) {

                        if (documentstatusList.get(a).getStatus().equalsIgnoreCase("In Progress") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Pending Approval") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Scheduled") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Resident Appointment") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Request Reassignment") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Approved") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Parts Pending") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Call")  ) {
                        holder.tv_status.setVisibility(View.VISIBLE);
                        holder.tv_upload.setEnabled(false);
                        holder.tv_upload.setAlpha((float) 0.255);
                        holder.iv_upload.setAlpha((float) 0.255);
                        holder.tv_status_upload.setVisibility(View.VISIBLE);
                            holder.ll_upload.setEnabled(false);
                            holder.ll_upload.setAlpha((float) 0.50);

                    }else {
                            holder.tv_status_upload.setTextColor(ContextCompat.getColor(con, R.color.red));
                            holder.tv_status.setTextColor(ContextCompat.getColor(con, R.color.red));
                            holder.tv_status_upload.setText("Your document was rejected. Kindly upload valid document.");
                            holder.tv_status.setText("Your document was rejected. Kindly upload valid document.");
                        holder.tv_status_upload.setVisibility(View.VISIBLE);
                        holder.tv_status.setVisibility(View.VISIBLE);
                            if (documentstatusList.get(a).getStatus().equalsIgnoreCase("Work Completed")){
                                holder.tv_status.setVisibility(View.GONE);
                            }

                    }
                }
            }

        }
        if (position == 5){
            for (int a=0;a<documentstatusList.size();a++) {
                if (documentstatusList.get(a).getSubCategory().equalsIgnoreCase("DEWA Registration Receipt")) {
                    if (documentstatusList.get(a).getStatus().equalsIgnoreCase("In Progress") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Pending Approval") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Scheduled") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Resident Appointment") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Request Reassignment") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Approved") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Parts Pending") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Call")  ) {
                        holder.tv_status.setVisibility(View.VISIBLE);
                        holder.tv_upload.setEnabled(false);
                        holder.tv_upload.setAlpha((float) 0.255);
                        holder.iv_upload.setAlpha((float) 0.255);
                        holder.tv_status_upload.setVisibility(View.VISIBLE);

                        holder.ll_upload.setEnabled(false);
                        holder.ll_upload.setAlpha((float) 0.50);
                    }else {
                        holder.tv_status_upload.setTextColor(ContextCompat.getColor(con, R.color.red));
                        holder.tv_status.setTextColor(ContextCompat.getColor(con, R.color.red));
                        holder.tv_status_upload.setText("Your document was rejected. Kindly upload valid document.");
                        holder.tv_status.setText("Your document was rejected. Kindly upload valid document.");
                        holder.tv_status_upload.setVisibility(View.VISIBLE);

                        holder.tv_status.setVisibility(View.VISIBLE);
                        if (documentstatusList.get(a).getStatus().equalsIgnoreCase("Work Completed")){
                            holder.tv_status.setVisibility(View.GONE);
                        }

                    }
                }
            }
        }
        if (position == 6 ){
            for (int a=0;a<documentstatusList.size();a++) {
                if (documentstatusList.get(a).getSubCategory().equalsIgnoreCase("Empower Registration Receipt")) {
                    if (documentstatusList.get(a).getStatus().equalsIgnoreCase("In Progress") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Pending Approval") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Scheduled") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Resident Appointment") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Request Reassignment") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Approved") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Parts Pending") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Call")  ) {
                        holder.tv_status.setVisibility(View.VISIBLE);
                        holder.tv_upload.setEnabled(false);
                        holder.iv_upload.setAlpha((float) 0.255);
                        holder.tv_upload.setAlpha((float) 0.255);
                        holder.tv_status_upload.setVisibility(View.VISIBLE);
                        holder.ll_upload.setEnabled(false);
                        holder.ll_upload.setAlpha((float) 0.50);
                    }else {
                        holder.tv_status_upload.setTextColor(ContextCompat.getColor(con, R.color.red));
                        holder.tv_status.setTextColor(ContextCompat.getColor(con, R.color.red));
                        holder.tv_status_upload.setText("Your document was rejected. Kindly upload valid document.");
                        holder.tv_status.setText("Your document was rejected. Kindly upload valid document.");
                        holder.tv_status_upload.setVisibility(View.VISIBLE);
                        holder.tv_status.setVisibility(View.VISIBLE);
                        if (documentstatusList.get(a).getStatus().equalsIgnoreCase("Work Completed")){
                            holder.tv_status.setVisibility(View.GONE);
                        }

                    }
                }
            }
        }
        if (position == 7 ){
            for (int a=0;a<documentstatusList.size();a++) {
                if (documentstatusList.get(a).getSubCategory().equalsIgnoreCase("Final DEWA Bill")) {
                    if (documentstatusList.get(a).getStatus().equalsIgnoreCase("In Progress") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Pending Approval") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Scheduled") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Resident Appointment") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Request Reassignment") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Approved") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Parts Pending") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Call")  ) {
                        holder.tv_status.setVisibility(View.VISIBLE);
                        holder.tv_upload.setEnabled(false);
                        holder.tv_upload.setAlpha((float) 0.255);
                        holder.iv_upload.setAlpha((float) 0.255);

                        holder.ll_upload.setEnabled(false);
                        holder.ll_upload.setAlpha((float) 0.50);
                        holder.tv_status_upload.setVisibility(View.VISIBLE);

                    }else {
                        holder.tv_status_upload.setTextColor(ContextCompat.getColor(con, R.color.red));
                        holder.tv_status.setTextColor(ContextCompat.getColor(con, R.color.red));
                        holder.tv_status_upload.setText("Your document was rejected. Kindly upload valid document.");
                        holder.tv_status.setText("Your document was rejected. Kindly upload valid document.");
                        holder.tv_status_upload.setVisibility(View.VISIBLE);

                        holder.tv_status.setText(documentstatusList.get(a).getStatus());
                        holder.tv_status.setVisibility(View.VISIBLE);
                        if (documentstatusList.get(a).getStatus().equalsIgnoreCase("Work Completed")){
                            holder.tv_status.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
        if (position == 8){
            for (int a=0;a<documentstatusList.size();a++) {
                if (documentstatusList.get(a).getSubCategory().equalsIgnoreCase("Final Empower Bill")) {
                    if (documentstatusList.get(a).getStatus().equalsIgnoreCase("In Progress") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Pending Approval") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Scheduled") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Resident Appointment") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Request Reassignment") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Approved") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Parts Pending") ||documentstatusList.get(a).getStatus().equalsIgnoreCase("Call")  ) {
                        holder.tv_status.setVisibility(View.VISIBLE);
                        holder.tv_upload.setEnabled(false);
                        holder.tv_upload.setAlpha((float) 0.255);
                        holder.iv_upload.setAlpha((float) 0.255);
                        holder.tv_status_upload.setVisibility(View.VISIBLE);

                        holder.ll_upload.setEnabled(false);
                        holder.ll_upload.setAlpha((float) 0.50);
                    }else {
                        holder.tv_status_upload.setTextColor(ContextCompat.getColor(con, R.color.red));
                        holder.tv_status.setTextColor(ContextCompat.getColor(con, R.color.red));
                        holder.tv_status_upload.setText("Your document was rejected. Kindly upload valid document.");
                        holder.tv_status.setText("Your document was rejected. Kindly upload valid document.");
                        holder.tv_status_upload.setVisibility(View.VISIBLE);

                        holder.tv_status.setVisibility(View.GONE);
                        if (documentstatusList.get(a).getStatus().equalsIgnoreCase("Work Completed")){
                            holder.tv_status.setVisibility(View.GONE);
                        }

                    }
                }
            }
        }

          if (position==1){

        holder.tv_status.setVisibility(View.GONE);
        holder.tv_status_upload.setVisibility(View.GONE);
        }

if (mData.size()==0){

    if (position == 0  ){
        holder.ll_upload.setVisibility(View.GONE);
        holder.ll_download.setVisibility(View.GONE);
     //   holder.tv_document_name.setText("Passport");


    }
    if (position == 1){
        holder.ll_upload.setVisibility(View.GONE);
        holder.ll_download.setVisibility(View.GONE);
       // holder.tv_document_name.setText("Passport");


    }
    if (position == 2 && a==0 ){
        holder.ll_upload.setVisibility(View.VISIBLE);
        holder.ll_download.setVisibility(View.GONE);
        holder.tv_document_name.setText("Upload Passport");


    }
    if (position == 3 && b==0 ){
        holder.ll_upload.setVisibility(View.VISIBLE);
        holder.ll_download.setVisibility(View.GONE);
        holder.tv_document_name.setText("Upload Emirates ID");

    }
    if (position == 4 && c==0 ){
        holder.ll_upload.setVisibility(View.VISIBLE);
        holder.ll_download.setVisibility(View.GONE);
        holder.tv_document_name.setText("Upload Visa");

    }
    if (position == 5 && d==0 ){
        holder.ll_upload.setVisibility(View.VISIBLE);
        holder.ll_download.setVisibility(View.GONE);
        holder.tv_document_name.setText("Upload DEWA Registration Receipt");

    }
    if (position == 6 && e==0 ){
        holder.ll_upload.setVisibility(View.VISIBLE);
        holder.ll_download.setVisibility(View.GONE);
        holder.tv_document_name.setText("Upload Empower Registration Receipt");

    }
    if (position == 7 && f==0 ){
        holder.ll_upload.setVisibility(View.VISIBLE);
        holder.ll_download.setVisibility(View.GONE);
        holder.tv_document_name.setText("Upload Final DEWA Bill");

    }
    if (position == 8 && g==0 ){
        holder.ll_upload.setVisibility(View.VISIBLE);
        holder.ll_download.setVisibility(View.GONE);
        holder.tv_document_name.setText("Upload Final Empower Bill");

    }





}




      /*  if (position==1){

        holder.tv_status.setVisibility(View.GONE);
        holder.tv_status_upload.setVisibility(View.GONE);
        }*/




        /*if (mData.get(position).getAttachmentType().equalsIgnoreCase(""))
        {
            holder.iv_upload.setVisibility(View.VISIBLE);
            holder.tv_upload.setVisibility(View.VISIBLE);
            holder.tv_displayname.setText(mData.get(position).getDisplayName());
            holder.tv_subname.setText(mData.get(position).getFileName());
        }else {
            holder.iv_upload.setVisibility(View.GONE);
            holder.tv_upload.setVisibility(View.GONE);
            holder.tv_displayname.setText(mData.get(position).getDisplayName());
            holder.tv_subname.setText(mData.get(position).getFileName());
        }*/

        holder.ll_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

if (position ==0){
    mClickListener.onItemClick(v, position,h);
}
                if (position ==1){
                    mClickListener.onItemClick(v, position,i);

                }if (position ==2){
                    mClickListener.onItemClick(v, position,a);

                }if (position ==3){
                    mClickListener.onItemClick(v, position,b);

                }if (position ==4){
                    mClickListener.onItemClick(v, position,c);

                }if (position ==5){
                    mClickListener.onItemClick(v, position,d);

                }if (position ==6){
                    mClickListener.onItemClick(v, position,e);

                }if (position ==7){
                    mClickListener.onItemClick(v, position,f);

                }if (position ==8){
                    mClickListener.onItemClick(v, position,g);

                }
            }
        });

        holder.tv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editListener.downloadPresses(position,holder.tv_attach.getText().toString(),holder.tv_haAttach.getText().toString());
            }
        });

        holder.tv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int currentPosition = position;
                editListener.uploadPresses(position,holder.tv_attach.getText().toString(),holder.tv_haAttach.getText().toString());

                if (currentPosition==position) {
                    /*holder.tv_upload.setEnabled(false);
                    holder.tv_upload.setAlpha((float) 0.255);
                    holder.iv_upload.setAlpha((float) 0.255);*/
                }
            }
        });
    }
    // total number of rows
    @Override
    public int getItemCount() {
        return 9;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_displayname, tv_subname, tv_download, tv_upload, tv_document_name,tv_expiry_date,tv_status,tv_status_upload,tv_attach,tv_haAttach;
        ImageView iv_download, iv_upload;
        LinearLayout ll_upload, ll_download,ll_parent;

        ViewHolder(View itemView) {
            super(itemView);
            tv_expiry_date = itemView.findViewById(R.id.tv_expiry_date);
            ll_upload = itemView.findViewById(R.id.ll_upload);
            ll_download = itemView.findViewById(R.id.ll_download);
            tv_displayname = itemView.findViewById(R.id.tv_displayname);
            tv_subname = itemView.findViewById(R.id.tv_subname);
            tv_download = itemView.findViewById(R.id.tv_download);
            tv_document_name = itemView.findViewById(R.id.tv_document_name);
            tv_attach = itemView.findViewById(R.id.tv_attach);

            tv_haAttach = itemView.findViewById(R.id.tv_haAttach);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_status_upload = itemView.findViewById(R.id.tv_status_upload);

            iv_download = itemView.findViewById(R.id.iv_download);
            tv_upload = itemView.findViewById(R.id.tv_upload);
            iv_upload = itemView.findViewById(R.id.iv_upload);
            ll_parent = itemView.findViewById(R.id.ll_parent);
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition(),0);
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return String.valueOf(mData.size());
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position, int status);
    }

    public interface AdapterInterface {
        public void downloadPresses(int position,String attachmnent_type,String haattachmnent_type);
        public void uploadPresses(int position,String attachmnent_type,String haattachmnent_type);

    }


    private static String getMonth(String date) throws ParseException {
        Date d = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        return monthName;
    }

}




