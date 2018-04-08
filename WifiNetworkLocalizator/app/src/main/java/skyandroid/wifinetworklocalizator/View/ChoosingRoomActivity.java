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

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import skyandroid.wifinetworklocalizator.Model.DataTypes.RoomInfo;
import skyandroid.wifinetworklocalizator.R;
import skyandroid.wifinetworklocalizator.ViewModel.AnonymousClientViewModel;

/**
 * Created by skywatcher_usr on 2018-04-08.
 */

public class ChoosingRoomActivity extends AppCompatActivity {

    AnonymousClientViewModel viewModel
            = new AnonymousClientViewModel(this);

    ListView possibleRoomsListView;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_room);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    viewModel.fetchPossibleRooms();
                    populatePossibleRoomsOnListView();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.doInBackground();
    }

    private void populatePossibleRoomsOnListView() {

        ArrayAdapter<RoomInfo> adapter
                = new ArrayAdapter<RoomInfo>(this, R.layout.room_info, viewModel.possibleRooms) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                if (convertView == null)
                    convertView = getLayoutInflater().inflate(R.layout.room_info, parent, false);

                RoomInfo currentIterm = viewModel.possibleRooms.get(position);
                TextView roomName = (TextView) convertView.findViewById(R.id.txtRoomName);

                return convertView;
            }
        };
        possibleRoomsListView.setAdapter(adapter);
    }
}
