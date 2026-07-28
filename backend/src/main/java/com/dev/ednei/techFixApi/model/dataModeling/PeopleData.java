package com.dev.ednei.techFixApi.model.dataModeling;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class PeopleData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cpf;

    private String phone;

    private String whatsapp;

    public PeopleData(String name, String cpf, String phone, String whatsapp) throws NumberParseException {

        this.name = name;
        this.cpf = cpf;
        this.phone = convertNationalStandardPhone(phone);
        this.whatsapp = convertNationalStandardPhone(whatsapp);
    }

    private String convertNationalStandardPhone(String phone) throws NumberParseException {
        PhoneNumberUtil util = PhoneNumberUtil.getInstance();

        var parsePhone = util.parse(phone, "BR");
        var formatCorrect =  util.format(parsePhone, PhoneNumberUtil.PhoneNumberFormat.E164).replaceAll("[^0-9]", "");

        return formatCorrect;
    }
}
