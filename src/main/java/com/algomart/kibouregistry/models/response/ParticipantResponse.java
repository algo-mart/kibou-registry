package com.algomart.kibouregistry.models.response;
import com.algomart.kibouregistry.entity.ContactInfo;
import com.algomart.kibouregistry.entity.Participants;
import com.algomart.kibouregistry.enums.Category;
import lombok.Data;


@Data
public class ParticipantResponse {
    private Long participantId;
    private String name;
    private Category category;
    private ContactInfo contactInfo;

    public ParticipantResponse(Participants participants) {
        this.participantId = participants.getParticipantId();
        this.name = participants.getName();
        this.category = participants.getCategory();
        this.contactInfo = participants.getContactInfo();
    }
}
