package com.example.shawyoungtang.facebooksearch.PagePOJO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Pages implements Parcelable {

    private List<Datum> data = null;
    private Paging paging;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
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
        dest.writeList(this.data);
        dest.writeParcelable(this.paging, flags);
    }

    public Pages() {
    }

    protected Pages(Parcel in) {
        this.data = new ArrayList<Datum>();
        in.readList(this.data, Datum.class.getClassLoader());
        this.paging = in.readParcelable(Paging.class.getClassLoader());
    }

    public static final Parcelable.Creator<Pages> CREATOR = new Parcelable.Creator<Pages>() {
        @Override
        public Pages createFromParcel(Parcel source) {
            return new Pages(source);
        }

        @Override
        public Pages[] newArray(int size) {
            return new Pages[size];
        }
    };
}