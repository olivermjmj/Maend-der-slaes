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

        System.out.println("Indlæser animationer for: " + characterType);

        for (AnimationType animType : AnimationType.values()) {
            List<Image> frames = new ArrayList<>();
            String animationName = animType.toString().toLowerCase();

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
                try {
                    String imagePath = basePath + "block/" + fileName;
                    System.out.println("Prøver at indlæse block: " + imagePath);
                    Image frame = new Image(imagePath);
                    if (!frame.isError()) {
                        frames.add(frame);
                    }
                } catch (Exception e) {
                    System.err.println("Kunne ikke indlæse block: " + fileName);
                }
            } else if (animType == AnimationType.GOT_HIT) {
                // Speciel håndtering af got_hit animationer
                try {
                    for (int i = 1; i <= 2; i++) {
                        String imagePath = basePath + "got_hit/" + fileName + i + ".png";
                        System.out.println("Prøver at indlæse got_hit: " + imagePath);
                        Image frame = new Image(imagePath);
                        if (!frame.isError()) {
                            frames.add(frame);
                            System.out.println("Succes: Indlæste got_hit frame " + i);
                        } else {
                            System.err.println("Fejl: Kunne ikke indlæse got_hit frame " + i + " (frame.isError() er true)");
                            // Print yderligere information om billedet
                            System.err.println("  Billede bredde: " + frame.getWidth());
                            System.err.println("  Billede højde: " + frame.getHeight());
                            System.err.println("  Billede exception: " + frame.getException());
                        }
                    }
                    System.out.println("Antal indlæste got_hit frames: " + frames.size());
                } catch (Exception e) {
                    System.err.println("Kunne ikke indlæse got_hit animation: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                // Håndtering af andre animationer
                int frameNumber = 1;
                boolean hasMoreFrames = true;

                while (hasMoreFrames) {
                    String imagePath = basePath + animationName + "/" + fileName + frameNumber + ".png";
                    System.out.println("Prøver at indlæse: " + imagePath);
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
                System.out.println("Tilføjede " + frames.size() + " frames for " + animType);
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

        System.out.println("Starter " + type + " animation med " + frames.size() + " frames");

        Timeline timeline = new Timeline();

        // Forskellige hastigheder for forskellige animations typer
        double frameVarighed = switch(type) {
            case GOT_HIT -> 0.2;    // 200ms per frame for got_hit
            case DEATH -> 0.3;      // 300ms for død
            case BLOCK -> 0.5;      // 500ms for block
            case LIGHT_ATTACK -> 0.15;  // 150ms for let angreb
            case MEDIUM_ATTACK -> 0.2;  // 200ms for medium angreb
            case HEAVY_ATTACK -> 0.25;  // 250ms for kraftigt angreb
            case IDLE -> 0.25;      // 250ms for idle
        };

        if (type == AnimationType.GOT_HIT) {
            // Særlig håndtering af got_hit animation
            System.out.println("GotHit animation starter - antal frames: " + frames.size());
            
            // Frame 1
            timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(0),
                e -> {
                    System.out.println("Viser GotHit frame 1");
                    spriteView.setImage(frames.get(0));
                })
            );

            // Frame 2
            timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(frameVarighed),
                e -> {
                    System.out.println("Viser GotHit frame 2");
                    spriteView.setImage(frames.get(1));
                })
            );

            // Hold sidste frame lidt længere og tilføj en ekstra keyframe for at sikre fuld visning
            timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(frameVarighed * 2))
            );

        } else {
            // Normal håndtering for andre animationer
            for (int i = 0; i < frames.size(); i++) {
                final Image frame = frames.get(i);
                timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(i * frameVarighed),
                    e -> spriteView.setImage(frame))
                );
            }
            
            // Tilføj en ekstra keyframe for at holde den sidste frame
            timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(frames.size() * frameVarighed))
            );
        }

        // Gå tilbage til idle efter animationen er færdig
        if (type != AnimationType.IDLE && type != AnimationType.DEATH) {
            timeline.setOnFinished(e -> {
                System.out.println("Animation " + type + " færdig, går tilbage til IDLE");
                afspilAnimation(AnimationType.IDLE);
            });
        }

        // For idle animation, gentag den
        if (type == AnimationType.IDLE) {
            timeline.setCycleCount(Timeline.INDEFINITE);
        }

        currentAnimation = timeline;
        timeline.play();
    }
}