package skyandroid.wifinetworklocalizator.ViewModel;

import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
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

    public ObservableField<Point> currentPoint = new ObservableField<>();
    public List<RoomInfo> possibleRooms = new ArrayList<>();

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

    public void fetchPossibleRooms() throws IOException {
        possibleRooms = ServerHandler.INSTANCE.getPossibleRooms();
    }
}
