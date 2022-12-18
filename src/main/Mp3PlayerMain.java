package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Mp3PlayerMain extends Application{

    private static Scene scene;
    private static Mp3PlayerController mpc;
    private static Mp3MediaPlayer mpm;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fl = new FXMLLoader(getClass().getResource("Mp3Player.fxml"));
        Mp3PlayerMain.scene = new Scene(fl.load());
        System.out.println("FXML Loaded.");

        Mp3PlayerMain.mpm = new Mp3MediaPlayer();

        Mp3PlayerMain.mpc = (Mp3PlayerController)fl.getController();

        mpc.setMp3PlayerMedia(mpm);

        stage.addEventHandler(KeyEvent.KEY_PRESSED, e -> Mp3PlayerKeyEvent.keyPressedAction(e, mpc));
        stage.addEventHandler(KeyEvent.KEY_TYPED, e -> Mp3PlayerKeyEvent.keyTypedAction(e, mpc));

        stage.getIcons().add(new Image("resources/icon.png"));
        stage.setScene(scene);
        stage.setTitle("Mp3Player");
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent arg0) {
                
                Platform.exit();
                System.exit(0);
            }
            
        });
    }

    public static void main(String[] args) {
        launch();
    }

    public static Scene getScene() {
        return scene;
    }

    public static Mp3PlayerController getFxController() {
        return mpc;
    }
    
}
