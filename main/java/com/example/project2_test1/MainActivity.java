package com.example.project2_test1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int now_year = Calendar.getInstance().get(Calendar.YEAR);
        int now_month = Calendar.getInstance().get(Calendar.MONTH)+1 ;

        ViewPager2 vpPager = findViewById(R.id.vpPager);
        FragmentStateAdapter adapter = new MonthViewAdapter(this,4);
        vpPager.setAdapter(adapter);

        //position = 12(현재년도-2018(초기 년도))+(현재달 -1)
        // 초기년도 = 현재년도-(현재년도의 1월 포지션/12)
        // 초기년도 =  현재년도-(position-현재달+1)/12
        //-> 숫자를 정해놓고 연산통해 일반화
        vpPager.setCurrentItem(12*(now_year-2018)+(now_month-1)); //52



//앱바
        // 첫 페이지에 현재 날짜 표시
        ActionBar ab = getSupportActionBar();
        ab.setTitle((now_year)+ "년 " + now_month + "월");

        // 넘길때마다 포지션으로 날짜 변경해 앱바에 출력
        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
               // int year = Calendar.getInstance().get(Calendar.YEAR);
                int year = (position/12)+2018;
                int month = position%12+1;
                ActionBar ab = getSupportActionBar();
                ab.setTitle((year)+ "년 " + month + "월");
            }
        });
    }

//오버플로우
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

// 오버플로우 메뉴 선택시 프래그 먼트 전환
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