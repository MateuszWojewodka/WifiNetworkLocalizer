package skyandroid.wifinetworklocalizator.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import skyandroid.wifinetworklocalizator.Model.HelperClasses.WifiDevicesDetails;
import skyandroid.wifinetworklocalizator.R;

public class StartUpActivity extends AppCompatActivity {

    Button startButton;
    Button adminButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        startButton = (Button) findViewById(R.id.btnLocalize);
        adminButton = (Button) findViewById(R.id.btnAdmin);
    }

    //View.OnClickListener

}
