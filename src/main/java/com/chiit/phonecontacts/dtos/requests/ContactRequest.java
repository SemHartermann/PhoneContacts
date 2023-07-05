package com.chiit.phonecontacts.dtos.requests;

import com.chiit.phonecontacts.validations.EmailPattern;
import com.chiit.phonecontacts.validations.PhoneNumberPattern;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequest {

    @NotBlank(message = "name must not be blank")
    private String name;

    private Set<String> emails;

    private Set<String> phoneNumbers;
}
