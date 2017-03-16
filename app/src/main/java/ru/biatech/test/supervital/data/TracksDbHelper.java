package ru.biatech.test.supervital.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import ru.biatech.test.supervital.Const;
import ru.biatech.test.supervital.model.TrackModel;

import static ru.biatech.test.supervital.data.TracksFavContract.TracksFavEntry.TABLE_NAME;

/**
 * Created by Vitaly Oantsa on 16.03.2017.
 */

public class TracksDbHelper extends SQLiteOpenHelper {
    public static final String TAG = TracksDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "tracks.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * Конструктор {@link TracksDbHelper}.
     *
     * @param context Контекст приложения
     */
    public TracksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Строка для создания таблицы
        String SQL_CREATE_GUESTS_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + TracksFavContract.TracksFavEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TracksFavContract.TracksFavEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + TracksFavContract.TracksFavEntry.COLUMN_NAME_ARTIST + " TEXT NOT NULL, "
                + TracksFavContract.TracksFavEntry.COLUMN_URL + " TEXT);";
        db.execSQL(SQL_CREATE_GUESTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean getIsTrackFavorite(String name, String name_artist){
        boolean res =false;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            String[] projection = {TracksFavContract.TracksFavEntry._ID};

            String selection = TracksFavContract.TracksFavEntry.COLUMN_NAME + "= ? " +
                    " AND " + TracksFavContract.TracksFavEntry.COLUMN_NAME_ARTIST + "= ?";
            String[] selectionArgs = {name, name_artist};

            cursor = db.query(
                    TracksFavContract.TracksFavEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);

            res = cursor.getCount() != 0;
        }
        finally {
            if (cursor!=null)
                    cursor.close();
        }

        return res;
    }

    public Integer getIsTrackFavoriteImgResource(String name, String name_artist){
        return getIsTrackFavorite(name, name_artist) ? Const.iOn : Const.iOff;
    }


    public void setIsTrackFavorite(String name, String name_artist, String url, boolean newFav){
        SQLiteDatabase db = getWritableDatabase();
        if (newFav){
            ContentValues values = new ContentValues();
            values.put(TracksFavContract.TracksFavEntry.COLUMN_NAME, name);
            values.put(TracksFavContract.TracksFavEntry.COLUMN_NAME_ARTIST, name_artist);
            values.put(TracksFavContract.TracksFavEntry.COLUMN_URL, url);
            long newRowId = db.insert(TracksFavContract.TracksFavEntry.TABLE_NAME, null, values);
            Log.d(TAG, "newRowId = " + newRowId);
        }
        else {
            db.delete(TracksFavContract.TracksFavEntry.TABLE_NAME,
                    TracksFavContract.TracksFavEntry.COLUMN_NAME + "= ? " +
                    " AND " + TracksFavContract.TracksFavEntry.COLUMN_NAME_ARTIST + "= ? ",
                    new String[] {name, name_artist});
        }
    };

    public ArrayList<TrackModel> getTracksFavoriteList(){
        ArrayList<TrackModel> res = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                TracksFavContract.TracksFavEntry._ID,
                TracksFavContract.TracksFavEntry.COLUMN_NAME,
                TracksFavContract.TracksFavEntry.COLUMN_NAME_ARTIST,
                TracksFavContract.TracksFavEntry.COLUMN_URL};
        Cursor cursor = db.query(
                TracksFavContract.TracksFavEntry.TABLE_NAME,
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                 // порядок сортировки
        try {
            int idColumnIndex = cursor.getColumnIndex(TracksFavContract.TracksFavEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(TracksFavContract.TracksFavEntry.COLUMN_NAME);
            int name_artistColumnIndex = cursor.getColumnIndex(TracksFavContract.TracksFavEntry.COLUMN_NAME_ARTIST);
            int urlColumnIndex = cursor.getColumnIndex(TracksFavContract.TracksFavEntry.COLUMN_URL);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                String name_artist = cursor.getString(name_artistColumnIndex);
                String url = cursor.getString(urlColumnIndex);
                res.add(new TrackModel(currentID, name, name_artist, url));
            }
            if (res.size() == 0)
                res.add(TrackModel.getEmptyTrackModel());
        }
        finally {
            cursor.close();
        }
        return res;
    }
}
