/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometric.attendance;

/**
 *
 * @author Ekunola  John
 */

import java.sql.*;

public class Report {
    
    public  int getColumns(){
        int i=0;
        String sql="SELECT * FROM Finger_Table";
         try (Connection conn = DbConnect.connect(); 
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)){
                //ResultSetMetaData md=rs.getMetaData();
                while(rs.next())i++;
                
                stmt.close();
                rs.close();
                //conn.close();
            } catch (SQLException e) {
            System.out.println(e.getMessage());
        
            }
         return i;
    }  
    
}
