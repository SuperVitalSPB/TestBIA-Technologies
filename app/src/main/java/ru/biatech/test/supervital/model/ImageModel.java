package ru.biatech.test.supervital.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vitaly Oantsa on 20.03.2017.
 */

public class ImageModel implements Parcelable {
    public static final Parcelable.Creator<ImageModel> CREATOR = new Parcelable.Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel source) {
            return new ImageModel(source);
        }

        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };

    public enum Size {
        small, medium, large, extralarge
    }

    @SerializedName("#text")
    private String text;
    @SerializedName("size")
    private Size size;

    public ImageModel(String text, Size size) {
        this.text = text;
        this.size = size;
    }

    /**
     * Parcel implementation
     */
    private ImageModel(Parcel in) {
        this.text = in.readString();
        this.size.valueOf(in.readString());
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
        dest.writeString(text);
        dest.writeString(size.toString());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

}
