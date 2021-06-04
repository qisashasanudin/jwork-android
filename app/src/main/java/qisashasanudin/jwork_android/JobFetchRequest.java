package qisashasanudin.jwork_android;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class JobFetchRequest extends StringRequest {
    private static final String URL =  NetworkAdapter.getIpAddress() + "/invoice/jobseeker/";
    private Map<String, String> params;

    public JobFetchRequest(String jobseekerId, Response.Listener<String> listener){
        super(Method.GET, URL+jobseekerId, listener, null);
        params = new HashMap<>();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
