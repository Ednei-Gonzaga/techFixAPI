package com.dev.ednei.techFixApi.controller;

import com.dev.ednei.techFixApi.DTOS.supportContacts.SupportContactCreateDTO;
import com.dev.ednei.techFixApi.DTOS.supportContacts.SupportContactFullDTO;
import com.dev.ednei.techFixApi.DTOS.supportContacts.SupportContactUpdateDTO;
import com.dev.ednei.techFixApi.repository.SupportContactRepository;
import com.dev.ednei.techFixApi.service.SupportContactService;
import com.dev.ednei.techFixApi.service.externalApis.evolutionApi.WhatsAppMessagesUtil;
import com.google.i18n.phonenumbers.NumberParseException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class SupportContactController {
    @Autowired
    private SupportContactService supportContactService;

    @PostMapping("/support-contact")
    public ResponseEntity<SupportContactFullDTO> saveContact(@RequestBody @Valid SupportContactCreateDTO dto) throws NumberParseException {
        var contact = supportContactService.saveSupportContact(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(contact);
    }

    @PutMapping("/support-contact/{id}")
    public ResponseEntity<SupportContactFullDTO> updateContact(@PathVariable Long id, @RequestBody  SupportContactUpdateDTO dto) throws NumberParseException {
        var contact = supportContactService.updateSupportContact(id, dto);
        return ResponseEntity.ok(contact);
    }

    @DeleteMapping("/support-contact/{id}")
    public ResponseEntity deleteContact(@PathVariable Long id) throws NumberParseException {
        supportContactService.deleteSupportContact(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    private SupportContactRepository  supportContactRepository;

    @GetMapping("/support-contact")
    public ResponseEntity<List<SupportContactFullDTO>> findAllSupportContacts() {
        return ResponseEntity.ok(supportContactService.findAllSupportContacts());
    }

}
