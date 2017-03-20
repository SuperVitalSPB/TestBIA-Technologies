package ru.biatech.test.supervital.activity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.biatech.test.supervital.testbia_tech.R;

public class CardTrack extends AppCompatActivity {
    private static final String TAG = CardTrack.class.getSimpleName();

    public static final String NAME_TRACK_ARTIST = "name_track_artist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_track);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final String[] names = intent.getStringArrayExtra(NAME_TRACK_ARTIST);

        TextView name_track = (TextView) findViewById(R.id.name_track);
        name_track.setText(names[0]);
        TextView name_artist = (TextView) findViewById(R.id.name_artist);
        name_artist.setText(names[1]);

        

        Button btnViewOnSite = (Button) findViewById(R.id.btnViewOnSite);
        btnViewOnSite.setEnabled(names[2].length()!=0);
        btnViewOnSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(names[2]));
                startActivity(browserIntent);
            }
        });

        if (names[3].length()!=0) {
            ImageView image = (ImageView) findViewById(R.id.image);
            Picasso.with(this).load(names[3]).into(image);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
