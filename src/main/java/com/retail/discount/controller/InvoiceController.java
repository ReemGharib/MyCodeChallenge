package com.retail.discount.controller;

import com.retail.discount.dtos.InvoiceDTO;
import com.retail.discount.entity.Invoice;
import com.retail.discount.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("invoice")
public class InvoiceController {
    @Autowired
    InvoiceService service;

    @GetMapping("getAllInvoices")
    public List<InvoiceDTO> getAll(){
        return service.getAll();
    }

    @PostMapping("calculateInvoice/{oldPrice}/{user_id}")
    public InvoiceDTO calculate(@RequestBody InvoiceDTO invoiceDTO,
                             @PathVariable("oldPrice")double oldPrice,
                             @PathVariable("user_id")String user_id)throws Exception{
        return service.calculateInvoice(invoiceDTO, oldPrice, user_id);
    }
}
