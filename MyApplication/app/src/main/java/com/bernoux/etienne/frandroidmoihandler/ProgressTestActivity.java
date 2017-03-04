package com.bernoux.etienne.frandroidmoihandler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import java.util.concurrent.Semaphore;


public class ProgressTestActivity extends AppCompatActivity {

        private Handler handler;
        private ProgressBar progress;
        private Button button;
        private Semaphore semaphore;
        private static final int MAX_AVAILABLE = 1;
        /** Appelé quand l’activité est créée en premier. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_progress_test);
            progress = (ProgressBar) findViewById(R.id.progressBar1);
            button = (Button) findViewById(R.id.button1);
            handler = new Handler();
            semaphore = new Semaphore(MAX_AVAILABLE,true);
        }
        public void startProgress(View view) {
    // I create my runnable thread

            //We set the button to unclickable
            button.setEnabled(false);

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        //We acquire a semaphore to prevent multi click
                        semaphore.acquire();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // We run a loop to increment value
                    for (int i = 0; i <= 10; i++) {
                        //We set value with i
                        final int value = i;
                        try {
                            //We wait to make the process look like heacy
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //Post method necessary for progress bar
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //The progress bar is set to is new value
                                progress.setProgress(value);
                            }
                        });

                    }
                    //We release the semaphore
                    semaphore.release();
                    //We set the button to enabled with an handler(out of our thread
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            button.setEnabled(true);
                        }
                    });

                }
            };
            //We start the thread
            new Thread(runnable).start();
        }
}
