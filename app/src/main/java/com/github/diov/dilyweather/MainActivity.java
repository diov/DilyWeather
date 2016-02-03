package com.github.diov.dilyweather;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.diov.dilyweather.engine.WeatherEngine;
import com.github.diov.dilyweather.model.WeatherModel;
import com.github.diov.dilyweather.utils.TimeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.textCond)
    TextView textCond;
    @Bind(R.id.imageCond)
    ImageView imageCond;
    @Bind(R.id.textNowtmp)
    TextView textNowtmp;
    @Bind(R.id.textOfftmp)
    TextView textOfftmp;
    @Bind(R.id.textAqi)
    TextView textAqi;
    @Bind(R.id.textQlf)
    TextView textQlf;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        initView();
        initData();

        loadBackdrop();
    }

    private void initData() {
        WeatherEngine.checkWeather();
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

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Shanghai");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherEngine.checkWeather();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showData(WeatherModel weatherModel) {
        WeatherModel.DataEntity dataEntity = weatherModel.getData().get(0);
        String cond = dataEntity.getNow().getCond().getTxt();
        String code = dataEntity.getNow().getCond().getCode();
        int mipmap = getResources().getIdentifier("w" + code, "mipmap", getPackageName());
        String nowTmp = dataEntity.getNow().getTmp();
        String maxTmp = dataEntity.getDaily_forecast().get(0).getTmp().getMax();
        String minTmp = dataEntity.getDaily_forecast().get(0).getTmp().getMin();
        String aqi = dataEntity.getAqi().getCity().getAqi();
        String qlty = dataEntity.getAqi().getCity().getQlty();

        textCond.setText(cond);
        textNowtmp.setText(nowTmp + "℃");
        textOfftmp.setText(minTmp + " | " + maxTmp + "℃");
        textAqi.setText("空气质量： " + aqi);
        textQlf.setText(qlty);
        imageCond.setImageResource(mipmap);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
