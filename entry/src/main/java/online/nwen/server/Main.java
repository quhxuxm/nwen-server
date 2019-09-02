package online.nwen.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Main extends SpringBootServletInitializer {
    public static void main(String[] args) {
        System.out.println("Nwen Service begin to start... \n\n");
        SpringApplication.run(Main.class, args);
        System.out.println(" \n\nNwen Service started.  \n\n");
    }
}
