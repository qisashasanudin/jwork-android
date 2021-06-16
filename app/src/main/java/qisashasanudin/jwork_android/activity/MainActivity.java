package qisashasanudin.jwork_android.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import qisashasanudin.jwork_android.fragment.HistoryFragment;
import qisashasanudin.jwork_android.fragment.HomeFragment;
import qisashasanudin.jwork_android.fragment.InvoiceFragment;
import qisashasanudin.jwork_android.fragment.SettingsFragment;
import qisashasanudin.jwork_android.object.Job;
import qisashasanudin.jwork_android.object.Location;
import qisashasanudin.jwork_android.adapter.MainListAdapter;
import qisashasanudin.jwork_android.R;
import qisashasanudin.jwork_android.object.Recruiter;
import qisashasanudin.jwork_android.request.MenuRequest;

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
        setContentView(R.layout.activity_main);
        bottomNavbar = findViewById(R.id.bottom_navbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            jobseekerId = extras.getInt("jobseekerId");
            jobseekerName = extras.getString("jobseekerName");
        }

        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment selectedFragment = null;

                switch(item.getItemId()){
                    case R.id.bottomnav_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.bottomnav_invoice:
                        selectedFragment = new InvoiceFragment();
                        break;
                    case R.id.bottomnav_history:
                        selectedFragment = new HistoryFragment();
                        break;
                    case R.id.bottomnav_settings:
                        selectedFragment = new SettingsFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        });
    }
}