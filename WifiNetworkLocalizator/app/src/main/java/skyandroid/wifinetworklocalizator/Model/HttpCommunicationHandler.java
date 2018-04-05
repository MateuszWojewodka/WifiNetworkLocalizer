package skyandroid.wifinetworklocalizator.Model;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by skywatcher_usr on 2018-04-04.
 */

class HttpCommunicationHandler {

    private OkHttpClient client = new OkHttpClient();
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String baseAddress = "http://192.168.173.1:1471/";

    public String doGetRequest(String resource) throws IOException {

        String url = baseAddress + resource;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String doPostRequest(String resource, String json) throws IOException {
        String url = baseAddress + resource;

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
