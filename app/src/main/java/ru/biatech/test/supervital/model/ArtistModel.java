package ru.biatech.test.supervital.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vitaly Oantsa on 15.03.2017.
 */

public class ArtistModel implements Parcelable {
    public static final Parcelable.Creator<ArtistModel> CREATOR = new Parcelable.Creator<ArtistModel>() {
        @Override
        public ArtistModel createFromParcel(Parcel source) {
            return new ArtistModel(source);
        }

        @Override
        public ArtistModel[] newArray(int size) {
            return new ArtistModel[size];
        }
    };

    @SerializedName("name")
    private String name;
    @SerializedName("mbid")
    private String mbid;
    @SerializedName("url")
    private String url;

    /**
     * Parcel implementation
     */
    private ArtistModel(Parcel in) {
        this.name = in.readString();
        this.mbid = in.readString();
        this.url =  in.readString();
    }

    /**
     * Parcel implementation
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Parcel implementation
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(mbid);
        dest.writeString(url);
    }

    public String getName() {
        return name;
    }

    public String getMbid() {
        return mbid;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
