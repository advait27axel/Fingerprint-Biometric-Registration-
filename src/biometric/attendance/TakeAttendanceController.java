
package biometric.attendance;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


public class TakeAttendanceController implements Initializable {

    @FXML
    private Label class_Details;

    @FXML
    private JFXTextField S_name;

    @FXML
    private JFXTextField S_matricno;

    @FXML
    private ImageView Pics1;

    @FXML
    private JFXHamburger navbarhambuger;
    

    @FXML
    private TextField testID;

    @FXML
    private VBox smallNavbar;


    @FXML
    private JFXDrawer drawer;

    @FXML
    private Pane p1;

    @FXML
    private ImageView Pics;
    
    HamburgerBackArrowBasicTransition nvbar=null;
    
    FadeTransition ft;
    private double xOffset = 0;
    private double yOffset = 0;
    Stage stage;
    Parent root;
    Attendance attendance= new Attendance();
    boolean isColumnSet=false;

 @Override
    public void initialize(URL url, ResourceBundle rb) {
        nvbar = new HamburgerBackArrowBasicTransition(navbarhambuger);
        nvbar.setRate(-1);
//        System.out.println(ClassListControl.getSelectedClass().str());
        showClassDetails();
        initl();
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
    
    // ui controls ends
    //=========================ON MOUSE DRAGGED =====================
     @FXML
    public void draggable1(MouseEvent event){
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }
    
    @FXML
    public void draggable(MouseEvent event ){
        stage=(Stage) p1.getScene().getWindow();
        
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
        
    } 
    //-----------------------------------------------------------------------
    private void showClassDetails(){//Shows the details of the currently selected class
       class_Details.setText(ClassListControl.getSelectedClass().str());
    }
    
   @FXML
   public void setNewAttendance(ActionEvent ev){
      String table=ClassListControl.getSelectedClass().getClassListTable();
      attendance.newAttendance(table);
       isColumnSet=true;
       System.out.println(attendance.getColumnSIZE());
    }
   
   @FXML
   public void mark(ActionEvent event) throws IOException{
       if(isColumnSet){
           String table=ClassListControl.getSelectedClass().getClassListTable();
         String sql="UPDATE "+table+" SET "+attendance.getColumnName()+" = 'TRUE' WHERE ID = ?";
           try(
               Connection conn =DbConnect.connect();
               PreparedStatement pstmt=conn.prepareStatement(sql)){
               pstmt.setInt(1, Integer.parseInt(testID.getText()));
               pstmt.executeUpdate();
               
           }catch (SQLException ex) {
               Logger.getLogger(TakeAttendanceController.class.getName()).log(Level.SEVERE, null, ex);
            
           }
           getIDDetails();
        }else{
           System.out.println("Some Error Occured");
       }
   }
    public void getIDDetails() throws IOException{
        String studentname;
        String studentmatno;
        Blob image;
        String table=ClassListControl.getSelectedClass().getClassListTable();
        String sql="SELECT * FROM "+table+" WHERE ID = ?";
        
        try(
               Connection conn =DbConnect.connect();
               PreparedStatement pstmt=conn.prepareStatement(sql)){
               pstmt.setInt(1, Integer.parseInt(testID.getText()));
               ResultSet rs = pstmt.executeQuery();
               while(rs.next()){
                   studentname=rs.getString("NAME");
                   studentmatno=rs.getString("MATRIC_NO");
                   try (InputStream Is = rs.getBinaryStream("IMAGE"); 
                        OutputStream os = new FileOutputStream(new File("photo.jpg"))) {
                       byte[]content = new byte[1024];
                       int size;
                       while((size=Is.read(content))!= -1)
                       {
                           os.write(content,0,size);
                       }
                   }
                    Image imagex = new Image("file:photo.jpg",200,200,true,true);
                     S_name.setText(studentname);
                     S_matricno.setText(studentmatno);
                    Pics.setImage(imagex);
               }
               
           }catch (SQLException ex) {
               Logger.getLogger(TakeAttendanceController.class.getName()).log(Level.SEVERE, null, ex);

    
        }
    
    
    }
}