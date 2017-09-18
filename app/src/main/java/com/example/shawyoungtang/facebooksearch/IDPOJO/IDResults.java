package com.example.shawyoungtang.facebooksearch.IDPOJO;


import android.os.Parcel;
import android.os.Parcelable;

public class IDResults implements Parcelable {

    private String id;
    private String name;
    private Picture picture;
    private Albums albums;
    private Posts posts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Albums getAlbums() {
        return albums;
    }

    public void setAlbums(Albums albums) {
        this.albums = albums;
    }

    public Posts getPosts() {
        return posts;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeParcelable(this.picture, flags);
        dest.writeParcelable(this.albums, flags);
        dest.writeParcelable(this.posts, flags);
    }

    public IDResults() {
    }

    protected IDResults(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.picture = in.readParcelable(Picture.class.getClassLoader());
        this.albums = in.readParcelable(Albums.class.getClassLoader());
        this.posts = in.readParcelable(Posts.class.getClassLoader());
    }

    public static final Parcelable.Creator<IDResults> CREATOR = new Parcelable.Creator<IDResults>() {
        @Override
        public IDResults createFromParcel(Parcel source) {
            return new IDResults(source);
        }

        @Override
        public IDResults[] newArray(int size) {
            return new IDResults[size];
        }
    };
}