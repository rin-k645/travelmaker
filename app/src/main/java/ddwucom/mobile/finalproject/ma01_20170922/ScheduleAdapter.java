package ddwucom.mobile.finalproject.ma01_20170922;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ScheduleAdapter extends CursorAdapter {
    LayoutInflater inflater;
    int layout;

    public ScheduleAdapter(Context context, int layout, Cursor c) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = inflater.inflate(layout, parent, false);
        ScheduleAdapter.ViewHolder holder = new ScheduleAdapter.ViewHolder();
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ScheduleAdapter.ViewHolder holder = (ScheduleAdapter.ViewHolder)view.getTag();

        if(holder.tvTitle == null) {
            holder.tvTitle = view.findViewById(R.id.tvScheduleName);
            holder.tvScheduleAddress = view.findViewById(R.id.tvScheduleAddress);
        }

        holder.tvTitle.setText(cursor.getString(cursor.getColumnIndex(ScheduleDBHelper.COL_TITLE)));
        holder.tvScheduleAddress.setText(cursor.getString(cursor.getColumnIndex(ScheduleDBHelper.COL_ADDRESS)));
    }

    static class ViewHolder {

        public ViewHolder() {
            tvTitle = null;
            tvScheduleAddress = null;

        }

        TextView tvTitle;
        TextView tvScheduleAddress;
    }
}
