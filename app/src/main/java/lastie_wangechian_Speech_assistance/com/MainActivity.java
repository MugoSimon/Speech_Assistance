package lastie_wangechian_Speech_assistance.com;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textView_voiceInput;
    private ImageView micButton;
    private final int REQ_CODE_VOICE_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_voiceInput = findViewById(R.id.textView);
        micButton = findViewById(R.id.btn_mic);

        micButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    textView_voiceInput.setText(null);
                    getVoiceInput();

                } catch (Exception e) {

                    Toast.makeText(MainActivity.this, "onclick isn't working: " + e.getMessage().trim(), Toast.LENGTH_LONG).show();
                    ;
                }
            }
        });
    }

    /*
    here you prompt the google voice assistant
    trigger also the recognizer_intent

     */
    private void getVoiceInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "unasema nini wewe??");

        try {
            startActivityForResult(intent, REQ_CODE_VOICE_INPUT);

        } catch (ActivityNotFoundException e) {

            Toast.makeText(MainActivity.this, "Activity not found: " + e.getMessage().trim(), Toast.LENGTH_LONG).show();
            ;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_VOICE_INPUT: {

                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textView_voiceInput.setText(result.get(0));
                }
                break;
            }
        }
    }
}
