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
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

import skyandroid.wifinetworklocalizator.Model.DataTypes.RoomInfo;
import skyandroid.wifinetworklocalizator.R;
import skyandroid.wifinetworklocalizator.View.AnonymousUser.LocalizationInRoomActivity;
import skyandroid.wifinetworklocalizator.ViewModel.AnonymousClientViewModel;

/**
 * Created by skywatcher_usr on 2018-04-10.
 */

public class ChoosingRoomActivity extends AppCompatActivity {

    AnonymousClientViewModel viewModel;

    ListView possibleRoomsListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_room);

        viewModel = new AnonymousClientViewModel(this);

        possibleRoomsListView = (ListView) findViewById(R.id.lvPossibleRooms);
        possibleRoomsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), MeasurmentsActivity.class);
                i.putExtra("roomName", ((RoomInfo) adapterView.getItemAtPosition(position)).roomName);
                i.putExtra("roomId", Integer.toString(((RoomInfo) adapterView.getItemAtPosition(position)).roomId));
                startActivity(i);
            }
        });

        fetchPossibleRooms();
    }

    @SuppressLint("StaticFieldLeak")
    private void fetchPossibleRooms() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    viewModel.fetchPossibleRooms();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                populatePossibleRoomsOnListView();
            }
        }.execute();
    }

    private void populatePossibleRoomsOnListView() {

        ArrayAdapter<RoomInfo> adapter
                = new ArrayAdapter<RoomInfo>(ChoosingRoomActivity.this, R.layout.room_info, viewModel.possibleRooms) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null)
                    convertView = getLayoutInflater().inflate(R.layout.room_info, parent, false);

                RoomInfo currentItem = viewModel.possibleRooms.get(position);
                TextView roomName = (TextView) convertView.findViewById(R.id.txtRoomName);
                roomName.setText(currentItem.roomName);

                return convertView;
            }
        };
        possibleRoomsListView.setAdapter(adapter);
    }
}
