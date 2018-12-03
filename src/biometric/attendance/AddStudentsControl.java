
package biometric.attendance;



//import com.digitalpersona.onetouch.*;
//import com.digitalpersona.onetouch.verification.DPFPVerification;

import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.EnumMap;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JFrame;


/**
 * FXML Controller class
 *
 * @author Amos GOdwin
 */

public class AddStudentsControl  implements Initializable {
    
    
    @FXML
    private Pane stack;

    @FXML
    private Pane formBg;
    
    @FXML
    private ImageView Pics;

    @FXML
    private JFXTextField student_name;

    @FXML
    private JFXTextField matno;
    @FXML
    private JFXTextField TerminalNumber;

    @FXML
    private JFXHamburger navbarhambuger;

    @FXML
    private JFXDrawer drawer;
    
    @FXML
    private VBox smallNavbar;
    
   
    
    @FXML
    private JFXComboBox<Label> selClass;
    
    private String classDBTable;
    
   
    
    private DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();
    private EnumMap<DPFPFingerIndex, DPFPTemplate> templates = new EnumMap<DPFPFingerIndex, DPFPTemplate>(DPFPFingerIndex.class);
    
    
    private byte[] FingerDetails   ;
    
    
    
    HamburgerBackArrowBasicTransition nvbar=null;
    
    ObservableList<Classes> classListCombo = FXCollections.observableArrayList();
    final FileChooser fileChooser = new FileChooser();
    private double xOffset = 0;
    private double yOffset = 0;
    private FileInputStream studentImage;
    FadeTransition ft;
    Stage stage;
    Parent root;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nvbar = new HamburgerBackArrowBasicTransition(navbarhambuger);
        nvbar.setRate(-1);
        initl();
        setClassToCombo();
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
        stage=(Stage) stack.getScene().getWindow();
        
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
        
    }
    //=====================UPLOAD PICTURES============
    @FXML
    public void uploadPictures(ActionEvent e) throws IOException{
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Upload Image");
        
        // limit fileChooser options to image files
     chooser.getExtensionFilters().addAll(
     new FileChooser.ExtensionFilter("JPG", "*.jpg"),
     new FileChooser.ExtensionFilter("PNG", "*.png")
); 
        
        File file = chooser.showOpenDialog(new Stage());
        if(file != null) {
            try {
                String imagepath = file.toURI().toURL().toString();
                
                Image image = new Image(imagepath);
                Pics.setImage(image);
                Pics.setPreserveRatio(true);
                Pics.setFitHeight(180);
                Pics.setFitWidth(180);
                studentImage=new FileInputStream(file);
            } catch (MalformedURLException ex) {
                Logger.getLogger(AddStudentsControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No file chosen");
            alert.setHeaderText("upload image");
            alert.showAndWait();
        }

        }//==============upoad button ends here====================
    
    private void setClassToCombo(){
        
            String sql = "SELECT * FROM Finger_Table";
            
            try (Connection conn = DbConnect.connect(); 
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)){
                
                while(rs.next()){
                    Classes classes=Classes.setClassFormDB(rs.getInt("ID"),rs.getString("DEPARTMENT"),rs.getString("LEVEL"),
                    rs.getString("COURSE"),rs.getString("CODE"));
                    classListCombo.add(classes);
                }
                // populates combo box
                    classListCombo.forEach((c) -> {
                        selClass.getItems().add(c.lbl());
                    });
                
                rs.close();
                stmt.close();
                
                
            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
            
       
    }
    @FXML
    private void scanBtn(ActionEvent e)throws IOException
    {
    
        new EnrollmentDialog(new JFrame(),2,null,templates).setVisible(true);
      
       
 
        
           
            
    }
    
    @FXML
    public void getSelectedClass(ActionEvent e){
       Label class_select=selClass.getSelectionModel().getSelectedItem();
       
       classListCombo.forEach((cn) -> {
           if(class_select==cn.lbl()) {
               classDBTable=cn.getClassListTable();
               System.out.println(classDBTable);
           }
        });
    }
    private boolean validate(){
        
        return !((student_name.getText().trim().equals(""))
                ||(student_name.getText().trim().equals("")) || (studentImage==null));
    }
    

    public void saveStudent(ActionEvent event) throws IOException{
           ArrayList<byte[]> d = EnrollmentDialog.fingerTemplates  ;
             if(validate()){
        
            String sql="INSERT INTO Finger_Table (Sub_Agent_First_Name,Agency_Number,Terminal_Number,Thumbnail,Template_1,Template_2) VALUES(?,?,?,?,?,?)";
         Student student=new Student(student_name.getText(),matno.getText(),TerminalNumber.getText(),studentImage);
        try (
            Connection conn = DbConnect.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getSubAgentName());
            pstmt.setString(2, student.getAgentNumber());
            pstmt.setString(3,student.getTerminalNumber());
            pstmt.setBinaryStream(4, student.getImage(),student.getImage().available()) ;
            pstmt.setBytes(5,d.get(0));
            pstmt.setBytes(6, d.get(1));
            int i=pstmt.executeUpdate();
            
        
    }   catch (SQLException ex) {
            Logger.getLogger(AddStudentsControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Sucessful");
            alert.setHeaderText("Student Sucessfully added");
            alert.show();
        
            student_name.setText("");
            matno.setText("");
            Pics.setImage(null);
            d.clear();
        }else{
             Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty field");
            alert.setHeaderText("some fields might be empty... \n all fields are required");
            alert.showAndWait();
        
        }
        
        
     
            
    }
}