package marchal.gabriel.bl.Administrador;

import marchal.gabriel.bl.Usuario.Usuario;

import java.time.LocalDate;

import static java.lang.StrictMath.abs;

/**
 * @author Gabriel Marchal
 * @version V1
 * @since 2020-06-18
 *
 */


public class Administrador extends Usuario {

    /**
     * Constructor simple del objeto Administrador
     */
    public Administrador() {
    }

    /**
     * Constructor  del objeto Administrador que toma:
     * @param nombre
     * @param identificacion
     * @param fechaNacimiento
     * @param correoElectronico
     * @param contrasena
     * @param estado
     * @param avatar
     */
    public Administrador(String nombre, String identificacion, LocalDate fechaNacimiento, String correoElectronico, String contrasena, boolean estado, String avatar) {
        super(nombre, identificacion, fechaNacimiento, correoElectronico, contrasena, estado, avatar);
    }
}
