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

public class AddSpendingActivity extends AppCompatActivity {
    EditText etTitle;
    EditText etPrice;
    EditText etCategory;
    EditText etDate;

    Button btnAdd;
    Button btnCancel;

    SpendingDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spending);

        etTitle = findViewById(R.id.etTitle);
        etPrice = findViewById(R.id.etPrice);
        etCategory = findViewById(R.id.etCetegory);
        etDate = findViewById(R.id.etDate);

        btnAdd = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);

        helper = new SpendingDBHelper(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdate:
                String title = etTitle.getText().toString();
                String price = etPrice.getText().toString();
                String category = etCategory.getText().toString();
                String date = etDate.getText().toString();

                //필수 항목 입력하지 않고 추가 클릭 시
                if (title.matches("") || price.matches("") || category.matches(""))
                    Toast.makeText(this, "필수 항목을 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                else {
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues value = new ContentValues();

                    value.put(helper.COL_TITLE, title);
                    value.put(helper.COL_PRICE, price);
                    value.put(helper.COL_CATEGORY, category);
                    value.put(helper.COL_DATE, date);

                    db.insert(helper.TABLE_NAME, null, value);

                    Intent resultIntent = new Intent();
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