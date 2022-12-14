package gui;

import gui.splashscreen.SplashScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        StartApp t1 = new StartApp(stage);
        SplashScreen r = new SplashScreen(null, true);
        Thread t2 = new Thread(r);
        t1.start();
        t1.join();
        t2.start();
    }

}
