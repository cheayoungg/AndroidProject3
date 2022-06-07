package com.example.project2_test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class MonthCalAddActivity extends AppCompatActivity implements OnMapReadyCallback {

    final static String TAG="SQLITEDBTEST";

    private static final int REQUEST_PERMISSIONS_FOR_LOCATION_UPDATES = 1;
    private static final int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION = 2;
    private FusedLocationProviderClient mFusedLocationClient;

    private DBHelper mDbHelper;
    Location mLocation;
    GoogleMap mMap;
    Geocoder geocoder;
    Button mapBtn;  // 찾기
    //Button saveBtn;  //저장
    //Button deleteBtn; //삭제
    //Button cancleBtn;  //취소
    EditText mId;
    EditText meditMap;
    EditText mTitle;
    EditText mMemo;
    TimePicker mstartTP;
    TimePicker mendTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_cal_add);

        mId = (EditText)findViewById(R.id._id);
        mTitle = (EditText) findViewById(R.id.edit_title);
        mMemo = (EditText) findViewById(R.id.edit_memo);
        meditMap = (EditText) findViewById(R.id.search);
        mDbHelper = new DBHelper(this);

                        /*
                  if (year == 0 || moth ==0)
                 String id = getIntent().getStringExtra("id",-1);
                 String title = getIntent().getStringExtra("title",-1);
                 int startH = getIntent().getIntExtra("startH",-1);
                 int startM = getIntent().getIntExtra("startM",-1);
                 int endH = getIntent().getIntExtra("endH",-1);
                 int endM = getIntent().getIntExtra("endM",-1);
                 String memo = getIntent().getStringExtra("memo",-1);

                mId.setText(id);
                mTitle.setText(title);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mstartTP.setHour(startH);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mstartTP.setMinute(startM);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mendTP.setHour(endH);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mendTP.setMinute(endM);
                }
                mMemo.setText(memo);

                */

        // 선택된 날짜 정보 받아옴 defaltValue ==-1이라면 if()
        int year = getIntent().getIntExtra("year",0);
        int month = getIntent().getIntExtra("month",0);
        String day = getIntent().getStringExtra("day");
        // int day = intent.getIntExtra("day", 0);
        // int hour = intent.getIntExtra("hour", 0);

        String Y = String.valueOf(year);
        String M = String.valueOf(month);

        mTitle.setText(Y+"/"+M+"/"+day);

        //엑티비티의 초기 화면 :  이전정보 있다면 그 정보 출력,
        // 원래 정보 들어있었다면 그 정보 수정,
       /* SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String sql = "select *from myDiary where diaryDate="+editTitle+"";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor == null) saveBtn.setText("저장");
        else saveBtn.setText("수정"); */

        mstartTP = (TimePicker)findViewById(R.id.start_time) ;
        TextView sttvH = (TextView) findViewById(R.id.resultSTH);  //시작 시
        TextView sttvM = (TextView) findViewById(R.id.resultSTM);   //시작 분
        mstartTP.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                // sttvH.setText(i);
                // sttvM.setText(i1);
                String Shour = String.valueOf(i);
                String Smin = String.valueOf(i1);
                sttvH.setText(Shour);
                sttvM.setText(Smin);
            }
        });
        mendTP = (TimePicker)findViewById(R.id.end_time) ;
        TextView ettvH = (TextView) findViewById(R.id.resultETH);
        TextView ettvM = (TextView) findViewById(R.id.resultETM);
        mendTP.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {

                String Ehour = String.valueOf(i);
                String Emin = String.valueOf(i1);
                ettvH.setText(Ehour);
                ettvM.setText(Emin);
            }
        });

        TextView rrsttvH = (TextView) findViewById(R.id.resultSSSTH);
        TextView rrsttvM = (TextView) findViewById(R.id.resultSSSTM);
        TextView rrettvH = (TextView) findViewById(R.id.resultEEETH);
        TextView rrettvM = (TextView) findViewById(R.id.resultEEETM);
        Cursor cursor = mDbHelper.getAllUsersBySQL();
//저장
        Button button = (Button)findViewById(R.id.insert);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                viewAllToTextView();
               // Intent intent = new Intent(getApplicationContext(), MonthFragment.class);
                //intent.putExtra("title", String.valueOf(mTitle)); // 그 cusor의 제목을 monthFragment에  전달

                rrsttvH.setText(sttvH.getText());  // 저장부분, 시작 시
                rrsttvM.setText(sttvM.getText());  // 저장, 시작 분
                rrettvH.setText(ettvH.getText());
                rrettvM.setText(ettvM.getText());

                insertRecord();
                viewAllToListView();

                //viewListMonth();

                //monthFragment로 이동
                 MonthFragment monthFragment = new MonthFragment();
                 getSupportFragmentManager().beginTransaction().replace(R.id.month_fragment,monthFragment).commit();

            }
        });
//삭제
        Button button1 = (Button)findViewById(R.id.delete);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRecord();
                //viewAllToTextView();
                viewAllToListView();
            }
        });
//업데이트
        Button button2 = (Button)findViewById(R.id.update);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRecord();
                //               viewAllToTextView();
                viewAllToListView();
            }
        });

        Button button3 = (Button)findViewById(R.id.veiwall);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //               viewAllToTextView();
                viewAllToListView();

            }
        });
//        viewAllToTextView();
        viewAllToListView();

//지도
        mapBtn =(Button)findViewById(R.id.searchbtn); //지도 찾기 버튼
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }

    // 그리드뷰 i번째 선택
         public void onTitleSelected(int i) {
               Intent intent = new Intent(getApplicationContext(), MonthFragment.class);
               intent.putExtra("gridview_selected", String.valueOf(i));
    }

// 지도 검색, 출력
    private void getLastLocation() {
        // 1. 위치 접근에 필요한 권한 검사 및 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MonthCalAddActivity.this,            // MainActivity 액티비티의 객체 인스턴스를 나타냄
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // 요청할 권한 목록을 설정한 String 배열
                    REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION    // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
            );
            return;
        }

        // 2. Task<Location> 객체 반환
        Task task = mFusedLocationClient.getLastLocation();

        // 3. Task가 성공적으로 완료 후 호출되는 OnSuccessListener 등록
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                mLocation = location;
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(MonthCalAddActivity.this);

            }
        });
    }

 @Override
 public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);
        //LatLng current = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current,15));


        mapBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                String str= meditMap.getText().toString();
                List<Address> addressList = null;
                try {
                    // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList = geocoder.getFromLocationName(
                            str, // 주소
                            10); // 최대 검색 결과 개수
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(addressList.get(0).toString());
                // 콤마를 기준으로 split
                String []splitStr = addressList.get(0).toString().split(",");
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2); // 주소
                System.out.println(address);

                String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
                System.out.println(latitude);
                System.out.println(longitude);

                // 좌표(위도, 경도) 생성
                LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                // 마커 생성
                MarkerOptions mOptions2 = new MarkerOptions();
                mOptions2.title("search result");
                mOptions2.snippet(address);
                mOptions2.position(point);
                // 마커 추가
                mMap.addMarker(mOptions2);
                // 해당 좌표로 화면 줌
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));
            }
        });
    }

   /* private void viewListMonth(){
        Cursor cursor = mDbHelper.getAllUsersBySQL();
        int year = getIntent().getIntExtra("year",0);
        int month = getIntent().getIntExtra("month",0);
        String day = getIntent().getStringExtra("day");

        String Y = String.valueOf(year);
        String M = String.valueOf(month);

        while(cursor.moveToNext()){
            if(cursor.getString(7) == Y && cursor.getString(8)==M && cursor.getString(9) == day){  // 눌러진 곳의 날짜정보와 같은 sql을 찾아서
                    Intent intent = new Intent(getApplicationContext(), MonthFragment.class);
                    intent.putExtra("title",cursor.getString(1)); // 그 cusor의 제목을 monthFragment에  전달
            }
        }
    }*/


    private void viewAllToListView() {  // 그리드뷰 선택 -> 다이어로그 -> 선택항목 할때
        // 이걸 monthFragment에 출력할까?
        Cursor cursor = mDbHelper.getAllUsersByMethod();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.month_cal_daa_item, cursor, new String[]{
                UserContract.Users._ID,
                UserContract.Users.KEY_TITLE,
                UserContract.Users.KEY_STARTH,
                UserContract.Users.KEY_STARTM,
                UserContract.Users.KEY_ENDH,
                UserContract.Users.KEY_ENDM,
                UserContract.Users.KEY_MEMO,
                UserContract.Users.KEY_YEAR,
                UserContract.Users.KEY_MONTH,
                UserContract.Users.KEY_DAY},
                new int[]{R.id._id, R.id.title,R.id.startH,R.id.startM,R.id.endH,R.id.endM, R.id.memo, R.id.year, R.id.month,R.id.day}, 0);

        ListView lv = (ListView)findViewById(R.id.listview);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Adapter adapter = adapterView.getAdapter();

                /*
                 String id = getIntent().getStringExtra("id",-1);
                 String title = getIntent().getStringExtra("title",-1);
                 int startH = getIntent().getIntExtra("startH",-1);
                 int startM = getIntent().getIntExtra("startM",-1);
                 int endH = getIntent().getIntExtra("endH",-1);
                 int endM = getIntent().getIntExtra("endM",-1);
                 String memo = getIntent().getStringExtra("memo",-1);

                mId.setText(id);
                mTitle.setText(title);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mstartTP.setHour(startH);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mstartTP.setMinute(startM);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mendTP.setHour(endH);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mendTP.setMinute(endM);
                }
                mMemo.setText(memo);

                */

                mId.setText(((Cursor)adapter.getItem(i)).getString(0));
                mTitle.setText(((Cursor)adapter.getItem(i)).getString(1));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mstartTP.setHour(((Cursor)adapter.getItem(i)).getInt(2));
                }
               // mTitle.setText(((Cursor)adapter.getItem(i)).getString(1));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mstartTP.setMinute(((Cursor)adapter.getItem(i)).getInt(3));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mendTP.setHour(((Cursor)adapter.getItem(i)).getInt(4));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mendTP.setMinute(((Cursor)adapter.getItem(i)).getInt(5));
                }
                mMemo.setText(((Cursor)adapter.getItem(i)).getString(6));
            }
        });
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    private void updateRecord() {
        EditText _id = (EditText)findViewById(R.id._id);
        EditText title = (EditText)findViewById(R.id.edit_title);
        EditText memo = (EditText)findViewById(R.id.edit_memo);
        TextView rrsttvH = (TextView) findViewById(R.id.resultSSSTH);
        TextView rrsttvM = (TextView) findViewById(R.id.resultSSSTM);
        TextView rrettvH = (TextView) findViewById(R.id.resultEEETH);
        TextView rrettvM = (TextView) findViewById(R.id.resultEEETM);

        int year = getIntent().getIntExtra("year",0);
        int month = getIntent().getIntExtra("month",0);
        String day = getIntent().getStringExtra("day");

        String Y = String.valueOf(year);
        String M = String.valueOf(month);

        mDbHelper.updateUserBySQL(_id.getText().toString(), title.getText().toString(), rrsttvH.getText().toString(), rrsttvM.getText().toString(),
                rrettvH.getText().toString(), rrettvM.getText().toString(), memo.getText().toString(),Y,M,day);
//        long nOfRows = mDbHelper.updateUserByMethod(_id.getText().toString(),
//                name.getText().toString(),
//                phone.getText().toString());
//        if (nOfRows >0)
//            Toast.makeText(this,"Record Updated", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(this,"No Record Updated", Toast.LENGTH_SHORT).show();
    }

    private void deleteRecord() {
        EditText _id = (EditText)findViewById(R.id._id);

        mDbHelper.deleteUserBySQL(_id.getText().toString());
//        long nOfRows = mDbHelper.deleteUserByMethod(_id.getText().toString());
//        if (nOfRows >0)
//            Toast.makeText(this,"Record Deleted", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(this,"No Record Deleted", Toast.LENGTH_SHORT).show();
    }

    private void insertRecord() {
        EditText title = (EditText)findViewById(R.id.edit_title);
        EditText memo = (EditText)findViewById(R.id.edit_memo);
        TextView rrsttvH = (TextView) findViewById(R.id.resultSSSTH);
        TextView rrsttvM = (TextView) findViewById(R.id.resultSSSTM);
        TextView rrettvH = (TextView) findViewById(R.id.resultEEETH);
        TextView rrettvM = (TextView) findViewById(R.id.resultEEETM);

        int year = getIntent().getIntExtra("year",0);
        int month = getIntent().getIntExtra("month",0);
        String day = getIntent().getStringExtra("day");

        String Y = String.valueOf(year);
        String M = String.valueOf(month);

        mDbHelper.insertUserBySQL(title.getText().toString(), rrsttvH.getText().toString(), rrsttvM.getText().toString(),
                rrettvH.getText().toString(),rrettvM.getText().toString(), memo.getText().toString(),Y,M,day);
//        long nOfRows = mDbHelper.insertUserByMethod(name.getText().toString(),phone.getText().toString());
//        if (nOfRows >0)
//            Toast.makeText(this,nOfRows+" Record Inserted", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(this,"No Record Inserted", Toast.LENGTH_SHORT).show();
    }
}