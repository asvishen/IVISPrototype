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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.ArrayList;
import java.util.List;


public class AirCon extends Activity {

    EditText temperaturetext;
    Intent intent;
    Button speakButton;
    private static final int REQUEST_CODE = 1234;
    RadioGroup rg1,rg2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_air_con);
        ((RadioGroup) findViewById(R.id.toggleGroup)).setOnCheckedChangeListener(ToggleListener);
        ((RadioGroup) findViewById(R.id.toggleGroup2)).setOnCheckedChangeListener(ToggleListener2);
        temperaturetext = (EditText)findViewById(R.id.textViewTemperature);
        rg1 = (RadioGroup)findViewById(R.id.toggleGroup);
        rg2 = (RadioGroup)findViewById(R.id.toggleGroup2);
        System.out.println("group 1 at oncreate is "+rg2);
        intent = getIntent();
        String temp = intent.getStringExtra("tempe");
        if (temp!=null)
        {
            temp = temp.substring(0,2);
            int x = Integer.parseInt(temp);
            if (x>80){temperaturetext.setText("80");
            }
            else if(x<60)
            {
                temperaturetext.setText("60");
            }
            else {
                temperaturetext.setText(temp);
            }
        }
        else{
            temperaturetext.setText("");
        }
        speakButton = (Button) findViewById(R.id.speakButton);
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
        Button img = (Button) findViewById(R.id.homeButton);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newI = new Intent(AirCon.this, MainActivity.class);
                startActivity(newI);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_air_con, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    static final RadioGroup.OnCheckedChangeListener ToggleListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(final RadioGroup radioGroup, final int i) {
            for (int j = 0; j < radioGroup.getChildCount(); j++) {
                final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
                view.setChecked(view.getId() == i);


            }

        }
    };
    static final RadioGroup.OnCheckedChangeListener ToggleListener2 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(final RadioGroup radioGroup, final int i) {
            for (int j = 0; j < radioGroup.getChildCount(); j++) {
                final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
                view.setChecked(view.getId() == i);
                view.setEnabled(true);
            }

        }

    };
    public void onToggle(View view) {
        ((RadioGroup)view.getParent()).check(view.getId());

    }

    public void plusCalled(View v)
    {
        String temp = temperaturetext.getText().toString();
        int tem = Integer.parseInt(temp);
        if(tem<80){
            tem +=1;
            temperaturetext.setText(String.valueOf(tem));}
    }
    public void minusCalled(View v)
    {
        String temp = temperaturetext.getText().toString();
        int tem = Integer.parseInt(temp);
        if(tem>60){
            tem -=1;
            temperaturetext.setText(String.valueOf(tem));
            System.out.println(tem);}
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

            Intent intent;

            String text = matches.get(0);
            if(text.contains("set") )
            {
                System.out.println("in set temperature");
                String[] parts = text.split(" ");
                String temp="";
                for(int i=1;i< parts.length;i++){
                    temp+=parts[i];
                }
                System.out.println(temp);
                temperaturetext.setText(temp);
            }
            else if(text.contains("direction") )
            {
                System.out.println("in direction");
                String[] parts = text.split(" ");
                String temp="";
                for(int i=1;i<2;i++){
                    temp+=parts[i];
                }
                System.out.println(temp);
                if(temp.contains("0")||temp.contains("1")||temp.contains("2")||temp.contains("3")||temp.contains("4"))
                {
                    int dir  = Integer.parseInt(temp);
                    for (int j = 0; j < rg1.getChildCount(); j++) {

                        final ToggleButton view = (ToggleButton) rg1.getChildAt(j);
                        System.out.println(rg1);
                        System.out.println(view);
                        view.setChecked(view.getId() == dir);
                    }

                }
            }
            else if(text.contains("speed")||text.contains("fan") )
            {
                System.out.println("in speed");
                String[] parts = text.split(" ");
                String temp="";
                for(int i=1;i<2;i++){
                    temp+=parts[i];

                }
                System.out.println(temp);
                if(temp.contains("0")||temp.contains("1")||temp.contains("2")||temp.contains("3")||temp.contains("4"))
                {
                    int speed = Integer.parseInt(temp);
                    for (int j = 0; j < rg1.getChildCount(); j++) {
                        final ToggleButton view = (ToggleButton) rg2.getChildAt(j);
                        System.out.println(rg2);
                        System.out.println(view);
                        view.setChecked(view.getId() == speed);
                    }
                }


            }
            else if(text.contains("home") || text.contains("main")  )
            {

                intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            }
            else {
                System.out.println(text);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}