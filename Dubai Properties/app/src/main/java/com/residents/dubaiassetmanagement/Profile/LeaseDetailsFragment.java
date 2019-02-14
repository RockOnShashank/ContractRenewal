package com.residents.dubaiassetmanagement.Profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.Model.TenantDetails;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.Utils.VolleySingleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LeaseDetailsFragment extends Fragment {

    private static final String TAG ="tab1";
    private Button test3;
    private TextView tv_communityName,tv_buildingNumber,tv_unitNumber,tv_leasePeriod,tv_leaseType,
            tv_leaseStartDate,tv_PaymentTerms,tv_leaseAmount,tv_agreementNumber,tv_tenantNumber,tv_devaPremise;
    private TenantDetails tenantDetails;
    View view;
String dates;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.tab3_fragment, container, false);
            tenantDetails = VolleySingleton.getInstance().getTenantDetails();
            init(view);
        return view;

    }
        public void init(View view){

             tv_communityName = view.findViewById(R.id.tv_communityName);
            tv_buildingNumber = view.findViewById(R.id.tv_buildingNumber);
            tv_unitNumber = view.findViewById(R.id.tv_unitNumber);
            tv_leasePeriod = view.findViewById(R.id.tv_leasePeriod);
            tv_leaseType = view.findViewById(R.id.tv_leaseType);
            tv_leaseStartDate = view.findViewById(R.id.tv_leaseStartDate);
            tv_PaymentTerms = view.findViewById(R.id.tv_PaymentTerms);
            tv_leaseAmount = view.findViewById(R.id.tv_leaseAmount);
            tv_agreementNumber = view.findViewById(R.id.tv_agreementNumber);
            tv_tenantNumber = view.findViewById(R.id.tv_tenantNumber);
            tv_devaPremise = view.findViewById(R.id.tv_devaPremise);




}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (tenantDetails != null) {




            tv_communityName.setText(tenantDetails.getTenantDetails().get(0).getTenantProjectName());

            tv_buildingNumber.setText(tenantDetails.getTenantDetails().get(0).getPropertyCode());
            tv_unitNumber.setText(tenantDetails.getTenantDetails().get(0).getUnitCode());
            tv_leasePeriod.setText(tenantDetails.getTenantDetails().get(0).getLeaseTerm()+" Months");

            tv_leaseType.setText(tenantDetails.getTenantDetails().get(0).getLeaseType());

            try {
                dates = getMonth(tenantDetails.getTenantDetails().get(0).getLeaseStartDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tv_leaseStartDate.setText(dates.replaceAll("-"," "));

            tv_PaymentTerms.setText(tenantDetails.getTenantDetails().get(0).getPaymentTerms()+" Installments");
            tv_leaseAmount.setText("AED "+tenantDetails.getTenantDetails().get(0).getLeaseAmount());
            //tv_agreementNumber.setText("NA");
            tv_tenantNumber.setText(tenantDetails.getTenantDetails().get(0).getTenantCode());
            tv_devaPremise.setText(tenantDetails.getTenantDetails().get(0).getDewaPremiseNumber());
        }
    }
    private static String getMonth(String date) throws ParseException {
        Date d = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
        return monthName;
    }
}
