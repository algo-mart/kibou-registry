package com.algomart.kibouregistry.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EmailRequest {

    private List<String> emailAddresses;
    private String subject;
    private String message;

}
