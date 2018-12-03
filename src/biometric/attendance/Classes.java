
package biometric.attendance;

//import java.util.ArrayList;
//import java.util.List;
import javafx.scene.control.Label;


public class Classes {
    

  private String department;
  private String level;
  private String course;
  private String courseCode;
  private String classListTable;
  private int id;
  private final Label label=new Label();
  private String str;
  //private List<Student> students=new ArrayList<>();

public static Classes setClassFormDB(int id,String department,String level,String course,String courseCode){
    Classes classes=new Classes();
    classes.department=department;
    classes.course=course;
    classes.level=level;
    classes.courseCode=courseCode;
    classes.id=id;
    classes.setClassList();
    return classes;
}

public Classes(){
}

public Classes(String department,String level,String course,String courseCode){
    this.department=department;
    this.course=course;
    this.level=level;
    this.courseCode=courseCode;
    setClassList();

}
  

public String getDepartment(){
    return department.toUpperCase();
}  
public String getLevel(){
    return level.toUpperCase();
}  
public String getCourse(){
    return course.toUpperCase();
}  
public String getCourseCode(){
    return courseCode.toUpperCase();
}  

public Label lbl(){
String str1="DEPARTMENT: "+getDepartment()+"\nLEVEL: "+getLevel()+
        "\nCOURSE: "+getCourse()+"\nCOURSE CODE: "+getCourseCode();
this.label.setText(str1);
return this.label;
}

public String str (){
String str1="DEPARTMENT: "+getDepartment()+"\nLEVEL: "+getLevel()+
        "\nCOURSE: "+getCourse()+"\nCOURSE CODE: "+getCourseCode();
this.str=str1;
return this.str;
}

private void setClassList(){
    String a=getDepartment().replaceAll("\\s", "");
    String b=getCourseCode().replaceAll("\\s", "");
    this.classListTable=a.trim()+b;
}

public String getClassListTable(){
   return this.classListTable;
}

public Classes getInstance(){
    return this;
}

}
