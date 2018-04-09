package skyandroid.wifinetworklocalizator.ViewModel;

import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import skyandroid.wifinetworklocalizator.Model.DataTypes.Point;
import skyandroid.wifinetworklocalizator.Model.DataTypes.ThreeMacIds;
import skyandroid.wifinetworklocalizator.Model.HelperClasses.WifiDevicesDetails;
import skyandroid.wifinetworklocalizator.Model.LocalizationLogic;
import skyandroid.wifinetworklocalizator.Model.ServerHandler;

/**
 * Created by skywatcher_usr on 2018-04-07.
 */

public class AdminClientViewModel implements ViewModel {

    private LocalizationLogic localizationLogic;

    public List<WifiDevicesDetails> possibleAccessPoints = new ArrayList<>();
    public String roomName = "";
    public int roomId = 0;

    public AdminClientViewModel(AppCompatActivity ctx) {
        localizationLogic = new LocalizationLogic(ctx);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    public void fetchPossibleAccessPoints() {
        possibleAccessPoints = localizationLogic.getAllWifiSignals();
    }

    public boolean checkIfRoomCanBeCreated(String roomName) throws Exception {
        return localizationLogic.checkIfRoomNameIsUnique(roomName);
    }

    public void onClickedCreateNewRoomButton(String roomName, ThreeMacIds macIds) throws IOException {

        roomName = roomName;
        ServerHandler.INSTANCE.putNewRoomWithDeterminantMacIds(roomName, macIds);
    }

    public void onClickedButtonDoMeasurment(Point point) throws IOException {
        localizationLogic.addRSSIMeasurmentInXYPoint(roomId, roomName, point);
    }
}
