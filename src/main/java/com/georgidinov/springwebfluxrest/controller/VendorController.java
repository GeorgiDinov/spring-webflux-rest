package com.georgidinov.springwebfluxrest.controller;


import com.georgidinov.springwebfluxrest.domain.Vendor;
import com.georgidinov.springwebfluxrest.repository.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class VendorController {

    private final VendorRepository vendorRepository;

    @Autowired
    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }


    @GetMapping("/api/v1/vendors")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Vendor> findAllVendors() {
        log.info("VendorController::findAllVendors");
        return this.vendorRepository.findAll();
    }


    @GetMapping("/api/v1/vendors/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Vendor> findVendorById(@PathVariable String id) {
        log.info("VendorController::findVendorById");
        return this.vendorRepository.findById(id);
    }

    @PostMapping("/api/v1/vendors")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createVendor(@RequestBody Publisher<Vendor> vendorPublisher) {
        log.info("VendorController::createVendor -> stream passed = {}", vendorPublisher);
        return this.vendorRepository.saveAll(vendorPublisher).then();
    }


    @PutMapping("/api/v1/vendors/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Vendor> updateVendor(@PathVariable String id,
                                     @RequestBody Vendor vendor) {
        log.info("VendorController::updateVendor -> id passed  = {}, vendor passed = {}", id, vendor);
        vendor.setId(id);
        return this.vendorRepository.save(vendor);
    }


}