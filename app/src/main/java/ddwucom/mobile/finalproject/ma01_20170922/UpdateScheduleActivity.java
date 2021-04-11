package ddwucom.mobile.finalproject.ma01_20170922;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateScheduleActivity extends AppCompatActivity {

    EditText etScheduleName;
    EditText etTime;
    EditText etScheduleDate;
    EditText etMemo;
    EditText etAddress;
    EditText etMapx;
    EditText etMapy;

    Button btnAdd;
    Button btnCancel;

    ScheduleDBHelper helper;
    ScheduleDto schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_schedule);

        schedule = (ScheduleDto) getIntent().getSerializableExtra("schedule");

        etScheduleName = findViewById(R.id.etScheduleName);
        etTime = findViewById(R.id.etTime);
        etScheduleDate = findViewById(R.id.etScheduleDate);
        etMemo = findViewById(R.id.etMemo);
        etAddress = findViewById(R.id.etAddress);
        etMapx = findViewById(R.id.etMapx);
        etMapy = findViewById(R.id.etMapy);

        btnAdd = findViewById(R.id.btnAdd2);
        btnCancel = findViewById(R.id.btnCancel2);

        helper = new ScheduleDBHelper(this);

        //기존 정보 보여주기
        etScheduleName.setText(schedule.getTitle());
        etTime.setText(schedule.getTime());
        etScheduleDate.setText(schedule.getDate());
        etMemo.setText(schedule.getMemo());
        etAddress.setText(schedule.getAddress());
        etMapx.setText(schedule.getMapx());
        etMapy.setText(schedule.getMapy());
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnUpdate2:
                String title = etScheduleName.getText().toString();
                String time = etTime.getText().toString();
                String date = etScheduleDate.getText().toString();
                String memo = etMemo.getText().toString();
                String address = etAddress.getText().toString();
                String mapx = etMapx.getText().toString();
                String mapy = etMapy.getText().toString();

                if(title.matches(""))
                    Toast.makeText(this, "필수 항목을 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                else {
                    schedule.setTitle(title);
                    schedule.setTime(time);
                    schedule.setDate(date);
                    schedule.setMemo(memo);
                    schedule.setAddress(address);
                    schedule.setMapx(mapx);
                    schedule.setMapy(mapy);

                    //dto 받아서 값 수정
                    SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
                    ContentValues row = new ContentValues();

                    row.put(helper.COL_TITLE, schedule.getTitle());
                    row.put(helper.COL_TIME, schedule.getTime());
                    row.put(helper.COL_DATE, schedule.getDate());
                    row.put(helper.COL_MEMO, schedule.getMemo());
                    row.put(helper.COL_ADDRESS, schedule.getAddress());
                    row.put(helper.COL_MAPX, schedule.getMapx());
                    row.put(helper.COL_MAPY, schedule.getMapy());

                    String whereClause = helper.COL_ID + "=?";
                    String[] whereArgs = new String[] { String.valueOf(schedule.getId()) };
                    sqLiteDatabase.update(helper.TABLE_NAME, row, whereClause, whereArgs);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("schedule", schedule);
                    setResult(RESULT_OK, resultIntent);

                    helper.close();
                    finish();
                }
                break;
            case R.id.btnCancel3:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}