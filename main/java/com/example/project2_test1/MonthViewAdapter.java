package com.example.project2_test1;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Calendar;

public class MonthViewAdapter extends FragmentStateAdapter {
    public int mCount;
    int pageMax = 100;

    public MonthViewAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }
// createFragmnet 밖에서 구함 : 페이지 넘길때마다 새로 구하는 것을 방지하기 위해
    int now_year = Calendar.getInstance().get(Calendar.YEAR);
    int now_month = Calendar.getInstance().get(Calendar.MONTH)+1 ;

    @Override
    public Fragment createFragment(int position) {
        //int index = getRealPosition(position);
        //int n;
        // if (now_month == 12)
        //  now_year++;

//  최종
        now_month = position % 12 + 1;  //처음 페이지로 설정한 52 % 12 +1 = 5
        now_year = (position /12)+2018;  //2022년도를 페이지의 중간으로 설정, 총 9년(2018~2026)의 데이터 자료
        MonthFragment month = MonthFragment.newInstance(now_year, now_month);  //인스턴스로 선언
        return month;


       /* if (now_month == 12)
            now_year++;
            now_month= (Calendar.getInstance().get(Calendar.MONTH)+position)%12+1;
            return MonthFragment.newInstance(now_year, now_month);*/


/*/ index를 기준으로 월을 계산
        //int index = 52;

        if (position==index){
            MonthFragment month=MonthFragment.newInstance(now_year,now_month);
            return month;
        }
        else if(position<index){
            //n=index-position;
           // now_month -=n;
            index--;
            if(now_month==0){
                //index=position+1;
                now_month = 12;
                now_year--;
            }
            MonthFragment month=MonthFragment.newInstance(now_year,now_month--);
            return month;
        }
        else if(position>index){
            //n=position-index;
            index++;
            if(now_month==13){
                //index=position-1;
                now_month=1;
                now_year++;
            }
            MonthFragment month=MonthFragment.newInstance(now_year,now_month++);
            return month;
        }
        else return null;*/
    }


    // 전체 페이지 개수 반환
    @Override
    public int getItemCount() {
        return pageMax ;
    }//최대 페이지 수 100개

   // public int getRealPosition(int position) { return position % mCount; }

}

