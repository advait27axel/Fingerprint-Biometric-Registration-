

package biometric.attendance;
import java.io.FileInputStream;
import java.util.ArrayList;

public class Student {
  private EnrollmentDialog  _EnrollmentDialog;
    
    
    private String subAgentName;
    private String AgentNumber;
    private String terminalNumber;
    private FileInputStream image;
    public byte [] biodata ;
    private ArrayList<byte[]>  fingerTemplate ;
    
    public Student(String subAgentName,String AgentNumber,String terminalNumber,FileInputStream image){
        this.subAgentName=subAgentName;
        this.AgentNumber=AgentNumber;
        this.terminalNumber = terminalNumber ;
        this.image=image;
        
       
    }
    
    public Student(){};//default constructor
    
    
//    public static Student setClassFromDB(int ID,String name,String matricNo,FileInputStream image){
//    Student student=new Student();
//    student.id=ID;
//    student.name=name;
//    student.matricNo=matricNo;
//    return student;
//}
    
    
    public ArrayList<byte[]> getFingerTemplate(){
        return fingerTemplate ;
    }
    
    public String getSubAgentName(){
        return this.subAgentName;
    }
    public String getAgentNumber(){
        return this.AgentNumber;
    }
    public String getTerminalNumber(){
        return this.terminalNumber;
    }
    public FileInputStream getImage(){
        return image;
    }
    public byte[] getBiodata(){
        return biodata ;
    }
   
}
