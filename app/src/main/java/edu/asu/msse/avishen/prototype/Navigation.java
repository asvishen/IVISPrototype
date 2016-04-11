package edu.asu.msse.avishen.prototype;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Navigation extends Activity {
    String place;
    private static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent1 = getIntent();
        getActionBar().hide();
        place=intent1.getStringExtra("place");
        super.onCreate(savedInstanceState);
        final TTSManager ttsManager = new TTSManager();

        ttsManager.init(this);
        ttsManager.initQueue("tell me the number to dial");
        if(place!=null) {


            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/dir/Current+Location/" + place));
            startActivity(intent);
        }
        else{
            setContentView(R.layout.activity_navigation);
//            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                    Uri.parse("https://www.google.com/maps" ));
//            startActivity(intent);
        }
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt =(EditText) findViewById(R.id.editText);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/dir/Current+Location/" +txt.getText()));
                startActivity(intent);
            }
        });
        final ListView listView = (ListView) findViewById(R.id.listView);
        final String[] values = new String[] { "Home",
                "Work",
                "ASU Poly",
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.mylist, values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index

                String place="";
                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);
                switch (position){
                    case 0: place = "Palo+Verde+Main";
                        break;
                    case 1: place = "ASU+Brickyard";
                            break;
                    case 2: place = "ASU+Poly";
                        break;
                }
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/dir/Current+Location/" +place));
                startActivity(intent);

                //setContentView(R.layout.activity_update_balance);


                // Show Alert


            }

        });
        Button speakButton = (Button) findViewById(R.id.speakButton);


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
        ImageButton img = (ImageButton) findViewById(R.id.homebot);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newI = new Intent(Navigation.this, MainActivity.class);
                startActivity(newI);
            }
        });

    }
    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Tell me where do you want to go?");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//            resultList.setAdapter(new ArrayAdapter<String>(this,
//                    android.R.layout.simple_list_item_1, matches));
            Intent intent;
            String place="";
            String text= matches.get(0);
                if(text.contains("home") ||text.contains("work")  || text.contains("asu") || text.contains("poly") ) {
                    switch (text) {
                        case "home":
                            place = "Palo+Verde+Main";
                            break;
                        case "work":
                            place = "ASU+Brickyard";

                            break;
                        case "asu":

                        case "poly":
                            place = "ASU+Poly";
                            break;

                    }
                    intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/maps/dir/Current+Location/" +place));
                    startActivity(intent);
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
                else if(text.contains("ac") || text.contains("aircon") || text.contains("air conditioner") )
                {
                    intent = new Intent(this,AirCon.class);
                    startActivity(intent);
                }
                else if(text.contains("car") || text.contains("controls")){

                    intent = new Intent(this,CarControl.class);
                    startActivity(intent);
                }else if(text.contains("settings") || text.contains("setting")){

                    intent = new Intent(this,Settings.class);
                    startActivity(intent);
                }
                else if(text.contains("homepage") || text.contains("page")){

                    intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                }
                else{

                final EditText dest = (EditText) findViewById(R.id.editText);
                dest.setText(text);
                Button btn = (Button) findViewById(R.id.button);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent finalintent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("https://www.google.com/maps/dir/Current+Location/" + dest.getText()));
                        startActivity(finalintent);
                    }
                });




//                String[] parts = text.split(" ");
//                place="";
//                for(int i=2;i< parts.length;i++){
//                    place+=parts[i]+" ";
//                }
//                intent = new Intent(android.content.Intent.ACTION_VIEW,
//                        Uri.parse("https://www.google.com/maps/dir/Current+Location/" + place));
//                startActivity(intent);
                 }



            }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
