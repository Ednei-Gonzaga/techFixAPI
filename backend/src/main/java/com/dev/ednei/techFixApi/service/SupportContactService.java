package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.DTOS.supportContacts.SupportContactCreateDTO;
import com.dev.ednei.techFixApi.DTOS.supportContacts.SupportContactFullDTO;
import com.dev.ednei.techFixApi.DTOS.supportContacts.SupportContactUpdateDTO;
import com.dev.ednei.techFixApi.infra.exceptions.errors.EntityNotFoundException;
import com.dev.ednei.techFixApi.infra.exceptions.errors.InvalidParameterException;
import com.dev.ednei.techFixApi.model.SupportContact;
import com.dev.ednei.techFixApi.model.enums.TypesContactsSupport;
import com.dev.ednei.techFixApi.repository.SupportContactRepository;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import jakarta.transaction.Transactional;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SupportContactService {
    @Autowired
    private SupportContactRepository supportContactRepository;

    //metodos para ROTAS

    @Transactional
    public SupportContactFullDTO saveSupportContact(SupportContactCreateDTO contactDto) throws NumberParseException {
        if (TypesContactsSupport.toValue(contactDto.type()) == null) {
            throw new InvalidParameterException("Tipo de contato informado com valor '" + contactDto.type() + "' está incorreto");
        }

        if(!verificationContactIsValid(contactDto.type(), contactDto.contact())){
            throw new InvalidParameterException("O contato informado não é valido ou não existe para o tipo " + contactDto.type());
        }

        if(supportContactRepository.existsByContact(contactDto.contact())){
            throw new EntityNotFoundException("Já existe contato com valor informado '" + contactDto.contact() + "'");
        }

        SupportContact supportContact = new SupportContact(contactDto);
        supportContactRepository.save(supportContact);

        return new SupportContactFullDTO(supportContact);
    }

    @Transactional
    public SupportContactFullDTO updateSupportContact(Long id, SupportContactUpdateDTO contactDto) throws NumberParseException {
        var supportContact = findByIdContact(id);

        if (StringUtils.hasText(contactDto.type()) && TypesContactsSupport.toValue(contactDto.type()) == null) {
            throw new InvalidParameterException("Tipo de contato informado com valor '" + contactDto.type() + "' está incorreto");
        }

        if((supportContactRepository.existsByContact(contactDto.contact())) && !contactDto.contact().equalsIgnoreCase(supportContact.getContact())){
            throw new EntityNotFoundException("Já existe contato com valor informado '" + contactDto.contact() + "'");
        }


        //Se Type não for nulo e contato for
        if(StringUtils.hasText(contactDto.type()) && !StringUtils.hasText(contactDto.contact())) {
            if(!verificationContactIsValid(contactDto.type(), supportContact.getContact())){
                throw new InvalidParameterException("O cantato cadastrado com valor '" + supportContact.getContact() + "' não e valido ou não existe para o novo tipo informado '" + contactDto.type() + "'");
            };
        }

        //Se Type e contact nao for nulos
        if(StringUtils.hasText(contactDto.type()) && StringUtils.hasText(contactDto.contact())) {
            if(!verificationContactIsValid(contactDto.type(), contactDto.contact())){
               throw new InvalidParameterException("O contato " + contactDto.contact() + " não é válido ou não existe do tipo informado '" + contactDto.type() + "'" );
            };
        }

        //Se Type for nulo, mas contact não
        if(!StringUtils.hasText(contactDto.type()) && StringUtils.hasText(contactDto.contact())) {
            if(!verificationContactIsValid(supportContact.getType().name(), contactDto.contact())){
                throw new InvalidParameterException("O novo contato informado " +contactDto.contact() + " não corresponde ao tipo '" +supportContact.getType()+"' já cadastrado" );
            };
        }

        supportContact.updateContact(contactDto);
        supportContactRepository.save(supportContact);

        return new SupportContactFullDTO(supportContact);
    }

    @Transactional
    public void deleteSupportContact(Long id) throws EntityNotFoundException {
        var supportContact = findByIdContact(id);
        supportContactRepository.delete(supportContact);
    }

    public List<SupportContactFullDTO> findAllSupportContacts() {
        var listSupport = supportContactRepository.findAll();
        return listSupport.stream().map(SupportContactFullDTO::new).toList();
    }

    //Metodos privados
    private Boolean verificationContactIsValid(String type, String contact) throws NumberParseException {
        if (TypesContactsSupport.toValue(type) == TypesContactsSupport.PHONE) {
           try {
               PhoneNumberUtil util = PhoneNumberUtil.getInstance();
               var parseNumber = util.parse(contact, "BR");

               if (util.isValidNumber(parseNumber)) {
                   return true;
               }
           } catch (NumberParseException e) {
               return false;
           }
        }

        if (TypesContactsSupport.toValue(type) == TypesContactsSupport.WHATSAPP) {
            try {
                PhoneNumberUtil util = PhoneNumberUtil.getInstance();
                var parseNumber = util.parse(contact, "BR");

                if (util.isValidNumber(parseNumber) && util.getNumberType(parseNumber) == PhoneNumberUtil.PhoneNumberType.MOBILE) {
                    return true;
                }
            }catch (NumberParseException e) {
                return false;
            }
        }

        if (TypesContactsSupport.toValue(type) == TypesContactsSupport.EMAIL) {
            if (EmailValidator.getInstance().isValid(contact)) {
                return true;
            }
        }

        return false;
    }

    private SupportContact findByIdContact(Long id) {
        var supportContact = supportContactRepository.findById(id);

        if(supportContact.isEmpty()){
            throw  new EntityNotFoundException("Contado de Suporte com ID " + id + " não encontrado" );
        }

        return supportContact.get();
    }
}
