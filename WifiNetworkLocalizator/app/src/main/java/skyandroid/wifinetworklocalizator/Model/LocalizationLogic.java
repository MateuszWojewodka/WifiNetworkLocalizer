package skyandroid.wifinetworklocalizator.Model;

import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;

import skyandroid.wifinetworklocalizator.Model.DataTypes.MeasurmentPoint;
import skyandroid.wifinetworklocalizator.Model.DataTypes.Point;
import skyandroid.wifinetworklocalizator.Model.DataTypes.ThreeMacIds;
import skyandroid.wifinetworklocalizator.Model.DataTypes.ThreeRSSISignals;
import skyandroid.wifinetworklocalizator.Model.HelperClasses.WifiDevicesDetails;
import skyandroid.wifinetworklocalizator.Model.HelperClasses.WifiHandler;

/**
 * Created by skywatcher_usr on 2018-04-07.
 */

public class LocalizationLogic {

    private WifiHandler wifiHandler;

    public LocalizationLogic(AppCompatActivity ctx) {

        wifiHandler = new WifiHandler(ctx);
    }

    public Point getNearestXYPointInAdditionToCurrentWifiSignals(String roomName, int roomId) throws IOException {

        ThreeRSSISignals threeRSSISignals = getAverageOfCurrentThreeDeterminantRSSISignalsMeasurments(roomName);
        return ServerHandler.INSTANCE.getNearestXYLocalizationPoint(roomId, threeRSSISignals);
    }

    public void addRSSIMeasurmentInXYPoint(int roomId, String roomaName, Point point) throws IOException {

        MeasurmentPoint measurmentPoint = new MeasurmentPoint();

        ThreeRSSISignals currentAvgRSSISignals = getAverageOfCurrentThreeDeterminantRSSISignalsMeasurments(roomaName);

        measurmentPoint.FirstMacIdRSSI = currentAvgRSSISignals.FirstRSSISignal;
        measurmentPoint.SecondMacIdRSSI = currentAvgRSSISignals.SecondRSSISignal;
        measurmentPoint.ThirdMacIdRSSI = currentAvgRSSISignals.ThirdRSSISignal;
        measurmentPoint.X = point.x;
        measurmentPoint.Y = point.y;

        ServerHandler.INSTANCE
                .addRSSIMeasurmentInXYPoint(roomId, measurmentPoint);
    }

    private ThreeRSSISignals getAverageOfCurrentThreeDeterminantRSSISignalsMeasurments(String roomName) throws IOException {

        int measurmentCount = 3;

        ThreeRSSISignals[] signalMeasurments
                = getSeveralCurrentDeterminantSignalsRSSIMeasurments(roomName, measurmentCount);

        return getAverageOfRSSISignalsMeasurments(signalMeasurments);
    }

    private ThreeRSSISignals getAverageOfRSSISignalsMeasurments(ThreeRSSISignals[] RSSISignals) {

        ThreeRSSISignals result = new ThreeRSSISignals();

        int firstSignalsSum = 0;
        int secondSignalsSum = 0;
        int thirdSignalsSum = 0;

        for(int i = 0; i<RSSISignals.length; i++) {
            firstSignalsSum += Integer.parseInt(RSSISignals[i].FirstRSSISignal);
            secondSignalsSum += Integer.parseInt(RSSISignals[i].SecondRSSISignal);
            thirdSignalsSum += Integer.parseInt(RSSISignals[i].ThirdRSSISignal);
        }

        result.FirstRSSISignal = Integer.toString(firstSignalsSum/RSSISignals.length);
        result.SecondRSSISignal = Integer.toString(secondSignalsSum/RSSISignals.length);
        result.ThirdRSSISignal = Integer.toString(thirdSignalsSum/RSSISignals.length);

        return result;
    }

    private ThreeRSSISignals[] getSeveralCurrentDeterminantSignalsRSSIMeasurments(String roomName, int measurmentCount) throws IOException {

        ThreeMacIds determinantMacIds = ServerHandler.INSTANCE.getThreeDeterminantMacIds(roomName);
        ThreeRSSISignals[] RSSISignals = new ThreeRSSISignals[measurmentCount];

        for(int i=0; i<measurmentCount; i++) {
            RSSISignals[i] = getThreeCurrentDeterminantRSSISignal(roomName, determinantMacIds);
        }

        return RSSISignals;
    }

    private ThreeRSSISignals getThreeCurrentDeterminantRSSISignal(String roomName, ThreeMacIds determinantMacIds) {

        List<WifiDevicesDetails> wifiDevicesSignals = wifiHandler.getWifiDevicesSignals();
        return filterDeterminantSignals(roomName, wifiDevicesSignals, determinantMacIds);
    }

    private ThreeRSSISignals filterDeterminantSignals
            (String roomName, List<WifiDevicesDetails> allWifiSignals, ThreeMacIds determinantMacIds) {

        ThreeRSSISignals result = new ThreeRSSISignals();

        for (WifiDevicesDetails signal : allWifiSignals) {

            if (signal.BSSID.equals(determinantMacIds.FirstMacId))
                result.FirstRSSISignal = signal.RSSI;
            else if (signal.BSSID.equals(determinantMacIds.SecondMacId))
                result.SecondRSSISignal = signal.RSSI;
            else if (signal.BSSID.equals(determinantMacIds.ThirdMacId))
                result.ThirdRSSISignal = signal.RSSI;
        }

        //todo wywal to
        result.SecondRSSISignal = "25";

        return result;
    }
}
