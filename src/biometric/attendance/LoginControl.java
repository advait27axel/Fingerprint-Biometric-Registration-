/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometric.attendance;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class LoginControl implements Initializable{
    
      @FXML
    private Pane adminbg;

    @FXML
    private JFXTextField Admin_id;

    @FXML
    private JFXPasswordField Amin_Password;

    @FXML
    private JFXButton Loginbtn;

    @FXML
    private Label loginstatus;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
         
    }
    Stage stage;
    Parent root;
    
    @FXML
    public void login(ActionEvent e) throws IOException{

        if (Admin_id.getText().toUpperCase().equals("ADMIN")&&Amin_Password.getText().equals("123admin")){
                //get reference to the button's stage         
                stage=(Stage) Loginbtn.getScene().getWindow();
                //load up OTHER FXML document
                root = FXMLLoader.load(getClass().getResource("Mainpg.fxml"));
                //create a new scene with root and set the stage
                 Scene scene = new Scene(root);
                 stage.setScene(scene);
                 stage.show();

        }
        else{
                loginstatus.setText("Invalid Login Id Or Passsword Entered");

                // clear text
                 Admin_id.setText("");
//                 Amin_Password.setText("");

        }
    
    
    }
    
    public void LoginKey(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER)  {
            //String text = Amin_Password.getText();
            if (Admin_id.getText().equals("ADMIN")&&Amin_Password.getText().equals("123ADMIN")){
               //get reference to the button's stage         
               stage=(Stage) Loginbtn.getScene().getWindow();
               //load up OTHER FXML document
               root = FXMLLoader.load(getClass().getResource("Mainpg.fxml"));
               //create a new scene with root and set the stage
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else{
            loginstatus.setText("Invalid Login Id Or Passsword Entered");
             
            // clears password field 
             Amin_Password.setText("");
            }
        }
    
    
    } 
}
