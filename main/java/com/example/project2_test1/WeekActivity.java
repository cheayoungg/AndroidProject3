package com.example.project2_test1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.Calendar;
import java.util.List;

public class WeekActivity extends AppCompatActivity {

    Calendar Cal;

    int now_year;
    int now_month;
    int now_week;
    int day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        //calendar인스턴스 설정
        Cal = Calendar.getInstance();

        ViewPager2 vpPager = findViewById(R.id.vpPager);
        FragmentStateAdapter adapter = new WeekFragmentAdapter(this);
        vpPager.setAdapter(adapter);
        vpPager.setCurrentItem(3);  //

        now_year = Calendar.getInstance().get(Calendar.YEAR);
        now_month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        now_week = Calendar.getInstance().get(Calendar.WEEK_OF_MONTH);
        day = Calendar.getInstance().get(Calendar.DATE);

        /*
        int year = (position/12)+2018;
        int month = position%12+1;

        int count;
        if(now_week==0)
            count=0;
        else if (now_week==1)
            count=1;
        else if (now_week==2)  */


 //앱바

        ActionBar ab = getSupportActionBar();
        ab.setTitle((now_year)+ "년 " + now_month + "월");
        //
        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                ActionBar ab = getSupportActionBar();
                if(now_week<7)
                   ab.setTitle(now_year+ "년 " + now_month + "월");
                else if(now_week == 0){
                   now_week=0;
                   ab.setTitle(now_year+ "년 " + now_month+1 + "월");
                   }
                now_week+=1;
    }
});



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.month:
                MonthFragment monthFragment = new MonthFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.month_fragment,monthFragment).commit();
                return true;
            case R.id.week:
                Intent intent = new Intent(this, WeekActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}