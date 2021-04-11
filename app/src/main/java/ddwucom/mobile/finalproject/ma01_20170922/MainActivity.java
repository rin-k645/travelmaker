package ddwucom.mobile.finalproject.ma01_20170922;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView; //바텀 네비게이션 뷰
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ScheduleFragment scheduleFragment;
    private SearchFragment searchFragment;
    private SpendingFragment spendingFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ////바텀 네비게이션 뷰
        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.action_schedule:
                        setFrag(0);
                        break;
                    case R.id.action_search:
                        setFrag(1);
                        break;
                    case R.id.action_spending:
                        setFrag(2);
                        break;
                }
                return true;
            }
        });

        scheduleFragment = new ScheduleFragment();
        searchFragment = new SearchFragment();
        spendingFragment = new SpendingFragment();
        setFrag(0); //첫 프래그먼트 화면 지정
    }

    //프레그먼트 교체
    private void setFrag(int n)
    {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        switch (n)
        {
            case 0:
                fragmentTransaction.replace(R.id.Main_Frame, scheduleFragment);
                fragmentTransaction.commit();
                break;

            case 1:
                fragmentTransaction.replace(R.id.Main_Frame, searchFragment);
                fragmentTransaction.commit();
                break;

            case 2:
                fragmentTransaction.replace(R.id.Main_Frame, spendingFragment);
                fragmentTransaction.commit();
                break;
        }
    }

}
