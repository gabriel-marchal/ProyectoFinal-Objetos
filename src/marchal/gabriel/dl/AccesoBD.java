package marchal.gabriel.dl;

import java.sql.*;
/**
 * @author Gabriel Marchal
 * @version V1
 * @since 2020-07-31
 *
 */

public class AccesoBD {

    private Connection conn = null;
    private Statement stmt = null;

    public AccesoBD(String URL, String user, String password) throws Exception {
        conn = DriverManager.getConnection(URL,user,password);
    }

    //Se usa para todos los INSERT, UPDATE O DELETE
    public void ejecutarINSERT_UPDATE_DELETE(String query) throws Exception{
        stmt = conn.createStatement();
        stmt.executeUpdate(query);
    }

    //Se usa para todas las consultas (SELECT)
    public ResultSet ejecutarSELECT(String query) throws Exception{
        ResultSet rs;
        stmt = conn.createStatement();
        rs = stmt.executeQuery(query);

        return rs;
    }
}