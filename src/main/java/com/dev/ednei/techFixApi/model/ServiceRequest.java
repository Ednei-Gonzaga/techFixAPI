package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.DTOS.serviceRequest.ServiceRequestCreateDTO;
import com.dev.ednei.techFixApi.model.enums.CategoryDevice;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "service_requests")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String device;

    @Enumerated(value = EnumType.STRING)
    private CategoryDevice category;

    @Column(name = "problem_description")
    private String problemDescription;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @OneToMany(mappedBy = "serviceRequest")
    private List<ServiceOrder> serviceOrderList;


    public ServiceRequest(ServiceRequestCreateDTO serviceDTO) {
        this.device = serviceDTO.device();
        this.category = CategoryDevice.fromString(serviceDTO.category());
        this.problemDescription = serviceDTO.problemDescription();
        this.client = new Client(serviceDTO.client());
    }

    public ServiceRequest( Long id) {
        this.id = id;
    }
}
