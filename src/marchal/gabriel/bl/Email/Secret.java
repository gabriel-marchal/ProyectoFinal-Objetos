package marchal.gabriel.bl.Email;

public class Secret {
    private final String emailSecret;
    private final String contrasenaSecret;
    private final String DBpassword;

    public Secret() {
        emailSecret = "epoxyfish3@gmail.com";
        contrasenaSecret = "proyecto@1!";
        DBpassword = "CB!123456";
    }

    public String getEmailSecret() {
        return emailSecret;
    }

    public String getContrasenaSecret() {
        return contrasenaSecret;
    }

}
