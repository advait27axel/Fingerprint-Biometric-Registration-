/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometric.attendance;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author JAMES BROWN
 */
public class NavBarControl {
    
    @FXML
    private JFXButton addClassBtn;

    @FXML
    private JFXButton addStudentBtn;

    @FXML
    private JFXButton attndanceBtn;

    @FXML
    private JFXButton statisticsBtn;

    @FXML
    private JFXButton logoutBtn;
    
    Stage stage;
    Parent root;
    
    @FXML
    public void addClass(ActionEvent e)throws Exception{
        stage=(Stage) addClassBtn.getScene().getWindow();
               //load up OTHER FXML document
               root = FXMLLoader.load(getClass().getResource("addClass.fxml"));
               //create a new scene with root and set the stage
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();  
     }
     
      @FXML
     public void addStudentBtn(ActionEvent e)throws Exception{
        stage=(Stage) addStudentBtn.getScene().getWindow();
               //load up OTHER FXML document
               root = FXMLLoader.load(getClass().getResource("AddStudents.fxml"));
               //create a new scene with root and set the stage
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();  
     }
     
     public void takeAttendenceBtn(ActionEvent e)throws Exception{
        stage=(Stage) attndanceBtn.getScene().getWindow();
               //load up OTHER FXML document
               root = FXMLLoader.load(getClass().getResource("ClassList.fxml"));
               //create a new scene with root and set the stage
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();  
     }
    
}


