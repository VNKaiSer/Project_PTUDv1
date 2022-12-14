package gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class StartApp extends Thread {
    private Stage stage;
    StartApp(Stage stage){
        this.stage = stage;
    }
    @Override
    public void  run(){
        Platform.runLater(() ->{
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("mm-v3.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ;
            stage.setScene(scene);
            stage.show();
        });

    }
}
