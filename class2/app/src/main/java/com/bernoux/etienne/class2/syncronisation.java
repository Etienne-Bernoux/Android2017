package com.bernoux.etienne.class2;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import android.os.Handler;
import android.widget.TextView;

public class syncronisation extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private Handler h;
    private TextView t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syncronisation);
        h = new Handler();
        t = (TextView) findViewById(R.id.textView2);

        new Thread(new Runnable() {
            @Override
            public void run() {

                h.post(new Runnable() {

                    @Override
                    public void run() {
                        t.setText("toto");
                    }
                });

            }}).start();
    }

}
