package skyandroid.wifinetworklocalizator.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import skyandroid.wifinetworklocalizator.Model.DataModels.RoomInfoModel;
import skyandroid.wifinetworklocalizator.Model.DataTypes.RoomInfo;

/**
 * Created by skywatcher_usr on 2018-04-04.
 */

public enum ServerHandler {

    INSTANCE;

    private HttpCommunicationHandler server
            = new HttpCommunicationHandler();

    public List<RoomInfo> getPossibleRooms() throws IOException {

        String jsonString = server.doGetRequest("localization/rooms");

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();
        List<RoomInfo> rooms = gson.fromJson(jsonString, new ArrayList<RoomInfo>().getClass());

        return rooms;
    }
}
