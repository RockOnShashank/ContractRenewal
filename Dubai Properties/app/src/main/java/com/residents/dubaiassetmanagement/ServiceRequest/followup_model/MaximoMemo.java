
package com.residents.dubaiassetmanagement.ServiceRequest.followup_model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaximoMemo implements Parcelable
{

    @SerializedName("ANYWHEREREFID")
    @Expose
    private ANYWHEREREFID aNYWHEREREFID;
    @SerializedName("ASSIGNMENTID")
    @Expose
    private ASSIGNMENTID aSSIGNMENTID;
    @SerializedName("ASSIGNREPLOCID")
    @Expose
    private ASSIGNREPLOCID aSSIGNREPLOCID;
    @SerializedName("CLIENTVIEWABLE")
    @Expose
    private Integer cLIENTVIEWABLE;
    @SerializedName("CREATEBY")
    @Expose
    private String cREATEBY;
    @SerializedName("CREATEDATE")
    @Expose
    private String cREATEDATE;
    @SerializedName("DESCRIPTION")
    @Expose
    private String dESCRIPTION;
    @SerializedName("LOGTYPE")
    @Expose
    private LOGTYPE lOGTYPE;
    @SerializedName("MODIFYBY")
    @Expose
    private String mODIFYBY;
    @SerializedName("MODIFYDATE")
    @Expose
    private String mODIFYDATE;
    @SerializedName("ORGID")
    @Expose
    private String oRGID;
    @SerializedName("RECORDKEY")
    @Expose
    private Integer rECORDKEY;
    @SerializedName("SCHED_SLOT")
    @Expose
    private Object sCHEDSLOT;
    @SerializedName("SITEID")
    @Expose
    private String sITEID;
    @SerializedName("WORKLOGID")
    @Expose
    private Integer wORKLOGID;
    @SerializedName("YARAPPDATE")
    @Expose
    private YARAPPDATE yARAPPDATE;
    public final static Creator<MaximoMemo> CREATOR = new Creator<MaximoMemo>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MaximoMemo createFromParcel(Parcel in) {
            return new MaximoMemo(in);
        }

        public MaximoMemo[] newArray(int size) {
            return (new MaximoMemo[size]);
        }

    }
    ;

    protected MaximoMemo(Parcel in) {
        this.aNYWHEREREFID = ((ANYWHEREREFID) in.readValue((ANYWHEREREFID.class.getClassLoader())));
        this.aSSIGNMENTID = ((ASSIGNMENTID) in.readValue((ASSIGNMENTID.class.getClassLoader())));
        this.aSSIGNREPLOCID = ((ASSIGNREPLOCID) in.readValue((ASSIGNREPLOCID.class.getClassLoader())));
        this.cLIENTVIEWABLE = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.cREATEBY = ((String) in.readValue((String.class.getClassLoader())));
        this.cREATEDATE = ((String) in.readValue((String.class.getClassLoader())));
        this.dESCRIPTION = ((String) in.readValue((String.class.getClassLoader())));
        this.lOGTYPE = ((LOGTYPE) in.readValue((LOGTYPE.class.getClassLoader())));
        this.mODIFYBY = ((String) in.readValue((String.class.getClassLoader())));
        this.mODIFYDATE = ((String) in.readValue((String.class.getClassLoader())));
        this.oRGID = ((String) in.readValue((String.class.getClassLoader())));
        this.rECORDKEY = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.sCHEDSLOT = ((Object) in.readValue((Object.class.getClassLoader())));
        this.sITEID = ((String) in.readValue((String.class.getClassLoader())));
        this.wORKLOGID = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.yARAPPDATE = ((YARAPPDATE) in.readValue((YARAPPDATE.class.getClassLoader())));
    }

    public MaximoMemo() {
    }

    public ANYWHEREREFID getANYWHEREREFID() {
        return aNYWHEREREFID;
    }

    public void setANYWHEREREFID(ANYWHEREREFID aNYWHEREREFID) {
        this.aNYWHEREREFID = aNYWHEREREFID;
    }

    public ASSIGNMENTID getASSIGNMENTID() {
        return aSSIGNMENTID;
    }

    public void setASSIGNMENTID(ASSIGNMENTID aSSIGNMENTID) {
        this.aSSIGNMENTID = aSSIGNMENTID;
    }

    public ASSIGNREPLOCID getASSIGNREPLOCID() {
        return aSSIGNREPLOCID;
    }

    public void setASSIGNREPLOCID(ASSIGNREPLOCID aSSIGNREPLOCID) {
        this.aSSIGNREPLOCID = aSSIGNREPLOCID;
    }

    public Integer getCLIENTVIEWABLE() {
        return cLIENTVIEWABLE;
    }

    public void setCLIENTVIEWABLE(Integer cLIENTVIEWABLE) {
        this.cLIENTVIEWABLE = cLIENTVIEWABLE;
    }

    public String getCREATEBY() {
        return cREATEBY;
    }

    public void setCREATEBY(String cREATEBY) {
        this.cREATEBY = cREATEBY;
    }

    public String getCREATEDATE() {
        return cREATEDATE;
    }

    public void setCREATEDATE(String cREATEDATE) {
        this.cREATEDATE = cREATEDATE;
    }

    public String getDESCRIPTION() {
        return dESCRIPTION;
    }

    public void setDESCRIPTION(String dESCRIPTION) {
        this.dESCRIPTION = dESCRIPTION;
    }

    public LOGTYPE getLOGTYPE() {
        return lOGTYPE;
    }

    public void setLOGTYPE(LOGTYPE lOGTYPE) {
        this.lOGTYPE = lOGTYPE;
    }

    public String getMODIFYBY() {
        return mODIFYBY;
    }

    public void setMODIFYBY(String mODIFYBY) {
        this.mODIFYBY = mODIFYBY;
    }

    public String getMODIFYDATE() {
        return mODIFYDATE;
    }

    public void setMODIFYDATE(String mODIFYDATE) {
        this.mODIFYDATE = mODIFYDATE;
    }

    public String getORGID() {
        return oRGID;
    }

    public void setORGID(String oRGID) {
        this.oRGID = oRGID;
    }

    public Integer getRECORDKEY() {
        return rECORDKEY;
    }

    public void setRECORDKEY(Integer rECORDKEY) {
        this.rECORDKEY = rECORDKEY;
    }

    public Object getSCHEDSLOT() {
        return sCHEDSLOT;
    }

    public void setSCHEDSLOT(Object sCHEDSLOT) {
        this.sCHEDSLOT = sCHEDSLOT;
    }

    public String getSITEID() {
        return sITEID;
    }

    public void setSITEID(String sITEID) {
        this.sITEID = sITEID;
    }

    public Integer getWORKLOGID() {
        return wORKLOGID;
    }

    public void setWORKLOGID(Integer wORKLOGID) {
        this.wORKLOGID = wORKLOGID;
    }

    public YARAPPDATE getYARAPPDATE() {
        return yARAPPDATE;
    }

    public void setYARAPPDATE(YARAPPDATE yARAPPDATE) {
        this.yARAPPDATE = yARAPPDATE;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(aNYWHEREREFID);
        dest.writeValue(aSSIGNMENTID);
        dest.writeValue(aSSIGNREPLOCID);
        dest.writeValue(cLIENTVIEWABLE);
        dest.writeValue(cREATEBY);
        dest.writeValue(cREATEDATE);
        dest.writeValue(dESCRIPTION);
        dest.writeValue(lOGTYPE);
        dest.writeValue(mODIFYBY);
        dest.writeValue(mODIFYDATE);
        dest.writeValue(oRGID);
        dest.writeValue(rECORDKEY);
        dest.writeValue(sCHEDSLOT);
        dest.writeValue(sITEID);
        dest.writeValue(wORKLOGID);
        dest.writeValue(yARAPPDATE);
    }

    public int describeContents() {
        return  0;
    }

}
