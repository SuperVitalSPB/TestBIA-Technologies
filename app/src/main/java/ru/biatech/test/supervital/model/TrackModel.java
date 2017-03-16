package ru.biatech.test.supervital.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vitaly Oantsa on 15.03.2017.
 */

public class TrackModel implements Parcelable {
    public static final Parcelable.Creator<TrackModel> CREATOR = new Parcelable.Creator<TrackModel>() {
        @Override
        public TrackModel createFromParcel(Parcel source) {
            return new TrackModel(source);
        }

        @Override
        public TrackModel[] newArray(int size) {
            return new TrackModel[size];
        }
    };

    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
    @SerializedName("artist")
    private ArtistModel artist;

    private Integer currentID;
    private String name_artist;

    public TrackModel(Integer currentID, String name, String name_artist, String url) {
        this.currentID = currentID;
        this.name = name;
        this.url =  url;
        this.name_artist =  name_artist;
    }

    public static TrackModel getEmptyTrackModel(){
        return new TrackModel(-1, "", "", "");
    }


    public Integer getId() {
        return currentID;
    }

    public void setId(Integer currentID) {
        this.currentID = currentID;
    }

    public String getName_artist() {
        return name_artist;
    }

    public void setName_artist(String name_artist) {
        this.name_artist = name_artist;
    }

    /**
     * Parcel implementation
     */
    private TrackModel(Parcel in) {
        this.name = in.readString();
        this.url =  in.readString();
        this.artist =  in.readParcelable(ArtistModel.class.getClassLoader());
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
        dest.writeString(url);
        dest.writeParcelable(artist, 0);
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public ArtistModel getArtist() {
        return artist;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setArtist(ArtistModel artist) {
        this.artist = artist;
    }
}
