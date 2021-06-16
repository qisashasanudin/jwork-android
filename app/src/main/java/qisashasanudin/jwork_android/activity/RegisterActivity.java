package qisashasanudin.jwork_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import qisashasanudin.jwork_android.R;
import qisashasanudin.jwork_android.request.JobseekerRequest;
import qisashasanudin.jwork_android.request.RegisterRequest;

public class RegisterActivity extends AppCompatActivity {
    boolean valid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        TextView goToLogin = findViewById(R.id.goToLogin);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                buttonRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = editTextName.getText().toString();
                        String email = editTextEmail.getText().toString();
                        String password = editTextPassword.getText().toString();

                        if (name.isEmpty()) {
                            editTextName.setError("Please enter your name");
                            editTextName.requestFocus();
                            return;
                        }

                        if (email.isEmpty()) {
                            editTextEmail.setError("Please enter your email address");
                            editTextEmail.requestFocus();
                            return;
                        }

                        if (password.isEmpty()) {
                            editTextPassword.setError("Please enter your password");
                            editTextPassword.requestFocus();
                            return;
                        }
                        if (validEmail(email) && validPassword(password)) {
                            if (validate(email)) {
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            if (jsonObject != null) {
                                                Toast.makeText(RegisterActivity.this, "Register Successful",
                                                        Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                intent.putExtra("jobseekerId", jsonObject.getInt("id"));
                                                intent.putExtra("jobseekerName", jsonObject.getString("name"));
                                                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                finish();
                                            }
                                        } catch (JSONException e) {
                                            Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_LONG)
                                                    .show();
                                        }
                                    }
                                };
                                RegisterRequest registerRequest = new RegisterRequest(name, email, password,
                                        responseListener);
                                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                                queue.add(registerRequest);
                            } else {
                                Toast.makeText(RegisterActivity.this, "Email already exists", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Email or password is invalid", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                goToLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public boolean validate(final String mail) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<String> jobseekers = null;

                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject jobseeker = jsonResponse.getJSONObject(i);
                        String emailCheck = jobseeker.getString("email");
                        jobseekers = new ArrayList<String>();
                        jobseekers.add(emailCheck);
                    }
                    for (String temp : jobseekers) {
                        valid = true;
                        if (temp.equals(mail)) {
                            valid = false;
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        };
        JobseekerRequest request = new JobseekerRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(request);

        return valid;
    }

    public boolean validEmail(final String emailCheck) {
        final String EMAIL_PATTERN = "\\A[a-z0-9!#$%&'*+/=?^_‘{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_‘{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\z";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailCheck);
        return matcher.matches();
    }

    public boolean validPassword(final String passwordCheck) {
        final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(passwordCheck);
        return matcher.matches();
    }
}