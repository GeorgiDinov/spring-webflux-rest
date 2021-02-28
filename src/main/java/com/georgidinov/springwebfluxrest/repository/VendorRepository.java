package com.georgidinov.springwebfluxrest.repository;

import com.georgidinov.springwebfluxrest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {

}