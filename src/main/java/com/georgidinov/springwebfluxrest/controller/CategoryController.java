package com.georgidinov.springwebfluxrest.controller;

import com.georgidinov.springwebfluxrest.domain.Category;
import com.georgidinov.springwebfluxrest.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class CategoryController {

    //== fields ==
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/api/v1/categories")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Category> findAllCategories() {
        log.info("CategoryController::findAllCategories");
        return categoryRepository.findAll();
    }

    @GetMapping("/api/v1/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Category> findCategoryById(@PathVariable String id) {
        return this.categoryRepository.findById(id);
    }

    @PostMapping("/api/v1/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createCategory(@RequestBody Publisher<Category> categoryStream) {
        return categoryRepository.saveAll(categoryStream).then();
    }

}