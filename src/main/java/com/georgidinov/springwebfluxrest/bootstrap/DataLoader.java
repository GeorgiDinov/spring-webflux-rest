package com.georgidinov.springwebfluxrest.bootstrap;

import com.georgidinov.springwebfluxrest.domain.Category;
import com.georgidinov.springwebfluxrest.domain.Vendor;
import com.georgidinov.springwebfluxrest.repository.CategoryRepository;
import com.georgidinov.springwebfluxrest.repository.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    //== fields ==
    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    @Autowired
    public DataLoader(CategoryRepository categoryRepository,
                      VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        this.categoryRepository.save(Category.builder().description("Fruits").build()).block();
        this.categoryRepository.save(Category.builder().description("Nuts").build()).block();
        this.categoryRepository.save(Category.builder().description("Breads").build()).block();
        this.categoryRepository.save(Category.builder().description("Meats").build()).block();
        this.categoryRepository.save(Category.builder().description("Eggs").build()).block();
        log.info("Loaded categories = {}", this.categoryRepository.count().block());

        this.vendorRepository.save(Vendor.builder().name("Bill's Burgers").build()).block();
        this.vendorRepository.save(Vendor.builder().name("Mike's Donuts").build()).block();
        this.vendorRepository.save(Vendor.builder().name("Fish Restaurant").build()).block();
        log.info("Loaded vendors = {} ", this.vendorRepository.count().block());
    }
}
