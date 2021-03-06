package ddwucom.mobile.finalproject.ma01_20170922;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchFragment extends Fragment implements AdapterView.OnItemLongClickListener {
    public static final String TAG = "SearchActivity";

    View view;

    Button btnSearch;
    EditText etTarget;
    ListView lvSearchList;

    SpotAdapter adapter;
    ArrayList<SpotDto> resultList;
    SpotXmlParsher parser;
    SpotNetworkManager networkManager;
    ImageFileManager imgFileManager;

    String apiAddress;
    String apiKey;
    String query;

    AlertDialog.Builder builder;

    ScheduleDBHelper helper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);

        btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this::onClick);
        etTarget = view.findViewById(R.id.etTarget);
        lvSearchList = view.findViewById(R.id.lvSearchList);

        resultList = new ArrayList();
        adapter = new SpotAdapter(getActivity(), R.layout.listview_spot, resultList);
        lvSearchList.setAdapter(adapter);

        apiAddress = getResources().getString(R.string.api_url);
        apiKey = getResources().getString(R.string.api_key);
        parser = new SpotXmlParsher();
        networkManager = new SpotNetworkManager(getActivity());
        imgFileManager = new ImageFileManager(getActivity());

        helper = new ScheduleDBHelper(getActivity());

        //???????????? ?????? ?????????
        lvSearchList.setOnItemLongClickListener(SearchFragment.this);

        //?????????
        return view;
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSearch:
                query = etTarget.getText().toString();  // UTF-8 ????????? ??????
                // OpenAPI ????????? query ?????? ??? ???????????? ???????????? ?????????
                try {
                    String result;
                    result = apiAddress + apiKey + "&keyword=" + URLEncoder.encode(query, "UTF-8")
                            + "&areaCode=&sigunguCode=&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide";
                    new NetworkAsyncTask().execute(result);
                    Log.d(TAG, result);
                }catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                // ????????? ???????????? ?????? ?????? ??? ???????????? ??????

                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // ?????? ?????? ??????
        imgFileManager.clearTemporaryFiles();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("?????? ??????")
                .setMessage(resultList.get(position).getTitle() + "???(???) ?????????????????????????")
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //?????? ??????
                        Toast.makeText(getActivity(), "?????????????????????", Toast.LENGTH_SHORT).show();

                        SQLiteDatabase db = helper.getWritableDatabase();
                        ContentValues value = new ContentValues();

                        value.put(helper.COL_TITLE, resultList.get(position).getTitle());
                        value.put(helper.COL_TIME, "");
                        value.put(helper.COL_DATE, "");
                        value.put(helper.COL_MEMO, "");
                        value.put(helper.COL_ADDRESS, resultList.get(position).getAddress());
                        value.put(helper.COL_MAPX, resultList.get(position).getMapx());
                        value.put(helper.COL_MAPY, resultList.get(position).getMapy());

                        db.insert(helper.TABLE_NAME, null, value);

                        Intent resultIntent = new Intent();
                        getActivity().setResult(Activity.RESULT_OK, resultIntent);
                        helper.close();

                    }
                })
                .setNegativeButton("??????", null)
                .setCancelable(false)
                .show();

        return true;
    }

    class NetworkAsyncTask extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDlg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDlg = ProgressDialog.show(getActivity(), "Wait", "Downloading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String address = strings[0];
            String result = null;
            // networking
            result = networkManager.downloadContents(address);
            if (result == null) return "Error!";

            Log.d(TAG, result);

            // parsing
            resultList = parser.parse(result); //-> ???????????? ??????????????????.

            return result;
        }


        @Override
        protected void onPostExecute(String result) {

            adapter.setList(resultList);    // Adapter ??? ?????? List ??? ?????? ??? notify
            progressDlg.dismiss();
        }

    }
}
