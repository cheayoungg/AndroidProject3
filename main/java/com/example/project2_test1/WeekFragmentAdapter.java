package com.example.project2_test1;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Calendar;

public class WeekFragmentAdapter extends FragmentStateAdapter {
    int pageMax = 50;
    //int index=3;
    /*int now_year = Calendar.getInstance().get(Calendar.YEAR);
    int now_month = Calendar.getInstance().get(Calendar.MONTH) + 1;
    int now_week = Calendar.getInstance().get(Calendar.WEEK_OF_MONTH) - 1;*/

    public WeekFragmentAdapter(FragmentActivity fa) {

        super(fa); //
        now_year = Calendar.getInstance().get(Calendar.YEAR);
        now_month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        now_week = Calendar.getInstance().get(Calendar.WEEK_OF_MONTH)-1  ; //0-5 6 4

    }
    int now_year;
    int now_month;
    int now_week;
    static int year;
    static int months;
    static int weeks;
    // 각 페이지를 나타내는 프래그먼트 반환
    @Override
    public Fragment createFragment(int position) {

        // 포지션 활용 권장!!!!!

        Log.d("test", "createFragment: "+position+" "+weeks);
        int index = 3;
        if (position == index) {
            WeekFragment week = new WeekFragment(now_year, now_month, now_week);
            weeks=now_week;
            months=now_month;
            return week;
        }
        else if (position < index) {
            --index;
            --weeks;
            if(weeks == -1){
                weeks=5;
                months-=1;
                WeekFragment week=new WeekFragment(now_year,months, weeks);
                return week;
            }
            else{
                WeekFragment week = new WeekFragment(now_year, months, weeks);
                return week;}

        }
        else if (position > index) {
            ++index;//
            weeks++;

            if (weeks == 6) {
                weeks=0;
                months+=1;
                WeekFragment week = new WeekFragment(now_year, months, weeks);
                return week;
            }
            else {WeekFragment week = new WeekFragment(now_year, months, weeks);
                return week;}

        }
        else return null;
    }
    // 전체 페이지 개수 반환
    @Override
    public int getItemCount() {
        return pageMax;
    }
}