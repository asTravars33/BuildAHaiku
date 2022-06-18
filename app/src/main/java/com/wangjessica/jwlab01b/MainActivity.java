package com.wangjessica.jwlab01b;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    String[] parts = {"Of ", " and ", "So ", " and so ", "We are "};
    String[][] options = {
            {"dust", "light", "air"},
            {"shadow", "starlight", "soil"},
            {"brilliant", "comforting", "unchanging"},
            {"pale", "round", "full"},
            {"the dreamers", "believers", "the sleepers"}
    };
    int idx = -1; // which part should be displayed next
    RadioButton clicked; // most recently selected radio button
    String haiku=""; // aggregate constructed haiku
    TextToSpeech speaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speaker = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                speaker.setLanguage(Locale.US);
            }
        });
    }

    public void optionClicked(View view) {
        clicked = (RadioButton) view;
    }

    public void buildLine(View view) {
        // update the haiku so far
        if(idx==-1){
            idx++;
        }
        else {
            haiku += (Character.isUpperCase(parts[idx].charAt(0)))?"\n"+parts[idx++]:parts[idx++];
            haiku += clicked.getText().toString();
        }

        // display the next set of options
        TextView text = findViewById(R.id.prompt);
        RadioGroup group = findViewById(R.id.text_options);

        if(idx>=parts.length){
            // haiku has been completed
            haiku = "Moon Haiku \n\n"+haiku;
            text.setText(haiku);
            group.setVisibility(View.GONE);
            Button btn = findViewById(R.id.send_button);
            btn.setVisibility(View.GONE);
            speaker.speak(haiku, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else{
            text.setText(parts[idx]);
            for(int i=0; i<3; i++){
                RadioButton cur = (RadioButton) group.getChildAt(i);
                cur.setText(options[idx][i]);
            }
        }
    }
}