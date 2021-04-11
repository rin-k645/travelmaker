package ddwucom.mobile.finalproject.ma01_20170922;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SpendingFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    final static int UPDATE_ACTIVITY_CODE = 200;

    View view;
    FloatingActionButton btnAddSpending;
    TextView tvSum;

    ListView lvSpending = null;
    SpendingDBHelper helper;
    Cursor cursor;
    SpendingAdapter adapter;

    Intent intent;
    AlertDialog.Builder builder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_spending, container, false);

        //setHasOptionsMenu(true);

        btnAddSpending = view.findViewById(R.id.btnAddSpending);
        btnAddSpending.setOnClickListener(this::onClick);

        //리스트뷰 처리
        helper = new SpendingDBHelper(getActivity());

        lvSpending = (ListView) view.findViewById(R.id.lvSpending);
        adapter = new SpendingAdapter(getActivity(), R.layout.listview_spending, null);
        lvSpending.setAdapter(adapter);

        //리스트뷰 클릭리스너
        lvSpending.setOnItemClickListener(SpendingFragment.this);
        lvSpending.setOnItemLongClickListener(SpendingFragment.this);

        //지출 합계
        tvSum = view.findViewById(R.id.tvSum);
        tvSum.setText(Integer.toString(sumOfSpending()));


        //뷰리턴
        return view;
    }

    public int sumOfSpending() {
        int sum = 0;

        SQLiteDatabase db = helper.getReadableDatabase();

        cursor = db.rawQuery("select * from " + SpendingDBHelper.TABLE_NAME, null);

        String price = null;

        while(cursor.moveToNext()) {
            price = cursor.getString(cursor.getColumnIndex(SpendingDBHelper.COL_PRICE));

            sum += Integer.parseInt(price);
        }

        return sum;
    }

    //리스트뷰 클릭 처리
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        //id로 데이터 선택하기
        cursor = db.rawQuery("SELECT * FROM " + SpendingDBHelper.TABLE_NAME + " where " + SpendingDBHelper.COL_ID + "='" + id + "'", null);

        String title = null;
        String price = null;
        String category = null;
        String date = null;

        while(cursor.moveToNext()) {
            title = cursor.getString(cursor.getColumnIndex(SpendingDBHelper.COL_TITLE));
            price = cursor.getString(cursor.getColumnIndex(SpendingDBHelper.COL_PRICE));
            category = cursor.getString(cursor.getColumnIndex(SpendingDBHelper.COL_CATEGORY));
            date = cursor.getString(cursor.getColumnIndex(SpendingDBHelper.COL_DATE));
        }

        //dto 생성
        SpendingDto spending = new SpendingDto(id, title, price, category, date);

        intent = new Intent(getActivity(), UpdateSpendingActivity.class);
        intent.putExtra("spending", spending);
        startActivityForResult(intent, UPDATE_ACTIVITY_CODE);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        cursor = (Cursor) adapter.getItem(position);
        String title = cursor.getString(cursor.getColumnIndex("title"));

        builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("소비 삭제")
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

                        cursor = sqLiteDatabase.rawQuery("select * from " + SpendingDBHelper.TABLE_NAME, null);
                        adapter.changeCursor(cursor);

                        tvSum.setText(Integer.toString(sumOfSpending()));

                        helper.close();
                    }
                })
                .setNegativeButton("취소", null)
                .setCancelable(false)
                .show();

        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddSpending: //플로팅버튼
                intent = new Intent(getActivity(), AddSpendingActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("select * from " + SpendingDBHelper.TABLE_NAME, null);

        adapter.changeCursor(cursor);

        tvSum.setText(Integer.toString(sumOfSpending()));

        helper.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (cursor != null) cursor.close();
    }

}
