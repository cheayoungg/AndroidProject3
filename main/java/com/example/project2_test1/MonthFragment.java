package com.example.project2_test1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthFragment extends Fragment {
    Calendar cal;
    ArrayList<String> dayList;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int mParam1;
    private int mParam2;

    int year;
    int month;

    public MonthFragment(){}

    public static MonthFragment newInstance(int year, int month) {
        MonthFragment fragment = new MonthFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year);
        args.putInt(ARG_PARAM2, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View monthView= inflater.inflate(R.layout.fragment_month, container, false);
        // 그리드뷰 객체 얻어옴
        GridView gridView = monthView.findViewById(R.id.gridview);

/*/메모 엑티비티로 이동
        FloatingActionButton fab =  monthView.findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            //Toast.makeText(getActivity().getApplicationContext(), "Test", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity(), MonthCalAddActivity.class);
            startActivity(intent);
        });*/

        dayList=new ArrayList<String>();
        cal = Calendar.getInstance();

//파라미터에 저장된 값을 넣어 cal설정
        cal.set(Integer.parseInt(String.valueOf(mParam1)), Integer.parseInt(String.valueOf(mParam2))-1, 1);

        //cal.set(year, month-1, 1); //이번달 1일 set
        int startday = cal.get(Calendar.DAY_OF_WEEK); //1일의 요일
// 1일 전 요일들에 공백채우기
        for (int i = 1; i < startday; i++) {
            dayList.add("");
        }
//현재 월에 끝일 구하기
        setCalDate(cal.get(Calendar.MONTH) + 1);
//6행 맞추기위한 공백채우기
        for(int i=dayList.size();i<42; i++){ // 6행7열 (42)- 리스트항목 개수 빼면..
            dayList.add("");
        }

//어댑터 연결
        ArrayAdapter<String>adapter
                = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        dayList ) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        //
                        // 어떠한 방향이든 상관없이 그리드뷰로 화면을 꽉 채우기 위해 각 칸의 높이를
                        // 그리드뷰의 높이 / 6 으로 설정
                        TextView tv1 = (TextView) super.getView(position, convertView, parent);

                        tv1.setBackgroundColor(Color.WHITE);
                        int gridviewH = gridView.getHeight() / 6;
                        // 화면 넘어가는 것 방지
                        // 외부코드 참고
                        tv1.setHeight(gridviewH-2);
                        return tv1;
                    }
        };
        gridView.setAdapter(adapter);

        //View caladd = inflater.inflate(R.layout.activity_month_cal_add,container,false);

// 그리드뷰 선택시
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),
                        mParam1+"/"+mParam2+"/"+dayList.get(i),Toast.LENGTH_SHORT).show(); //파라메터로 받아온 정보활용
                //view.setBackgroundColor(Color.YELLOW); // 색깔 누적

                //그리드뷰 선택 후 이동
                FloatingActionButton fab =  monthView.findViewById(R.id.fab);
                fab.setOnClickListener(nview -> {//  ??
                    //Toast.makeText(getActivity().getApplicationContext(), "Test", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), MonthCalAddActivity.class);
                    intent.putExtra("year",mParam1);
                    intent.putExtra("month",mParam2);
                    intent.putExtra("day",dayList.get(i));
                    startActivity(intent);
                });


                //해당 그리드뷰 날짜 month_cal_add Activity title에 뿌려주기  ??
               /*/ EditText settitle = (EditText) caladd.get
                ((MonthCalAddActivity)getActivity()).selectedYear = mParam1;
                ((MonthCalAddActivity)getActivity()).selectedMonth = mParam2;
                ((MonthCalAddActivity)getActivity()).selectedDay = dayList.get(i);*/

            }
        });

        //DBHelper helper = new DBHelper(this);
        return monthView;
    }

    // 해당 월에 표시할 일 수
    private void setCalDate(int now_month){
        cal.set(Calendar.MONTH, now_month - 1);

        for (int i = 0; i < cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add(String.valueOf(i+1));
        }
    }
}