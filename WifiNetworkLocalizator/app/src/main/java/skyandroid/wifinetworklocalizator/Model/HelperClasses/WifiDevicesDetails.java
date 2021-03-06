package skyandroid.wifinetworklocalizator.Model.HelperClasses;

/**
 * Created by skywatcher_usr on 2017-11-28.
 */

public class WifiDevicesDetails {

    public String SSID;
    public String RSSI;
    public String BSSID;

    public WifiDevicesDetails() {}

    public WifiDevicesDetails(String SSID, String RSSI, String BSSID) {
        this.SSID = SSID;
        this.RSSI = RSSI;
        this.BSSID = BSSID;
    }

    public String getSSID() {
        return SSID;
    }

    public String getRSSI() {
        return RSSI;
    }

    public String getBSSID() {
        return BSSID;
    }
}
