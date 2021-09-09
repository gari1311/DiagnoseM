package com.example.mdt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Earpiece extends AppCompatActivity {
    Button btn7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earpiece);
        btn7 = (Button)findViewById(R.id.btn7);
        MediaPlayer mPlayer = MediaPlayer.create(Earpiece.this, R.raw.audio);
        AudioManager mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        int maxVolumeMusic = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolumeMusic, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        mAudioManager.setMode(AudioManager.MODE_IN_CALL);
        mAudioManager.setSpeakerphoneOn(false);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mPlayer.start();
                Intent intent = new Intent(Earpiece.this, Testearpiece.class);
                startActivity(intent);
            }
        });

    }
}