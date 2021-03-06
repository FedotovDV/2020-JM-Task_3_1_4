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
        HttpEntity<String> request;
        String json;
        ResponseEntity<String> responseEntity;
        StringBuilder code = new StringBuilder();


        HttpHeaders httpHeaders = restTemplate.headForHeaders(url);
        String cookieHeader = httpHeaders.getFirst(HttpHeaders.SET_COOKIE);

        HttpHeaders myHeaders = new HttpHeaders();
        myHeaders.add(HttpHeaders.COOKIE, cookieHeader);
        myHeaders.add("content-type", "application/json");

        User user = new User((long) 3, "James", "Brown", (byte) 22);
        json = objectMapper.writeValueAsString(user);
        request = new HttpEntity<>(json, myHeaders);
        responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        code.append(responseEntity.toString().substring(5, 11));
        user.setName("Thomas");
        user.setLastName("Shelby");
        json = objectMapper.writeValueAsString(user);
        request = new HttpEntity<>(json, myHeaders);
        responseEntity = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        code.append(responseEntity.toString().substring(5, 11));

        responseEntity = restTemplate.exchange(url + "/" + user.getId(), HttpMethod.DELETE, request, String.class);
        code.append(responseEntity.toString().substring(5, 11));
        System.out.println(code);

    }

}
