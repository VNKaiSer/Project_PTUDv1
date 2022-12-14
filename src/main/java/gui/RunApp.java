package gui;

import gui.splashscreen.SplashScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RunApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws InterruptedException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("UI_DangNhap.fxml"));
        Scene scene = null;
            scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();





    }
}
