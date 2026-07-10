package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.DTOS.serviceOrder.ServiceOrderDetailDTO;
import com.dev.ednei.techFixApi.model.ServiceOrder;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.model.enums.CategoryDevice;
import com.dev.ednei.techFixApi.model.enums.ServiceOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {


    boolean existsByIdentificationCode(String code);

    @Query("""
                SELECT so.id, cl.name, cl.cpf, cl.phone, cl.whatsapp, 
                        (SELECT e.name FROM Employee e WHERE e.user = so.userTechnical), sr.device, sr.category, sr.problemDescription, so.identificationCode, so.status,
                        so.dateTimeStart, so.dateTimeCompleted, so.dateTimeUpdateStatus,so.userTechnical.id, sr.id
                FROM ServiceOrder so
                JOIN so.serviceRequest sr
                JOIN sr.client cl
                WHERE so.id = :idServiceOrder
            """)
    Optional<ServiceOrderDetailDTO> getDetailsById(@Param("idServiceOrder") Long idServiceOrder);


    @Query("""
                 SELECT so.id, cl.name, cl.cpf, cl.phone, cl.whatsapp, 
                        (SELECT e.name FROM Employee e WHERE e.user = so.userTechnical), sr.device, sr.category, sr.problemDescription, so.identificationCode, so.status,
                        so.dateTimeStart, so.dateTimeCompleted, so.dateTimeUpdateStatus,so.userTechnical.id, sr.id
                FROM ServiceOrder so
                JOIN so.serviceRequest sr
                JOIN sr.client cl
                WHERE so.identificationCode = :code
            """)
    Optional<ServiceOrderDetailDTO> getDetailsByCode(@Param("code") String code);


    @Query("""
                 SELECT so.id, cl.name, cl.cpf, cl.phone, cl.whatsapp, 
                        (SELECT e.name FROM Employee e WHERE e.user = so.userTechnical), sr.device, sr.category, sr.problemDescription, so.identificationCode, so.status,
                        so.dateTimeStart, so.dateTimeCompleted, so.dateTimeUpdateStatus, so.userTechnical.id, sr.id
                FROM ServiceOrder so
                JOIN so.serviceRequest sr
                JOIN sr.client cl
                WHERE so.status in :status 
                AND sr.category in :category
                ORDER BY so.dateTimeStart ASC
            """)
    Page<ServiceOrderDetailDTO> findAllOrAllByStatusActivesAndCategory(@Param("status") List<ServiceOrderStatus> status, @Param("category") List<CategoryDevice> category, Pageable pageable);// Busca todos por status ATIVOS, Por ordem e de começo

    @Query("""
                 SELECT so.id, cl.name, cl.cpf, cl.phone, cl.whatsapp, 
                        (SELECT e.name FROM Employee e WHERE e.user = so.userTechnical), sr.device, sr.category, sr.problemDescription, so.identificationCode, so.status,
                        so.dateTimeStart, so.dateTimeCompleted, so.dateTimeUpdateStatus,so.userTechnical.id,  sr.id
                FROM ServiceOrder so
                JOIN so.serviceRequest sr
                JOIN sr.client cl
                 WHERE so.status in :status 
                AND sr.category in :category
                ORDER BY so.dateTimeStart DESC
            
            """)
    Page<ServiceOrderDetailDTO> findAllOrAllByStatusFinishAndCategory(@Param("status") List<ServiceOrderStatus> status, @Param("category") List<CategoryDevice> category, Pageable pageable);// Busca status dos FINALIZADOS mpor mais recentes

    @Query("""
                 SELECT so.id, cl.name, cl.cpf, cl.phone, cl.whatsapp, 
                        (SELECT e.name FROM Employee e WHERE e.user = so.userTechnical), sr.device, sr.category, sr.problemDescription, so.identificationCode, so.status,
                        so.dateTimeStart, so.dateTimeCompleted, so.dateTimeUpdateStatus,so.userTechnical.id,  sr.id
                FROM ServiceOrder so
                JOIN so.serviceRequest sr
                JOIN sr.client cl
                 WHERE so.status in :status 
                AND sr.category in :category
                AND so.userTechnical = :userTechnical
                ORDER BY so.dateTimeStart 
            
            """)
    Page<ServiceOrderDetailDTO> findAllOrAllByStatusActivesAndCategoryMyTask(@Param("status") List<ServiceOrderStatus> status, @Param("category") List<CategoryDevice> category, @Param("userTechnical") User userTechnical, Pageable pageable);

    @Query("""
                 SELECT so.id, cl.name, cl.cpf, cl.phone, cl.whatsapp, 
                        (SELECT e.name FROM Employee e WHERE e.user = so.userTechnical), sr.device, sr.category, sr.problemDescription, so.identificationCode, so.status,
                        so.dateTimeStart, so.dateTimeCompleted, so.dateTimeUpdateStatus, so.userTechnical.id, sr.id
                FROM ServiceOrder so
                JOIN so.serviceRequest sr
                JOIN sr.client cl
                 WHERE so.status in :status 
                AND sr.category in :category
                AND so.userTechnical = :userTechnical
                ORDER BY so.dateTimeStart DESC
            
            """)
    Page<ServiceOrderDetailDTO> findAllOrAllByStatusFinishAndCategoryMyTask(@Param("status") List<ServiceOrderStatus> status, @Param("category") List<CategoryDevice> category, @Param("userTechnical") User userTechnical, Pageable pageable);


}
