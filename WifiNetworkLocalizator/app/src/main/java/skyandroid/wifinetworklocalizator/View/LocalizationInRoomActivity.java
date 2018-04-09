package skyandroid.wifinetworklocalizator.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import skyandroid.wifinetworklocalizator.R;
import skyandroid.wifinetworklocalizator.ViewModel.AnonymousClientViewModel;

/**
 * Created by skywatcher_usr on 2018-04-08.
 */

public class LocalizationInRoomActivity extends AppCompatActivity {

    AnonymousClientViewModel viewModel;

    Button getYourPositionButton;
    TextView roomNameText;
    TextView XpointText;
    TextView YPointText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localization_in_room);

        viewModel = new AnonymousClientViewModel(this);

        Bundle b = getIntent().getExtras();
        String roomName = b.getString("roomName");
        int roomId = Integer.parseInt(b.getString("roomId"));

        getYourPositionButton = (Button) findViewById(R.id.btnCheckLocalization);
        roomNameText = (TextView) findViewById(R.id.txtRoom);
        XpointText = (TextView) findViewById(R.id.txtPointX);
        YPointText = (TextView) findViewById(R.id.txtPointY);

        roomNameText.setText("dupa");
//        roomNameText.setText(roomName.toString());

        getYourPositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    viewModel.onClickedButtonGetYourXYPoint();
                    XpointText.setText(Integer.toString(viewModel.currentPoint.x));
                    YPointText.setText(Integer.toString(viewModel.currentPoint.y));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
