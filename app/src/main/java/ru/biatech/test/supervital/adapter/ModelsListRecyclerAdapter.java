package ru.biatech.test.supervital.adapter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ru.biatech.test.supervital.ApplicationBIATech;
import ru.biatech.test.supervital.Const;
import ru.biatech.test.supervital.testbia_tech.R;
import ru.biatech.test.supervital.activity.CardTrack;
import ru.biatech.test.supervital.model.TrackModel;

/**
 * Created by Vitaly Oantsa on 15.03.2017.
 */

public class ModelsListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ModelsListRecyclerAdapter.class.getSimpleName();

    private final FragmentActivity context;
    private ApplicationBIATech myAppl;
    private ArrayList<TrackModel> models;

    public boolean isBottomList;
   

    View.OnClickListener mViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "mViewOnClickListener");
            String[] nf = (String[]) view.getTag();
            if (nf==null)
                return;
            Intent intent = new Intent(context, CardTrack.class)
                    .putExtra(CardTrack.NAME_TRACK_ARTIST, nf);
            context.startActivity(intent);
        }
    };

    View.OnTouchListener mStarOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setTop(v.getTop() + 10);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                v.setTop(v.getTop() - 10);
                v.performClick(); // то, по чему помазали то и тапнули
            }
            return true;
        }
    };


    public ModelsListRecyclerAdapter(ArrayList<TrackModel> models, FragmentActivity context) {
        this.models = models;
        this.context = context;
        myAppl = (ApplicationBIATech) context.getApplication();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_model, parent, false);
        return new ViewHolderTrack(v);
    }

    ViewHolderTrack holderTrack;
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holderTrack = (ViewHolderTrack) holder;
        final TrackModel model = models.get(position);
        String name = model.getName(),
                name_artist = (model.getArtist()==null ? model.getName_artist() : model.getArtist().getName()),
                url = model.getUrl();

        String[] names = {name, name_artist, url}; // для формы с инфой
        if (model.getId()!=null && model.getId()==-1) {
            names = null;
            name = context.getString(R.string.listEmpty);
        }
        String sStr =  "name = " + name +
                " name_artist = " + name_artist +
                " isFav = " + myAppl.getmDbHelper().getIsTrackFavorite(name, name_artist);
        Log.d(TAG, sStr);

        holderTrack.image_fav_star.setVisibility(model.getId()!=null && model.getId()==-1 ? View.INVISIBLE : View.VISIBLE);

        ((CardView) holderTrack.name.getParent().getParent().getParent()).setOnClickListener(mViewOnClickListener);
        ((CardView) holderTrack.name.getParent().getParent().getParent()).setTag(names);

        holderTrack.name.setText(name);
        holderTrack.name.setOnClickListener(mViewOnClickListener);
        holderTrack.name.setTag(names);

        holderTrack.artist_name.setText(name_artist);
        holderTrack.artist_name.setOnClickListener(mViewOnClickListener);
        holderTrack.artist_name.setTag(names);


        int isFav = myAppl.getmDbHelper().getIsTrackFavoriteImgResource(name, name_artist);
        holderTrack.image_fav_star.setImageResource(isFav);
        holderTrack.image_fav_star.setTag(isFav);
        holderTrack.image_fav_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = model.getName(),
                        name_artist = (model.getArtist()==null ? model.getName_artist() : model.getArtist().getName()),
                        url = model.getUrl();

                boolean newFav = Integer.valueOf(view.getTag().toString()).equals(Const.iOff);

                String sStr = "image clicked, position: " + position +
                        " name = " + name +
                        " name_artist = " + name_artist +
                        " url = " + url +
                        " newFav = " + newFav;
                Log.d(TAG, sStr);

                myAppl.getmDbHelper().setIsTrackFavorite(name, name_artist, url, newFav);
                notifyDataSetChanged();
            }
        });
        holderTrack.image_fav_star.setOnTouchListener(mStarOnTouchListener);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolderTrack extends RecyclerView.ViewHolder {
        public TextView name, artist_name;
        public ImageView image_fav_star;

        public ViewHolderTrack(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            artist_name = (TextView) v.findViewById(R.id.artist_name);
            image_fav_star = (ImageView) v.findViewById(R.id.image_fav_star);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Log.d(TAG, "getItemViewType");
        isBottomList = (position == models.size()-1);
        return super.getItemViewType(position);
    }
}