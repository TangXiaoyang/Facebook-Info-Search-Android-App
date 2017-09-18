package com.example.shawyoungtang.facebooksearch.PlacePOJO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Places implements Parcelable {

    private List<Datum> data = null;
    private Paging paging;

    public List<Datum> getData() {
        return data;
    }

    public Paging getPaging() { return paging; }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public void setPaging(Paging paging) { this.paging = paging; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.data);
        dest.writeParcelable(this.paging, flags);
    }

    public Places() {
    }

    protected Places(Parcel in) {
        this.data = new ArrayList<Datum>();
        in.readList(this.data, Datum.class.getClassLoader());
        this.paging = in.readParcelable(Paging.class.getClassLoader());
    }

    public static final Parcelable.Creator<Places> CREATOR = new Parcelable.Creator<Places>() {
        @Override
        public Places createFromParcel(Parcel source) {
            return new Places(source);
        }

        @Override
        public Places[] newArray(int size) {
            return new Places[size];
        }
    };
}