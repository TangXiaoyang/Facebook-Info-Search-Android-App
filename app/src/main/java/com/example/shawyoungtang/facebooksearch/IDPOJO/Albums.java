package com.example.shawyoungtang.facebooksearch.IDPOJO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Albums implements Parcelable {

    private List<Datum> data = null;
    private Paging_ paging;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Paging_ getPaging() {
        return paging;
    }

    public void setPaging(Paging_ paging) {
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

    public Albums() {
    }

    protected Albums(Parcel in) {
        this.data = new ArrayList<Datum>();
        in.readList(this.data, Datum.class.getClassLoader());
        this.paging = in.readParcelable(Paging_.class.getClassLoader());
    }

    public static final Parcelable.Creator<Albums> CREATOR = new Parcelable.Creator<Albums>() {
        @Override
        public Albums createFromParcel(Parcel source) {
            return new Albums(source);
        }

        @Override
        public Albums[] newArray(int size) {
            return new Albums[size];
        }
    };
}