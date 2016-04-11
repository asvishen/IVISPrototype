package edu.asu.msse.avishen.prototype;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Dialer extends Activity {
    EditText edittext;

    Button speakButton;
    Button button,button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,
            button11;
    private static final int REQUEST_CODE = 1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //startVoiceRecognitionActivity();
        setContentView(R.layout.activity_dialer);
        getActionBar().hide();
        edittext = (EditText) findViewById(R.id.edittext);
        //text.setText("Display this text");
        button = (Button) findViewById(R.id.one);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edittext.append("1");
            }
        });
        button1 = (Button) findViewById(R.id.two);
        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                edittext.append("2");
            }
        });

        button2 = (Button) findViewById(R.id.three);
        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                edittext.append("3");
            }
        });

        button3 = (Button) findViewById(R.id.four);
        button3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                edittext.append("4");
            }
        });

        button4 = (Button) findViewById(R.id.five);
        button4.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                edittext.append("5");
            }
        });

        button5 = (Button) findViewById(R.id.six);
        button5.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                edittext.append("6");
            }
        });

        button6 = (Button) findViewById(R.id.seven);
        button6.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                edittext.append("7");
            }
        });

        button7 = (Button) findViewById(R.id.eight);
        button7.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                edittext.append("8");
            }
        });

        button8 = (Button) findViewById(R.id.nine);
        button8.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                edittext.append("9");
            }
        });

        button9 = (Button) findViewById(R.id.pound);
        button9.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                edittext.append("#");
            }
        });

        button10 = (Button) findViewById(R.id.star);
        button10.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                edittext.append("*");
            }
        });

        button11 = (Button) findViewById(R.id.zero);
        button11.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                edittext.append("0");
            }
        });

        final TTSManager ttsManager = new TTSManager();
        ttsManager.init(this);

        speakButton =(Button) findViewById(R.id.speakButton);

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
                Intent newI = new Intent(Dialer.this, MainActivity.class);
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
            // for(String text: matches){
            String text = matches.get(0);
            if(text.contains("1") || text.contains("one") ){

                System.out.println("String is"+text);
                edittext.append("1");
            }
            if(text.contains("2") || text.contains("two") || text.contains("to") ){

                System.out.println("String is"+text);
                edittext.append("2");
            }
            if(text.contains("3") || text.contains("three") ){

                System.out.println("String is"+text);
                edittext.append("3");
            }
            if(text.contains("4") || text.contains("four") ){

                System.out.println("String is"+text);
                edittext.append("4");
            }

            if(text.contains("5") || text.contains("five") ){

                System.out.println("String is"+text);
                edittext.append("5");
            }

            if(text.contains("6") || text.contains("six") ){

                System.out.println("String is"+text);
                edittext.append("6");
            }

            if(text.contains("7") || text.contains("seven") ){

                System.out.println("String is"+text);
                edittext.append("7");
            }

            if(text.contains("8") || text.contains("eight") ){

                System.out.println("String is"+text);
                edittext.append("8");
            }

            if(text.contains("9") || text.contains("nine") ){

                System.out.println("String is"+text);
                edittext.append("1");
            }

            if(text.contains("0") || text.contains("zero") ){

                System.out.println("String is"+text);
                edittext.append("0");
            }

            if(text.contains("delete")  ){

                String x = edittext.getText().toString();
                System.out.println("String is"+x);
                x= x.substring(0,x.length()-1);
                edittext.setText(x);
            }

            if(text.contains("clear")){
                String x = edittext.getText().toString();
                System.out.println("String is"+x);
                x= "";
                edittext.setText(x);
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void backSpace(View v){

        String x = edittext.getText().toString();
        System.out.println("String is"+x);
        x= x.substring(0,x.length()-1);
        edittext.setText(x);


    }

//    public void callingMethod(View V){
//        System.out.println("In callling method");
//
//        Intent intent = new Intent(this,Hello.class);
//        intent.putExtra("callnumber",edittext.getText().toString());
//        startActivity(intent);
//    }


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
