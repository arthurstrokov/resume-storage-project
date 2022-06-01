package com.gmail.arthurstrokov.resume.filter;

import com.gmail.arthurstrokov.resume.entity.Gender;
import lombok.Data;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
/**
 * SearchCriteria class for representation of a constraint
 *
 * @author Arthur Strokov
 */
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

    @SneakyThrows
    public void setValue(Object value) {
        String presentedValue = (String) value;
        if (presentedValue.matches("^\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12]\\d|3[01])$")) {
            this.value = new SimpleDateFormat("yyyy-MM-dd").parse(presentedValue);
        } else if (presentedValue.matches("^M(ALE)?$|^F(EMALE)?$")) {
            this.value = Gender.valueOf(presentedValue);
        } else {
            this.value = value;
        }
    }
}
