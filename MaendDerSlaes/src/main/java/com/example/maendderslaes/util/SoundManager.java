package com.example.maendderslaes.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class SoundManager {

    private MediaPlayer musicPlayer;

    public void playSound(String filePath) {
        try {
            Media sound = new Media(new File(filePath).toURI().toString());
            MediaPlayer effectPlayer = new MediaPlayer(sound);

            effectPlayer.play();

            //Cleans up after the audio is done.
            effectPlayer.setOnEndOfMedia(() -> {
                effectPlayer.dispose();
            });

        } catch (Exception e) {
            System.out.println("Error: Couldn't play the sound effect: " + e.getMessage());
        }
    }

    public void playSoundOnRepeat(String filePath) {
        try {
            if (this.musicPlayer != null) {
                this.musicPlayer.stop();
            }

            Media music = new Media(new File(filePath).toURI().toString());
            this.musicPlayer = new MediaPlayer(music);
            this.musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            this.musicPlayer.play();

        } catch (Exception e) {
            System.out.println("Error: Couldn't play the background music: " + e.getMessage());
        }
    }

    public void stopBackgroundMusic() {
        if (this.musicPlayer != null) {
            this.musicPlayer.stop();
        }
    }
}
