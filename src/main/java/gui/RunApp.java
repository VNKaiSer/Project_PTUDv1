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
    public void start(Stage stage) throws InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("UI_DangNhap.fxml"));
        Scene scene = null;
        CTRL_DangNhap ctrlDangNhap;
        do {
            try {
            scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            ctrlDangNhap = fxmlLoader.getController();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }while (ctrlDangNhap.getTk() != -1 && ctrlDangNhap.getTk() != -1);

        StartApp t1 = new StartApp(stage);
        SplashScreen r = new SplashScreen(null, true);
        Thread t2 = new Thread(r);
        t1.start();
        t1.join();
        t2.start();



    }
}
