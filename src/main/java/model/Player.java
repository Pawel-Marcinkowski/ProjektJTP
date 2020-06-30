package model;

import javafx.scene.image.ImageView;

public class Player extends Character {
    public Player( double X, double Y ) {
        picture = new ImageView("player.png");
        Xpos = X;
        Ypos = Y;
        picture.setLayoutX(Xpos);
        picture.setLayoutY(Ypos);
    }
}
