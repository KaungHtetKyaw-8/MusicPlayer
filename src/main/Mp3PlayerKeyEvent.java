package main;

import javafx.scene.input.KeyEvent;

public class Mp3PlayerKeyEvent {
    protected static void keyPressedAction(KeyEvent e,Mp3PlayerController mpc) {
        try {
            switch (e.getCode()) {
                case RIGHT:
                    mpc.getMp3PlayerMedia().seek(2.5);
                    System.out.println("Right key.");
                    break;
                case LEFT:
                    mpc.getMp3PlayerMedia().seek(-2.5);
                    System.out.println("Left key.");
                    break;
                case UP:
                    mpc.getVolumeSlider().setValue(mpc.getVolumeSlider().getValue() + 1);
                    System.out.println("Up key.");
                    break;
                case DOWN:
                    mpc.getVolumeSlider().setValue(mpc.getVolumeSlider().getValue() - 1);
                    System.out.println("Down key.");
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            System.out.println("Files doesn't selected.");
            Mp3PlayerAlert.errorAlert("Please select a music file first!");
        }
    }

    protected static void keyTypedAction(KeyEvent e,Mp3PlayerController mpc) {
        try {
            switch (e.getCharacter()) {
                case " ":
                    mpc.playPauseMedia();
                    System.out.println("Space key.");
                    break;
                case "n":
                    mpc.nextMedia();
                    System.out.println("N key.");
                    break;
                case "p":
                    mpc.previousMedia();
                    System.out.println("P key.");
                    break;
                case "r":
                    mpc.resetMedia();
                    System.out.println("R key.");
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            System.out.println("Files doesn't selected.");
            Mp3PlayerAlert.errorAlert("Please select a music file first!");
        }
    }
}
