package com.bernoux.etienne.mycalculatorfrag;

import android.app.SearchManager;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Browse extends AppCompatActivity implements View.OnClickListener {
    private Intent intent;
    private TextView result;
    private TextView url;
    private Button buttonSubmit;
    private Button buttonFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        result = (TextView) findViewById(R.id.result);
        url = (TextView) findViewById(R.id.url);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);

        buttonFragment = (Button) findViewById(R.id.buttonFragment);
        buttonFragment.setOnClickListener(this);

        intent = getIntent();
        double res = 0;
        if(intent !=null){
            res=intent.getDoubleExtra("result",-1);
            result.setText(""+res);
        }
    }

    @Override
    public void onClick(View view) {
        System.out.println(view.getId());
        switch (view.getId()){

            case R.id.buttonSubmit:
                System.out.println("submit");
                String query = url.getText().toString();
                Intent intent1 = new Intent(Intent.ACTION_WEB_SEARCH);
                intent1.putExtra(SearchManager.QUERY, query);
                startActivity(intent1);
                break;

            case R.id.buttonFragment:
                System.out.println("activity");
                Intent intent2 = new Intent(Browse.this, Fragment.class);
                startActivity(intent2);
                break;


            default:
                System.out.println("default");
        }

    }
}
