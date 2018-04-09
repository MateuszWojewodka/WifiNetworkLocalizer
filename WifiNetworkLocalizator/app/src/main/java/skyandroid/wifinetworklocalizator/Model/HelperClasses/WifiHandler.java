package skyandroid.wifinetworklocalizator.Model.HelperClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import skyandroid.wifinetworklocalizator.Model.HelperClasses.WifiDevicesDetails;

/**
 * Created by skywatcher_usr on 2018-04-06.
 */

public class WifiHandler {

    private AppCompatActivity ctx;
    private WifiManager wifi;
    private List<WifiDevicesDetails> wifiDevicesSignals = new ArrayList<>();
    private Object wifiScanningLock = new Object();
    private boolean scanningFinished = true;

    public WifiHandler(AppCompatActivity ctx) {

        this.ctx = ctx;
        wifi = (WifiManager) ctx.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        registerWifiScanResultsReceiverEvent();
    }

    public void setUpWifiModule() {
        if (wifi.isWifiEnabled() == false) {
            wifi.setWifiEnabled(true);
        }
    }

    public List<WifiDevicesDetails> getWifiDevicesSignals() {

        setUpWifiModule();

        synchronized (wifiScanningLock) {

            scanningFinished = false;
            wifi.startScan();

            while(!scanningFinished)
                try {
                    wifiScanningLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            return wifiDevicesSignals;
        }
    }

    private void registerWifiScanResultsReceiverEvent(){

        ctx.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                synchronized (wifiScanningLock) {
                    wifiDevicesSignals = getWifiScarResultsDetailsDataList(wifi.getScanResults());
                    scanningFinished = true;
                    wifiScanningLock.notifyAll();
                }
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    private List<WifiDevicesDetails> getWifiScarResultsDetailsDataList(List<ScanResult> wifiScanResults) {

        List<WifiDevicesDetails> wifiSignalsDetailsDataList = new ArrayList<>();
        for (Iterator<ScanResult> i = wifiScanResults.iterator(); i.hasNext();) {
            ScanResult s = i.next();
            wifiSignalsDetailsDataList.add(new WifiDevicesDetails(s.SSID, Integer.toString(s.level), s.BSSID));
        }

        return wifiSignalsDetailsDataList;
    }
}
