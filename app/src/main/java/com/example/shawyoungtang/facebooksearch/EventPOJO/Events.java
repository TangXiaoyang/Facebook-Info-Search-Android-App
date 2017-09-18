package com.example.shawyoungtang.facebooksearch.EventPOJO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Events implements Parcelable {

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
        dest.writeTypedList(this.data);
        dest.writeParcelable(this.paging, flags);
    }

    public Events() {
    }

    protected Events(Parcel in) {
        this.data = in.createTypedArrayList(Datum.CREATOR);
        this.paging = in.readParcelable(Paging.class.getClassLoader());
    }

    public static final Parcelable.Creator<Events> CREATOR = new Parcelable.Creator<Events>() {
        @Override
        public Events createFromParcel(Parcel source) {
            return new Events(source);
        }

        @Override
        public Events[] newArray(int size) {
            return new Events[size];
        }
    };
}