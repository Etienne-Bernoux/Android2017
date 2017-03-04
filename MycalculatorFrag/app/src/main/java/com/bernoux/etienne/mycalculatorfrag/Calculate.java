package com.bernoux.etienne.mycalculatorfrag;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.concurrent.Semaphore;

public class Calculate extends AppCompatActivity implements View.OnClickListener{
    private Handler handler;
    private Semaphore semaphore;
    private TextView value;
    final static private  String TAG="calculate";

    private Button buttonPlus;
    private Button buttonMinus;
    private Button buttonTimes;
    private Button buttonObelus;
    private Button buttonDot;
    private Button buttonEqual;
    private GridLayout grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        handler = new Handler();
        value = (TextView) findViewById(R.id.numberEntry);
        buttonPlus = (Button) findViewById(R.id.buttonPlus);
        buttonMinus = (Button) findViewById(R.id.buttonMinus);
        buttonTimes = (Button) findViewById(R.id.buttonTimes);
        buttonObelus = (Button) findViewById(R.id.buttonObelus);
        buttonDot = (Button) findViewById(R.id.buttonDot);
        buttonEqual = (Button) findViewById(R.id.buttonEqual);
        semaphore = new Semaphore(1);

        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout);
        int childCount = grid.getChildCount();

        for (int i = 0; i < childCount; i++) {
            Button button = (Button) grid.getChildAt(i);
            button.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        Button button;
        button = (Button)view;
        String lastChar = StringGetLast(value.getText().toString());
        String lastWord = getLastWord(value.getText().toString());
        //System.out.println(getStringBouton(button));
        //System.out.println(lastChar);
        //System.out.println(lastWord);

        switch (button.getId()){
            case R.id.buttonCancel:
                cancel();
                break;
            case R.id.buttonReturn:
                back();
                break;
            case R.id.buttonMinus:
                if(  lastChar.contains("-") ){
                    back();
                }
                if(lastWord.length()<2 && lastChar.contains("0")) {
                    back();
                }
                setValue(getStringBouton(button));
                break;
            case R.id.buttonPlus:
            case R.id.buttonTimes:
            case R.id.buttonObelus:
                if(value.getText().toString().isEmpty()) break;
                if( lastChar.contains("+") || lastChar.contains("-") || lastChar.contains("×") || lastChar.contains("÷") ){
                    back();
                }
                setValue(getStringBouton(button));
                break;
            case R.id.buttonDot:
                if(!lastWord.contains(".")){
                    setValue(".");
                }
                break;
            case R.id.buttonEqual:
                if( lastChar.contains("+") || lastChar.contains("-") || lastChar.contains("×") || lastChar.contains("÷") ){
                    break;
                }
                docalc();
                break;
            default:
                if(lastWord.length()<2 && lastChar.contains("0")) {
                    back();
                }
                setValue(getStringBouton(button));
        }
        //tempValue = Double.parseDouble(value.getText().toString());
    }



    public void docalc() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                buttonEqual.setEnabled(false);
            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    //We acquire a semaphore to prevent multi click
                    semaphore.acquire();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String calcul = value.getText().toString();
                if(calcul.charAt(0) == '-'){
                  char[] mychar = calcul.toCharArray();
                    mychar[0] = 'm';
                    calcul = String.copyValueOf(mychar);
                }
                calcul = calcul.replace("+-","+m");
                calcul = calcul.replace("+","/+/");
                calcul = calcul.replace("×-","×m");
                calcul = calcul.replace("×","/×/");
                calcul = calcul.replace("÷-","÷m");
                calcul = calcul.replace("÷","/÷/");
                calcul = calcul.replace("-","/-/");
                calcul = calcul.replace("m","-");
                System.out.println(calcul);
                String[] temp = calcul.split("/");
                double res = 0;
                String tempOperator="";

                for (int i=0;i<temp.length;i++) {
                    if(i==0){
                        res= Double.parseDouble(temp[i]);
                    }
                    else if(i%2==1){
                        tempOperator = temp[i];
                    }
                    else if(i%2==0){
                        switch(tempOperator){
                            case "+":
                                res = res + Double.parseDouble(temp[i]);
                                break;
                            case "-":
                                res = res - Double.parseDouble(temp[i]);
                                break;
                            case "×":
                                res = res * Double.parseDouble(temp[i]);
                                break;
                            case "÷":
                                res = res / Double.parseDouble(temp[i]);
                                if(Double.isNaN(res) && Double.isInfinite(res)){
                                    res = 0;
                                }
                                break;
                        }
                    }
                }

                setTextValue("" + res);
                //We release the semaphore
                semaphore.release();
                //We set the button to enabled with an handler(out of our thread
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        buttonEqual.setEnabled(true);
                    }
                });

                final double finalRes = res;




            };

        };
        Thread toto = new Thread(runnable);
        toto.start();





    }

    public void cancel(){
        setTextValue("");
    }
    public void back(){
        String temp = this.value.getText().toString();
        if(temp.length()>0){
            temp = temp.substring(0,temp.length()-1);
        }
        setTextValue(temp);
    }



    /**
     *
     *
     */
    public String StringGetLast(String string){
        if(string.length()<1){
            Log.i(TAG, "StringGetLast: There are no value");
            return "";
        }
        return String.valueOf(string.charAt(string.length()-1));
    }
    public String getLastWord(String string){
        string = string.replace("+","/");
        string = string.replace("×","/");
        string = string.replace("÷","/");
        System.out.println(string);
        String[] temp = string.split("/");
       return temp [temp.length-1];
    }
    public void setTextValue(final String text){
        handler.post(new Runnable() {
            @Override
            public void run() {
                //The progress bar is set to is new value
                value.setText(text);
            }
        });
    }
    public void setValue(final String val){
        handler.post(new Runnable() {
            @Override
            public void run() {
                value.append(val);
            }
        });
    }
    public String getStringBouton(Button button){
        return (String)button.getText();
    }

}
