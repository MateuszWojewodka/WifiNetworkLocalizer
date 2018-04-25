package skyandroid.wifinetworklocalizator.View.AdminUser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import skyandroid.wifinetworklocalizator.Model.DataTypes.FourMacIds;
import skyandroid.wifinetworklocalizator.Model.HelperClasses.WifiDevicesDetails;
import skyandroid.wifinetworklocalizator.R;
import skyandroid.wifinetworklocalizator.ViewModel.AdminClientViewModel;

/**
 * Created by skywatcher_usr on 2018-04-09.
 */

public class ChooseDeterminantMacIdsActivity extends AppCompatActivity {

    AdminClientViewModel viewModel;

    ListView possibleAccessPointsListView;
    Button confirmButton;
    Button refreshButton;

    List<String> checkedMacIds = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_macids);

        viewModel = new AdminClientViewModel(this);

        Bundle b = getIntent().getExtras();
        viewModel.roomName = b.getString("roomName");

        possibleAccessPointsListView = (ListView) findViewById(R.id.lvPossibleAccesPoints);
        refreshButton = (Button) findViewById(R.id.btnRefreshChoseMacIds);
        confirmButton = (Button) findViewById(R.id.btnConfirmChoseMacIds);

        confirmButton.setEnabled(false);
        fetchPossibleAccessPoints();

        possibleAccessPointsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                CheckBox chooseCheckBox = (CheckBox) view.findViewById(R.id.cbMIs);
                String macId = ((WifiDevicesDetails) adapterView.getItemAtPosition(position)).BSSID;

                if (chooseCheckBox.isChecked()) {
                    chooseCheckBox.setChecked(false);
                    subMacIdCount(macId);
                }
                else {
                    if (checkedMacIds.size() >=3)
                        Toast.makeText(getApplicationContext(), "Można wybrać maksymalnie 3 id.", Toast.LENGTH_SHORT).show();
                    else {
                        chooseCheckBox.setChecked(true);
                        addMacIdCount(macId);
                    }
                }
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedMacIds.clear();
                confirmButton.setEnabled(false);
                fetchPossibleAccessPoints();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewRoomWithMacIds();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void createNewRoomWithMacIds() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    viewModel.createNewRoom(getThreeMacIdsFromStringList(checkedMacIds));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent i = new Intent(getApplicationContext(), MeasurmentsActivity.class);
                i.putExtra("roomName" ,viewModel.roomName);
                startActivity(i);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void fetchPossibleAccessPoints() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                viewModel.fetchPossibleAccessPoints();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                populatePossibleAccesPointsOnListView();
            }
        }.execute();
    }

    private FourMacIds getThreeMacIdsFromStringList(List<String> list) {

        FourMacIds result = new FourMacIds();
        result.FirstMacId = list.get(0);
        result.SecondMacId = list.get(1);
        result.ThirdMacId = list.get(2);

        return result;
    }

    private void populatePossibleAccesPointsOnListView() {

        ArrayAdapter<WifiDevicesDetails> adapter
                = new ArrayAdapter<WifiDevicesDetails>(ChooseDeterminantMacIdsActivity.this, R.layout.scan_detailsdub, viewModel.possibleAccessPoints) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null)
                    convertView = getLayoutInflater().inflate(R.layout.scan_detailsdub, parent, false);

                WifiDevicesDetails currentItem = viewModel.possibleAccessPoints.get(position);

                TextView RSSI = (TextView) convertView.findViewById(R.id.txtRSSIVal);
                TextView BSSI = (TextView) convertView.findViewById(R.id.txtBSSIDVal);
                TextView SSID = (TextView) convertView.findViewById(R.id.txtSSIDVal);

                RSSI.setText(currentItem.RSSI);
                BSSI.setText(currentItem.BSSID);
                SSID.setText(currentItem.SSID);

                CheckBox chooseCheckBox = (CheckBox) convertView.findViewById(R.id.cbMIs);

                if (checkedMacIds.contains(currentItem.BSSID))
                    chooseCheckBox.setChecked(true);
                else
                    chooseCheckBox.setChecked(false);

                return convertView;
            }
        };
        possibleAccessPointsListView.setAdapter(adapter);
    }

    void addMacIdCount(String macId) {
        checkedMacIds.add(macId);
        if(checkedMacIds.size()>=3)
            confirmButton.setEnabled(true);
    }

    void subMacIdCount(String macId) {
        checkedMacIds.remove(macId);
        if (checkedMacIds.size()<3)
            confirmButton.setEnabled(false);
    }
}
