package qisashasanudin.jwork_android.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import qisashasanudin.jwork_android.adapters.NetworkAdapter;

public class MenuRequest extends StringRequest {
    private static final String URL = NetworkAdapter.getIpAddress() + "/job";
    private Map<String, String> params;

    public MenuRequest( Response.Listener<String> listener) {
        super(Method.GET, URL, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams()  {
        return params;
    }
}