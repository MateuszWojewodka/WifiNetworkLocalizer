package skyandroid.wifinetworklocalizator.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ChoosingRoomActivity.class);
                startActivity(i);
            }
        });
    }

}
