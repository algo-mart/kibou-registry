package com.algomart.kibouregistry.models.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class APIResponse {
    private String status;
    private String message;
    private Object data;

    public APIResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}