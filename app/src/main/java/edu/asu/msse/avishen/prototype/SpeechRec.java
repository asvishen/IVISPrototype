package edu.asu.msse.avishen.prototype;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SpeechRec extends Activity {

    private static final int REQUEST_CODE = 1234;
    private ListView resultList;
    Button speakButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_rec);

        speakButton = (Button) findViewById(R.id.speakButton);

        resultList = (ListView) findViewById(R.id.list);

        // Disable button if no recognition service is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            speakButton.setEnabled(false);
            Context context = getApplicationContext();
            CharSequence text = "Hello toast!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        speakButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognitionActivity();
            }
        });
    }

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "AndroidBite Voice Recognition...");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//            resultList.setAdapter(new ArrayAdapter<String>(this,
//                    android.R.layout.simple_list_item_1, matches));
            Intent intent;
            for(String text: matches){
                android.util.Log.w("Main Activity","THis is the text gathered................"+text);
                if(text.contains("Music") ||text.contains("Navigation") || text.contains("Take me to") || text.contains("Directions"))
                {
                    intent = new Intent(this,Navigation.class);
                    startActivity(intent);
                }
                else if(text.contains("Navigate") ||text.contains("Play") )
                {
                    intent = new Intent(this,Music.class);
                    startActivity(intent);
                }
                else if(text.contains("Phone") || text.contains("Dial") || text.contains("Call") )
                {
                     intent = new Intent(this,Dialer.class);
                    startActivity(intent);
                }
                else if(text.contains("AC") || text.contains("Aircon") || text.contains("Air Conditioner") )
                {
                   intent = new Intent(this,AirCon.class);
                    startActivity(intent);
                }
                else if(text.contains("Car") || text.contains("Controls")){

                    intent = new Intent(this,CarControl.class);
                    startActivity(intent);
                }else if(text.contains("Settings") || text.contains("Setting")){

                    intent = new Intent(this,Settings.class);
                    startActivity(intent);
                }



            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
