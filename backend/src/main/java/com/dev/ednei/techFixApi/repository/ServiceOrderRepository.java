package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderDetailDTO;
import com.dev.ednei.techFixApi.model.ServiceOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {


    boolean existsByIdentificationCode(String code);

    @Query("""
        SELECT so.id, cl.name, cl.cpf, cl.phone, cl.whatsapp, 
                (SELECT e.name FROM Employee e WHERE e.user = so.userTechnical), sr.device, sr.category, sr.problemDescription, so.identificationCode, so.status,
                so.dateTimeStart, so.dateTimeCompleted, so.dateTimeUpdateStatus, sr.id
        FROM ServiceOrder so
        JOIN so.serviceRequest sr
        JOIN sr.client cl
        WHERE so.id = :idServiceOrder
    """)
    Optional<ServiceOrderDetailDTO> getDetailsById(@Param("idServiceOrder") Long idServiceOrder);


    @Query("""
         SELECT so.id, cl.name, cl.cpf, cl.phone, cl.whatsapp, 
                (SELECT e.name FROM Employee e WHERE e.user = so.userTechnical), sr.device, sr.category, sr.problemDescription, so.identificationCode, so.status,
                so.dateTimeStart, so.dateTimeCompleted, so.dateTimeUpdateStatus, sr.id
        FROM ServiceOrder so
        JOIN so.serviceRequest sr
        JOIN sr.client cl
        WHERE so.identificationCode = :code
    """)
    Optional<ServiceOrderDetailDTO> getDetailsByCode(@Param("code")String code);

    @Query("""
         SELECT so.id, cl.name, cl.cpf, cl.phone, cl.whatsapp, 
                (SELECT e.name FROM Employee e WHERE e.user = so.userTechnical), sr.device, sr.category, sr.problemDescription, so.identificationCode, so.status,
                so.dateTimeStart, so.dateTimeCompleted, so.dateTimeUpdateStatus, sr.id
        FROM ServiceOrder so
        JOIN so.serviceRequest sr
        JOIN sr.client cl
    """)
   Page<ServiceOrderDetailDTO> findAllByOrder(Pageable pageable);
    // terminar AMANHÂ
}
