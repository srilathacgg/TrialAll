package cgg.gov.in.trialall.viewmodel2.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import cgg.gov.in.trialall.R;
import cgg.gov.in.trialall.viewmodel2.fragments.ImageFragment;
import cgg.gov.in.trialall.viewmodel2.fragments.MusicFragment;
import cgg.gov.in.trialall.viewmodel2.fragments.PdfFragment;
import cgg.gov.in.trialall.viewmodel2.fragments.VideoFragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    CircleImageView img1, img2, img3, img4;
    LinearLayout linearlayout_pdf, linearlayout_music, linearlayout_videos, linearlayout_images;
    Fragment fragment;
    FloatingActionMenu fab;
    FloatingActionButton fab_pdf, fab_music, fab_images, fab_videos;
    int selectedButton = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v2);

        img1 = findViewById(R.id.id1);
        img2 = findViewById(R.id.id2);
        img3 = findViewById(R.id.id3);
        img4 = findViewById(R.id.id4);

        linearlayout_pdf = findViewById(R.id.linearlayout_pdf);
        linearlayout_music = findViewById(R.id.linearlayout_music);
        linearlayout_images = findViewById(R.id.linearlayout_images);
        linearlayout_videos = findViewById(R.id.linearlayout_videos);

        fab = findViewById(R.id.fab);
        fab_pdf = findViewById(R.id.fab_pdf);
        fab_music = findViewById(R.id.fab_music);
        fab_images = findViewById(R.id.fab_images);
        fab_videos = findViewById(R.id.fab_videos);

        fab_pdf.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedButton = 1;
                fab.close(true);
                pdf();
            }
        });
        fab_music.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedButton = 2;
                fab.close(true);
                music();
            }
        });
        fab_images.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedButton = 3;
                fab.close(true);
                images();
            }
        });
        fab_videos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedButton = 4;
                fab.close(true);
                videos();
            }
        });


        if (savedInstanceState != null) {

            selectedButton = savedInstanceState.getInt("selectedButton");

            if (selectedButton == 1) {
                img1.setColorFilter(Color.parseColor("#da3c2f"));

            }
            if (selectedButton == 2) {
                img2.setColorFilter(Color.parseColor("#ce56ae"));

            }
            if (selectedButton == 3) {
                img3.setColorFilter(Color.parseColor("#4983c6"));

            }
            if (selectedButton == 4) {
                img4.setColorFilter(Color.parseColor("#30c9bf"));
            }
        } else {
            pdf();
        }

        linearlayout_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedButton = 1;
                pdf();
            }
        });

        linearlayout_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedButton = 2;
                music();
            }
        });

        linearlayout_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedButton = 3;
                images();
            }
        });

        linearlayout_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedButton = 4;
                videos();
            }
        });
    }

    public void pdf() {
        img1.setColorFilter(Color.parseColor("#da3c2f"));
        img2.setColorFilter(Color.parseColor("#ffffff"));
        img3.setColorFilter(Color.parseColor("#ffffff"));
        img4.setColorFilter(Color.parseColor("#ffffff"));

        fragment = new PdfFragment();
        loadFragment(fragment);
    }

    public void music() {
        img2.setColorFilter(Color.parseColor("#ce56ae"));
        img1.setColorFilter(Color.parseColor("#ffffff"));
        img3.setColorFilter(Color.parseColor("#ffffff"));
        img4.setColorFilter(Color.parseColor("#ffffff"));

        MusicFragment fragment = new MusicFragment();
        loadFragment(fragment);
    }

    public void images() {
        img3.setColorFilter(Color.parseColor("#4983c6"));
        img2.setColorFilter(Color.parseColor("#ffffff"));
        img1.setColorFilter(Color.parseColor("#ffffff"));
        img4.setColorFilter(Color.parseColor("#ffffff"));

        fragment = new ImageFragment();
        loadFragment(fragment);
    }

    public void videos() {
        img4.setColorFilter(Color.parseColor("#4983c6"));
        img2.setColorFilter(Color.parseColor("#ffffff"));
        img3.setColorFilter(Color.parseColor("#ffffff"));
        img1.setColorFilter(Color.parseColor("#ffffff"));

        fragment = new VideoFragment();
        loadFragment(fragment);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        pdf();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("selectedButton", selectedButton);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
