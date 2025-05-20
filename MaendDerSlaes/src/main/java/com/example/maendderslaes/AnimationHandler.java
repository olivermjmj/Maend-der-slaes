package com.example.maendderslaes;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimationHandler {
    private final ImageView spriteView;
    private final CharacterType characterType;
    private final Map<AnimationType, List<Image>> animations;
    private Timeline currentAnimation;

    public AnimationHandler(CharacterType type, ImageView spriteView) {
        this.characterType = type;
        this.spriteView = spriteView;
        this.animations = new HashMap<>();
        indlæsAnimationer();
    }

    private void indlæsAnimationer() {
        String basePath = "file:data/sprites/" + characterType.toString().toLowerCase() + "/";
        String baseCharacter = (characterType == CharacterType.PLAYER) ? "Character" : "Skeleton";

        for (AnimationType animType : AnimationType.values()) {
            List<Image> frames = new ArrayList<>();
            String animationName = animType.toString();

            // Håndter specielle navne for hver animation type
            String fileName = switch (animType) {
                case BLOCK -> baseCharacter + "Block.png";
                case GOT_HIT -> baseCharacter + "GotHit";
                case HEAVY_ATTACK -> baseCharacter + "HeavyAttack";
                case LIGHT_ATTACK -> baseCharacter + "LightAttack";
                case MEDIUM_ATTACK -> baseCharacter + "MediumAttack";
                case DEATH -> baseCharacter + "Death";
                case IDLE -> baseCharacter + "Idle";
            };

            if (animType == AnimationType.BLOCK) {
                // Block har kun ét billede uden nummer
                try {
                    String imagePath = basePath + "block/" + fileName;
                    Image frame = new Image(imagePath);
                    if (!frame.isError()) {
                        frames.add(frame);
                    }
                } catch (Exception e) {
                    System.err.println("Kunne ikke indlæse block: " + fileName);
                }
            } else {
                // For alle andre animationer, prøv at indlæse nummererede frames
                int frameNumber = 1;
                boolean hasMoreFrames = true;

                while (hasMoreFrames) {
                    String imagePath = basePath +
                            animType.toString().toLowerCase() + "/" +
                            fileName + frameNumber + ".png";
                    try {
                        Image frame = new Image(imagePath);
                        if (!frame.isError()) {
                            frames.add(frame);
                            frameNumber++;
                        } else {
                            hasMoreFrames = false;
                        }
                    } catch (Exception e) {
                        hasMoreFrames = false;
                    }
                }
            }

            if (!frames.isEmpty()) {
                animations.put(animType, frames);
            } else {
                System.err.println("Ingen frames fundet for " + animType + " animation for " + characterType);
            }
        }
    }

    public void afspilAnimation(AnimationType type) {
        if (currentAnimation != null) {
            currentAnimation.stop();
        }

        List<Image> frames = animations.get(type);
        if (frames == null || frames.isEmpty()) {
            System.err.println("Ingen frames fundet for animation: " + type + " for " + characterType);
            return;
        }

        Timeline timeline = new Timeline();
        double frameVarighed = 0.1; // 100 ms mellem hver frame

        for (int i = 0; i < frames.size(); i++) {
            final Image frame = frames.get(i);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(i * frameVarighed),
                            e -> spriteView.setImage(frame))
            );
        }

        // Gå tilbage til idle efter animationen er færdig
        if (type != AnimationType.IDLE && type != AnimationType.DEATH) {
            timeline.setOnFinished(e -> afspilAnimation(AnimationType.IDLE));
        }

        // For idle animation, gentag den
        if (type == AnimationType.IDLE) {
            timeline.setCycleCount(Timeline.INDEFINITE);
        }

        currentAnimation = timeline;
        timeline.play();
    }

    public void stopAnimation() {
        if (currentAnimation != null) {
            currentAnimation.stop();
            currentAnimation = null;
        }
    }

    public boolean harAnimation(AnimationType type) {
        List<Image> frames = animations.get(type);
        return frames != null && !frames.isEmpty();
    }
}