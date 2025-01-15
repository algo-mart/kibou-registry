package com.algomart.kibouregistry.models.request;
import com.algomart.kibouregistry.entity.ContactInfo;
import com.algomart.kibouregistry.enums.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class ParticipantRequest {
    private String name;
    private Category category;

    @Valid
    @NotNull(message = "Contact information is required")
    @JsonProperty("contact_info")
    private ContactInfo contactInfo;
}
