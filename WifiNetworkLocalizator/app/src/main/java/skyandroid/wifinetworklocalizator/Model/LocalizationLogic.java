package skyandroid.wifinetworklocalizator.Model;

import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;

import skyandroid.wifinetworklocalizator.Model.DataTypes.Point;
import skyandroid.wifinetworklocalizator.Model.DataTypes.ThreeMacIds;
import skyandroid.wifinetworklocalizator.Model.DataTypes.ThreeRSSISignals;

/**
 * Created by skywatcher_usr on 2018-04-07.
 */

public class LocalizationLogic {

    private WifiHandler wifiHandler;

    public LocalizationLogic(AppCompatActivity ctx) {

        wifiHandler = new WifiHandler(ctx);
    }

    public Point getXYPointInAdditionToCurrentWifiSignals(String roomName, int roomId) throws IOException {

        List<WifiDevicesDetails> wifiDevicesSignals = wifiHandler.getWifiDevicesSignals();
        ThreeRSSISignals threeRSSISignals = filterDeterminantSignals(roomName, wifiDevicesSignals);

        return ServerHandler.INSTANCE.getNearestXYLocalizationPoint(roomId, threeRSSISignals);
    }

    private ThreeRSSISignals filterDeterminantSignals
            (String roomName, List<WifiDevicesDetails> allWifiSignals) throws IOException {

        ThreeRSSISignals result = new ThreeRSSISignals();
        ThreeMacIds determinantMacIds = ServerHandler.INSTANCE.getThreeDeterminantMacIds(roomName);

        for (WifiDevicesDetails signal : allWifiSignals) {

            if (signal.BSSID.equals(determinantMacIds.FirstMacId))
                result.FirstRSSISignal = signal.RSSI;
            else if (signal.BSSID.equals(determinantMacIds.SecondMacId))
                result.SecondRSSISignal = signal.RSSI;
            else if (signal.BSSID.equals(determinantMacIds.ThirdMacId))
                result.ThirdRSSISignal = signal.RSSI;
        }

        return result;
    }
}
