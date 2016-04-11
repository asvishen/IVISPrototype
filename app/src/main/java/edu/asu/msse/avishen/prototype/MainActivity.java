package edu.asu.msse.avishen.prototype;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    GridView gridView;
    private static final int REQUEST_CODE = 1234;
    Button speakButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().hide();
        gridView = (GridView) findViewById(R.id.gridview);
        final TTSManager ttsManager = new TTSManager();

        ttsManager.init(this);


        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent;
                switch(position){
                    case 0:
                            intent = new Intent(MainActivity.this, Dialer.class);

                            ttsManager.initQueue("tell me the number to dial");


                        intent = new Intent(MainActivity.this,Dialer.class);
                             startActivity(intent);
                             break;
                    case 1:
                            intent = new Intent(MainActivity.this,Navigation.class);
                            ttsManager.initQueue("You can choose from favorites or tell me where you want to go?");
                            startActivity(intent);
//                            intent = new Intent(android.content.Intent.ACTION_VIEW,
//                                Uri.parse("https://www.google.com/maps" ));
                            break;
                    case 2:  intent = new Intent(MainActivity.this,Music.class);
                            startActivity(intent);
                            break;
                    case 3:  intent = new Intent(MainActivity.this,AirCon.class);
                            startActivity(intent);
                            break;
                    case 4:

                            break;
                    case 5:  intent = new Intent(MainActivity.this,CarControl.class);
                            startActivity(intent);
                            break;

                }

            }
        });
        speakButton = (Button) findViewById(R.id.speakButton);


        // Disable button if no recognition service is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            speakButton.setEnabled(false);
        }
        speakButton.setOnClickListener(new View.OnClickListener() {
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
                "Tell me what to do?");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            final TTSManager ttsManager = new TTSManager();

            ttsManager.init(this);

//            resultList.setAdapter(new ArrayAdapter<String>(this,
//                    android.R.layout.simple_list_item_1, matches));
            Intent intent;
            String text = matches.get(0);
//            for(String text: matches){
                if(text.contains("route") ||text.contains("navigate")  || text.contains("directions") || text.contains("navigation") )
                {
                    if(text.contains("to")){


                    String[] parts = text.split(" ");
                    String place="";
                    for(int i=2;i< parts.length;i++){
                        place+=parts[i]+" ";
                    }
                    intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/maps/dir/Current+Location/" + place));
                    startActivity(intent);}
                    else{
                        intent = new Intent(MainActivity.this,Navigation.class);
                        ttsManager.initQueue("You can choose from favorites or tell me where you want to go?");
                        startActivity(intent);
                    }
                }
                else if(text.contains("music") ||text.contains("play") )
                {
                    intent = new Intent(this,Music.class);
                    startActivity(intent);
                }
                else if(text.contains("phone") || text.contains("dial") || text.contains("call") )
                {
                    intent = new Intent(this,Dialer.class);
                    startActivity(intent);
                }
                else if(text.contains("ac") || text.contains("aircon") || text.contains("conditioner")||text.contains("temperature") ) {

                    if (text.contains("set")) {
                        //String t = text[0];
                        String[] parts = text.split(" ");
                        String temp = parts[parts.length - 1];
                        for (int i = 3; i < parts.length; i++) {
                            temp += parts[i] + " ";
                        }

                        intent = new Intent(this, AirCon.class);
                        intent.putExtra("tempe", temp);
                        startActivity(intent);
                    } else {
                        intent = new Intent(this, AirCon.class);
                        startActivity(intent);
                    }
                }else if(text.contains("car") || text.contains("controls")){

                    intent = new Intent(this,CarControl.class);
                    startActivity(intent);
                }else if(text.contains("settings") || text.contains("setting")){

//                    intent = new Intent(this,Settings.class);
//                    startActivity(intent);
                }



//            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
