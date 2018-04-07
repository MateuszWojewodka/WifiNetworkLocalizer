package skyandroid.wifinetworklocalizator.ViewModel;

import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;

import skyandroid.wifinetworklocalizator.Model.DataTypes.Point;
import skyandroid.wifinetworklocalizator.Model.DataTypes.RoomInfo;
import skyandroid.wifinetworklocalizator.Model.LocalizationLogic;
import skyandroid.wifinetworklocalizator.Model.ServerHandler;
import android.databinding.ObservableArrayMap;
import android.databinding.ObservableField;

/**
 * Created by skywatcher_usr on 2018-04-06.
 */

public class AnonymousClientViewModel implements ViewModel {

    public ObservableField<Point> currentPoint;
    public ObservableField<List<RoomInfo>> possibleRooms;

    private LocalizationLogic localizationLogic;
    private String roomName = "";
    private int roomId = 0;

    public AnonymousClientViewModel(AppCompatActivity ctx) {
        localizationLogic = new LocalizationLogic(ctx);
    }

    @Override
    public void onCreate() {}

    @Override
    public void onPause() {}

    @Override
    public void onResume() {}

    @Override
    public void onDestroy() {}

    public void onClickedButtonGetYourXYPoint() throws IOException {
        currentPoint.set(localizationLogic.getNearestXYPointInAdditionToCurrentWifiSignals(roomName, roomId));
    }

    public void onClickButtonGetPossibleRooms() throws IOException {
        possibleRooms.set(ServerHandler.INSTANCE.getPossibleRooms());
    }
}
