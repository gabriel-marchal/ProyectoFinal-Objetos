package marchal.gabriel.dl;


import marchal.gabriel.Utils;
/**
 * @author Gabriel Marchal
 * @version V1
 * @since 2020-07-31
 *
 */

public class Conector {

    private static AccesoBD coneccionBD = null;

    public static AccesoBD getConnector() throws Exception{

        String[] infoBD = Utils.getProperties();
        String URL =infoBD[0]+"//"+infoBD[1]+"/"+infoBD[2]+"?useSSL="+infoBD[5];
        String user =infoBD[3];
        String password = infoBD[4];
        if(coneccionBD == null){
            coneccionBD = new AccesoBD(URL,user,password);
        }
        return coneccionBD;
    }
}