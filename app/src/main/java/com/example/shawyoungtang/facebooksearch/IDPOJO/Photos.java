package com.example.shawyoungtang.facebooksearch.IDPOJO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Photos implements Parcelable {

    private List<Datum_> data = null;
    private Paging paging;

    public List<Datum_> getData() {
        return data;
    }

    public void setData(List<Datum_> data) {
        this.data = data;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.data);
        dest.writeParcelable(this.paging, flags);
    }

    public Photos() {
    }

    protected Photos(Parcel in) {
        this.data = in.createTypedArrayList(Datum_.CREATOR);
        this.paging = in.readParcelable(Paging.class.getClassLoader());
    }

    public static final Parcelable.Creator<Photos> CREATOR = new Parcelable.Creator<Photos>() {
        @Override
        public Photos createFromParcel(Parcel source) {
            return new Photos(source);
        }

        @Override
        public Photos[] newArray(int size) {
            return new Photos[size];
        }
    };
}