package qisashasanudin.jwork_android.requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import qisashasanudin.jwork_android.adapters.NetworkAdapter;

/**
 * <h1>Request untuk fetch objek jobseeker</h1>
 * <p>Proses request yang dipanggil dari activity</p>
 *
 * @author Qisas Tazkia Hasanuidn
 * @version 1.0
 */
public class JobseekerRequest extends StringRequest {
    private static final String URL = NetworkAdapter.getIpAddress() + "/jobseeker";
    private Map<String, String> params;

    /**
     * Request objek customer
     * @param listener Response yang dilakukan dari objek yang terdapat pada View
     */
    public JobseekerRequest (Response.Listener<String> listener){
        super(Request.Method.GET, URL, listener, null);
        params = new HashMap<>();
    }

    /**
     * Mengembalikan parameter Map dari POST yang digunakan untuk request invoice
     *
     * @return Parameter request dalam aplikasi
     * @throws AuthFailureError jika terjadi kesalahan autentikasi
     */
    @Override
    protected Map<String,String> getParams() throws AuthFailureError {
        return params;
    }
}