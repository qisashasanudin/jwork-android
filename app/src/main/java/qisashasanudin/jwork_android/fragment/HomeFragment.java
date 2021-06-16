package qisashasanudin.jwork_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import qisashasanudin.jwork_android.R;
import qisashasanudin.jwork_android.activity.ApplyJobActivity;
import qisashasanudin.jwork_android.adapter.MainListAdapter;
import qisashasanudin.jwork_android.object.Job;
import qisashasanudin.jwork_android.object.Location;
import qisashasanudin.jwork_android.object.Recruiter;
import qisashasanudin.jwork_android.request.MenuRequest;

public class HomeFragment extends Fragment {

    private ArrayList<Recruiter> listRecruiter = new ArrayList<>();
    private ArrayList<Job> jobList = new ArrayList<>();
    private HashMap<Recruiter, ArrayList<Job>> childMapping = new HashMap<>();
    private int jobseekerId;
    private String jobseekerName;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null){
            jobseekerId = extras.getInt("jobseekerId");
            jobseekerName = extras.getString("jobseekerName");
        }

        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Intent intent = new Intent(getActivity(), ApplyJobActivity.class);
                Job selectedJob = childMapping.get(listRecruiter.get(groupPosition)).get(childPosition);

                intent.putExtra("jobId", selectedJob.getId());
                intent.putExtra("jobName", selectedJob.getName());
                intent.putExtra("jobCategory", selectedJob.getCategory());
                intent.putExtra("jobFee", selectedJob.getFee());

                intent.putExtra("jobseekerId", jobseekerId);
                intent.putExtra("jobseekerName", jobseekerName);
                startActivity(intent);
                return true;
            }
        });

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshList();
            }
        });

        return view;
    }

        protected void refreshList(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    if (jsonResponse != null) {
                        for (int i = 0; i < jsonResponse.length(); i++){
                            JSONObject job = jsonResponse.getJSONObject(i);
                            JSONObject recruiter = job.getJSONObject("recruiter");
                            JSONObject location = recruiter.getJSONObject("location");

                            String city = location.getString("city");
                            String province = location.getString("province");
                            String description = location.getString("description");

                            Location location1 = new Location(province, city, description);

                            int recruiterId = recruiter.getInt("id");
                            String recruiterName = recruiter.getString("name");
                            String recruiterEmail = recruiter.getString("email");
                            String recruiterPhoneNumber = recruiter.getString("phoneNumber");

                            Recruiter newRecruiter = new Recruiter(recruiterId, recruiterName, recruiterEmail, recruiterPhoneNumber, location1);

                            int jobId = job.getInt("id");
                            String jobName = job.getString("name");
                            int jobFee = job.getInt("fee");
                            String jobCategory = job.getString("category");

                            Job newJob = new Job(jobId, jobName, newRecruiter, jobFee, jobCategory);

                            if (listRecruiter.size() > 0) {
                                boolean success = true;
                                for (Recruiter rec : listRecruiter)
                                    if (rec.getId() == newRecruiter.getId())
                                        success = false;
                                if (success) {
                                    listRecruiter.add(newRecruiter);
                                }
                            } else {
                                listRecruiter.add(newRecruiter);
                            }

                            if (jobList.size() > 0) {
                                boolean success = true;
                                for (Job thisJob : jobList)
                                    if (thisJob.getId() == newJob.getId())
                                        success = false;
                                if (success) {
                                    jobList.add(newJob);
                                }
                            } else {
                                jobList.add(newJob);
                            }

                            for (Recruiter sel : listRecruiter) {
                                ArrayList<Job> temp = new ArrayList<>();
                                for (Job jobs : jobList) {
                                    if (jobs.getRecruiter().getName().equals(sel.getName()) || jobs.getRecruiter().getEmail().equals(sel.getEmail()) || jobs.getRecruiter().getPhoneNumber().equals(sel.getPhoneNumber())) {
                                        temp.add(jobs);
                                    }
                                }
                                childMapping.put(sel, temp);
                            }
                        }
                        listAdapter = new MainListAdapter(getActivity(), listRecruiter, childMapping);
                        expListView.setAdapter(listAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "JSON excecption: " + e, Toast.LENGTH_LONG).show();
                }
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(menuRequest);
    }
}