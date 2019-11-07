package cgg.gov.in.trialall.swipetorefresh;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import cgg.gov.in.trialall.R;

public class SwipeToRefreshActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    TextView textView;
    AdView adView1;
    AdRequest adRequest;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intersteialads);
        adView1 = findViewById(R.id.ad_view);
        adRequest = new AdRequest.Builder().build();
        adView1.loadAd(adRequest);
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        textView = findViewById(R.id.textView);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SwipeToRefreshActivity.this, InterstitialAdsActivity.class);
                startActivity(intent);

            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                //your code on swipe refresh
                //we are checking networking connectivity
                boolean connection = isNetworkAvailable();
                if (connection) {
                    textView.setText("internet connect");
                    textView.setTextColor(Color.GREEN);
                } else {
                    textView.setText("not connected");
                    textView.setTextColor(Color.RED);
                }

            }
        });
        swipeRefreshLayout.setColorSchemeColors(Color.YELLOW);
    }

    @Override
    public void onPause() {
        if (adView1 != null) {
            adView1.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView1 != null) {
            adView1.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView1 != null) {
            adView1.destroy();
        }
        super.onDestroy();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }
}