package com.example.maendderslaes.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundManager {

    private MediaPlayer effectPlayer;
    private MediaPlayer musicPlayer;

    public void playSound(String filePath) {

        try {

            if (effectPlayer != null && effectPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                effectPlayer.stop();
            }

            Media sound = new Media(new File(filePath).toURI().toString());
            effectPlayer = new MediaPlayer(sound);
            effectPlayer.play();

        } catch (Exception e) {
            System.out.println("Error couldn't play the sound effect: " + e.getMessage());
        }
    }

    public void playSoundOnRepeat(String filePath) {

        try {

            if (musicPlayer != null && musicPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                musicPlayer.stop();
            }

            Media music = new Media(new File(filePath).toURI().toString());
            musicPlayer = new MediaPlayer(music);
            musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            musicPlayer.play();

        } catch (Exception e) {
            System.out.println("Error couldn't play the background music: " + e.getMessage());
        }
    }

    public void stopBackgroundMusic() {

        if (musicPlayer != null) {
            musicPlayer.stop();
        }
    }
}
