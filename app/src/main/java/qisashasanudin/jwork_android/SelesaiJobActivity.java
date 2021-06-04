package qisashasanudin.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.android.volley.*;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SelesaiJobActivity extends AppCompatActivity {
    TextView spacing1;
    View divider1;
    TextView tvInvoiceId, tvJobseekerName, tvInvoiceDate, tvPaymentType, tvInvoiceStatus, tvReferralCode, tvJobName, tvJobFee, tvTotalFee;
    TextView staticJobseekerName, staticInvoiceDate, staticPaymentType, staticInvoiceStatus, staticReferralCode, staticJobName, staticTotalFee;
    Button btnCancel, btnFinish;

    String jobseekerName, jobName, invoiceDate, referralCode, paymentType;
    int jobseekerId, jobFee, totalFee, currentInvoiceId, adminFee, extraFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_job);

        spacing1 = findViewById(R.id.spacing1);
        divider1 = findViewById(R.id.divider1);

        tvInvoiceId = findViewById(R.id.invoice_id);
        tvJobseekerName = findViewById(R.id.jobseeker_name);
        tvInvoiceDate = findViewById(R.id.invoice_date);
        tvPaymentType = findViewById(R.id.payment_type);
        tvInvoiceStatus = findViewById(R.id.invoice_status);
        tvReferralCode = findViewById(R.id.referral_code);
        tvJobName = findViewById(R.id.job_name);
        tvJobFee = findViewById(R.id.job_fee);
        tvTotalFee = findViewById(R.id.total_fee);

        staticJobseekerName = findViewById(R.id.staticJobseekerName);
        staticInvoiceDate = findViewById(R.id.staticInvoiceDate);
        staticPaymentType = findViewById(R.id.staticPaymentType);
        staticInvoiceStatus = findViewById(R.id.staticInvoiceStatus);
        staticReferralCode = findViewById(R.id.staticReferralCode);
        staticJobName = findViewById(R.id.staticJobName);
        staticTotalFee = findViewById(R.id.staticTotalFee);

        btnCancel = findViewById(R.id.btnCancel);
        btnFinish = findViewById(R.id.btnFinish);

        tvInvoiceId.setText("There are no ongoing invoices.");

        spacing1.setVisibility(View.GONE);
        divider1.setVisibility(View.GONE);

        tvJobseekerName.setVisibility(View.GONE);
        tvInvoiceDate.setVisibility(View.GONE);
        tvPaymentType.setVisibility(View.GONE);
        tvInvoiceStatus.setVisibility(View.GONE);
        tvReferralCode.setVisibility(View.GONE);
        tvJobName.setVisibility(View.GONE);
        tvJobFee.setVisibility(View.GONE);
        tvTotalFee.setVisibility(View.GONE);

        staticJobseekerName.setVisibility(View.GONE);
        staticInvoiceDate.setVisibility(View.GONE);
        staticPaymentType.setVisibility(View.GONE);
        staticInvoiceStatus.setVisibility(View.GONE);
        staticReferralCode.setVisibility(View.GONE);
        staticJobName.setVisibility(View.GONE);
        staticTotalFee.setVisibility(View.GONE);

        btnCancel.setVisibility(View.GONE);
        btnFinish.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jobseekerId = extras.getInt("jobseekerId");
            jobseekerName = extras.getString("jobseekerName");
        }

        fetchJob();
    }

    public void fetchJob() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    if (jsonResponse != null) {
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONObject invoice = jsonResponse.getJSONObject(i);
                            JSONArray jobs = invoice.getJSONArray("jobs");
                            currentInvoiceId = invoice.getInt("id");
                            String invoiceStatus = invoice.getString("invoiceStatus");

                            if (invoiceStatus.equals("Ongoing")) {
                                for (int j = 0; j < jobs.length(); j++) {
                                    JSONObject job = jobs.getJSONObject(j);
                                    jobName = job.getString("name");
                                    jobFee = job.getInt("fee");
                                    tvJobName.setText(jobName);
                                    tvJobFee.setText("Rp. " + jobFee);
                                }

                                spacing1.setVisibility(View.VISIBLE);
                                divider1.setVisibility(View.VISIBLE);
                                staticJobseekerName.setVisibility(View.VISIBLE);
                                staticInvoiceDate.setVisibility(View.VISIBLE);
                                staticJobName.setVisibility(View.VISIBLE);
                                staticInvoiceStatus.setVisibility(View.VISIBLE);
                                staticPaymentType.setVisibility(View.VISIBLE);
                                staticTotalFee.setVisibility(View.VISIBLE);
                                tvJobseekerName.setVisibility(View.VISIBLE);
                                tvInvoiceDate.setVisibility(View.VISIBLE);
                                tvPaymentType.setVisibility(View.VISIBLE);
                                tvInvoiceStatus.setVisibility(View.VISIBLE);
                                tvJobName.setVisibility(View.VISIBLE);
                                tvJobFee.setVisibility(View.VISIBLE);
                                tvTotalFee.setVisibility(View.VISIBLE);
                                btnCancel.setVisibility(View.VISIBLE);
                                btnFinish.setVisibility(View.VISIBLE);

                                tvInvoiceId.setText("Invoice ID: " + currentInvoiceId);
                                tvJobseekerName.setText(jobseekerName);
                                tvInvoiceStatus.setText(invoiceStatus);

                                invoiceDate = invoice.getString("date");
                                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US);
                                Date date = inputFormat.parse(invoiceDate);
                                Locale indonesia = new Locale("in", "ID");
                                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", indonesia);
                                invoiceDate = outputFormat.format(date);

                                tvInvoiceDate.setText(invoiceDate);
                                paymentType = invoice.getString("paymentType");
                                tvPaymentType.setText(paymentType);
                                totalFee = invoice.getInt("totalFee");
                                tvTotalFee.setText("Rp. " + totalFee);

                                switch (paymentType) {
                                    case "Bank":
                                        adminFee = invoice.getInt("adminFee");
                                        tvReferralCode.setVisibility(View.GONE);
                                        staticReferralCode.setVisibility(View.GONE);
                                        break;
                                    case "EWallet":
                                        JSONObject bonus = invoice.getJSONObject("bonus");
                                        referralCode = bonus.getString("referralCode");
                                        if (bonus.isNull("referralCode")) {
                                            tvReferralCode.setVisibility(View.GONE);
                                            staticReferralCode.setVisibility(View.GONE);
                                        } else {
                                            extraFee = bonus.getInt("extraFee");
                                            tvReferralCode.setVisibility(View.VISIBLE);
                                            staticReferralCode.setVisibility(View.VISIBLE);
                                            tvReferralCode.setText(referralCode);
                                        }
                                        break;
                                }
                            } else {
                                tvInvoiceId.setText("There are no ongoing invoices.");

                                spacing1.setVisibility(View.GONE);
                                divider1.setVisibility(View.GONE);
                                staticReferralCode.setVisibility(View.GONE);
                                staticJobseekerName.setVisibility(View.GONE);
                                staticInvoiceDate.setVisibility(View.GONE);
                                staticJobName.setVisibility(View.GONE);
                                staticInvoiceStatus.setVisibility(View.GONE);
                                staticPaymentType.setVisibility(View.GONE);
                                staticTotalFee.setVisibility(View.GONE);
                                tvJobseekerName.setVisibility(View.GONE);
                                tvInvoiceDate.setVisibility(View.GONE);
                                tvPaymentType.setVisibility(View.GONE);
                                tvInvoiceStatus.setVisibility(View.GONE);
                                tvReferralCode.setVisibility(View.GONE);
                                tvJobName.setVisibility(View.GONE);
                                tvJobFee.setVisibility(View.GONE);
                                tvTotalFee.setVisibility(View.GONE);
                                btnCancel.setVisibility(View.GONE);
                                btnFinish.setVisibility(View.GONE);
                            }
                        }

                    }
                } catch (JSONException | ParseException e) {
                    Toast.makeText(SelesaiJobActivity.this, "No invoice found.", Toast.LENGTH_LONG).show();
                }
            }
        };

        JobFetchRequest request = new JobFetchRequest(Integer.toString(jobseekerId), responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
        queue.add(request);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                Toast.makeText(SelesaiJobActivity.this, "This invoice is cancelled", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiJobActivity.this);
                            builder.setMessage("Please try again.").create().show();
                        }
                    }
                };

                JobBatalRequest request = new JobBatalRequest(Integer.toString(currentInvoiceId), responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
                queue.add(request);
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                Toast.makeText(SelesaiJobActivity.this, "This invoice is finished", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiJobActivity.this);
                            builder.setMessage("Please try again.").create().show();
                        }
                    }
                };

                JobSelesaiRequest request = new JobSelesaiRequest(Integer.toString(currentInvoiceId), responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivity.this);
                queue.add(request);
            }
        });
    }
}