package ma.emsi.lpri.httpclient.controller;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@RestController
public class HDFSController implements Serializable {
    @PutMapping(value = "/add/file/{filename}")
    public Object  createFile(@PathVariable String filename) throws URISyntaxException, IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:9870/webhdfs/v1/user/root/"+filename+"?op=CREATE"))
                .timeout(Duration.of(20, ChronoUnit.SECONDS))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).headers().allValues("location").get(0);
    }
    /*DONE*/
    @PutMapping("/add/directory/{directoryName}")
    public Object createDirectory(@PathVariable String directoryName) throws URISyntaxException, IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:9870/webhdfs/v1/user/root/"+directoryName+"?op=MKDIRS&permission=755"))
                .timeout(Duration.of(20, ChronoUnit.SECONDS))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
        //return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }
    /*DONE*/
    @DeleteMapping("/delete/{filename}")
    public Object deleteFile(@PathVariable String filename) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:9870/webhdfs/v1/user/root/"+filename+"?op=DELETE"))
                .timeout(Duration.of(20, ChronoUnit.SECONDS))
                .DELETE()
                .build();

        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
    /*DONE*/
    @DeleteMapping("/delete/dir/{directoryName}")
    public Object deleteDirectory(@PathVariable String directoryName) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:9870/webhdfs/v1/user/root/"+directoryName+"?op=DELETE"))
                .timeout(Duration.of(20, ChronoUnit.SECONDS))
                .DELETE()
                .build();

        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
    /*DONE*/
    @GetMapping("/directories/{path}")
    public Object listDirectory(@PathVariable String path) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:9870/webhdfs/v1/"+path+"?op=LISTSTATUS"))
                    .timeout(Duration.of(20, ChronoUnit.SECONDS))
                    .GET()
                    .build();

        HttpResponse response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
    /*DONE*/
    @GetMapping("/directories")
    public Object listDirectory() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:9870/webhdfs/v1/?op=LISTSTATUS"))
                .timeout(Duration.of(20, ChronoUnit.SECONDS))
                .GET()
                .build();

        return  HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
    /*DONE*/
    @GetMapping("/status/{path}/{filename}")
    @ResponseBody
    public Object getFileStatus(@PathVariable String path, @PathVariable String filename) throws URISyntaxException, IOException, InterruptedException, ClassNotFoundException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:9870/webhdfs/v1/"+path+"/"+filename+"?op=GETFILESTATUS"))
                .timeout(Duration.of(20, ChronoUnit.SECONDS))
                .GET()
                .build();

        HttpResponse response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        //Gson gson= new Gson();
        //return gson.fromJson(new JSONObject(response.body().toString()).toString(),FileStatus.class);
        //return new JSONObject(response.body().toString());
        return response.body();
    }

    //public void rename(){}
    @GetMapping("/read/{filename}")
    public void readFile(){

    }

}
