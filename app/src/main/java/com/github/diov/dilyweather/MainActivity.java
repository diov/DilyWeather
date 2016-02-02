package com.github.diov.dilyweather;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.github.diov.dilyweather.model.Contants;
import com.github.diov.dilyweather.model.WeatherModel;
import com.github.diov.dilyweather.utils.ConsumGsonRequest;
import com.github.diov.dilyweather.utils.TimeUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mainText;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

        loadBackdrop();
    }

    private void loadBackdrop() {
        final ImageView backdrop = (ImageView) findViewById(R.id.backdrop);
        if (TimeUtil.isDayorNight() == TimeUtil.DAY) {
            Glide.with(this).load(R.mipmap.day).centerCrop().into(backdrop);
        } else {
            Glide.with(this).load(R.mipmap.night).centerCrop().into(backdrop);
        }
    }

    /**
     * 初始化界面
     */
    private void initView() {
        mainText = ((TextView) findViewById(R.id.mainText));

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Shanghai");

        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void initData() {
        String url = Contants.URL + "?city=" + Contants.LOCAL + "&key=" + Contants.APIkey;
        ConsumGsonRequest consumGsonRequest = new ConsumGsonRequest(url, WeatherModel.class, null, new Response
                .Listener<WeatherModel>() {
            @Override
            public void onResponse(WeatherModel response) {
                String status = response.getData().get(0).getStatus();
                mainText.setText(status);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        AppContext.getInstance().addToRequestQueue(consumGsonRequest);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }
}
