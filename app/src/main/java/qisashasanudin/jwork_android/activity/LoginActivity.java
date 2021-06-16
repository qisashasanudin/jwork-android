package qisashasanudin.jwork_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import qisashasanudin.jwork_android.R;
import qisashasanudin.jwork_android.requests.LoginRequest;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        TextView goToRegister = findViewById(R.id.goToRegister);

        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                buttonLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = editTextEmail.getText().toString();
                        String password = editTextPassword.getText().toString();

                        if(email.isEmpty()){
                            editTextEmail.setError("Please enter your email address");
                            editTextEmail.requestFocus();
                            return;
                        }

                        if(password.isEmpty()){
                            editTextPassword.setError("Please enter your password");
                            editTextPassword.requestFocus();
                            return;
                        }

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject != null) {
                                        if(jsonObject.getString("name") == "null"){
                                            throw new JSONException("name");
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.putExtra("jobseekerId", jsonObject.getInt("id"));
                                            intent.putExtra("jobseekerName", jsonObject.getString("name"));
                                            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }else{
                                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                                }
                            }

                        };

                        LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                        queue.add(loginRequest);
                    }
                });
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                goToRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

    }
}
