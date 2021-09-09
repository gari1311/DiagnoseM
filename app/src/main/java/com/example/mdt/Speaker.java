package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Speaker extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    private Button mButton;
    private boolean isPlayingOnSpeaker = false;
    Button btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);
        btn5 = (Button)findViewById(R.id.btn5);
        AudioManager mAudioManager;
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        //mAudioManager.setMode(AudioManager.MODE_IN_CALL);
        mAudioManager.setSpeakerphoneOn(true);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.audio);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Intent intent = new Intent(Speaker.this, Testspeaker.class);
                startActivity(intent);
            }
        });


    }
}