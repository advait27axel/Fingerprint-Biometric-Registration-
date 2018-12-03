
package biometric.attendance;

import java.sql.*;


public class DbConnect {
    //jdbc:sqlserver://52.71.147.253:1433;databaseName=FINGERPRINT [sa_htsusers1901 on db_accessadmin]
    
    public  static Connection connect(){
        //String url = "jdbc:sqlserver://52.71.147.253:1433;databaseName=FINGERPRINT;user=sa_htsusers1901;password=p@$$w0rd321";
        String url = "jdbc:sqlserver://52.71.147.253:1433;databaseName=FINGERPRINT;user=sa_htsusers1901;password=p@$$w0rd321";
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn=DriverManager.getConnection(url);
            return conn;
    
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(e);
            return null;
        }
}
}
