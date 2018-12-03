/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometric.attendance;


import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPTemplate;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EnumMap;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JFrame;



public class MainController implements Initializable{
    
    @FXML
    private JFXButton addclassbtn;

    @FXML
    private JFXButton addstudentbn;

    @FXML
    private JFXButton takeattendance;

    @FXML
    private JFXButton viewreport;
    @FXML
    private JFXButton testing ;
    
   private EnumMap<DPFPFingerIndex, DPFPTemplate> templates;
    
    
   
    private double xOffset = 0;
    private double yOffset = 0;
    Stage stage; 
    Parent root;
     
     //----------------------------MAIN PAGE CONTROLS----------------------------------------
     @FXML
     public void addClass(ActionEvent e)throws Exception{
        stage=(Stage) addclassbtn.getScene().getWindow();
               //load up OTHER FXML document
               root = FXMLLoader.load(getClass().getResource("AddStudents.fxml"));
               //create a new scene with root and set the stage
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();  
     }
     
      @FXML
     public void addStudentBtn(ActionEvent e)throws Exception{
        stage=(Stage) addstudentbn.getScene().getWindow();
               //load up OTHER FXML document
               root = FXMLLoader.load(getClass().getResource("AddStudents.fxml"));
               //create a new scene with root and set the stage
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();  
     }
     
     @FXML
         public void takeAttendenceBtn(ActionEvent e)throws Exception{
             VerificationDialog dlg = new VerificationDialog(MainController.this, templates, 21,474);
            		dlg.addPropertyChangeListener(new PropertyChangeListener()
            		{
            			public void propertyChange(final PropertyChangeEvent e) {
            				String name = e.getPropertyName();
            				if (VerificationDialog.FAR_PROPERTY.equals(name)) {
              			        farAchieved.setText("" + (Integer)e.getNewValue());
            				} else
            				if (VerificationDialog.MATCHED_PROPERTY.equals(name)) {
            					fingerMatched.setSelected((Boolean)e.getNewValue());
            				}
            			}
            		});
                	dlg.setVisible(true);
//            // stage = (Stage) testing.getScene().getWindow();
//                stage=(Stage) takeattendance.getScene().getWindow();
//               //load up OTHER FXML document
//               root = FXMLLoader.load(getClass().getResource("ClassList.fxml"));
//               //create a new scene with root and set the stage
//                Scene scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();  
     }
//     public void viewStats(ActionEvent e)throws Exception{
//        stage=(Stage) takeattendance.getScene().getWindow();
//               //load up OTHER FXML document
//               root = FXMLLoader.load(getClass().getResource("ClassList.fxml"));
//               //create a new scene with root and set the stage
//                Scene scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();  
//     }
     
    @Override
      public void initialize(URL rl, ResourceBundle rb) {
      
      }

       @FXML
    public void draggable1(MouseEvent event){
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }
    
    @FXML
    public void draggable(MouseEvent event){
        stage=(Stage) addclassbtn.getScene().getWindow();
        
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
        
    }
       

     @FXML
    public void exitAppl(MouseEvent e){
         Platform.exit(); 
         
    }   
    
}


 
