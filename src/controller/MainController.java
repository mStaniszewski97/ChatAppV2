package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.Main.*;

public class MainController implements Initializable {

    private Stage stage;
    public static String ip;
    public static String nickName;

    @FXML
    private Button startButton;

    @FXML
    private RadioButton radioButton1;

    @FXML
    private TextField nickText;

    @FXML
    private TextField ipText;

    @FXML
    private RadioButton radioButton2;

    @FXML
    void onClick(ActionEvent event) throws IOException {
        if(radioButton1.isSelected()&&checkFields(radioButton1)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Connection...");
            alert.setHeaderText(null);
            alert.setContentText("Connection in progress...");

            alert.showAndWait();

            isServer = true;
            nickName = nickText.getText();
            newPane();
        }
        if(radioButton2.isSelected()&&checkFields(radioButton2)){
            ip = ipText.getText();
            isServer = false;
            nickName = nickText.getText();
            newPane();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final ToggleGroup group = new ToggleGroup();
        radioButton1.setText("Server");
        radioButton1.setToggleGroup(group);
        radioButton2.setText("Client");
        radioButton2.setToggleGroup(group);
    }
    public boolean checkFields(RadioButton pom){
        if(pom.equals(radioButton1)){
            if(nickText.getText().isEmpty()){
                wrongNicknameAlert();
                return false;
            }
            else{
                return true;
            }
        }
        else{
            if(nickText.getText().isEmpty()){
                wrongNicknameAlert();
                return false;
            }
            else if(ipText.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Something is wrong");
                alert.setHeaderText(null);
                alert.setContentText("Ip server is needed");

                alert.showAndWait();
                return false;
            }
            else{
                return true;
            }
        }
    }
    public void newPane() throws IOException{
        stage = (Stage) startButton.getScene().getWindow();
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("/view/ChatPane.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private void wrongNicknameAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Something is wrong");
        alert.setHeaderText(null);
        alert.setContentText("Give me your nickname!");

        alert.showAndWait();
    }
}
