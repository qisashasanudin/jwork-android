package qisashasanudin.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ApplyJobRequest extends StringRequest {
    private Map<String, String> params;
    private static final String URL = NetworkAdapter.getIpAddress() + "/invoice";

    public ApplyJobRequest(String jobId, String jobseekerId, String url, Response.Listener<String> listener){
        super(Method.POST, URL + url, listener, null);
        params = new HashMap<>();
        params.put("jobIdList", jobId);
        params.put("jobseekerId", jobseekerId);
    }

    public ApplyJobRequest(String jobId, String jobseekerId, String referralCodeOrAdminFee, String url, Response.Listener<String> listener) {
        super(Method.POST, URL + url, listener, null);
        params = new HashMap<>();
        params.put("jobIdList", jobId);
        params.put("jobseekerId", jobseekerId);

        if(url.equals("/createEWalletPayment")){
            params.put("referralCode", referralCodeOrAdminFee);
        }else if(url.equals("/createBankPayment")){
            params.put("adminFee", referralCodeOrAdminFee);
        }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return params;
    }
}
