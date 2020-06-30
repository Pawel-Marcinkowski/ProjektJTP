package model;

import javafx.scene.image.ImageView;

public class Character {
    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 800;
    protected double Xpos;
    protected double Ypos;
    protected ImageView picture;

    public void move(int valueOfMovement, String axis) {
        switch (axis) {
            case "X":
                if (valueOfMovement > 0) {
                    if (Xpos >= (GAME_WIDTH - 44)) {
                        valueOfMovement = -1 * valueOfMovement;
                    }
                } else {
                    if (Xpos < 4) {
                        valueOfMovement = -1 * valueOfMovement;
                    }
                }
                Xpos = picture.getLayoutX() + valueOfMovement;
                picture.setLayoutX(Xpos);
                break;

            case "Y":
                if (valueOfMovement > 0) {
                    if (Ypos >= (GAME_HEIGHT - 44)) {
                        valueOfMovement = -1 * valueOfMovement;
                    }
                } else {
                    if (Ypos < 4) {
                        valueOfMovement = -1 * valueOfMovement;
                    }
                }
                Ypos = picture.getLayoutY() + valueOfMovement;
                picture.setLayoutY(Ypos);
                break;
        }
    }

    public ImageView getPicture() {
        return picture;
    }

    public double getXpos() {
        return Xpos;
    }

    public double getYpos() {
        return Ypos;
    }
}
