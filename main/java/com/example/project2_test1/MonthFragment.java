package com.example.project2_test1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthFragment extends Fragment {
    Calendar cal;
    ArrayList<String> dayList;
    ArrayList<String> titleList;
    ArrayList<GridItem> calList;
    ArrayList<ItemMonth> monthList;
    //GridAdapter gridAdapter;
    static ItemAdapter adapt;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG_TEXT = "text";

    private int mParam1;
    private int mParam2;
    private DBHelper mDbHelper;

    int year;
    int month;

    AlertDialog.Builder builder; //다이얼로그
    String[] title; //다이얼로그 row
    List<String> dialogItem ;

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
        mDbHelper = new DBHelper(this.getContext());
        monthList = new ArrayList<ItemMonth>();

/*/메모 엑티비티로 이동
        FloatingActionButton fab =  monthView.findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            //Toast.makeText(getActivity().getApplicationContext(), "Test", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity(), MonthCalAddActivity.class);
            startActivity(intent);
        });*/
        calList=new ArrayList<GridItem>();
        dayList=new ArrayList<String>();
        titleList=new ArrayList<String>();
        cal = Calendar.getInstance();

//파라미터에 저장된 값을 넣어 cal설정
        cal.set(Integer.parseInt(String.valueOf(mParam1)), Integer.parseInt(String.valueOf(mParam2))-1, 1);

        //cal.set(year, month-1, 1); //이번달 1일 set
        int startday = cal.get(Calendar.DAY_OF_WEEK); //1일의 요일
// 1일 전 요일들에 공백채우기
        for (int i = 1; i < startday; i++) {
            dayList.add("");
            titleList.add("");
            monthList.add(new ItemMonth(String.valueOf(mParam1), String.valueOf(mParam2), null, null, null));
            //calList.add(new GridItem(" "," "));
        }
//현재 월에 끝일 구하기
        setCalDate(cal.get(Calendar.MONTH) + 1);
//6행 맞추기위한 공백채우기
        for(int i=dayList.size();i<42; i++){ // 6행7열 (42)- 리스트항목 개수 빼면..
            dayList.add("");
            //calList.add(new GridItem(" "," "));
            titleList.add("");
        }
        for(int i=0;i<42;i++){
            //GridItem gridItem = new GridItem(String.valueOf(dayList.get(i)),String.valueOf(titleList.get(i)));
            calList.add(new GridItem(" "," "));
            calList.get(i).setDay(dayList.get(i));
            monthList.add(new ItemMonth(String.valueOf(mParam1), String.valueOf(mParam2), null, null, null));
         //   calList.get(i).setTitle(titleList.get(i));
            //calList.add(gridItem);
        }

        Cursor cursor = mDbHelper.getAllUsersBySQL();

//어댑터 연결
        adapt = new ItemAdapter(
                        getActivity(),
                        R.layout.gridview_item,
                        monthList ) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view =super.getView(position,convertView,parent);
                        View tv_cell = (View) view.findViewById(R.id.month_item);
                        tv_cell.getLayoutParams().height = gridView.getHeight()/6;
                        return tv_cell;

                    }
        };
       // gridView.setAdapter(adapter);
        gridView.setAdapter(adapt);

// 그리드뷰 선택시
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

           /* public void showDialog(View view){
                final String[] items = new String[]{"채영","민아"};
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("목록");
                dlg.setItems(items,new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        // Toast.makeText(MainActivity.this,items[which],Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();
            }*/
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GridItem gridItem = calList.get(i);
                Toast.makeText(getActivity(),
                        mParam1+"/"+mParam2+"/"+dayList.get(i),Toast.LENGTH_SHORT).show(); //파라메터로 받아온 정보활용

                //아무것도 없으면 :그리드뷰 선택 후 이동 , 일정있으면 : 다이어로그
                //if(titleList.get(i)== null)
                FloatingActionButton fab =  monthView.findViewById(R.id.fab);
                fab.setOnClickListener(nview -> {//  ??
                    //Toast.makeText(getActivity().getApplicationContext(), "Test", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), MonthCalAddActivity.class);
                    intent.putExtra("year",mParam1);
                    intent.putExtra("month",mParam2);
                    intent.putExtra("day",dayList.get(i));
                    startActivity(intent);

                });
                //뭐가 있으면 -> 다이어로그 title.get(i) 가
                // showDialoglist();
               // showDialog(getActivity());
                /*
                //final String[] items = new String[]{"채영","민아"};
                //String item = StringOfvalues(titleList.get(i));
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                //dlg.setTitle(mParam1+"/"+mParam2+"/"+dayList.get(i));
                item[1] = StringOfvalues(titleList.get(i));
                dlg.setItems(items,new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        // Toast.makeText(MainActivity.this,items[which],Toast.LENGTH_SHORT).show();

                    }
                });
                dlg.show();*/



                /* Activity activity = getActivity();

                // 선택된 항목 위치(position)을 OnTitleSelectedListener 인터페이스를 구현한 액티비티로 전달
                if (activity instanceof OnTitleSelectedListener)
                    ((OnTitleSelectedListener)activity).onTitleSelected(position);
            }*/


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
//그리드뷰 i번째 항목 선택 알려주는 인터페이스
    public interface OnTitleSelectedListener {
        public void onTitleSelected(int i);
    }

    // 해당 월에 표시할 일 수
    private void setCalDate(int now_month) {
        cal.set(Calendar.MONTH, now_month - 1);
        mDbHelper = new DBHelper(getActivity());
        //Cursor cursor = mDbHelper.getAllUsersBySQL();
        //String puttitle = getIntent().getStringExtra("title");

        for (int i = 0; i < cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add(String.valueOf(i + 1));

            Cursor cursor = mDbHelper.getDayUsersBySQL(String.valueOf(mParam1), String.valueOf(mParam2), String.valueOf(i));
            //int titlenum = cursor.getColumnIndex(UserContract.Users.KEY_TITLE);
            if(cursor.moveToNext()){
                int titlenum = cursor.getColumnIndex(UserContract.Users.KEY_TITLE);
                monthList.add(new ItemMonth(String.valueOf(mParam1), String.valueOf(mParam2),
                        String.valueOf(i),cursor.getString(titlenum),null));
            }else{
                monthList.add(new ItemMonth(String.valueOf(mParam1), String.valueOf(mParam2),
                        String.valueOf(i), null, null));
            }

    }

    /*private void showDialoglist(){
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog,null);
        builder.setView(view);

        final ListView listView = (ListView)view.findViewById(R.id.dialog_listview);
        final AlertDialog dialog = builder.create();

        /*SimpleAdapter simpleAdapter=new SimpleAdapter(getActivity(),dialogItem,R.layout.dialog_row,
                new String[]{TAG_TEXT},new int[]{R.id.dialog_row});
        listView.setAdapter(simpleAdapter);
        listView.setOnClickListener(new );*/


        /* Cursor cursor = mDbHelper.getAllUsersByMethod();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.dialog_row, cursor, new String[]{
                UserContract.Users.KEY_TITLE},
                new int[]{R.id.dialog_row}, 0);

        ListView lv = (ListView)findViewById(R.id.listview);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Adapter adapter = adapterView.getAdapter();

                Intent intent = new Intent(getActivity(), MonthCalAddActivity.class);
                intent.putExtra("id",((Cursor)adapter.getItem(i)).getString(0));
                intent.putExtra("title",((Cursor)adapter.getItem(i)).getString(1));
                intent.putExtra("startH",((Cursor)adapter.getItem(i)).getInt(2));
                intent.putExtra("startM",((Cursor)adapter.getItem(i)).getInt(3));
                intent.putExtra("endH",((Cursor)adapter.getItem(i)).getInt(4));
                intent.putExtra("endM",((Cursor)adapter.getItem(i)).getInt(5));
                intent.putExtra("memo",((Cursor)adapter.getItem(i)).getString(6));
                startActivity(intent);
        });
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }*/

   /* private class GridAdapter extends ArrayAdapter<GridItem> {

       // private List<String> list;  //날짜
        private List<GridItem> list;
        //private List<String> callist; // 일정
        private LayoutInflater inflater;
        TextView tvItemGridView;
        TextView todo1;

        public GridAdapter(Context context, List<GridItem> list) {
            super(context,R.layout.gridview_item,list);
            this.list = list;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {

            return list.size();
        }

       // @Override
        //public String getItem(int position) {
           // return list.get(position);
        //}
        //public StringBuffer getcalItem(int position) {
        //    return callist.get(position);
       // }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.gridview_item, parent, false);

            }

            tvItemGridView = (TextView)convertView.findViewById(R.id.tv_item_gridview);
            todo1 = (TextView)convertView.findViewById(R.id.todo1);

            final GridItem countryItem = list.get(position);
            tvItemGridView.setText(countryItem.getDay());
            todo1.setText(countryItem.getTitle());

            //StringBuffer buf = new StringBuffer();
            //buf.append(callist.get(position));
            //buf.append("" + callist.get(position)+"\n");

           // tvItemGridView.setText("" + getItem(position));
            //todo1.setText(callist.get(position));
            //todo1.setText("" + getItem(position) + "\n" +getcalItem((position)));
            //todo1.setText(buf);

           mDbHelper = new DBHelper(getActivity());
            Cursor cursor = mDbHelper.getAllUsersBySQL();
           // TextView result = monthView.findViewById(R.id.result);

            /*StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                buffer.append(cursor.getInt(0)+" \t");
                buffer.append(cursor.getString(1)+" \t");
                buffer.append(cursor.getString(2)+"\n");
            }
            todo1.setText(buffer);*/

         //   return convertView;
        //}
    }

}