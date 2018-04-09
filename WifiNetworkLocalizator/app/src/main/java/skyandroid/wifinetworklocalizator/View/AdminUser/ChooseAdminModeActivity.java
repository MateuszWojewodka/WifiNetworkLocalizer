package skyandroid.wifinetworklocalizator.View.AdminUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import skyandroid.wifinetworklocalizator.R;
import skyandroid.wifinetworklocalizator.View.AnonymousUser.ChoosingRoomActivity;
import skyandroid.wifinetworklocalizator.ViewModel.AdminClientViewModel;
import skyandroid.wifinetworklocalizator.ViewModel.AnonymousClientViewModel;

/**
 * Created by skywatcher_usr on 2018-04-09.
 */

public class ChooseAdminModeActivity extends AppCompatActivity {

    AdminClientViewModel viewModel;

    EditText roomNameEditText;
    Button createNewRoomButton;
    Button useExistingRoomButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_mode);

        viewModel = new AdminClientViewModel(this);

        roomNameEditText = (EditText) findViewById(R.id.edtRoomNameAdminMode);

        createNewRoomButton = (Button) findViewById(R.id.btnAddNewRoomAdminMode);
        createNewRoomButton.setEnabled(false);

        useExistingRoomButton = (Button) findViewById(R.id.btnUseExsistingAdminMode);

        roomNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().equals(""))
                    createNewRoomButton.setEnabled(true);
            }
        });

        createNewRoomButton.setOnClickListener(getCreateRoomListener());
        useExistingRoomButton.setOnClickListener(getUseExistingRoomListener());
    }

    View.OnClickListener getCreateRoomListener(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (viewModel.checkIfRoomCanBeCreated(roomNameEditText.getText().toString())){
                        Intent i = new Intent(getApplicationContext(), ChoosingRoomActivity.class);
                        startActivity(i);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Dana nazwa jest juz w użyciu.", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    View.OnClickListener getUseExistingRoomListener(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
    }
}
