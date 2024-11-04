package com.algomart.kibouregistry.models.request;

import com.algomart.kibouregistry.entity.ContactInfo;
import com.algomart.kibouregistry.enums.Category;
import lombok.Data;

@Data
public class ParticipantRequest {
    private String name;
    private Category category;
    private ContactInfo contactInfo;
}
