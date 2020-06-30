package view;

import common.CsvLoginRecord;
import common.GameSaver;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Bullet;
import model.Enemy;
import model.Player;

import java.util.Random;
import java.util.Vector;

public class GameViewManager {
    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 800;
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Player player;
    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private boolean isUpKeyPressed;
    private boolean isDownKeyPressed;
    private Label scoreCounter;
    private Random rand;
    private int points;
    private int enemiesNumber;
    private Vector<Enemy> enemies;
    private int loopCounter;
    private Vector<Bullet> bullets;
    private AnimationTimer gameTimer;
    private Label gameOverLabel;
    private int numberOfBullets;
    private boolean paused;
    private Label pauseInfo;
    private Button saveButton;
    private Button exitButton;
    private GameSaver gameSaver;
    private Button menuButton;
    private Stage menuStage;
    private CsvLoginRecord playerAccount;

    public GameViewManager(GameSaver gameSaver, boolean isLoaded) {
        rand = new Random();
        this.gameSaver = gameSaver;
        paused = false;
        if (isLoaded) {
            int savedGameIterator = 0;
            numberOfBullets = this.gameSaver.getSavedGame().get(savedGameIterator).getNumberOfBullets();
            player = new Player(this.gameSaver.getSavedGame().get(savedGameIterator).getXpos(), this.gameSaver.getSavedGame().get(0).getYpos());
            savedGameIterator++;
            enemiesNumber = this.gameSaver.getSavedGame().get(savedGameIterator).getNumberOfEnemies();
            enemies = new Vector<>();
            for (int i = 0; i < enemiesNumber; i++) {
                enemies.add(new Enemy(i, true,
                        this.gameSaver.getSavedGame().get(savedGameIterator).getXpos(),
                        this.gameSaver.getSavedGame().get(savedGameIterator).getYpos(),
                        this.gameSaver.getSavedGame().get(savedGameIterator).getDirection(),
                        this.gameSaver.getSavedGame().get(savedGameIterator).isTurn()));
                savedGameIterator++;
            }
            bullets = new Vector<>();
            for (int i = 0; i < numberOfBullets; i++) {
                bullets.add(new Bullet(player.getXpos(), player.getYpos(),
                        this.gameSaver.getSavedGame().get(savedGameIterator).getDirection(),
                        this.gameSaver.getSavedGame().get(savedGameIterator).isTurn(), i));
                savedGameIterator++;
            }
            points = this.gameSaver.getSavedGame().get(savedGameIterator - 1).getPoints();
        } else {
            points = 0;
            numberOfBullets = 0;
            rand = new Random();
            player = new Player(280, 380);
            bullets = new Vector<>();
            enemiesNumber = rand.nextInt(10) + 5;
            enemies = new Vector<>();
            for (int i = 0; i < enemiesNumber; i++) {
                enemies.add(new Enemy(i, false, 0, 0, "", false));
            }
        }
        initializeStage();
        createKeyListeners();
        initializeScoreCounter();
    }

    public void createNewGame(Stage menuStage, CsvLoginRecord playerAccount) {
        this.menuStage = menuStage;
        this.playerAccount = playerAccount;
        menuStage.hide();
        gamePane.setStyle("-fx-background-color: Wheat");
        gamePane.getChildren().add(player.getPicture());
        gamePane.getChildren().add(scoreCounter);
        for (int i = 0; i < enemiesNumber; i++) {
            gamePane.getChildren().add(enemies.get(i).getPicture());
        }
        createGameLoop();
        gameStage.show();
    }

    private void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setTitle("Viral shooter");
        gameStage.getIcons().add(new Image("virus.png"));
        gameStage.setScene(gameScene);
    }

    private void createKeyListeners() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.A) {
                    isLeftKeyPressed = true;
                    isUpKeyPressed = false;
                    isRightKeyPressed = false;
                    isDownKeyPressed = false;
                }

                if (event.getCode() == KeyCode.D) {
                    isRightKeyPressed = true;
                    isUpKeyPressed = false;
                    isDownKeyPressed = false;
                    isLeftKeyPressed = false;
                }

                if (event.getCode() == KeyCode.W) {
                    isUpKeyPressed = true;
                    isDownKeyPressed = false;
                    isRightKeyPressed = false;
                    isLeftKeyPressed = false;
                }

                if (event.getCode() == KeyCode.S) {
                    isDownKeyPressed = true;
                    isUpKeyPressed = false;
                    isRightKeyPressed = false;
                    isLeftKeyPressed = false;
                }

                if (event.getCode() == KeyCode.UP) {
                    numberOfBullets++;
                    bullets.add(new Bullet((int) player.getXpos(), (int) player.getYpos(), "Y", false, numberOfBullets));
                    gamePane.getChildren().add(bullets.get(bullets.size() - 1).getPicture());
                }

                if (event.getCode() == KeyCode.DOWN) {
                    numberOfBullets++;
                    bullets.add(new Bullet((int) player.getXpos(), (int) player.getYpos(), "Y", true, numberOfBullets));
                    gamePane.getChildren().add(bullets.get(bullets.size() - 1).getPicture());
                }

                if (event.getCode() == KeyCode.LEFT) {
                    numberOfBullets++;
                    bullets.add(new Bullet((int) player.getXpos(), (int) player.getYpos(), "X", false, numberOfBullets));
                    gamePane.getChildren().add(bullets.get(bullets.size() - 1).getPicture());
                }
                if (event.getCode() == KeyCode.RIGHT) {
                    numberOfBullets++;
                    bullets.add(new Bullet((int) player.getXpos(), (int) player.getYpos(), "X", true, numberOfBullets));
                    gamePane.getChildren().add(bullets.get(bullets.size() - 1).getPicture());
                }

                if (event.getCode() == KeyCode.ESCAPE) {
                    paused = !paused;
                    if (paused) {
                        pauseGame();
                    } else {
                        resumeGame();
                    }
                }
            }
        });


        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.A) {
                    allMovingKeysFalse();
                }

                if (event.getCode() == KeyCode.D) {
                    allMovingKeysFalse();
                }

                if (event.getCode() == KeyCode.W) {
                    allMovingKeysFalse();
                }

                if (event.getCode() == KeyCode.S) {
                    allMovingKeysFalse();
                }
            }
        });
    }

    private void pauseGame() {
        gameTimer.stop();
        pauseInfo = new Label("PAUSE");
        pauseInfo.setLayoutX(GAME_WIDTH / 3);
        pauseInfo.setLayoutY(GAME_HEIGHT / 5);
        pauseInfo.setFont(new Font("Papyrus", GAME_WIDTH / 15));
        saveButton = new Button();
        saveButton.setText("SAVE GAME");
        saveButton.setLayoutX(GAME_WIDTH / 3);
        saveButton.setLayoutY(GAME_HEIGHT / 3);
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Seved Info");
                alert.setHeaderText(null);
                gameSaver.createPositionsInfo(enemies, player, bullets, points);
                if (gameSaver.saveGame()) {
                    alert.setContentText("Saved successfully!");
                } else {
                    alert.setContentText("Save error!");
                }
                alert.showAndWait();
            }
        });

        exitButton = new Button();
        exitButton.setText("EXIT");
        exitButton.setLayoutX(GAME_WIDTH / 3);
        exitButton.setLayoutY(GAME_HEIGHT / 3 + 100);
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameStage.hide();

            }
        });

        menuButton = new Button();
        menuButton.setText("MENU");
        menuButton.setLayoutX(GAME_WIDTH / 3);
        menuButton.setLayoutY(GAME_HEIGHT / 3 + 50);
        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameStage.hide();
                menuStage.show();

            }
        });

        gamePane.getChildren().add(pauseInfo);
        gamePane.getChildren().add(saveButton);
        gamePane.getChildren().add(exitButton);
        gamePane.getChildren().add(menuButton);
    }

    private void resumeGame() {
        gamePane.getChildren().remove(pauseInfo);
        gamePane.getChildren().remove(saveButton);
        gamePane.getChildren().remove(exitButton);
        gamePane.getChildren().remove(menuButton);
        gameTimer.start();
    }

    private void allMovingKeysFalse() {
        isDownKeyPressed = false;
        isUpKeyPressed = false;
        isRightKeyPressed = false;
        isLeftKeyPressed = false;
    }

    private void initializeScoreCounter() {
        scoreCounter = new Label();
        scoreCounter.setLayoutX(10);
        scoreCounter.setLayoutY(10);
        scoreCounter.setFont(new Font("Papyrus", 25));
    }

    private void win() {
        gameTimer.stop();
        gameOverLabel = new Label("You won! \n Your points: " + points);
        gameOverLabel.setLayoutY(GAME_HEIGHT / 2 - 125);
        gameOverLabel.setLayoutX(GAME_WIDTH / 2 - 150);
        gameOverLabel.setFont(new Font("Papyrus", 50));
        if(playerAccount.getHighScore() < points) playerAccount.setHighScore(points);
        gameSaver.saveAccount();
        gamePane.getChildren().add(gameOverLabel);
    }

    private void moveEnemies() {
        for (int i = 0; i < enemiesNumber; i++) {
            if (enemies.get(i).isTurn()) {
                enemies.get(i).move(rand.nextInt(4), enemies.get(i).getDirection());
            } else {
                enemies.get(i).move((-1) * rand.nextInt(4), enemies.get(i).getDirection());
            }
        }
    }

    private void movePlayer() {
        if (isLeftKeyPressed) {
            if (player.getPicture().getLayoutX() > 0) {
                player.move(-4, "X");
            }
        }
        if (isRightKeyPressed) {
            if (player.getPicture().getLayoutX() < (GAME_WIDTH - 40)) {
                player.move(4, "X");
            }
        }
        if (isUpKeyPressed) {
            if (player.getPicture().getLayoutY() > 0) {
                player.move(-4, "Y");
            }
        }
        if (isDownKeyPressed) {
            if (player.getPicture().getLayoutX() < (GAME_HEIGHT - 40)) {
                player.move(4, "Y");
            }
        }
    }

    private void moveBullets() {
        for (Bullet bullet : bullets) {
            bullet.move();
        }
    }

    private void compareCoords() {
        for (int i = 0; i < enemiesNumber; i++) {
            if (collide(player, enemies.get(i))) {
                gameTimer.stop();
                gameOverLabel = new Label("Game over");
                gameOverLabel.setLayoutY(GAME_HEIGHT / 2 - 75);
                gameOverLabel.setLayoutX(GAME_WIDTH / 2 - 125);
                gameOverLabel.setFont(new Font("Papyrus", 50));
                gamePane.getChildren().add(gameOverLabel);
            }
        }

        for (int x = 0; x < enemies.size(); x++) {
            for (int i = 0; i < bullets.size(); i++) {
                if (collide(bullets.get(i), enemies.get(x))) {
                    points++;
                    enemiesNumber--;
                    enemies.get(x).getPicture().setVisible(false);
                    enemies.remove(x);
                    bullets.get(i).getPicture().setVisible(false);
                    bullets.remove(i);
                    break;
                }
            }
        }
    }

    private boolean collide(Player player, Enemy enemy) {
        double dx = (player.getXpos() + 20) - (enemy.getXpos() + 20);
        double dy = (player.getYpos() + 20) - (enemy.getYpos() + 20);
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < 40;
    }

    private boolean collide(Bullet bullet, Enemy enemy) {
        double dx = (bullet.getXpos() + 20) - (enemy.getXpos() + 20);
        double dy = (bullet.getYpos() + 20) - (enemy.getYpos() + 20);
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < 20;
    }

    private void createGameLoop() {
        loopCounter = 0;
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (loopCounter > 40) {
                    loopCounter = 0;
                    for (int i = 0; i < enemiesNumber; i++) {
                        enemies.get(i).setOppositeDirection();
                        enemies.get(i).setRandomTurn();
                    }
                } else {
                    loopCounter++;
                }
                moveEnemies();
                movePlayer();
                moveBullets();
                compareCoords();
                if (enemies.size() == 0) {
                    win();
                }
                scoreCounter.setText("Score: " + points);
            }
        };
        gameTimer.start();
    }

}
