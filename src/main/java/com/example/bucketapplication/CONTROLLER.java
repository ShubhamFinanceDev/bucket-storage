package com.example.bucketapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@RestController
public class CONTROLLER {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file")MultipartFile file){

        try {
            fileUploadService.upload(file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }}

        @GetMapping("/tocken")
        public String tokenGenarte() throws IOException, InterruptedException, URISyntaxException {
            String clientId = "e219b8606e50415a8d829775480e0ae1";
            String clientSecret = "336034ea-bb34-4b70-89e2-29b8f2d22054";
            String tokenUrl = "https://idcs-637f7bc3651c4bf9a7bbbbe7d14720c7.identity.oraclecloud.com/oauth2/v1/token"; // Replace with your token URL

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("grant_type", "client_credentials");
            requestBody.put("client_id", clientId);
            requestBody.put("client_secret", clientSecret);
            requestBody.put("token_name","cr");
            requestBody.put("scope","https://5E5713B76042478A93A9EABFB113747F.integration.eu-frankfurt-1.ocp.oraclecloud.com:443urn:opc:resource:consumer::all");

            StringJoiner sj = new StringJoiner("&");
            for (Map.Entry<String, String> entry : requestBody.entrySet()) {
                sj.add(entry.getKey() + "=" + entry.getValue());
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(tokenUrl))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(sj.toString()))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Access Token Response: " + response.body());
            } else {
                System.out.println("Failed to get token. Status: " + response.statusCode());
                System.out.println("Response: " + response.body());
            }

            return "success";
        }

}
