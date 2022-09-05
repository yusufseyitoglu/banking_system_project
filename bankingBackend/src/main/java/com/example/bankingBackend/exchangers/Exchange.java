package com.example.bankingBackend.exchangers;

import java.net.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


import com.example.bankingBackend.enums.AccountType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
public class Exchange implements IExchange{
	
    @Autowired
    private RestTemplate restTemplate;
	
    @Override
    public double exchange(double amount, AccountType base, AccountType to) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "application/json");
        headers.set("authorization", "apikey 43b4e427d411fc7344ad8992fc01d504");
        HttpEntity<String> model = new HttpEntity<>(headers);
        ResponseEntity<CollectApiResponse> collectApiResponse = restTemplate.exchange("https://api.collectapi.com/economy/exchange?int=" + amount + "&to=" + to + "&base=" + base,
                HttpMethod.GET, model,CollectApiResponse.class);
        return collectApiResponse.getBody().getResult().getData().stream().findFirst().get().getCalculated();

    }

}
