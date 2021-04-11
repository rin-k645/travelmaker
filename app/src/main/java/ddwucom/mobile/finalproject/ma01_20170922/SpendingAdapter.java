package ddwucom.mobile.finalproject.ma01_20170922;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class SpendingAdapter extends CursorAdapter {
    LayoutInflater inflater;
    int layout;

    public SpendingAdapter(Context context, int layout, Cursor c) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = inflater.inflate(layout, parent, false);
        ViewHolder holder = new ViewHolder();
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder)view.getTag();

        if(holder.tvTitle == null) {
            holder.tvTitle = view.findViewById(R.id.tvTitle);
            holder.tvPrice = view.findViewById(R.id.tvPrice);
            holder.tvCategory = view.findViewById(R.id.tvCategory);
        }

        holder.tvTitle.setText(cursor.getString(cursor.getColumnIndex(SpendingDBHelper.COL_TITLE)));
        holder.tvPrice.setText(cursor.getString(cursor.getColumnIndex(SpendingDBHelper.COL_PRICE)));
        holder.tvCategory.setText(cursor.getString(cursor.getColumnIndex(SpendingDBHelper.COL_CATEGORY)));
    }

    static class ViewHolder {

        public ViewHolder() {
            tvTitle = null;
            tvPrice = null;
            tvCategory = null;
        }

        TextView tvTitle;
        TextView tvPrice;
        TextView tvCategory;
    }
}
