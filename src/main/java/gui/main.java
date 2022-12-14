package gui;

import gui.splashscreen.SplashScreen;
import javafx.application.Application;
import javafx.application.Platform;
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
//        Login t0 = new Login(stage);
//        t0.start();
//        t0.setPriority(Thread.MAX_PRIORITY);
//        if (t0.login() == 1 || t0.login() == 2 || t0.login() == 0) {
            StartApp t1 = new StartApp(stage);
            SplashScreen r = new SplashScreen(null, true);
            Thread t2 = new Thread(r);
            t1.start();
            t1.join();
            t2.start();
//        }
    }

}

class Login extends Thread{
    private Stage stage;
    private CTRL_DangNhap ctrlDangNhap;
    Login(Stage stage){
        this.stage = stage;
    }
    @Override
    public void run(){
            Platform.runLater(() ->{
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("UI_DangNhap.fxml"));
            Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ctrlDangNhap = fxmlLoader.getController();
            stage.setScene(scene);
            stage.show();
            })
            ;

    }
    public int login(){
        return 0;
    }
}
