package ddwucom.mobile.finalproject.ma01_20170922;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScheduleDBHelper extends SQLiteOpenHelper {
    final static String DB_NAME = "schedule.db";
    public final static String TABLE_NAME = "schedule_table";
    public final static String COL_ID = "_id";
    public final static String COL_TITLE = "title";
    public final static String COL_ADDRESS = "address";
    public final static String COL_MAPX = "mapx";
    public final static String COL_MAPY = "mapy";
    public final static String COL_DATE = "date";
    public final static String COL_TIME = "time";
    public final static String COL_MEMO = "memo";


    public ScheduleDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + " (" + COL_ID + " integer primary key autoincrement, "
                + COL_TITLE + " TEXT, " + COL_ADDRESS + " TEXT, " + COL_MAPX + " TEXT, " + COL_MAPY + " TEXT, " +  COL_DATE + " TEXT, " +  COL_TIME + " TEXT, " + COL_MEMO + " TEXT )";
        db.execSQL(sql);

        //셈플 데이터
        db.execSQL("INSERT INTO " + ScheduleDBHelper.TABLE_NAME + " VALUES (NULL, '오죽헌', '강원도 강릉시 율곡로3139번길 24', '128.8776814918', '37.7787651808', '2020.12.23', '14:00', '택시타고가기');");
        db.execSQL("INSERT INTO " + ScheduleDBHelper.TABLE_NAME + " VALUES (NULL, '초당맷돌순두부', '강원도 강릉시 초당순두부길 75', '128.9154527370', '37.7901229368', '2020.12.23', '18:00', '해물순두부맛집');");
        db.execSQL("INSERT INTO " + ScheduleDBHelper.TABLE_NAME + " VALUES (NULL, '경포대', '강원도 강릉시 경포로 365', '128.8965126086', '37.7955691591', '2020.12.23', '18:00', '근처에경포해변');");
        db.execSQL("INSERT INTO " + ScheduleDBHelper.TABLE_NAME + " VALUES (NULL, '하슬라아트월드', '강원도 강릉시 강동면 율곡로 1441', '129.0102329067', '37.7065316769', '2020.12.24', '18:00', '강릉시내에서버스타고가기');");
        db.execSQL("INSERT INTO " + ScheduleDBHelper.TABLE_NAME + " VALUES (NULL, '안목해변', '강원도 강릉시 창해로14번길 20-1', '128.9473504054', '37.7726505813', '2020.12.25', '10:00', '커피거리에서커피마시기');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(db);
    }
}
