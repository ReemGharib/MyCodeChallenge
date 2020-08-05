package com.retail.discount.service;

import com.retail.discount.dtos.InvoiceDTO;
import com.retail.discount.dtos.UserDTO;
import com.retail.discount.dtos.UserTypeDTO;
import com.retail.discount.entity.Invoice;
import com.retail.discount.entity.User;
import com.retail.discount.repository.InvoiceRepository;
import com.retail.discount.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class InvoiceService {
    @Autowired
    InvoiceRepository repository;
    @Autowired
    UserRepository userRepository;

    public List<InvoiceDTO> getAll(){
        List<Invoice> invoiceList=invoiceList=repository.findAll();
        return invoiceList.stream().map(invoice -> {
            return InvoiceDTO.builder()
                             .id(invoice.getId())
                             .totalPrice(invoice.getTotalPrice())
                             .priceAfterDiscount(invoice.getPriceAfterDiscount())
                              .userDTO(UserDTO.builder()
                                        .id(invoice.getUser().getId())
                                        .phone(invoice.getUser().getPhone())
                                        .firstName(invoice.getUser().getFirstName())
                                        .email(invoice.getUser().getEmail())
                                        .lastName(invoice.getUser().getLastName())
                                        .startDate(invoice.getUser().getStartDate())
                                        .userTypeDTO(UserTypeDTO.builder()
                                                .id(invoice.getUser().getUserType().getId())
                                                .type(invoice.getUser().getUserType().getType()).build()).build())
                             .build();
        }).collect(Collectors.toList());
    }

    public InvoiceDTO calculateInvoice(InvoiceDTO invoiceDTO, double oldPrice, String userId)throws Exception{

        double total_price_after_discount = 0;
        double round_old_price;

        log.info("Find user by id {}", userId);
        User user=this.findUserById(userId);

        String userType=user.getUserType().getType();
        switch(userType){
            case "Affiliate" :
                total_price_after_discount=(oldPrice * 0.9);
                break;
            case "Employee" :
                total_price_after_discount=(oldPrice *0.7);
                break;
            case "Customer" :
                Date current_date = new Date();
                Integer difference_date=current_date.getYear()-user.getStartDate().getYear();
                System.out.println(userType);
                System.out.println(difference_date);
                if(difference_date > 2){
                    total_price_after_discount=oldPrice * 0.95;
                }
                else {
                    if (oldPrice >= 100){
                        round_old_price=Math.floor(oldPrice/100);
                        total_price_after_discount = oldPrice - (round_old_price * 5);
                    }
                }
                break;
            default:total_price_after_discount=oldPrice;
        }

//        invoiceDTO.setTotalPrice(oldPrice);
//        invoiceDTO.setPriceAfterDiscount("$ "+total_price_after_discount);
//        invoiceDTO.set(user);
        Invoice invoice = Invoice.builder()
                .id(UUID.randomUUID().toString())
                .priceAfterDiscount("$ "+total_price_after_discount)
                .totalPrice(oldPrice)
                .user(user)
                .build();
        repository.save(invoice);
        return InvoiceDTO.builder()
                .id(invoice.getId())
                .priceAfterDiscount(invoice.getPriceAfterDiscount())
                .totalPrice(invoice.getTotalPrice())
                .userDTO(UserDTO.builder()
                        .id(invoice.getUser().getId())
                        .startDate(invoice.getUser().getStartDate())
                        .email(invoice.getUser().getEmail())
                        .lastName(invoice.getUser().getLastName())
                        .firstName(invoice.getUser().getFirstName())
                        .phone(invoice.getUser().getPhone())
                        .userTypeDTO(UserTypeDTO.builder()
                                .id(invoice.getUser().getUserType().getId())
                                .type(invoice.getUser().getUserType().getType())
                                .build())
                        .build())
                .build();
    }
    private User findUserById(String userId){
        return userRepository.findById(userId).orElseThrow( () -> new EntityNotFoundException("User_DOES_NOT_EXIST " + userId));
    }
}
