package skyandroid.wifinetworklocalizator.Model;

import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;

import skyandroid.wifinetworklocalizator.Model.DataTypes.MeasurmentPoint;
import skyandroid.wifinetworklocalizator.Model.DataTypes.Point;
import skyandroid.wifinetworklocalizator.Model.DataTypes.RoomInfo;
import skyandroid.wifinetworklocalizator.Model.DataTypes.FourMacIds;
import skyandroid.wifinetworklocalizator.Model.DataTypes.FourRSSISignals;
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

    public int createNewRoomAndGetItsId(String roomName, FourMacIds macIds) throws Exception {

        int id = 0;
        ServerHandler.INSTANCE.putNewRoomWithDeterminantMacIds(roomName, macIds);

        List<RoomInfo> allRooms = ServerHandler.INSTANCE.getPossibleRooms();

        for (RoomInfo room: allRooms
                ) {
            if (room.roomName.equals(roomName))
                id = room.roomId;
        }

        return id;
    }

    public boolean checkIfRoomNameIsUnique(String roomName) throws Exception {

        List<RoomInfo> allRooms = ServerHandler.INSTANCE.getPossibleRooms();

        for (RoomInfo room: allRooms
                ) {
            if (room.roomName.equals(roomName))
                return false;
        }

        return true;
    }

    public Point getNearestXYPointInAdditionToCurrentWifiSignals(String roomName, int roomId) throws IOException {

        FourRSSISignals fourRSSISignals = getAverageOfCurrentFourDeterminantRSSISignalsMeasurments(roomName);
        return ServerHandler.INSTANCE.getNearestXYLocalizationPoint(roomId, fourRSSISignals);
    }

    public FourRSSISignals addRSSIMeasurmentInXYPoint(int roomId, String roomaName, Point point) throws IOException {

        MeasurmentPoint measurmentPoint = new MeasurmentPoint();

        FourRSSISignals currentAvgRSSISignals = getAverageOfCurrentFourDeterminantRSSISignalsMeasurments(roomaName);

        measurmentPoint.FirstMacIdRSSI = currentAvgRSSISignals.FirstRSSISignal;
        measurmentPoint.SecondMacIdRSSI = currentAvgRSSISignals.SecondRSSISignal;
        measurmentPoint.ThirdMacIdRSSI = currentAvgRSSISignals.ThirdRSSISignal;
        measurmentPoint.X = point.x;
        measurmentPoint.Y = point.y;

        ServerHandler.INSTANCE
                .addRSSIMeasurmentInXYPoint(roomId, measurmentPoint);

        return currentAvgRSSISignals;
    }

    public List<WifiDevicesDetails> getAllWifiSignals(){
        return wifiHandler.getWifiDevicesSignals();
    }

    private FourRSSISignals getAverageOfCurrentFourDeterminantRSSISignalsMeasurments(String roomName) throws IOException {

        int measurmentCount = 4;

        FourRSSISignals[] signalMeasurments
                = getSeveralCurrentDeterminantSignalsRSSIMeasurments(roomName, measurmentCount);

        return getAverageOfRSSISignalsMeasurments(signalMeasurments);
    }

    private FourRSSISignals getAverageOfRSSISignalsMeasurments(FourRSSISignals[] RSSISignals) {

        FourRSSISignals result = new FourRSSISignals();

        int firstSignalsSum = 0;
        int secondSignalsSum = 0;
        int thirdSignalsSum = 0;
        int fourthSignalsSum = 0;

        for(int i = 0; i<RSSISignals.length; i++) {
            firstSignalsSum += Integer.parseInt(RSSISignals[i].FirstRSSISignal);
            secondSignalsSum += Integer.parseInt(RSSISignals[i].SecondRSSISignal);
            thirdSignalsSum += Integer.parseInt(RSSISignals[i].ThirdRSSISignal);
            fourthSignalsSum += Integer.parseInt(RSSISignals[i].FourthRSSISignal);
        }

        result.FirstRSSISignal = Integer.toString(firstSignalsSum/RSSISignals.length);
        result.SecondRSSISignal = Integer.toString(secondSignalsSum/RSSISignals.length);
        result.ThirdRSSISignal = Integer.toString(thirdSignalsSum/RSSISignals.length);
        result.FourthRSSISignal = Integer.toString(fourthSignalsSum/RSSISignals.length);

        return result;
    }

    private FourRSSISignals[] getSeveralCurrentDeterminantSignalsRSSIMeasurments(String roomName, int measurmentCount) throws IOException {

        FourMacIds determinantMacIds = ServerHandler.INSTANCE.getFourDeterminantMacIds(roomName);
        FourRSSISignals[] RSSISignals = new FourRSSISignals[measurmentCount];

        for(int i=0; i<measurmentCount; i++) {
            RSSISignals[i] = getFourCurrentDeterminantRSSISignal(roomName, determinantMacIds);
        }

        return RSSISignals;
    }

    private FourRSSISignals getFourCurrentDeterminantRSSISignal(String roomName, FourMacIds determinantMacIds) {

        List<WifiDevicesDetails> wifiDevicesSignals = wifiHandler.getWifiDevicesSignals();
        return filterDeterminantSignals(roomName, wifiDevicesSignals, determinantMacIds);
    }

    private FourRSSISignals filterDeterminantSignals
            (String roomName, List<WifiDevicesDetails> allWifiSignals, FourMacIds determinantMacIds) {

        FourRSSISignals result = new FourRSSISignals();

        for (WifiDevicesDetails signal : allWifiSignals) {

            if (signal.BSSID.equals(determinantMacIds.FirstMacId))
                result.FirstRSSISignal = signal.RSSI;
            else if (signal.BSSID.equals(determinantMacIds.SecondMacId))
                result.SecondRSSISignal = signal.RSSI;
            else if (signal.BSSID.equals(determinantMacIds.ThirdMacId))
                result.ThirdRSSISignal = signal.RSSI;
            else if (signal.BSSID.equals(determinantMacIds.FourthMacId))
                result.FourthRSSISignal = signal.RSSI;
        }

        return result;
    }
}
