package client_config;

import gui.MainWindow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientSpringContext {

    @Bean
    public MainWindow mainWindow(){
        return new MainWindow();
    }
}
