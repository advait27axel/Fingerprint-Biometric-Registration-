
package biometric.attendance;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Attendance {
    private String col_name;
    int col=0;
    
    public int getNumberOfColumns(String table){
        String sql = "SELECT * FROM "+table;
           
            try (Connection conn = DbConnect.connect(); 
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)){
                ResultSetMetaData md=rs.getMetaData();
                col=md.getColumnCount();
                stmt.close();
                rs.close();
                //conn.close();
            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
         return col;
    }
    private int calcAttendWeeks(String table){
        return (getNumberOfColumns(table)-3);
    }
    
    public String getColumnName(){
        return this.col_name;
    }
    
    public int getColumnSIZE(){
        return this.col;
    }
    
    
    public void newAttendance(String table){
        
         col_name="WEEK"+calcAttendWeeks(table);
         String sql="ALTER TABLE "+table+" ADD COLUMN "+col_name+" BOOL NOT NULL DEFAULT FALSE";
         try(
         Connection conn=DbConnect.connect();
         PreparedStatement stmt=conn.prepareStatement(sql);
         ){
            stmt.executeUpdate();    
         }catch (SQLException ex) {
            Logger.getLogger(Attendance.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}