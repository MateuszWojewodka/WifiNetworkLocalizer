package skyandroid.wifinetworklocalizator.ViewModel;

import java.io.IOException;

import skyandroid.wifinetworklocalizator.Model.DataTypes.Point;
import skyandroid.wifinetworklocalizator.Model.LocalizationLogic;

/**
 * Created by skywatcher_usr on 2018-04-07.
 */

public class AdminClientViewModel implements ViewModel {

    private LocalizationLogic localizationLogic;
    private String roomName = "";
    private int roomId = 0;

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

    public void onClickedButtonDoMeasurment(Point point) throws IOException {
        localizationLogic.addRSSIMeasurmentInXYPoint(roomId, roomName, point);
    }
}
