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

public class AddScheduleActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

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
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd2:
                String title = etScheduleName.getText().toString();
                String time = etTime.getText().toString();
                String date = etScheduleDate.getText().toString();
                String memo = etMemo.getText().toString();
                String address = etAddress.getText().toString();
                String mapx = etMapx.getText().toString();
                String mapy = etMapy.getText().toString();


                //필수 항목 입력하지 않고 추가 클릭 시
                if (title.matches(""))
                    Toast.makeText(this, "필수 항목을 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                else {
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues value = new ContentValues();

                    value.put(helper.COL_TITLE, title);
                    value.put(helper.COL_TIME, time);
                    value.put(helper.COL_DATE, date);
                    value.put(helper.COL_MEMO, memo);
                    value.put(helper.COL_ADDRESS, address);
                    value.put(helper.COL_MAPX, mapx);
                    value.put(helper.COL_MAPY, mapy);

                    db.insert(helper.TABLE_NAME, null, value);

                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    helper.close();
                    finish();
                }
                break;
            case R.id.btnCancel2:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}