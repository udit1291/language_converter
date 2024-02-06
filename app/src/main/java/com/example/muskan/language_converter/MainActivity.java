package com.example.muskan.language_converter;

import android.content.Intent;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech mtts;
    private Button btn1,btn3;
    private TextView btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1=(Button)findViewById(R.id.button1);
        btn2=(TextView) findViewById(R.id.button2);
        btn3=(Button)findViewById(R.id.button3);

        mtts= new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS){
                    int result=mtts.setLanguage(Locale.ENGLISH);
                    if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED) {
//                        Toast.makeText(this,"TTS",Toast.LENGTH_SHORT).show();123456test12712832487
                    }else{
                        btn3.setEnabled(true);
                    }
                }else{
                    //Toast.makeText(this,"TTS",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpeechInput(v);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=btn2.getText().toString();
                mtts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
    }

    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void getSpeechInput(View view){
        Intent intent= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,10);
        }else{
            Toast.makeText(this,"your device does not support",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch(requestCode){
            case 10:
                if (resultCode==RESULT_OK && data!=null){
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    btn2.setText(result.get(0));

                }
                break;
        }
    }
}
