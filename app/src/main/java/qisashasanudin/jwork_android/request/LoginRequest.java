package qisashasanudin.jwork_android.requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import qisashasanudin.jwork_android.adapters.NetworkAdapter;

public class LoginRequest extends StringRequest {
    private static final String URL = NetworkAdapter.getIpAddress() + "/jobseeker/login";
    private Map<String, String> params;

    public LoginRequest(String email, String password, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    protected Map<String,String> getParams() throws AuthFailureError {
        return params;
    }
}
