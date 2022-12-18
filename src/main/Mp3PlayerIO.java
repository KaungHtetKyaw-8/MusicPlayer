package main;

import java.io.File;
import java.util.List;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class Mp3PlayerIO {

    private static File directory;

    protected static File[] folderOpen() {
        DirectoryChooser dc = new DirectoryChooser();

        try {
            directory = dc.showDialog(Mp3PlayerMain.getScene().getWindow());

            return directory.listFiles((dir, name) -> name.endsWith(".mp3"));

        } catch (Exception ex) {
            System.out.println("Files doesn't open yet.");
            return null;
        }
        
    }

    protected static List<File> fileOpen() {
        FileChooser fc = new FileChooser();

        fc.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("MP3", "*.mp3")
        ); 
    
        try {
            return fc.showOpenMultipleDialog(Mp3PlayerMain.getScene().getWindow());
        } catch (Exception ex) {
            System.out.println("Folder doesn't open yet");
            return null;
        }
    }


    
}
