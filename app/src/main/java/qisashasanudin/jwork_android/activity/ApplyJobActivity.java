package qisashasanudin.jwork_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import qisashasanudin.jwork_android.R;
import qisashasanudin.jwork_android.request.ApplyJobRequest;
import qisashasanudin.jwork_android.request.BonusRequest;

public class ApplyJobActivity extends AppCompatActivity {

    private int jobId;
    private int jobseekerId;
    private String jobName;
    private String jobCategory;
    private double jobFee;
    private int bonus;
    private String selectedPayment;

    ApplyJobRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jobId = extras.getInt("jobId");
            jobseekerId = extras.getInt("jobseekerId");
            jobName = extras.getString("jobName");
            jobCategory = extras.getString("jobCategory");
            jobFee = extras.getInt("jobFee");
            bonus = extras.getInt("bonus");
            selectedPayment = extras.getString("selectedPayment");
        }

        TextView applyJob = findViewById(R.id.applyJob);
        TextView job_name = findViewById(R.id.job_name);
        TextView job_category = findViewById(R.id.job_category);
        TextView job_fee = findViewById(R.id.job_fee);
        TextView textCode = findViewById(R.id.textCode);
        TextView total_fee = findViewById(R.id.total_fee);
        EditText referral_code = findViewById(R.id.referral_code);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton ewallet = findViewById(R.id.ewallet);
        RadioButton bank = findViewById(R.id.bank);

        Button btnCount = findViewById(R.id.btnCount);
        Button btnApply = findViewById(R.id.btnApply);

        btnCount.setEnabled(false);
        btnApply.setEnabled(false);
        textCode.setVisibility(View.GONE);
        referral_code.setVisibility(View.GONE);

        job_name.setText(jobName);
        job_category.setText(jobCategory);
        job_fee.setText(Double.toString(jobFee));
        total_fee.setText(Double.toString(0));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                btnCount.setEnabled(true);
                btnApply.setEnabled(false);
                switch (checkedId) {
                    case R.id.ewallet:
                        textCode.setVisibility(View.VISIBLE);
                        referral_code.setVisibility(View.VISIBLE);
                        break;
                    case R.id.bank:
                        textCode.setVisibility(View.GONE);
                        referral_code.setVisibility(View.GONE);
                        break;
                }
            }
        });

        btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int chosenRadioButtonId = radioGroup.getCheckedRadioButtonId();
                btnCount.setEnabled(false);
                btnApply.setEnabled(true);

                switch (chosenRadioButtonId) {
                    case R.id.bank:
                        total_fee.setText(Double.toString(jobFee));
                        break;
                    case R.id.ewallet:
                        if (!referral_code.getText().toString().isEmpty()) {
                            final String referralCode = referral_code.getText().toString();
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject != null) {
                                            int extraFee = jsonObject.getInt("extraFee");
                                            int minTotalFee = jsonObject.getInt("minTotalFee");
                                            boolean active = jsonObject.getBoolean("active");
                                            if (active && jobFee >= minTotalFee) {
                                                Toast.makeText(ApplyJobActivity.this, "Job Applied", Toast.LENGTH_LONG)
                                                        .show();
                                                total_fee.setText("Rp. " + (jobFee + extraFee));
                                            } else {
                                                Toast.makeText(ApplyJobActivity.this,
                                                        "Bonus inactive or You haven't reached the minimum job fee to use this referral code",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(ApplyJobActivity.this, "Referral Code not found",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                            BonusRequest bonusRequest = new BonusRequest(referralCode, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                            queue.add(bonusRequest);
                        } else {
                            total_fee.setText(Double.toString(jobFee));
                        }
                        break;
                }
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int chosenRadioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(chosenRadioButtonId);
                final String payMethod = radioButton.getText().toString().trim();
                final String referralCode = referral_code.getText().toString().trim();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                Toast.makeText(ApplyJobActivity.this, "Your job is being applied", Toast.LENGTH_LONG)
                                        .show();
                                Intent intent = new Intent(ApplyJobActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("jobseekerId", jobseekerId);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ApplyJobActivity.this, "Process failed", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ApplyJobActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("jobseekerId", jobseekerId);
                            startActivity(intent);

                        }
                    }
                };

                switch (chosenRadioButtonId) {
                    case R.id.bank:
                        request = new ApplyJobRequest(jobId + "", jobseekerId + "", "/createBankPayment",
                                responseListener);
                        break;
                    case R.id.ewallet:
                        request = new ApplyJobRequest(jobId + "", jobseekerId + "", "" + referralCode,
                                "/createEWalletPayment", responseListener);
                        break;
                }

                RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                queue.add(request);
            }
        });
    }

}