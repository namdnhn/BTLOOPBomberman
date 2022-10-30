package uet.oop.bomberman;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    public static void play(String sound) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(sound));
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void stop(String sound) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(sound));
            clip.open(inputStream);
            clip.stop();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

//package uet.oop.bomberman;
//
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.Clip;
//import java.io.File;
//import java.net.URL;
//import java.util.Objects;
//import javax.sound.sampled.AudioInputStream;
//
//public class Sound {
//    public static void play(String sound) {
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    Clip clip = AudioSystem.getClip();
//                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(BombermanGame.class.getResourceAsStream(sound)));
//                    clip.open(inputStream);
//                    clip.start();
//                } catch (Exception e) {
//                    System.err.println(e.getMessage());
//                }
//            }
//        }).start();
//
//    }
//    public static void stop(String sound){
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    Clip clip = AudioSystem.getClip();
//                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(BombermanGame.class.getResourceAsStream(sound)));
//                    clip.open(inputStream);
//                    clip.stop();
//                } catch (Exception e) {
//                    System.err.println(e.getMessage());
//                }
//            }
//        }).start();
//    }
//}