package marchal.gabriel.bl.Usuario;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

import static java.lang.StrictMath.abs;

public class Usuario {
    private String nombre;
    private String identificacion;
    private LocalDate fechaNacimiento;
    private int edad;
    private String correoElectronico;
    private String contrasena;
    private boolean estado;
    private String avatar;

    public Usuario() {
    }

    public Usuario(String nombre, String identificacion, LocalDate fechaNacimiento, String correoElectronico, String contrasena, boolean estado, String avatar) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = getEdad();
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.estado = estado;
        this.avatar = avatar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        this.edad = getEdad();
    }

    public int getEdad() {
        Period period = Period.between(LocalDate.now(), fechaNacimiento);
        return abs(period.getYears());
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        String estadoString;
        if(estado) {
            estadoString = "Activo";
        }else{
            estadoString = "Inactivo";
        }

        return nombre + ", Identificacion: " + identificacion + ", DOB: " +fechaNacimiento +", Edad: "+ edad +", "+
                 correoElectronico + ", " + estadoString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return getCorreoElectronico().equals(usuario.getCorreoElectronico());
    }



    @Override
    public int hashCode() {
        return Objects.hash(getCorreoElectronico(), getContrasena());
    }
}
