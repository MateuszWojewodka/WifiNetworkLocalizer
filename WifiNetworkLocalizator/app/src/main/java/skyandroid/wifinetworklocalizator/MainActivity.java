package skyandroid.wifinetworklocalizator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.Manifest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import skyandroid.wifinetworklocalizator.Model.DataTypes.RoomInfo;
import skyandroid.wifinetworklocalizator.Model.ServerHandler;

import static skyandroid.wifinetworklocalizator.Model.ServerHandler.INSTANCE;

public class MainActivity extends AppCompatActivity {

    WifiManager wifi;
    List<ScanResult> wifiScanResults;

    List<WifiDevicesDetails> wifiDevicesDetailsDataList;
    ListView wifiDevicesDetailsListView;
    Button startScanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread() {
            @Override
            public void run() {
                try {
                    List<RoomInfo> a = ServerHandler.INSTANCE.getPossibleRooms();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        wifiDevicesDetailsListView = (ListView) findViewById(R.id.lvScanDetails);
        startScanButton = (Button) findViewById(R.id.btnScanWifi);

        startScanButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                scanForWifiDevicesDetails();
            }
        });

        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        setUpWifiModule();

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                wifiScanResults = wifi.getScanResults();
                startScanButton.setEnabled(true);
                mapScanResultsToWifiDetailsDataList();
                populateWifiDevicesDetailsToList();
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    private void setUpWifiModule() {
        if (wifi.isWifiEnabled() == false) {
            wifi.setWifiEnabled(true);
        }
    }

    private void scanForWifiDevicesDetails() {
        startScanButton.setEnabled(false);
        wifi.startScan();
    }

    private void mapScanResultsToWifiDetailsDataList() {

        wifiDevicesDetailsDataList = new ArrayList<>();
        try
        {
            for (Iterator<ScanResult> i = wifiScanResults.iterator(); i.hasNext();) {
                ScanResult s = i.next();
                wifiDevicesDetailsDataList.add(new WifiDevicesDetails(s.SSID, Integer.toString(s.level), s.BSSID));
            }
        }
        catch (Exception e){}
    }

    private void populateWifiDevicesDetailsToList() {
        ArrayAdapter<WifiDevicesDetails> adapter = new ArrayAdapter<WifiDevicesDetails>(MainActivity.this, R.layout.scan_details, wifiDevicesDetailsDataList) {

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                if (view == null)
                    view = getLayoutInflater().inflate(R.layout.scan_details, parent, false);

                WifiDevicesDetails currentItem = wifiDevicesDetailsDataList.get(position);

                TextView SSID = (TextView) view.findViewById(R.id.txtSSIDValue);
                SSID.setText(currentItem.getSSID());
                TextView RSSI = (TextView) view.findViewById(R.id.txtRSSIValue);
                RSSI.setText(currentItem.getRSSI());
                TextView BSSID = (TextView) view.findViewById(R.id.txtBSSIDValue);
                BSSID.setText(currentItem.getBSSID());

                return view;
            }
        };
        wifiDevicesDetailsListView.setAdapter(adapter);
    }

}
