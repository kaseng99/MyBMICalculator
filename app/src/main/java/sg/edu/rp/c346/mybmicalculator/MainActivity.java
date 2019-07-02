package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
TextView tvdate , tvbmi , tvOut;
    EditText etweight , etheight;
    Button btncalc , btnreset;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etweight = findViewById(R.id.etWeight);
        etheight = findViewById(R.id.etHeight);
        tvdate = findViewById(R.id.tvDate);
        tvbmi = findViewById(R.id.tvBmi);
        tvOut = findViewById(R.id.tvOutcome);
        btncalc = findViewById(R.id.btnCalc);
        btnreset = findViewById(R.id.btnReset);





        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            etheight.setText("");
            etweight.setText("");
            tvdate.setText("");
            tvbmi.setText("");
            }
        });

        btncalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                float weight = Float.parseFloat(etweight.getText().toString());
                float height = Float.parseFloat(etheight.getText().toString());
                float bmi = weight / (height * height);
                String output = "";
                if(bmi<18.5){
                    output="You are underweight";
                }else if(bmi>=18.5 && bmi <=24.9){
                    output="Your BMI is normal";
                }else if(bmi>=25 && bmi <=29.9){
                    output="You are overweight";
                }else{
                    output="You are obese";
                }

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.putString("Date" ,datetime);
                prefEdit.putFloat("BMI" ,bmi);
                prefEdit.putString("Output" ,output);

                prefEdit.commit();

                tvdate.setText(datetime);
                tvbmi.setText(String.format("%.3f",bmi));
                tvOut.setText(output);
                etheight.setText("");
                etweight.setText("");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        String date = prefs.getString("Date","No Date!");
        float bmi = prefs.getFloat("BMI",0);
        String output = prefs.getString("Output","No Output!");
        tvdate.setText(date);
        tvbmi.setText(String.format("%f",bmi));
        tvOut.setText(output);

    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        float weight = Float.parseFloat(etweight.getText().toString());
        float height = Float.parseFloat(etheight.getText().toString());
        prefEdit.putFloat("Weight" ,weight);
        prefEdit.putFloat("Height" ,height);
    }
}


