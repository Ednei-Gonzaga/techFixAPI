package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.DTOS.serviceRequest.ServiceRequestCreateDTO;
import com.dev.ednei.techFixApi.DTOS.serviceRequest.ServiceRequestUpdateDTO;
import com.dev.ednei.techFixApi.model.enums.CategoryDevice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "service_requests")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ServiceRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String device;

    @Enumerated(EnumType.STRING)
    private CategoryDevice category;

    @Column(name = "problem_description")
    private String problemDescription;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @OneToMany(mappedBy = "serviceRequest")
    private List<ServiceOrder> serviceOrder;

    public ServiceRequests(ServiceRequestCreateDTO requestDto) {
        this.device = requestDto.device();
        this.category = CategoryDevice.toString(requestDto.category());
        this.problemDescription = requestDto.problemDescription();
        this.createdAt = LocalDateTime.now();
        this.client = new Client(requestDto.client());
    }

    public ServiceRequests(Long serviceRequestId) {
        this.id = serviceRequestId;
    }

    public void updateServiceRequest(ServiceRequestUpdateDTO requestDto) {
        if(StringUtils.hasText(requestDto.device())) {
            this.device = requestDto.device();
        }

        if(StringUtils.hasText(requestDto.category())) {
            this.category = CategoryDevice.toString(requestDto.category());
        }

        if(StringUtils.hasText(requestDto.problemDescription())) {
            this.problemDescription = requestDto.problemDescription();
        }

        if(requestDto.client() != null) {
            this.client = new Client(requestDto.client());
        }

    }
}
