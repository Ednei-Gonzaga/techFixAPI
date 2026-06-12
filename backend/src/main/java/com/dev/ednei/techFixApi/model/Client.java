package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.model.dataModeling.PeopleData;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
public class Client extends PeopleData {
    @OneToMany(mappedBy = "client")
    private List<ServiceRequests> serviceRequests;

}
