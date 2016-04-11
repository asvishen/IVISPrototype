package edu.asu.msse.avishen.prototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TextToSpeech extends Activity {

    private Button speakNowButton;
    private EditText editText;
    TTSManager ttsManager = null;
    Intent intent;
    String x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);
        intent = getIntent();
        x = intent.getStringExtra("key");
        System.out.println("In activity 2 " + x);
        ttsManager = new TTSManager();
        ttsManager.init(this);
        ttsManager.initQueue(x);
        speak();
////        String text = x;
////        ttsManager.initQueue(text);
//
//        editText = (EditText) findViewById(R.id.input_text);
//        speakNowButton = (Button) findViewById(R.id.speak_now);
//        //speak();
//        speakNowButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                String text = editText.getText().toString();
////                     text = x;
//                ttsManager.initQueue(text);
//                speak();
//            }
//        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_text_to_speech, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ttsManager.shutDown();
    }
    public void speak(){
        String text = x;
        ttsManager.initQueue(text);
    }
}