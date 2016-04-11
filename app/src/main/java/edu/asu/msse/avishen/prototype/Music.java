package edu.asu.msse.avishen.prototype;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;


public class Music extends Activity {

    private static final int REQUEST_CODE = 1234;

    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_music);
        Log.v(getString(R.string.app_name),"in Music onCreate");
        ((RadioGroup) findViewById(R.id.togglegroup)).setOnCheckedChangeListener(ToggleListener);
        rg = ((RadioGroup) findViewById(R.id.togglegroup));

        final TTSManager ttsManager = new TTSManager();
        ttsManager.init(this);

        Button speakButton;
        speakButton = (Button) findViewById(R.id.speakButton);


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
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognitionActivity();
            }
        });
        ImageButton img = (ImageButton) findViewById(R.id.homebot);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newI = new Intent(Music.this, MainActivity.class);
                startActivity(newI);
            }
        });



    }

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Tell me what to do?");
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
            String text = matches.get(0);
            if(text.contains("bos")||text.contains("bars")||text.contains("boss"))
            {
                int x = 0;
                final ToggleButton view = (ToggleButton) rg.getChildAt(x);
                view.setChecked(true);
                System.out.println(text);

            }
            else if(text.contains("play"))
            {
                int x = 0;
                final ToggleButton view = (ToggleButton) rg.getChildAt(x);
                view.setChecked(true);
                System.out.println(text);
            }
            else if(text.contains("home"))
            {
                Intent inte = new Intent(this,MainActivity.class);
                startActivity(inte);
                System.out.println("Home");

            }

//                if(text.contains("play") )
//                {
//                    int x = 0;
//                    final ToggleButton view = (ToggleButton) rg.getChildAt(x);
//                    view.setChecked(true);
//                }




        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music, menu);
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

    static final RadioGroup.OnCheckedChangeListener ToggleListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(final RadioGroup radioGroup, final int i) {
//            final ToggleButton view = (ToggleButton) radioGroup.getChildAt(i);
//               view.setChecked(true);
            for (int j = 0; j < radioGroup.getChildCount(); j++) {
                final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
                view.setChecked(view.getId() == i);
            }
        }
    };


    public void onToggle(View view) {
        ((RadioGroup)view.getParent()).check(view.getId());
    }

}
