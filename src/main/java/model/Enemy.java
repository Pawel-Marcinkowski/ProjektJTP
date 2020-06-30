package model;

import javafx.scene.image.ImageView;

import java.util.Random;

public class Enemy extends Character {

    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 800;
    private String direction;
    private boolean turn;
    private Random rand;
    private int enemyID;

    public Enemy(int ID, boolean isLoaded, double X, double Y, String direction, boolean turn) {
        enemyID = ID;
        rand = new Random();
        picture = new ImageView("enemy.png");
        if (isLoaded) {
            Xpos = X;
            Ypos = Y;
            this.direction = direction;
            this.turn = turn;
        } else {
            Xpos = rand.nextInt((GAME_WIDTH + 1) - 40);
            if (rand.nextBoolean()) {
                Ypos = rand.nextInt((GAME_HEIGHT / 2 + 1) - 80);
            } else {
                Ypos = rand.nextInt((GAME_HEIGHT / 2 + 1) - 40) + (GAME_HEIGHT / 2 + 1) + 80;
            }
            if (rand.nextBoolean()) {
                this.direction = "X";
            } else {
                this.direction = "Y";
            }
            this.turn = rand.nextBoolean();
        }
        picture.setLayoutX(Xpos);
        picture.setLayoutY(Ypos);
    }

    public void setOppositeDirection() {
        if (direction.equals("Y")) {
            direction = "X";
        } else {
            direction = "Y";
        }
    }

    public void setRandomTurn() {
        turn = rand.nextBoolean();
    }

    public String getDirection() {
        return direction;
    }

    public boolean isTurn() {
        return turn;
    }

    public int getEnemyID() {
        return enemyID;
    }

}
