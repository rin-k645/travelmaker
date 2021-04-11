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

public class UpdateSpendingActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etPrice;
    EditText etCategory;
    EditText etDate;

    Button btnUpdate;
    Button btnCancel;

    SpendingDBHelper helper;
    SpendingDto spending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_spending);

        spending = (SpendingDto) getIntent().getSerializableExtra("spending");

        etTitle = findViewById(R.id.etTitle);
        etPrice = findViewById(R.id.etPrice);
        etCategory = findViewById(R.id.etCetegory);
        etDate = findViewById(R.id.etDate);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);

        helper = new SpendingDBHelper(this);

        //기존 정보 보여주기
        etTitle.setText(spending.getTitle());
        etPrice.setText(spending.getPrice());
        etCategory.setText(spending.getCategory());
        etDate.setText(spending.getDate());
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnUpdate:
                String title = etTitle.getText().toString();
                String price = etPrice.getText().toString();
                String category = etCategory.getText().toString();
                String date = etDate.getText().toString();

                if(title.matches("") || price.matches("") || category.matches(""))
                    Toast.makeText(this, "필수 항목을 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                else {
                    spending.setTitle(title);
                    spending.setPrice(price);
                    spending.setCategory(category);
                    spending.setDate(date);

                    //dto 받아서 값 수정
                    SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
                    ContentValues row = new ContentValues();

                    row.put(helper.COL_TITLE, spending.getTitle());
                    row.put(helper.COL_PRICE, spending.getPrice());
                    row.put(helper.COL_CATEGORY, spending.getCategory());
                    row.put(helper.COL_DATE, spending.getDate());

                    String whereClause = helper.COL_ID + "=?";
                    String[] whereArgs = new String[] { String.valueOf(spending.getId()) };
                    sqLiteDatabase.update(helper.TABLE_NAME, row, whereClause, whereArgs);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("spending", spending);
                    setResult(RESULT_OK, resultIntent);

                    helper.close();
                    finish();
                }
                break;
            case R.id.btnCancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}