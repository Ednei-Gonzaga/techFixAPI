package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.model.enums.CategoryDevice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "service_requests")
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String device;

    private CategoryDevice category;

    @Column(name = "problem_description")
    private String problemDescription;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client idClient;

    @OneToMany(mappedBy = "idServiceRequest")
    private List<ServiceOrder> serviceOrder;

}
