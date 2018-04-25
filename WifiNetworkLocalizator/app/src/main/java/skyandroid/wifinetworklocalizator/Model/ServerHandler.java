package skyandroid.wifinetworklocalizator.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import skyandroid.wifinetworklocalizator.Model.DataTypes.MeasurmentPoint;
import skyandroid.wifinetworklocalizator.Model.DataTypes.Point;
import skyandroid.wifinetworklocalizator.Model.DataTypes.RoomInfo;
import skyandroid.wifinetworklocalizator.Model.DataTypes.FourMacIds;
import skyandroid.wifinetworklocalizator.Model.DataTypes.FourRSSISignals;
import skyandroid.wifinetworklocalizator.Model.HelperClasses.HttpCommunicationHandler;

/**
 * Created by skywatcher_usr on 2018-04-04.
 */

public enum ServerHandler {

    INSTANCE;

    private HttpCommunicationHandler server
            = new HttpCommunicationHandler();

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<RoomInfo> getPossibleRooms() throws IOException {

        String jsonString = server.doGetRequest("localization/rooms");

        return gson.fromJson(jsonString, new TypeToken<List<RoomInfo>>(){}.getType());
    }

    public FourMacIds getFourDeterminantMacIds(String roomName) throws IOException {

        String jsonString = server.doGetRequest("localization/rooms/" + roomName);

        return gson.fromJson(jsonString, FourMacIds.class);
    }

    public void putNewRoomWithDeterminantMacIds(String roomName, FourMacIds macIds) throws IOException {

        String jsonPostString = gson.toJson(macIds);

        String resource = "localization/rooms/" + roomName;
        server.doPutRequest(resource, jsonPostString);
    }

    public void addRSSIMeasurmentInXYPoint(int roomId, MeasurmentPoint measurmentPoint) throws IOException {

        String jsonPostString = gson.toJson(measurmentPoint);

        String resource = "localization/rooms/" + roomId + "/point";

        server.doPostRequest(resource, jsonPostString);
    }

    public Point getNearestXYLocalizationPoint(int roomId, FourRSSISignals measurmentSignals) throws IOException {

        String resource = "localization/rooms/" + roomId + "/point";
        String query =
                "?firstMacId=" + measurmentSignals.FirstRSSISignal +
                "&secondMacId=" + measurmentSignals.SecondRSSISignal +
                "&thirdMacId=" + measurmentSignals.ThirdRSSISignal;

        String responseJson = server.doGetRequest(resource + query);
        return gson.fromJson(responseJson, Point.class);
    }
}
