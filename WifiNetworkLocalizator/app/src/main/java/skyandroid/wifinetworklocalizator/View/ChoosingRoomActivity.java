package skyandroid.wifinetworklocalizator.View;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import skyandroid.wifinetworklocalizator.Model.DataTypes.RoomInfo;
import skyandroid.wifinetworklocalizator.R;
import skyandroid.wifinetworklocalizator.ViewModel.AnonymousClientViewModel;

/**
 * Created by skywatcher_usr on 2018-04-08.
 */

public class ChoosingRoomActivity extends AppCompatActivity {

    AnonymousClientViewModel viewModel;

    ListView possibleRoomsListView;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_room);

        viewModel = new AnonymousClientViewModel(this);
        possibleRoomsListView = (ListView) findViewById(R.id.lvPossibleRooms);

        final List<String> jakasLista = new ArrayList<>();
        jakasLista.add("jeden");
        jakasLista.add("dwa");
        jakasLista.add("trzy");

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
