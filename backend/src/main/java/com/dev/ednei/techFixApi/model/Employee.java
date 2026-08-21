package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.DTOS.employees.EmployeeManagerUpdateDTO;
import com.dev.ednei.techFixApi.DTOS.employees.EmployeeProfileUpdateDTO;
import com.dev.ednei.techFixApi.DTOS.user.UserCreateDTO;
import com.dev.ednei.techFixApi.model.dataModeling.PeopleData;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.flywaydb.core.internal.util.StringUtils;

@Entity
@Table(name = "employees")
@Getter
@NoArgsConstructor
public class Employee extends PeopleData {

    private String email;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;

    public Employee(UserCreateDTO userCreateDTO, User user) throws NumberParseException {
        super(userCreateDTO.name(), userCreateDTO.cpf().replaceAll("[^0-9]", ""), userCreateDTO.phone(), userCreateDTO.whatsapp());
        this.email = userCreateDTO.email().toLowerCase();
        this.user = user;
    }


    public void updateById(EmployeeManagerUpdateDTO dto) throws NumberParseException {
        if (StringUtils.hasText(dto.name())) {
            this.setName(dto.name());
        }
        if (StringUtils.hasText(dto.cpf())) {
            this.setCpf(dto.cpf().replaceAll("[^0-9]", ""));
        }
        if (StringUtils.hasText(dto.phone())) {
            this.setPhone(dto.phone().replaceAll("[^0-9]", ""));
        }
        if (StringUtils.hasText(dto.whatsapp())) {
            this.setWhatsapp(dto.whatsapp().replaceAll("[^0-9]", ""));
        }
        if (StringUtils.hasText(dto.email())) {
            this.email = dto.email();

        }
    }

    public void updateByEmployeeLogged(EmployeeProfileUpdateDTO dto) throws NumberParseException {
        if (StringUtils.hasText(dto.phone())) {
            this.setPhone(dto.phone());
        }
        if (StringUtils.hasText(dto.whatsapp())) {
            this.setWhatsapp(dto.whatsapp());
        }
        if (StringUtils.hasText(dto.email())) {
            this.email = dto.email();

        }
    }

}
