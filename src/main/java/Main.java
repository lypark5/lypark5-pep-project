import Controller.SocialMediaController;
import io.javalin.Javalin;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();

        // I am adding a global exception handler here
        app.exception(Exception.class, (e, ctx) -> {
            ctx.status(500).json("Internal Server Error: " + e.getMessage());
        });

        app.start(8080);
    }
}
