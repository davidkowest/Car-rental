import client_config.ClientSpringContext;
import gui.MainWindow;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientMain {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ClientSpringContext.class);
        MainWindow mainWindow = (MainWindow)ctx.getBean(MainWindow.class);
        mainWindow.initGUI();
    }

}
