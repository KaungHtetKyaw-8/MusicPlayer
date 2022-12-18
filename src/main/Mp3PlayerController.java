package main;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Mp3PlayerController implements Initializable{

    @FXML
    private ImageView lImg,rImg;
    @FXML
    private Label songLabel;
    @FXML
    private Button playPauseButton,repeatButton,resetButton,previousButton,nextButton;
    @FXML
    private ComboBox<String> speedBox;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ProgressBar songProgressBar;
    @FXML
    private GridPane playList;

    private Mp3MediaPlayer mpm;

    private static final int[] Speeds = {25, 50, 75, 100, 125, 150, 175, 200};

    private boolean buttonActiveStatus = false;
    private boolean isFilesExist = false;
    private boolean repete = true;
    private boolean running = false;
    private boolean complete = false;

    RotateTransition rImgRotate, lImgRotate;

    public Mp3PlayerController(){
    }

    public Mp3PlayerController(Mp3MediaPlayer mpm){
        this.mpm = mpm;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        System.out.println("Default : Button disable.");
        setButtonActive(buttonActiveStatus);
        
        for (int i = 0; i < Speeds.length; i++) {
            
            speedBox.getItems().add(Integer.toString(Speeds[i]) + "%");
        }

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                Double value = volumeSlider.getValue() * 0.002;
                mpm.getMediaPlayer().setVolume(value);
            }
            
        });

        songProgressBar.setOnMouseDragged(mouseProgressEvent);
        songProgressBar.setOnMouseClicked(mouseProgressEvent);

        rImgRotate = getRotateTransition(rImg);
        lImgRotate = getRotateTransition(lImg);


        // songProgressBar.setStyle("-fx-accent : #d00dd1;");
        
    }

    public void playPauseMedia() {
        if (isRunning()) {
            pauseMedia();
        }else{
            playMedia();
        }
    }
    
    public void playMedia() {

        if (mpm.getMediaPlayer() == null) {
            mpm.loadsong();  
            setSongLabel(mpm.getCurrentSongName());
        }

        changeSpeed();

        setRunning(true);

        mpm.getMediaPlayer().setVolume(getVolumeSlider().getValue() * 0.002);

        beginTimer();

        mpm.playMedia();

        discPlay();

        SVGChange.butonChangeShape(playPauseButton, SVGChange.PAUSE);
        
    }

    public void pauseMedia() {
        mpm.pauseMedia();
        discPause();
        setRunning(false);
        SVGChange.butonChangeShape(playPauseButton, SVGChange.PLAY_Circle);
    }

    public void resetMedia() {
        pauseMedia();
        mpm.resetMedia();
        // getSongProgressBar().setProgress(0);
        playMedia();
    }

    public void previousMedia() {
        mpm.previousMedia();
        mpm.loadsong();
        playMedia();
        setSongLabel(mpm.getCurrentSongName());
    }

    public void nextMedia() {
        mpm.nextMedia();
        mpm.loadsong();
        playMedia();
        setSongLabel(mpm.getCurrentSongName());
    }


    public void repete() {
        if (!isRepete()) {
            setRepete(true);
            System.out.println("Repete : " + isRepete());
            repeatButton.setEffect(new Bloom(0.0));
        }else{
            setRepete(false);
            System.out.println("Repete : " + isRepete());
            repeatButton.setEffect(null);
        }
    }

    private void discPlay(){
        rImgRotate.play();
        lImgRotate.play();
    }

    private void discPause() {
        rImgRotate.pause();
        lImgRotate.pause();
    }

    private void loadPlayList() {
        PlayList.addPlayList(this);
    }

    private void beginTimer() {

        mpm.getMediaPlayer().currentTimeProperty().addListener((arg0,arg1,arg2) -> {

            Double current = mpm.getMediaPlayer().getCurrentTime().toSeconds();
            Double end = mpm.getMediaPlayer().getTotalDuration().toSeconds();
            getSongProgressBar().setProgress(current/end);

            // System.out.println("End : "+end);
            // System.out.println("Current : "+current);

            if (current.longValue() >= end.longValue() && end != 0) {
                // System.out.println(mpm.getMediaPlayer().getStatus());
                System.out.println("End Music.");
                if (this.isRepete()) {
                    nextMedia();
                } else {
                    pauseMedia();
                }

            }
            
        });
        
    }

    public void fileOpen() {
        List<File> files = Mp3PlayerIO.fileOpen();
        // System.out.println(files.size());
        files.forEach(f -> {
            if (f.exists()) {
                 if (!mpm.getSongs().isEmpty()) {
                     if (!isFilesExist(f)) {
                         mpm.getSongs().add(f);
                         System.out.println("Files exist and loaded");
                         playMedia();
                         loadPlayList();
                     }
                 }else{
                    mpm.getSongs().add(f);
                    loadPlayList();
                    System.out.println("First files loaded.");
                 }
            }
         });
        if (!mpm.getSongs().isEmpty() && !isButtonActive()) {
            setButtonActive(true);
        }
    }

    public void folderOpen() {
        File files[] = Mp3PlayerIO.folderOpen();

        if (files != null) {
            for (File f : files) {
                if (!isFilesExist(f)) {
                    mpm.getSongs().add(f);
                }
            }
        }
        loadPlayList();
        System.out.println("Folder exist and loaded.");
        if (!mpm.getSongs().isEmpty()) {
            if (!isButtonActive()) {
                setButtonActive(true);
            } 
        }
    }

    private boolean isFilesExist(File f) {
        isFilesExist = false;
        mpm.getSongs().forEach(e->{
            if (e.equals(f)) {
                isFilesExist = true;
            }
        });
        return isFilesExist;
    }

    public void changeSpeed() {
        if (getSpeedBox().getValue() == null) {
            mpm.getMediaPlayer().setRate(1);
        }else{
            // mediaPlayer.setRate(Integer.parseInt(speedBox.getValue()) * 0.01);
            mpm.getMediaPlayer().setRate(Integer.parseInt(getSpeedBox().getValue().substring(0, getSpeedBox().getValue().length() - 1)) * 0.01);
            System.out.println("Speed changed : " + getSpeedBox().getValue());
        }
    }

    public void close() {
        System.out.println("Platform existing...");
        Platform.exit();
        System.exit(0);
    }


    public boolean isButtonActive() {
        return buttonActiveStatus;
    }

    public void setButtonActive(boolean c) {
        buttonActiveStatus = c;
        repeatButton.setDisable(!c);
        playPauseButton.setDisable(!c);
        nextButton.setDisable(!c);
        previousButton.setDisable(!c);
        resetButton.setDisable(!c);
        speedBox.setDisable(!c);
        volumeSlider.setDisable(!c);
        if (!c) {
            System.out.println("Button disabled.");
        } else {
            System.out.println("Button enabled.");
        }
    }

    public void clearList() {
        pauseMedia();
        mpm.clearList();
        getSongProgressBar().setProgress(0);
        getSongLabel().setText("Mp3 Player");
        setButtonActive(false);
        setRunning(false);
        getPlayList().getChildren().clear();
    }

    public Slider getVolumeSlider() {
        return this.volumeSlider;
    }

    public ProgressBar getSongProgressBar() {
        return songProgressBar;
    }

    public Label getSongLabel() {
        return songLabel;
    }

    public ComboBox<String> getSpeedBox() {
        return speedBox;
    }

    public void setMp3PlayerMedia(Mp3MediaPlayer mpm) {
        this.mpm = mpm;
    }

    public Mp3MediaPlayer getMp3PlayerMedia() {
        return mpm;
    }     

    public boolean isRepete() {
        return repete;
    }

    public void setRepete(boolean repete) {
        this.repete = repete;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public GridPane getPlayList() {
        return playList;
    }

    public void setSongLabel(String songLabel) {
        // this.songLabel.setText(null);
        // System.out.println("SetLable : " + songLabel);
        getSongLabel().textProperty().setValue(songLabel);
        System.out.println(getSongLabel().textProperty().getValue());
        
    }

    private RotateTransition getRotateTransition(Node n){
        RotateTransition rt = new RotateTransition();
        rt.setNode(n);
        rt.setDuration(Duration.millis(3000));
        rt.setCycleCount(TranslateTransition.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.setByAngle(360);
        rt.setAxis(Rotate.Z_AXIS);

        return rt;
    }

    EventHandler<MouseEvent> mouseProgressEvent = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent e) {
            double temp = e.getX();
            if (buttonActiveStatus && temp > 0 && temp < 690) {
                getMp3PlayerMedia().getMediaPlayer().seek(Duration.seconds((getMp3PlayerMedia().getMediaPlayer().getTotalDuration().toSeconds()*(temp/690))));
                songProgressBar.setProgress(temp/690);
            }
        }
        
    };
    
}
