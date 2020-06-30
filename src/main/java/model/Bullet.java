package model;

import javafx.scene.image.ImageView;

public class Bullet extends Character {
    private String direction;
    private boolean turn;
    private int bulletID;

    public Bullet(double playerXpos, double playerYpos, String direction, boolean turn, int ID) {
        bulletID = ID;
        picture = new ImageView("bullet.png");
        this.direction = direction;
        this.turn = turn;
        Xpos = playerXpos + 20;
        Ypos = playerYpos + 20;
        picture.setLayoutX(Xpos);
        picture.setLayoutY(Ypos);
    }

    public void move() {
        switch (direction) {
            case "X":
                if (turn) {
                    Xpos = picture.getLayoutX() + 10;
                } else {
                    Xpos = picture.getLayoutX() - 10;
                }
                picture.setLayoutX(Xpos);
                break;

            case "Y":
                if (turn) {
                    Ypos = picture.getLayoutY() + 10;
                } else {
                    Ypos = picture.getLayoutY() - 10;
                }
                picture.setLayoutY(Ypos);
                break;
        }
    }

    public int getBulletID() {
        return bulletID;
    }

    public String getDirection() {
        return direction;
    }

}
