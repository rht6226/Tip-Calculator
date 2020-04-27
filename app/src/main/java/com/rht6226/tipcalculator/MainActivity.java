package com.rht6226.tipcalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private AlertDialog.Builder dialogBuilder;
    private EditText billValueGetter;
    private RadioGroup currencyRadioGroup;
    private RadioButton radioButton;
    private TextView showTipPercentage;
    private SeekBar tipSeekBar;

    private float billAmount;
    private float tipPercentage;
    private String currency;
    private float result;
    private float total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.calculateBtn);
        billValueGetter = findViewById(R.id.billEditor);
        currencyRadioGroup = findViewById(R.id.radioGroup);
        showTipPercentage = findViewById(R.id.showTipPercentage);
        tipSeekBar = findViewById(R.id.tipSeeBar);

        radioButton = findViewById(R.id.rupeesRadio);
        radioButton.setChecked(true);
        currency = getResources().getString(R.string.currency_rupees);

        showTipPercentage.setText("0 %");

        currencyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = findViewById(checkedId);

                switch (checkedId) {
                    case R.id.rupeesRadio:
                        currency = getResources().getString(R.string.currency_rupees);
                        break;
                    case R.id.dollarRadio:
                        currency = getResources().getString(R.string.currency_dollars);
                        break;
                    case R.id.euroRadio:
                        currency = getResources().getString(R.string.currency_euros);
                        break;
                    case R.id.yenRadio:
                        currency = getResources().getString(R.string.currency_yen);
                        break;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Switched Currency : ");
                stringBuilder.append(currency);
                Toast.makeText(getApplicationContext(), stringBuilder, Toast.LENGTH_SHORT).show();
            }
        });

        tipSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipPercentage = tipSeekBar.getProgress();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(tipPercentage);
                stringBuilder.append(" %");
                showTipPercentage.setText(stringBuilder);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("Started Seeking", "Changing Value");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("Stopped seeking", "value established");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!billValueGetter.getText().toString().equals("")){
                    billAmount = Float.parseFloat(billValueGetter.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter a bill amount!", Toast.LENGTH_LONG).show();
                    return;
                }

                result = billAmount * tipPercentage / 100;
                total = billAmount + result;

                dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                dialogBuilder.setCancelable(false);
                dialogBuilder.setTitle("Tip Calculated");

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("\tBill Amount = ");
                stringBuilder.append(currency);
                stringBuilder.append(". ");
                stringBuilder.append(billAmount);
                stringBuilder.append("\n");
                stringBuilder.append("\tTip Amount = ");
                stringBuilder.append(currency);
                stringBuilder.append(". ");
                stringBuilder.append(result);
                stringBuilder.append("\n");
                stringBuilder.append("\tTotal Amount = ");
                stringBuilder.append(currency);
                stringBuilder.append(". ");
                stringBuilder.append(total);

                dialogBuilder.setMessage(stringBuilder);

                dialogBuilder.setNegativeButton("Calculate Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        billValueGetter.setText("");
                        dialog.cancel();
                    }
                });

                dialogBuilder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                });

                AlertDialog alert = dialogBuilder.create();
                alert.show();
            }
        });
    }
}
