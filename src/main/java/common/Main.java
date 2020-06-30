package common;

import javafx.application.Application;
import javafx.stage.Stage;
import view.MenuViewManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            MenuViewManager manager = new MenuViewManager();
            primaryStage = manager.getMainStage();
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
