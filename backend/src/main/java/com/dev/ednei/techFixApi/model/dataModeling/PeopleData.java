package com.dev.ednei.techFixApi.model.dataModeling;

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

    public PeopleData(String name, String cpf, String phone, String whatsapp){
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.whatsapp = whatsapp;
    }
}
