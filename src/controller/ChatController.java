package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Client;
import model.Server;

import java.net.URL;
import java.util.ResourceBundle;

import static controller.MainController.nickName;
import static model.Main.*;
import static controller.MainController.ip;

public class ChatController implements Initializable{

    @FXML
    private TextArea chatBox;

    @FXML
    private TextField textToSend;

    @FXML
    private Button sendButton;

    @FXML
    void sendClick(ActionEvent event) {
        String message = isServer ? connection.getNickName()+ ": " : connection.getNickName()+": ";
        message += textToSend.getText();
        textToSend.clear();

        chatBox.appendText(message + "\n");

        try {
            connection.send(message);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            chatBox.appendText("message not sent, there is nobody there!\n");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatBox.setEditable(false);
        if (isServer){
            connection = createServer();
            try {
                connection.startConnection();
                connection.setNickName(nickName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            connection = createClient();
            connection.setNickName(nickName);
            try {
                connection.startConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Server createServer() {
        return new Server(6000, data -> {
            Platform.runLater(() -> {
                if(data.toString().equals("Connection lost..."))
                    showAlert();
                chatBox.appendText(data.toString() + "\n");
            });
        });
    }

    private Client createClient() {
        return new Client(ip, 6000, data -> {  //"127.0.0.1"
            Platform.runLater(() -> {
                if(data.toString().equals("Connection lost..."))
                    showAlert();
                chatBox.appendText(data.toString() + "\n");
            });
        });
    }
    private void showAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Connection...");
        alert.setHeaderText(null);
        alert.setContentText("Sorry but something is wrong with connection");

        alert.showAndWait();
        System.exit(0);
    }

}
