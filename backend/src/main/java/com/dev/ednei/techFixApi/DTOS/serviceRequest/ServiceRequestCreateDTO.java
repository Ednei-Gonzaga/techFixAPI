package com.dev.ednei.techFixApi.DTOS.serviceRequest;


import com.dev.ednei.techFixApi.model.ServiceRequests;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServiceRequestCreateDTO(

        @NotBlank
        String Device,

        @NotBlank
        String category,

        @NotBlank
        String problemDescription,

        @NotNull
        Long client

) {
        public ServiceRequestCreateDTO(ServiceRequests serviceRequests) {
                this(serviceRequests.getDevice(), serviceRequests.getCategory().name(), serviceRequests.getProblemDescription(), serviceRequests.getClient().getId());
        }
}
