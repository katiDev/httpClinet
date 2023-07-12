package ma.emsi.lpri.httpclient;

import ma.emsi.lpri.httpclient.controller.HDFSController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@SpringBootApplication
public class HttpClientApplication extends SpringBootServletInitializer implements CommandLineRunner {
    @Autowired
    HDFSController hdfsController;

    public static void main(String[] args) {
        SpringApplication.run(HttpClientApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HttpClientApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        //HttpRequest.newBuilder(request, (name, value) -> !name.equalsIgnoreCase("Foo-Bar"));

        //request.

        Object response =  hdfsController.createFile("file.txt");
        System.out.println(response);
     /*   System.out.println(response.body());
        System.out.println(response.statusCode());
        HttpHeaders headers =  response.headers();
        headers.allValues("location").get(0);
        String location = headers.allValues("location").get(0);
        System.out.println(headers.allValues("location").get(0));*/

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(response.toString()))
                .timeout(Duration.of(20, ChronoUnit.SECONDS))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

       HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    }
}
