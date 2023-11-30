
package br.com.infox.dal;

import java.sql.*;
/**
 *
 * @author Lucas Montello
 */
public class moduloConexao {
    
    public static Connection conector(){
        Connection conector = null;
        
        String URL = "jdbc:mysql://localhost:3306/db_biblioteca?characterEncoding=utf-8";
        String USER = "dba";
        String PASSWORD = "root";
        
        try{
        conector = DriverManager.getConnection(URL, USER, PASSWORD);
        return conector;
        } catch(Exception e) {
            return null;
        }
    }
}
