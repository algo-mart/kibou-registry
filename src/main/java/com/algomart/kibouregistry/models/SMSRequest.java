package com.algomart.kibouregistry.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SMSRequest {

    @NotBlank
    private String phoneNumbers;

    @NotBlank
    private String message;

    public SMSRequest(@JsonProperty("phoneNumber") String phoneNumbers,
                      @JsonProperty("message") String message) {
        this.phoneNumbers = phoneNumbers;
        this.message = message;

    }

}
