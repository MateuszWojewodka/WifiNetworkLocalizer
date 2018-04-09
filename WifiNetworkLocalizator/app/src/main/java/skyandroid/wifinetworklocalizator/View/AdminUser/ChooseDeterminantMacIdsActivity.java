package skyandroid.wifinetworklocalizator.View.AdminUser;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

import skyandroid.wifinetworklocalizator.Model.DataTypes.RoomInfo;
import skyandroid.wifinetworklocalizator.Model.HelperClasses.WifiDevicesDetails;
import skyandroid.wifinetworklocalizator.R;
import skyandroid.wifinetworklocalizator.View.AnonymousUser.ChoosingRoomActivity;
import skyandroid.wifinetworklocalizator.ViewModel.AdminClientViewModel;

/**
 * Created by skywatcher_usr on 2018-04-09.
 */

public class ChooseDeterminantMacIdsActivity extends AppCompatActivity {

    AdminClientViewModel viewModel;

    ListView possibleAccessPointsListView;
    Button confirmButton;
    Button refreshButton;

    int checkedMacIdsCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_macids);

        viewModel = new AdminClientViewModel(this);

        possibleAccessPointsListView = (ListView) findViewById(R.id.lvPossibleAccesPoints);
        confirmButton = (Button) findViewById(R.id.btnConfirmChoseMacIds);
        refreshButton = (Button) findViewById(R.id.btnRefreshChoseMacIds);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchPossibleAccessPoints();
            }
        });
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

    private void populatePossibleAccesPointsOnListView() {

        ArrayAdapter<WifiDevicesDetails> adapter
                = new ArrayAdapter<WifiDevicesDetails>(ChooseDeterminantMacIdsActivity.this, R.layout.scan_details, viewModel.possibleAccessPoints) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null)
                    convertView = getLayoutInflater().inflate(R.layout.scan_details, parent, false);

                WifiDevicesDetails currentItem = viewModel.possibleAccessPoints.get(position);

                TextView RSSI = (TextView) findViewById(R.id.txtRSSIValue);
                TextView BSSI = (TextView) findViewById(R.id.txtBSSIDValue);
                TextView SSID = (TextView) findViewById(R.id.txtSSIDValue);

                RSSI.setText(currentItem.RSSI);
                BSSI.setText(currentItem.BSSID);
                SSID.setText(currentItem.SSID);

                CheckBox chooseCheckBox = (CheckBox) findViewById(R.id.checkBoxMacIds);
                chooseCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked && checkedMacIdsCount >= 3) {
                            compoundButton.setChecked(false);
                            Toast.makeText(getApplicationContext(), "Można wybrać max 3 id!", Toast.LENGTH_SHORT).show();
                        }
                        else if (isChecked && checkedMacIdsCount < 3) {
                            addMacIdCount();
                        }
                        else if (!isChecked)
                            subMacIdCount();
                    }
                });
                return convertView;
            }
        };
        possibleAccessPointsListView.setAdapter(adapter);
    }

    void addMacIdCount() {
        checkedMacIdsCount++;
        if(checkedMacIdsCount>=3)
            confirmButton.setEnabled(false);
    }

    void subMacIdCount() {
        checkedMacIdsCount--;
        if (checkedMacIdsCount<3)
            confirmButton.setEnabled(true);
    }
}
