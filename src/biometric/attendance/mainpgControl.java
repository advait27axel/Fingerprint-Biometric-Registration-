/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometric.attendance;

import java.sql.*;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


public class mainpgControl implements Initializable{
    Connection coonn;
    
    Stage stage;
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    @FXML
    private Pane addclbg;
    
    @FXML
    private Label validStatus;
    
    @FXML
    private VBox smallNavbar;
    
    @FXML
    JFXHamburger navbarhambuger;
    
    @FXML
    private JFXDrawer drawer;
    
     @FXML
    private JFXTextField departmment;

    @FXML
    private JFXTextField level;

    @FXML
    private JFXTextField course;

    @FXML
    private JFXTextField courseCode;
    
    Parent root;
    FadeTransition ft;
    HamburgerBackArrowBasicTransition nvbar=null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nvbar = new HamburgerBackArrowBasicTransition(navbarhambuger);
        nvbar.setRate(-1);
       init();
    }
    // ui controls
    private void init(){
        try{
            VBox box =FXMLLoader.load(getClass().getResource("NavBar.fxml"));
           drawer.setSidePane(box);
        
        } catch (IOException ex) {
            Logger.getLogger(mainpgControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void hamuger(MouseEvent e){
        nvbar.setRate(nvbar.getRate()*-1);
        nvbar.play();
        
        
            if (drawer.isHidden() || drawer.isHiding()) {
                hideSmallNavbar();
                smallNavbar.setVisible(false);
                //smallNavbar.
                drawer.open();
            } else {
                smallNavbar.setVisible(true);
                showSmallNavbar();
                drawer.close();
            }
    }
    @FXML
    public void exitAppl(MouseEvent e){
         Platform.exit(); 
    }
    
    @FXML
    public void draggable1(MouseEvent event){
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }
    
    @FXML
    public void draggable(MouseEvent event){
        stage=(Stage) addclbg.getScene().getWindow();
        
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
        
    }
    
    //small NavBar-----------------
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
     
     public void logoutbtn(ActionEvent e)throws Exception{
        stage=(Stage) smallNavbar.getScene().getWindow();
               //load up OTHER FXML document
               root = FXMLLoader.load(getClass().getResource("Login.fxml"));
               //create a new scene with root and set the stage
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();  
     }
    
    // ui controls ends
    
    @FXML
    public void addClassToDB(String dep,String level,String Course,String couCode){
        Classes newClass=new Classes(dep,level,Course,couCode);
        System.out.println(newClass.getClassListTable());
        String sql = "INSERT INTO Finger_Table (DEPARTMENT,LEVEL,COURSE,CODE,STUDENTS_CLASS) VALUES(?,?,?,?,?)";
        String sql2 = "CREATE TABLE "+newClass.getClassListTable()+"(\n"
                + "	id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,\n"
                + "	NAME VARCHAR,\n"
                + "	MATRIC_NO VARCHAR UNIQUE,\n"
                + "	IMAGE BLOB\n"
                + ");";   
        try (
            Connection conn = DbConnect.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            Statement stmt = conn.createStatement()) {
            pstmt.setString(1, newClass.getDepartment()); 
            pstmt.setString(2, newClass.getLevel());
            pstmt.setString(3, newClass.getCourse());
            pstmt.setString(4, newClass.getCourseCode());
            pstmt.setString(5, newClass.getClassListTable());
            pstmt.executeUpdate();
            stmt.execute(sql2);
            
            //checks if database connection object is closed
            //and closes if false.
            if (!pstmt.isClosed()) pstmt.close();
            if (!stmt.isClosed())stmt.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // to check if all fields in the form are filled.
    public boolean validate(){
        return !((departmment.getText().trim().equals(""))||(level.getText().trim().equals(""))
                ||(course.getText().trim().equals(""))||(courseCode.getText().trim().equals("")));

    }
    
    public void Submit(){
        if(!validate()){
            validStatus.setText("All field are required");
        }
        else {
            addClassToDB(departmment.getText(),level.getText(),
               course.getText(),courseCode.getText());
        
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Sucessful");
            alert.setHeaderText("Class Sucessfully added");
            alert.showAndWait();
            validStatus.setText("");
            departmment.setText("");    
            level.setText("");    
            course.setText("");    
            courseCode.setText(""); 
        }   
    }
    
    
    
}
        