/*package dev.danvega.h2demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class H2DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(H2DemoApplication.class, args);
    }

}
*/
package dev.danvega.h2demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class H2DemoApplication {

    public static void main(String[] args) { // <-- ¡ESTA ES LA FIRMA CORRECTA!
        SpringApplication.run(H2DemoApplication.class, args);
    }

}