package com.georgidinov.springwebfluxrest.bootstrap;

import com.georgidinov.springwebfluxrest.domain.Category;
import com.georgidinov.springwebfluxrest.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    //== fields ==
    private final CategoryRepository categoryRepository;

    @Autowired
    public DataLoader(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        Mono<Category> category = this.categoryRepository.save(Category.builder().description("the_description").build());
        log.info("saved category = {}", category.block());
    }
}
