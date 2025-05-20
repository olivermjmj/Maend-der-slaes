package com.example.maendderslaes.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.net.URL;

public class SoundManager {

    // Attributes
    private MediaPlayer musicPlayer;

    // ________________________________________

    public void playSoundFromFilePath(String filePath) {
        try {
            Media sound = new Media(new File(filePath).toURI().toString());
            MediaPlayer effectPlayer = new MediaPlayer(sound);
            effectPlayer.play();
            effectPlayer.setOnEndOfMedia(effectPlayer::dispose);
        } catch (Exception e) {
            System.out.println("Error: Couldn't play the sound effect from file path: " + e.getMessage());
        }
    }

    // ________________________________________

    public void playSoundFromResource(URL resourceUrl) {
        if (resourceUrl == null) {
            System.out.println("Error: Resource URL is null");
            return;
        }
        try {
            Media sound = new Media(resourceUrl.toExternalForm());
            MediaPlayer effectPlayer = new MediaPlayer(sound);
            effectPlayer.play();
            effectPlayer.setOnEndOfMedia(effectPlayer::dispose);
        } catch (Exception e) {
            System.out.println("Error: Couldn't play the sound effect from resource: " + e.getMessage());
        }
    }

    // ________________________________________

    public void playBackgroundMusicFromFilePath(String filePath) {
        try {
            if (this.musicPlayer != null) {
                this.musicPlayer.stop();
            }
            Media music = new Media(new File(filePath).toURI().toString());
            this.musicPlayer = new MediaPlayer(music);
            this.musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            this.musicPlayer.play();
        } catch (Exception e) {
            System.out.println("Error: Couldn't play background music from file path: " + e.getMessage());
        }
    }

    // ________________________________________

    public void playBackgroundMusicFromResource(URL resourceUrl) {
        if (resourceUrl == null) {
            System.out.println("Error: Resource URL is null");
            return;
        }
        try {
            if (this.musicPlayer != null) {
                this.musicPlayer.stop();
            }
            Media music = new Media(resourceUrl.toExternalForm());
            this.musicPlayer = new MediaPlayer(music);
            this.musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            this.musicPlayer.play();
        } catch (Exception e) {
            System.out.println("Error: Couldn't play background music from resource: " + e.getMessage());
        }
    }

    // ________________________________________

    public void stopBackgroundMusic() {
        if (this.musicPlayer != null) {
            this.musicPlayer.stop();
        }
    }

} // SoundManager end