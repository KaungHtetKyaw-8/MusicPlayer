package main;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.util.Duration;

public class Mp3MediaPlayer {

    private Media media;
    private MediaPlayer mediaPlayer;

    private int songNumber = 0;
    private ArrayList<File> songs = new ArrayList<>();

    protected MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    protected void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    protected ArrayList<File> getSongs() {
        return songs;
    }

    protected void setSongs(ArrayList<File> songs) {
        this.songs = songs;
    }

    protected Media getMedia() {
        return media;
    }

    protected int getSongNumber() {
        return songNumber;
    }

    protected void setSongNumber(int songNumber) {
        this.songNumber = songNumber;
    }

    protected String getCurrentSongName() {
        if (songs != null) {
            String temp =songs.get(songNumber).getName();
            return temp.substring(0, temp.lastIndexOf('.'));
        }else{
            return "No Name";
        }
    }

    protected void playMedia() {
        
        mediaPlayer.play();

        System.out.println("Media Played.");
   
    }

    protected void pauseMedia() {

        mediaPlayer.pause();

        System.out.println("Media paused.");
    }

    protected void resetMedia() {
        
        loadsong();

        System.out.println("Media Reset.");
    }

    protected void previousMedia() {

        if (getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
            getMediaPlayer().dispose();
        }

        if (songNumber > 0) {
            
            songNumber--;

        }else{
            songNumber = songs.size() - 1;
        }
        System.out.println("Previous media.");
    }

    protected void nextMedia() {

        if (getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
            getMediaPlayer().dispose();
        }
        
        if (songNumber < songs.size() - 1) {
            
            songNumber++;

        }else{
            songNumber = 0;
        }
        System.out.println("Next media.");
    }

    protected void seek(Double du) {
        getMediaPlayer().seek(mediaPlayer.getCurrentTime().add(Duration.seconds(du)));
    }

    public void loadsong(){
        if (!songs.isEmpty()) {
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer  = new MediaPlayer(media);
            System.gc();
            System.out.println("Media loading complete.");
        }else{
            Mp3PlayerAlert.infoAlert("Please select the files first.");
        }
    }

    protected void clearList() {
        if (mediaPlayer != null) {
            setSongNumber(0);
            getSongs().clear();
            getMediaPlayer().dispose();
            media = null;
            mediaPlayer = null;
            System.out.println("PlayList clered.");
        }
    }

    public void setMedia(Media media) {
        this.media = media;
    }

}
