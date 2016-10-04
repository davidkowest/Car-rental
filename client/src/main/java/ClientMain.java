import com.epam.carrental.config.Config;
import gui.MainWindow;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientMain {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        MainWindow mainWindow = (MainWindow)ctx.getBean(MainWindow.class);
        mainWindow.initGUI();
    }

}
