package com.maybank.demo.DemoRestControllerSimulator.util;

import com.maybank.demo.DemoRestControllerSimulator.model.User;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApiUtil {

    @Setter
    private WebClient webClient;

    private String salesLoyaltyUrl = "http://localhost:8080/api/users";

    @Autowired
    public ApiUtil(WebClient webClient) {
        this.webClient = webClient;
    }


    public void callPostApi(User user) {
        if (user == null) {
            throw new NullPointerException("User can not be null");
        } else {

            this.webClient
                    .post()
                    .uri(salesLoyaltyUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(user), User.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnSuccess(x -> {
                        System.out.println("x.getBytes() ---> " + x);

                    })
                    .doOnError(err -> {
                        System.out.println("error");
                    })
                    .subscribe();
        }
    }

}
