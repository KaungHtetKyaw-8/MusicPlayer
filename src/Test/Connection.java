package Test;

import java.beans.Encoder;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.spi.AudioFileReader;

import org.tritonus.share.sampled.Encodings;



public class Connection {
    public static void main(String[] args) {
        // final AudioFilePlayer player = new AudioFilePlayer ();
        // player.play("D:/Anime/Music/Mp3/[ENG SUB] Secret Base - 10 Years After Ver.-(1080p).mp3");
        // player.play("something.ogg");
        UrlTest();
    }

    private static void UrlTest() {
        // Font.getFontNames().forEach(System.out::println);
        try {
            URL u = new URL("https://file-examples.com/storage/fe19e1a6e563854389e633c/2017/11/file_example_WAV_2MG.wav");
            // URLConnection uc = u.openConnection();
            // uc.connect();
            // System.out.println("URL Connection Complete.");
            AudioInputStream at =  AudioSystem.getAudioInputStream(u);
            Clip clip = AudioSystem.getClip();
            byte[] b = Files.readAllBytes(Path.of("D:/Anime/Music/Mp3/[ENG SUB] Secret Base - 10 Years After Ver.-(1080p).mp3"));
            // AudioFormat af = new AudioFormat(Encoding.PCM_SIGNED, 24000, 64000, 2, 64, 24000, false);
            AudioFileFormat er = AudioSystem.getAudioFileFormat(u);
            System.out.println(er.getType());
            // AudioFormat af = new AudioFormat(44000, 32000, 2, true, false);
            // clip.open(af, b, 0, 4096);
            clip.open(at);
            System.out.println(clip.isOpen());
            clip.start();
            System.out.println("Audio Playing.");
            System.out.println(clip.getMicrosecondLength());
            // clip.loop(Clip.LOOP_CONTINUOUSLY);
            while (clip.getMicrosecondLength() > clip.getMicrosecondPosition()) {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(clip.getMicrosecondPosition());
            }
            System.out.println("Audio Complete.");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
        
}
