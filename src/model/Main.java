package model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    public static boolean isServer;
    public static NetworkConnection connection;

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent parent = (Parent) FXMLLoader.load(getClass().getResource("/view/MainPane.fxml"));
            Scene scene = new Scene(parent);
            primaryStage.setTitle("ChatApp");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        connection.closeConnection();
    }
}
