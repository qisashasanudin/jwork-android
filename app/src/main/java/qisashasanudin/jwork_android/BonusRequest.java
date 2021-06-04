package qisashasanudin.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class BonusRequest extends StringRequest {
    private Map<String,String> params;
    private static final String url = NetworkAdapter.getIpAddress() + "/bonus";

    public BonusRequest(String referralCode, Response.Listener<String> listener){
        super(Method.GET, url+referralCode, listener, null);
        params = new HashMap<>();
    }

    @Override
    protected Map<String,String> getParams() throws AuthFailureError {
        return params;
    }
}
