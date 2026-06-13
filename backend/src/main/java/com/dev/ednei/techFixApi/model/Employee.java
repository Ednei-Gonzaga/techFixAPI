package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.DTOS.user.UserCreateDTO;
import com.dev.ednei.techFixApi.model.dataModeling.PeopleData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Getter
@NoArgsConstructor
public class Employee extends PeopleData {

    private String email;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;

    public Employee(UserCreateDTO userCreateDTO, User user) {
        super(userCreateDTO.name(), userCreateDTO.cpf(), userCreateDTO.phone(), userCreateDTO.whatsapp());
        this.email = userCreateDTO.email();
        this.user = user;
    }


}
