package com.rgonzalez.sound;

import java.io.File;
import javax.sound.sampled.*;

public class SoundTest {

    public void test() throws Exception {
        File sf = new File("ding.wav");

        AudioFileFormat aff = AudioSystem.getAudioFileFormat(sf);
        AudioInputStream ais = AudioSystem.getAudioInputStream(sf);

        AudioFormat af = aff.getFormat();

        DataLine.Info info = new DataLine.Info(
                Clip.class,
                ais.getFormat(),
                ((int) ais.getFrameLength()
                * af.getFrameSize()));

        Clip ol = (Clip) AudioSystem.getLine(info);
        ol.open(ais);
        ol.loop(3);

        System.out.println("reproducion empezada, apretar CTRL-C para interrumpir");
    }

    public static void main(String[] args) {
        try {
            SoundTest soundTest = new SoundTest();
            soundTest.test();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
