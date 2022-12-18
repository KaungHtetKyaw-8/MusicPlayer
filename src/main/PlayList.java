package main;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PlayList {
    private static int row;
    public static void addPlayList(Mp3PlayerController mpc) {
        row = 0;
        mpc.getPlayList().getChildren().clear();
        mpc.getMp3PlayerMedia().getSongs().stream().sorted();
        for (int i = 0; i < mpc.getMp3PlayerMedia().getSongs().size(); i++) {
            Label lb = new Label(mpc.getMp3PlayerMedia().getSongs().get(i).getName().substring(0, mpc.getMp3PlayerMedia().getSongs().get(i).getName().lastIndexOf('.')));
            Button btn = new Button();
            btn.setUserData(i);
            btn.setFocusTraversable(false);
            btn.setMaxSize(10, 10);
            lb.setTextOverrun(OverrunStyle.ELLIPSIS);
            lb.setTextFill(Color.WHITE);
            lb.setFont(new Font("Playball", 20));
            SVGChange.butonChangeShape(btn, SVGChange.PLAY);
            btn.setOnAction(e -> {
                mpc.getMp3PlayerMedia().setSongNumber((Integer)((Button)e.getSource()).getUserData());
                mpc.getMp3PlayerMedia().loadsong();
                mpc.setSongLabel(mpc.getMp3PlayerMedia().getCurrentSongName());
                mpc.playMedia();
            });
                
            mpc.getPlayList().addColumn(row++, lb);
            mpc.getPlayList().addColumn(row, btn);
            // mpc.getPlayList().setStyle(".grid {-fx-background-color : black;} .cell {-fx-border-width : 1 1 1 1; -fx-border-color : black;}");
            row = 0 ;
        }
        System.out.println("PlayList Updated.");
    }
}
