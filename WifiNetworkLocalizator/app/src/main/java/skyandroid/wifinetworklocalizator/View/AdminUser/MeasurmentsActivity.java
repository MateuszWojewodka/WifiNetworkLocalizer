package skyandroid.wifinetworklocalizator.View.AdminUser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import skyandroid.wifinetworklocalizator.Model.DataTypes.Point;
import skyandroid.wifinetworklocalizator.R;
import skyandroid.wifinetworklocalizator.ViewModel.AdminClientViewModel;

/**
 * Created by skywatcher_usr on 2018-04-10.
 */

public class MeasurmentsActivity extends AppCompatActivity {

    AdminClientViewModel viewModel;

    Button measureButton;

    EditText pointXEditText;
    EditText pointYEditText;

    TextView firstMacIdTextView;
    TextView fecondMacIdTextView;
    TextView thirdMacIdTextView;

    TextView firstRSSITextView;
    TextView secondRSSITextView;
    TextView thirdRSSITextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurments);

        viewModel = new AdminClientViewModel(this);

        Bundle b = getIntent().getExtras();
        viewModel.roomName = b.getString("roomName");
        viewModel.roomId = b.getString("roomId") == null ? 0 : Integer.parseInt(b.getString("roomId"));

        measureButton = (Button) findViewById(R.id.btnDoMeasurment);

        pointXEditText = (EditText) findViewById(R.id.edtXPointMeasurment);
        pointYEditText = (EditText) findViewById(R.id.edtYPointMeasurment);

        firstRSSITextView = (TextView) findViewById(R.id.txtFirstRSSI);
        secondRSSITextView = (TextView) findViewById(R.id.txtSecondRSSI);
        thirdRSSITextView = (TextView) findViewById(R.id.txtThirdRSSI);

        pointXEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().equals("") && !pointYEditText.getText().toString().trim().equals(""))
                    measureButton.setEnabled(true);
                else measureButton.setEnabled(false);
            }
        });

        pointYEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().equals("") && !pointXEditText.getText().toString().trim().equals(""))
                    measureButton.setEnabled(true);
                else measureButton.setEnabled(false);
            }
        });

        measureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                measureButton.setEnabled(false);
                doMeasurment();
                Toast.makeText(getApplicationContext(), "Dokonywanie pomiaru...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void doMeasurment() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                Point point = new Point();
                point.x = Integer.parseInt(pointXEditText.getText().toString());
                point.y = Integer.parseInt(pointYEditText.getText().toString());

                try {
                    viewModel.doMeasurment(point);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                measureButton.setEnabled(true);
                firstRSSITextView.setText(viewModel.measurmentSignals.FirstRSSISignal);
                secondRSSITextView.setText(viewModel.measurmentSignals.SecondRSSISignal);
                thirdRSSITextView.setText(viewModel.measurmentSignals.ThirdRSSISignal);
                Toast.makeText(getApplicationContext(), "Pomiar wysłano do bazy danych.", Toast.LENGTH_SHORT).show();

                pointXEditText.setText("");
                pointYEditText.setText("");
            }
        }.execute();
    }
}
