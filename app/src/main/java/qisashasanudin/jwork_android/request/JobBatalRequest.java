package qisashasanudin.jwork_android.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import qisashasanudin.jwork_android.adapter.NetworkAdapter;

public class JobBatalRequest extends StringRequest {
    private static final String URL = NetworkAdapter.getIpAddress() + "/invoice/invoiceStatus/";
    private Map<String, String> params;

    public JobBatalRequest(String id, Response.Listener<String> listener) {
        super(Method.PUT, URL + id, listener, null);
        params = new HashMap<>();
        params.put("status", "Cancelled");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
