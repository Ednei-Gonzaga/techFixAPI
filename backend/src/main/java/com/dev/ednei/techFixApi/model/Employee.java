package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.model.dataModeling.PeopleData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends PeopleData {

    private String email;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User idUser;

}
