package qisashasanudin.jwork_android.adapter;

public class NetworkAdapter {
    private static NetworkAdapter instance;
    private static String ipAddress = "http://192.168.100.9:8080";

    private NetworkAdapter(){}

    public static String getIpAddress() {
        return ipAddress;
    }

    public static void setIpAddress(String ipAddress) {
        NetworkAdapter.ipAddress = ipAddress;
    }

    public static synchronized NetworkAdapter getInstance(){
        if(instance==null){
            instance = new NetworkAdapter();
        }
        return instance;
    }
}