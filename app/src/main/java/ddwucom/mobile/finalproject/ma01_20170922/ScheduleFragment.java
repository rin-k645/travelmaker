package ddwucom.mobile.finalproject.ma01_20170922;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ScheduleFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, OnMapReadyCallback {
    final static int UPDATE_ACTIVITY_CODE2 = 300;

    View view;
    FloatingActionButton btnAddSchedule;

    ListView lvSchedule = null;
    ScheduleDBHelper helper;
    Cursor cursor;
    ScheduleAdapter adapter;

    Intent intent;
    AlertDialog.Builder builder;

    MapView mapView;
    GoogleMap mGoogleMap;
    Marker marker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schedule, container, false);

        btnAddSchedule = view.findViewById(R.id.btnAddShedule);
        btnAddSchedule.setOnClickListener(this::onClick);

        //리스트뷰 처리
        helper = new ScheduleDBHelper(getActivity());

        lvSchedule = (ListView) view.findViewById(R.id.lvSchedule);
        adapter = new ScheduleAdapter(getActivity(), R.layout.listview_schedule, null);
        lvSchedule.setAdapter(adapter);

        //리스트뷰 클릭리스너
        lvSchedule.setOnItemClickListener(ScheduleFragment.this);
        lvSchedule.setOnItemLongClickListener(ScheduleFragment.this);

        //맵뷰
        mapView = (MapView)view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        //뷰리턴
        return view;
    }

    //리스트뷰 클릭 처리
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getActivity(), id + "선택", Toast.LENGTH_SHORT).show();

        SQLiteDatabase db = helper.getReadableDatabase();

        //id로 데이터 선택하기
        cursor = db.rawQuery("SELECT * FROM " + ScheduleDBHelper.TABLE_NAME + " where " + ScheduleDBHelper.COL_ID + "='" + id + "'", null);

        String title = null;
        String address = null;
        String mapx = null;
        String mapy = null;
        String date = null;
        String time = null;
        String memo = null;

        while(cursor.moveToNext()) {
            title = cursor.getString(cursor.getColumnIndex(ScheduleDBHelper.COL_TITLE));
            address = cursor.getString(cursor.getColumnIndex(ScheduleDBHelper.COL_ADDRESS));
            mapx = cursor.getString(cursor.getColumnIndex(ScheduleDBHelper.COL_MAPX));
            mapy = cursor.getString(cursor.getColumnIndex(ScheduleDBHelper.COL_MAPY));
            date = cursor.getString(cursor.getColumnIndex(ScheduleDBHelper.COL_DATE));
            time = cursor.getString(cursor.getColumnIndex(ScheduleDBHelper.COL_TIME));
            memo = cursor.getString(cursor.getColumnIndex(ScheduleDBHelper.COL_MEMO));
        }

        //dto 생성
        ScheduleDto schedule = new ScheduleDto(id, title, address, mapx, mapy, date, time, memo);

        intent = new Intent(getActivity(), UpdateScheduleActivity.class);
        intent.putExtra("schedule", schedule);
        startActivityForResult(intent, UPDATE_ACTIVITY_CODE2);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        cursor = (Cursor) adapter.getItem(position);
        String title = cursor.getString(cursor.getColumnIndex("title"));

        builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("일정 삭제")
                .setMessage(title + "을(를) 삭제하시겠습니까?")
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //삭제 수행
                        Toast.makeText(getActivity(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

                        String whereClause = helper.COL_ID + "=?";
                        String[] whereArgs = new String[] { String.valueOf(id) };
                        sqLiteDatabase.delete(helper.TABLE_NAME, whereClause,whereArgs);

                        cursor = sqLiteDatabase.rawQuery("select * from " + ScheduleDBHelper.TABLE_NAME, null);
                        adapter.changeCursor(cursor);

                        helper.close();
                    }
                })
                .setNegativeButton("취소", null)
                .setCancelable(false)
                .show();

        return true;
    }

    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddShedule: //플로팅버튼
                intent = new Intent(getActivity(), AddScheduleActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("select * from " + ScheduleDBHelper.TABLE_NAME, null);

        adapter.changeCursor(cursor);
        helper.close();

        //맵뷰
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (cursor != null) cursor.close();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        //db에서 일정 정보 읽어와서 지도에 마커로 표시
        SQLiteDatabase db = helper.getReadableDatabase();

        cursor = db.rawQuery("select * from " + ScheduleDBHelper.TABLE_NAME, null);

        long id = 0;
        String title = null;
        String mapx = null;
        String mapy = null;

        while(cursor.moveToNext()) {
            id = cursor.getLong(cursor.getColumnIndex(ScheduleDBHelper.COL_ID));
            title = cursor.getString(cursor.getColumnIndex(ScheduleDBHelper.COL_TITLE));
            mapx = cursor.getString(cursor.getColumnIndex(ScheduleDBHelper.COL_MAPX));
            mapy = cursor.getString(cursor.getColumnIndex(ScheduleDBHelper.COL_MAPY));

            if(id == 1) {
                LatLng currentLoc = new LatLng(Double.parseDouble(mapy), Double.parseDouble(mapx));
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc,12));

                mGoogleMap.addMarker(new MarkerOptions()
                        .position(currentLoc)
                        .title(title));
            } else{
                if(mapy != null && mapx !=null) {
                    mGoogleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(mapy), Double.parseDouble(mapx)))
                            .title(title));
                }
            }
        }

    }
}
