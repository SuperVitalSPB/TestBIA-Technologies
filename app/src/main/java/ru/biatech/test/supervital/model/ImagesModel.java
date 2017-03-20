package ru.biatech.test.supervital.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vitaly Oantsa on 20.03.2017.
 */

public class ImagesModel implements Parcelable {
    public static final Parcelable.Creator<ImagesModel> CREATOR = new Parcelable.Creator<ImagesModel>() {
        @Override
        public ImagesModel createFromParcel(Parcel source) {
            return new ImagesModel(source);
        }

        @Override
        public ImagesModel[] newArray(int size) {
            return new ImagesModel[size];
        }
    };

    @SerializedName("image")
    private List<ImageModel> image;

    /**
     * Parcel implementation
     */
    private ImagesModel(Parcel in) {
        if (ParcelUtils.readBooleanFromParcel(in)) this.image = in.createTypedArrayList(ImageModel.CREATOR);
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
        dest.writeTypedList(image);
    }

    public List<ImageModel> getImage() {
        return image;
    }

    public void setImage(List<ImageModel> image) {
        this.image = image;
    }
}
