package marchal.gabriel;

import java.io.FileInputStream;
import java.util.Properties;

public class Utils {

    public static String[] getProperties() throws Exception{
        String[] properties = new String[6];
        Properties p = new Properties();
        String path = "src\\marchal\\gabriel\\bd.properties";

        try
        {
            p.load(new FileInputStream(path));
            properties[0]= p.getProperty("driver");
            properties[1]= p.getProperty("servidor");
            properties[2]= p.getProperty("baseDatos");
            properties[3]= p.getProperty("usuario");
            properties[4]= p.getProperty("password");
            properties[5] = p.getProperty("SSL");

            return properties;

        }catch (Exception e){
            throw e;
        }
    }
}