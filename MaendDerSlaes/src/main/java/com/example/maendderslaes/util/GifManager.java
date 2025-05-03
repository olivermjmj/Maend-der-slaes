package com.example.maendderslaes.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class GifManager {

    private ImageView imageView;

    public GifManager(ImageView imageView) {

        this.imageView = imageView;
    }

    public void playGif(String gifPath) {

        try {

            Image gif = new Image(gifPath);
            imageView.setImage(gif);
        } catch (Exception e) {
            System.out.println("couldn't load .gif: " + e.getMessage());
        }
    }
}
