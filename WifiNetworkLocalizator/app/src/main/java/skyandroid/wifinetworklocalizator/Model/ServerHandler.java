package skyandroid.wifinetworklocalizator.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import skyandroid.wifinetworklocalizator.Model.DataTypes.MeasurmentPoint;
import skyandroid.wifinetworklocalizator.Model.DataTypes.Point;
import skyandroid.wifinetworklocalizator.Model.DataTypes.RoomInfo;
import skyandroid.wifinetworklocalizator.Model.DataTypes.ThreeMacIds;
import skyandroid.wifinetworklocalizator.Model.DataTypes.ThreeRSSISignals;
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

    public ThreeMacIds getThreeDeterminantMacIds(String roomName) throws IOException {

        String jsonString = server.doGetRequest("localization/rooms/" + roomName);

        return gson.fromJson(jsonString, ThreeMacIds.class);
    }

    public void putNewRoomDeterminantMacIds(String roomName, ThreeMacIds macIds) throws IOException {

        String jsonPostString = gson.toJson(macIds);

        server.doPostRequest("localization/rooms/" + roomName, jsonPostString);
    }

    public void addRSSIMeasurmentInXYPoint(int roomId, MeasurmentPoint measurmentPoint) throws IOException {

        String jsonPostString = gson.toJson(measurmentPoint);

        String resource = "localization/rooms/" + roomId + "/point";

        server.doPostRequest(resource, jsonPostString);
    }

    public Point getNearestXYLocalizationPoint(int roomId, ThreeRSSISignals measurmentSignals) throws IOException {

        String resource = "localization/rooms" + roomId + "/point";
        String query =
                "?firstMacId=" + measurmentSignals.FirstRSSISignal +
                "&secondMacId=" + measurmentSignals.SecondRSSISignal +
                "&thirdMacId=" + measurmentSignals.ThirdRSSISignal;

        String responseJson = server.doGetRequest(resource + query);
        return gson.fromJson(responseJson, Point.class);
    }
}
