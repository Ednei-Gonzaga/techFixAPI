package com.dev.ednei.techFixApi.model;

import com.dev.ednei.techFixApi.DTOS.supportContacts.SupportContactCreateDTO;
import com.dev.ednei.techFixApi.DTOS.supportContacts.SupportContactUpdateDTO;
import com.dev.ednei.techFixApi.model.enums.TypesContactsSupport;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Entity
@Table(name = "support_contacts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SupportContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypesContactsSupport type;

    private String contact;

    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public SupportContact(SupportContactCreateDTO contactDto) throws NumberParseException {
        this.type = TypesContactsSupport.toValue(contactDto.type());

        if (TypesContactsSupport.toValue(contactDto.type()) == TypesContactsSupport.EMAIL) {
            this.contact = contactDto.contact();
        } else {
            this.contact = PhoneNumberUtil.getInstance().parse(contactDto.contact(), "BR").toString().replaceAll("[^0-9]","");
        }
        this.description = contactDto.description();
        this.createdAt = LocalDateTime.now();
    }

    public void updateContact(SupportContactUpdateDTO contactDto) {
        if (StringUtils.hasText(contactDto.type())) {
            this.type = TypesContactsSupport.toValue(contactDto.type());
        }

        if (StringUtils.hasText(contactDto.description())) {
            this.description = contactDto.description();
        }

        if (StringUtils.hasText(contactDto.contact())) {
            this.contact = contactDto.contact();
        }
    }
}
