package marchal.gabriel.bl.Email;
import marchal.gabriel.bl.OrdenDeCompra.OrdenDeCompra;
import marchal.gabriel.bl.Subasta.Subasta;
import marchal.gabriel.bl.Usuario.Usuario;
import org.apache.commons.mail.*;


public class EnviarEmail extends Secret {

    private String mensajeBienvenidaSbj = "Gracias por registrarse con Colectors Bazar!";
    private String seleccionModeradorSbj = "Usted ha sido seleccionado como moderador!";
    private String ganadorSubastaSbj = "Usted ha sido el ganador de una Subasta!";


    public HtmlEmail emailSetup() throws EmailException {
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        DefaultAuthenticator authenticator = new DefaultAuthenticator(getEmailSecret(), getContrasenaSecret());
        email.setAuthenticator(authenticator);
        email.setSSLOnConnect(true);
        email.setStartTLSRequired(true);
        email.setFrom(getEmailSecret());
        return email;
    }

    public EnviarEmail enviarEmailBienvenida(Usuario usuario) throws EmailException {
        HtmlEmail email = emailSetup();
        email.setSubject(mensajeBienvenidaSbj);
        email.setHtmlMsg(getMensajeBienvenidaMsg(usuario));
        email.addTo(usuario.getCorreoElectronico());
        email.send();
        System.out.println("EMAIL: enviarEmailBienvenida");
        return null;
    }
    public EnviarEmail enviarEmailModerador(Usuario usuario, Subasta subasta) throws EmailException {
        HtmlEmail email = emailSetup();
        email.setSubject(seleccionModeradorSbj);
        email.setHtmlMsg(getSeleccionModeradorMsg(usuario,subasta));
        email.addTo(usuario.getCorreoElectronico());
        email.send();
        System.out.println("EMAIL: enviarEmailModerador");
        return null;
    }
    public EnviarEmail enviarEmailadminSelectModerador(Usuario usuario)throws EmailException{
        HtmlEmail email = emailSetup();
        email.setSubject(seleccionModeradorSbj);
        email.setHtmlMsg(getAdminSelectModerador(usuario));
        email.addTo(usuario.getCorreoElectronico());
        email.send();
        System.out.println("EMAIL: enviarEmailadminSelectModerador");
        return null;
    }
    public EnviarEmail enviarEmailGanadorSubasta(Usuario usuario, Subasta subasta, OrdenDeCompra ordenDeCompra) throws EmailException {
        HtmlEmail email = emailSetup();
        email.setSubject(ganadorSubastaSbj);
        email.setHtmlMsg(getEnviarEmailGanadorSubasta(usuario,subasta,ordenDeCompra));
        email.addTo(usuario.getCorreoElectronico());
        email.send();
        System.out.println("EMAIL: enviarEmailGanadorSubasta");
        return null;
    }

    public String getMensajeBienvenidaMsg(Usuario usuario) {
        String msg = "<body style=\"background-color: #a1ebfe;\">" +
                "<div style=\"margin: 0,auto; width:200px; border-radius:10px;\">" +
                "<p>Hola <b>"+ usuario.getNombre()+"!</b></p>\n" +
                "<p>Gracias por registrarse con Colectors Bazar!</p></div></body>\n";

        return msg;
    }
    public String getSeleccionModeradorMsg(Usuario usuario, Subasta subasta){
        String msg = "<body style=\"background-color: #a1ebfe; margin: 0,auto; width:200px; border-radius:10px;\">" +
                "<p>Hola <b>\""+ usuario.getNombre()+"!</b></p>";
        msg += "<p>Usted ha sido seleccionado como moderador para la subasta<p>\n";
        msg += "<h2 style=\"margin: 0,auto; font-weight: bold; font-size:20px; \">"+subasta.getId()+"</h2></body>";

        return msg;
    }
    public String getAdminSelectModerador(Usuario usuario){
        String msg = "<body style=\"background-color: #a1ebfe; margin: 0,auto; width:200px; border-radius:10px;\">" +
                "<p>Felicidades <b>\""+ usuario.getNombre()+"!</b></p>";
        msg += "<p>Usted ha sido seleccionado como moderador para Collectors Bazar!<p>";

        return msg;
    }
    public String getEnviarEmailGanadorSubasta(Usuario usuario, Subasta subasta, OrdenDeCompra ordenDeCompra){
        String msg = "<body style=\"background-color: #a1ebfe; margin: 0,auto; width:200px; border-radius:10px;\">" +
                "<p>Hola <b>"+ usuario.getNombre()+"!</b></p>";
        msg += "<p>Usted ha sido el ganador de la subasta:<p>\n";
        msg += "<h2 style=\"margin: 0,auto; font-weight: bold; font-size:20px; \">"+subasta.getId()+"</h2>";
        msg += "<p>"+ordenDeCompra.toStringEmail()+"</p></body>";

        return msg;
    }
}