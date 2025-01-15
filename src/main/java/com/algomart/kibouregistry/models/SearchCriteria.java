package com.algomart.kibouregistry.models;
import com.algomart.kibouregistry.enums.SearchOperation;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Data
public class SearchCriteria {
    private String key;
    private Object value;
    private SearchOperation operation;
    public SearchCriteria(String key, Object value, SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }
}