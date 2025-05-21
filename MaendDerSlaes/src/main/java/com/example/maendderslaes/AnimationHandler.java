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
        String baseCharacter = switch (characterType) {
            case PLAYER -> "Character";
            case SKELETON -> "Skeleton";
            case WEREWOLF -> "Werewolf";
            case MINOTAUR -> "Minotaur";
        };

        for (AnimationType animType : AnimationType.values()) {
            List<Image> frames = new ArrayList<>();
            String animationName = animType.toString().toLowerCase();

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
                try {
                    String imagePath = basePath + "block/" + fileName;
                    Image frame = new Image(imagePath);
                    if (!frame.isError()) {
                        frames.add(frame);
                    }
                } catch (Exception e) {
                }
            } else if (animType == AnimationType.GOT_HIT) {
                try {
                    for (int i = 1; i <= 2; i++) {
                        String imagePath = basePath + "got_hit/" + fileName + i + ".png";
                        Image frame = new Image(imagePath);
                        if (!frame.isError()) {
                            frames.add(frame);
                        }
                    }
                } catch (Exception e) {
                }
            } else {
                int frameNumber = 1;
                boolean hasMoreFrames = true;

                while (hasMoreFrames) {
                    String imagePath = basePath + animationName + "/" + fileName + frameNumber + ".png";
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
            }
        }
    }

    public void afspilAnimation(AnimationType type) {
        if (currentAnimation != null) {
            currentAnimation.stop();
        }

        List<Image> frames = animations.get(type);
        if (frames == null || frames.isEmpty()) {
            return;
        }

        Timeline timeline = new Timeline();

        double frameVarighed = switch(type) {
            case GOT_HIT -> 0.2;
            case DEATH -> 0.3;
            case BLOCK -> 0.5;
            case LIGHT_ATTACK -> 0.15;
            case MEDIUM_ATTACK -> 0.2;
            case HEAVY_ATTACK -> 0.25;
            case IDLE -> 0.25;
        };

        if (type == AnimationType.GOT_HIT) {
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(0),
                            e -> spriteView.setImage(frames.get(0)))
            );

            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(frameVarighed),
                            e -> spriteView.setImage(frames.get(1)))
            );

            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(frameVarighed * 2))
            );

        } else {
            for (int i = 0; i < frames.size(); i++) {
                final Image frame = frames.get(i);
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(i * frameVarighed),
                                e -> spriteView.setImage(frame))
                );
            }

            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(frames.size() * frameVarighed))
            );
        }

        if (type != AnimationType.IDLE && type != AnimationType.DEATH) {
            timeline.setOnFinished(e -> afspilAnimation(AnimationType.IDLE));
        }

        if (type == AnimationType.IDLE) {
            timeline.setCycleCount(Timeline.INDEFINITE);
        }

        currentAnimation = timeline;
        timeline.play();
    }
}