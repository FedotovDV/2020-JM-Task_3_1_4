package ru.javamentor.task_3_1_4;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.javamentor.task_3_1_4.model.User;


@SpringBootApplication
public class Task314Application {




    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(Task314Application.class, args);

        ObjectMapper objectMapper = new ObjectMapper();
        final String url = "http://91.241.64.178:7081/api/users";

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response
                = restTemplate.getForEntity(url, String.class);
        HttpHeaders httpHeaders = restTemplate.headForHeaders(url);
        System.out.println(response.toString());
//        HttpHeaders httpHeaders = response.getHeaders();
//        HttpHeaders httpHeaders = restTemplate.headForHeaders(url);
        String cookieHeader = httpHeaders.getFirst(HttpHeaders.SET_COOKIE);
        System.out.println(cookieHeader);
        HttpHeaders myHeaders = new HttpHeaders();
        myHeaders.add(HttpHeaders.COOKIE, cookieHeader );
        myHeaders.add("content-type", "application/json");
        User user = new User((long) 3,"James","Brown", (byte) 22);
        String json = objectMapper.writeValueAsString(user);
        System.out.println(json);
        HttpEntity<String> request = new HttpEntity<>(json,myHeaders);
        System.out.println(request.toString());

        ResponseEntity<String> responsePost = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        System.out.println(responsePost.toString());

    }

}
