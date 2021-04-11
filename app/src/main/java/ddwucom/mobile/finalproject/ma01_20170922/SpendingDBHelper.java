package ddwucom.mobile.finalproject.ma01_20170922;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SpendingDBHelper extends SQLiteOpenHelper {
    final static String DB_NAME = "spending.db";
    public final static String TABLE_NAME = "spending_table";
    public final static String COL_ID = "_id";
    public final static String COL_TITLE = "title";
    public final static String COL_PRICE = "price";
    public final static String COL_CATEGORY = "category";
    public final static String COL_DATE = "date";

    public SpendingDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + " (" + COL_ID + " integer primary key autoincrement, "
                + COL_TITLE + " TEXT, " + COL_PRICE + " TEXT, " + COL_CATEGORY + " TEXT, " + COL_DATE + " TEXT )";
        db.execSQL(sql);

        //셈플 데이터
        db.execSQL("INSERT INTO " + SpendingDBHelper.TABLE_NAME + " VALUES (NULL, 'KTX왕복', '51000', '교통비', '2020.12.23');");
        db.execSQL("INSERT INTO " + SpendingDBHelper.TABLE_NAME + " VALUES (NULL, '숙소', '76000', '숙박비', '2020.12.23');");
        db.execSQL("INSERT INTO " + SpendingDBHelper.TABLE_NAME + " VALUES (NULL, '오죽헌', '3000', '입장료', '2020.12.23');");
        db.execSQL("INSERT INTO " + SpendingDBHelper.TABLE_NAME + " VALUES (NULL, '해물순두부', '8000', '식비', '2020.12.23');");
        db.execSQL("INSERT INTO " + SpendingDBHelper.TABLE_NAME + " VALUES (NULL, '하슬라아트월드', '12000', '입장료', '2020.12.23');");
        db.execSQL("INSERT INTO " + SpendingDBHelper.TABLE_NAME + " VALUES (NULL, '엽서', '1000', '쇼핑', '2020.12.23');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(db);
    }
}
