package com.algomart.kibouregistry.models.response;
import com.algomart.kibouregistry.entity.ContactInfo;
import com.algomart.kibouregistry.entity.User;
import com.algomart.kibouregistry.enums.Category;
import lombok.Data;
@Data
public class UserResponse {
    private Long userId;
    private String name;
    private Category category;
    private ContactInfo contactInfo;
    public UserResponse(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.category = user.getCategory();
        this.contactInfo = user.getContactInfo();
    }
}
