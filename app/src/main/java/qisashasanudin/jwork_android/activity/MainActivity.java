package qisashasanudin.jwork_android.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import qisashasanudin.jwork_android.fragment.HistoryFragment;
import qisashasanudin.jwork_android.fragment.HomeFragment;
import qisashasanudin.jwork_android.fragment.InvoiceFragment;
import qisashasanudin.jwork_android.fragment.SettingsFragment;
import qisashasanudin.jwork_android.object.Job;
import qisashasanudin.jwork_android.R;
import qisashasanudin.jwork_android.object.Recruiter;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Recruiter> listRecruiter = new ArrayList<>();
    private ArrayList<Job> jobList = new ArrayList<>();
    private HashMap<Recruiter, ArrayList<Job>> childMapping = new HashMap<>();
    private int jobseekerId;
    private String jobseekerName;
    BottomNavigationView bottomNavbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        setContentView(R.layout.activity_main);
        bottomNavbar = findViewById(R.id.bottom_navbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jobseekerId = extras.getInt("jobseekerId");
            jobseekerName = extras.getString("jobseekerName");
        }

        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                selectFragment(item.getItemId());
                return true;
            }
        });

        bottomNavbar.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull @NotNull MenuItem item) {}
        });


    }

    private void selectFragment(int itemId){
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        switch (itemId) {
            case R.id.bottomnav_home:
                fts.replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.bottomnav_invoice:
                fts.replace(R.id.fragment_container, new InvoiceFragment()).commit();
                break;
            case R.id.bottomnav_history:
                fts.replace(R.id.fragment_container, new HistoryFragment()).commit();
                break;
            case R.id.bottomnav_settings:
                fts.replace(R.id.fragment_container, new SettingsFragment()).commit();
                break;
        }
    }
}