package view;

import common.CsvLoginRecord;
import common.GameSaver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuViewManager {
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;
    private AnchorPane mainPane;
    private AnchorPane loginPane;
    private Stage mainStage;
    private Scene mainScene;
    private Button startButton;
    private Button exitButton;
    private Button loadButton;
    private Button loginButton;
    private TextField loginInput;
    private boolean isLoaded;
    private GameSaver gameSaver;
    private CsvLoginRecord playerAccount;

    public MenuViewManager() {
        gameSaver = new GameSaver();
        mainPane = new AnchorPane();
        loginPane = new AnchorPane();
        mainPane.setStyle("-fx-background-image: url(menuBackground.png)");
        Scene loginScene = new Scene(loginPane, WIDTH, HEIGHT);
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setTitle("Viral shooter");
        mainStage.getIcons().add(new Image("virus.png"));
        mainStage.setScene(loginScene);
        gameSaver.readLoginFile();
        loginManager();
        createMenuElements();
        initializeButtonListeners();
    }

    public void loginManager() {
        createLoginPane();
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkIfRegistered();
                playerAccount.setLastLogin(System.currentTimeMillis());
                gameSaver.saveAccount();
                createAccountLabel();
                mainStage.setScene(mainScene);
            }
        });
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void createAccountLabel() {
        Label accountInfo = new Label();
        accountInfo.setText("Nick: " + playerAccount.getNickName() + "\nHigh score: " + playerAccount.getHighScore());
        accountInfo.setLayoutX(10);
        accountInfo.setLayoutY(10);
        mainPane.getChildren().add(accountInfo);
    }

    private void checkIfRegistered() {
        String login = loginInput.getText();
        boolean isRegistered = false;
        for (int i = 0; i < gameSaver.getAccounts().size(); i++) {
            if (login.equals(gameSaver.getAccounts().get(i).getNickName())) {
                playerAccount = gameSaver.getAccounts().get(i);
                isRegistered = true;
                break;
            }
        }

        if (!isRegistered) {
            gameSaver.getAccounts().add(new CsvLoginRecord());
            gameSaver.getAccounts().get(gameSaver.getAccounts().size() - 1).setNickName(login);
            playerAccount = gameSaver.getAccounts().get(gameSaver.getAccounts().size() - 1);
        }
    }

    private void createLoginPane() {
        Label title = new Label("Log in");
        title.setFont(new Font("Papyrus", 32));
        title.setLayoutX(WIDTH / 6);
        title.setLayoutY(HEIGHT / 12);

        loginInput = new TextField();
        loginInput.setLayoutY(HEIGHT / 6 + 100);
        loginInput.setLayoutX(WIDTH / 4);
        loginInput.setPrefHeight(100);
        loginInput.setPrefWidth(400);

        loginButton = new Button();
        loginButton.setLayoutX(WIDTH / 4);
        loginButton.setLayoutY(HEIGHT / 6 + 300);
        loginButton.setPrefHeight(100);
        loginButton.setPrefWidth(400);
        loginButton.setText("LOG IN");

        loginPane.getChildren().add(title);
        loginPane.getChildren().add(loginInput);
        loginPane.getChildren().add(loginButton);
    }

    private void createMenuElements() {
        startButton = new Button();
        startButton.setLayoutX(200);
        startButton.setLayoutY(50);
        startButton.setPrefHeight(100);
        startButton.setPrefWidth(400);
        startButton.setText("Start");

        loadButton = new Button();
        loadButton.setLayoutX(200);
        loadButton.setLayoutY(250);
        loadButton.setPrefHeight(100);
        loadButton.setPrefWidth(400);
        loadButton.setText("Load");

        exitButton = new Button();
        exitButton.setLayoutX(200);
        exitButton.setLayoutY(450);
        exitButton.setPrefHeight(100);
        exitButton.setPrefWidth(400);
        exitButton.setText("Exit");

        mainPane.getChildren().add(startButton);
        mainPane.getChildren().add(exitButton);
        mainPane.getChildren().add(loadButton);
    }

    private void initializeButtonListeners() {
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameViewManager gameManager = new GameViewManager(gameSaver, false);
                gameManager.createNewGame(mainStage, playerAccount);
            }
        });

        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isLoaded = true;
                gameSaver.readSaveFile();
                GameViewManager gameManager = new GameViewManager(gameSaver, isLoaded);
                gameManager.createNewGame(mainStage, playerAccount);
            }
        });

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }
}