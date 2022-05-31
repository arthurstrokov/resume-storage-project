package com.gmail.arthurstrokov.resume.filter;

import com.gmail.arthurstrokov.resume.entity.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
    private boolean orPredicate;

    public SearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        setValue(value);
    }

    public void setValue(Object value) {
        String presentedValue = (String) value;
        if (presentedValue.matches("\\d{4}-\\d{2}-{2}")) {
            this.value = LocalDate.parse(presentedValue);
        } else if (presentedValue.matches("^M(ALE)?$|^F(EMALE)?$")) {
            this.value = Gender.valueOf(presentedValue);
        } else {
            this.value = value;
        }
    }
}
