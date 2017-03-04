package com.bernoux.etienne.mycalculatorfrag;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import layout.Fragment1;

/**
 * Created by etien on 21/02/2017.
 */

public class Fragment extends AppCompatActivity implements Fragment1.OnFragmentInteractionListener {

    private TextView textView1;
    private TextView textView2;
    private Fragment1 frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        textView1 = (TextView) findViewById(R.id.textViewFrag1);
        textView2 = (TextView) findViewById(R.id.textViewFrag2);

        frag = Fragment1.newInstance();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment1,frag).commit();
    }

    @Override
    public void onFragmentInteraction(Object object) {
        System.out.println("Interaction");

    }
}
