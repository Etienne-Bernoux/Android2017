package com.bernoux.etienne.frandroideurodollar;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class ConvertirMonnaie extends AppCompatActivity {
    EditText text;
    RadioButton euroButton;
    RadioButton dollarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.text = (EditText) findViewById(R.id.editText);
        this.euroButton = (RadioButton) findViewById(R.id.radioButtonEuro);
        this.dollarButton = (RadioButton) findViewById(R.id.radioButtonDollar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conversion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void myClickHandler(View view) {
        switch (view.getId()) {
            case R.id.button:
                if (this.text.getText().length() == 0) {
                    Toast.makeText(this, "Please enter a valid number",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                float inputValue = Float.parseFloat(this.text.getText().toString());
                if (this.euroButton.isChecked()) {
                    this.text.setText(String
                            .valueOf(convertDollarToEuro(inputValue)));
                    euroButton.setChecked(false);
                    dollarButton.setChecked(true);
                } else {
                    text.setText(String
                            .valueOf(convertEuroToDollar(inputValue)));
                    dollarButton.setChecked(false);
                    euroButton.setChecked(true);
                }
                break;
        }
    }
    // Convertir Dollar à Euro
    private float convertDollarToEuro(float dollar) {
        return (float) (dollar*0.938286); // formule à utiliser
    }
    // Convertir Euro à Dollar
    private float convertEuroToDollar(float euro) {
        return (float) (euro*1.06573); // formule à utiliser
    }
}

