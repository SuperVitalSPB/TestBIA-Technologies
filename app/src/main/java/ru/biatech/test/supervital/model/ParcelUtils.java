package ru.biatech.test.supervital.model;
import android.os.Parcel;

/**
 * Created by Vitaly Oantsa on 15.03.2017.
 */

public class ParcelUtils {
    public static boolean readBooleanFromParcel(Parcel parcel) {
        // How to read/write a boolean when implementing the Parcelable interface?
        // @link http://stackoverflow.com/a/7089687

        return (parcel.readInt() != 0);
    }
}
