/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometric.attendance;

import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
//import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JFrame;


public class ClassListControl implements Initializable{
    
    @FXML
    private Pane bacground;

    @FXML
    private JFXHamburger navbarhambuger;

    @FXML
    private JFXDrawer drawer;
    
    @FXML
    private JFXListView<String> listView1; 
    
    @FXML
    private JFXButton proceedBtn;
    
    @FXML 
    private JFXButton VerifyCapture ;
    
    @FXML
    private VBox smallNavbar;
    
    private EnumMap<DPFPFingerIndex, DPFPTemplate> templates = new EnumMap<DPFPFingerIndex, DPFPTemplate>(DPFPFingerIndex.class);
  
    Parent root;
    FadeTransition ft;
    
    public byte[]  tests = new byte [200];
    DPFPTemplate template = DPFPGlobal.getTemplateFactory().createTemplate();

ObservableList<Classes> data = FXCollections.observableArrayList();  
    
    HamburgerBackArrowBasicTransition nvbar=null;
    
    private double xOffset = 0;
    private double yOffset = 0;
    Stage stage;
    static Classes cl;
    
    //-----------------------------------------------
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nvbar = new HamburgerBackArrowBasicTransition(navbarhambuger);
        nvbar.setRate(-1);
        initl();
        getClassFromDB();
    } 
    
    
    private void getClassFromDB(){
        
            String sql = "SELECT * FROM Finger_Table";
            
            try (Connection conn = DbConnect.connect(); 
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)){
                
                while(rs.next()){
                    Classes classes=Classes.setClassFormDB(rs.getInt("ID"),rs.getString("DEPARTMENT"),rs.getString("LEVEL"),
                    rs.getString("COURSE"),rs.getString("CODE"));
                    data.add(classes);
                }
                listView1.setExpanded(true);
                listView1.depthProperty().set(5);
                
              
                    data.forEach((c) -> {
                        listView1.getItems().add(c.str());
                    });
                    
                rs.close();
                stmt.close();
                
                
            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
            
       
    }

    
    private void initl(){
        try{
            VBox box =FXMLLoader.load(getClass().getResource("NavBar.fxml"));
           drawer.setSidePane(box);
        
        } catch (IOException ex) {
            Logger.getLogger(AddStudentsControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void exitAppl(MouseEvent e){
         Platform.exit(); 
    }
    
    @FXML
    public void hamuger(MouseEvent e){
        nvbar.setRate(nvbar.getRate()*-1);
        nvbar.play();
        
        
            if (drawer.isHidden() || drawer.isHiding()) {
                hideSmallNavbar();
                smallNavbar.setVisible(false);
                drawer.open();
            } else {
                drawer.close();
                smallNavbar.setVisible(true);
                showSmallNavbar();
            }
    }
    
     //----------------small NavBar-----------------
    private void hideSmallNavbar(){
     ft = new FadeTransition(Duration.millis(30), smallNavbar);
     ft.setFromValue(1.0);
     ft.setToValue(0.0);
     ft.setCycleCount(1);
     ft.setAutoReverse(false);

     ft.play();
    }
    
    private void showSmallNavbar(){
     ft = new FadeTransition(Duration.millis(1500), smallNavbar);
     ft.setFromValue(0.0);
     ft.setToValue(1.0);
     ft.setCycleCount(1);
     ft.setAutoReverse(false);

     ft.play();
    }
    
     //---------small navbar icon links controls-----
    
     @FXML
    public void addClass(ActionEvent e)throws Exception{
        stage=(Stage) smallNavbar.getScene().getWindow();
               //load up OTHER FXML document
               root = FXMLLoader.load(getClass().getResource("addClass.fxml"));
               //create a new scene with root and set the stage
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();  
     }
     
      @FXML
     public void addStudentBtn(ActionEvent e)throws Exception{
        stage=(Stage) smallNavbar.getScene().getWindow();
               //load up OTHER FXML document
               root = FXMLLoader.load(getClass().getResource("AddStudents.fxml"));
               //create a new scene with root and set the stage
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();  
     }
     
     public void takeAttendenceBtn(ActionEvent e)throws Exception{
        stage=(Stage) smallNavbar.getScene().getWindow();
               //load up OTHER FXML document
               root = FXMLLoader.load(getClass().getResource("ClassList.fxml"));
               //create a new scene with root and set the stage
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();  
     }
     public void VerifyCapture(ActionEvent e ) throws Exception{
         
         new EnrollmentDialog(new JFrame(),10,null,templates).setVisible(true);
     }
    
    // ui controls ends
    
    //=========================ON MOUSE DRAGGED =====================
    @FXML
    public void draggable1(MouseEvent event){
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }
    
    @FXML
    public void draggable(MouseEvent event ){
        stage=(Stage) bacground.getScene().getWindow();
        
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
        
    }

    //method to proceed to attendance taking...
    @FXML
    public void proceedToTakeAtt(ActionEvent e) throws IOException, SQLException{
        
         try (Statement s = DbConnect.connect().createStatement()) {
        try (ResultSet rs = s.executeQuery("select Template_1 from Finger_Table")) {
         ArrayList<DPFPTemplate> FingerData = new ArrayList<>();
            
            while (rs.next()) {
               byte[] a = rs.getBytes(1);
                template.deserialize(a);
              FingerData.add(template);
            } 
                System.out.print(FingerData);
        }
    }
        
        // String class_select=listView1.getSelectionModel().getSelectedItem();
//       if (class_select!=null){  
//           
//           data.forEach((cn) -> {
//           if(class_select.equals(cn.str())) {
//               cl=cn.getInstance();//get instance of selected class and add to cl.
//               System.out.println(cn.getCourseCode());
//            }
//            });
//            stage=(Stage) proceedBtn.getScene().getWindow();
//               //load up OTHER FXML document
//               root = FXMLLoader.load(getClass().getResource("TakeAttendance.fxml"));
//               //create a new scene with root and set the stage
//                Scene scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//       }else{
//           Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("Please Select a Class");
//            alert.showAndWait();
//       }
//
//       String class_select=listView1.getSelectionModel().getSelectedItem();
//       if (class_select!=null){
//
//           data.forEach((cn) -> {
//           if(class_select.equals(cn.str())) {
//               cl=cn.getInstance();//get instance of selected class and add to cl.
//               System.out.println(cn.getCourseCode());
//            }
//            });
//            stage=(Stage) proceedBtn.getScene().getWindow();
//               //load up OTHER FXML document
//               root = FXMLLoader.load(getClass().getResource("TakeAttendance.fxml"));
//               //create a new scene with root and set the stage
//                Scene scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//       }else{
//           Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("Please Select a Class");
//            alert.showAndWait();
//       }
        
    } 
    //methods return instance of selected class
    public static Classes getSelectedClass(){
        return cl;
    }
    
}
