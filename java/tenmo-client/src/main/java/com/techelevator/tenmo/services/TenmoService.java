package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.view.ConsoleService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TenmoService {

    private String BASE_URL;
    //private ConsoleService console = new ConsoleService();
    private RestTemplate restTemplate = new RestTemplate();

    public TenmoService(String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }

    public Balance getBalance(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(httpHeaders);

        Balance balance = restTemplate.exchange(BASE_URL + "/balance",
                HttpMethod.GET, entity, Balance.class).getBody();

        return balance;
    }

    public void makeTransfer(String token) {


    }


}
