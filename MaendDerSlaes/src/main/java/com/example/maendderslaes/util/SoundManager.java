package com.example.maendderslaes.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundManager {

    private Media sound;
    private MediaPlayer mediaPlayer;;

    public void playSound(String filePath) {
        try {

            //Stops the current sound and plays it again.
            if(mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.stop();
            }

            this.sound = new Media(new File(filePath).toURI().toString());
            this.mediaPlayer = new MediaPlayer(sound);
            this.mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error couldn't play the given sound file: " + e.getMessage());
        }
    }
}
