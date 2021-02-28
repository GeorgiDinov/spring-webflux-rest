package com.georgidinov.springwebfluxrest.controller;

import com.georgidinov.springwebfluxrest.domain.Category;
import com.georgidinov.springwebfluxrest.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class CategoryControllerTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryController categoryController;

    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void findAllCategories() {
        BDDMockito.given(categoryRepository.findAll()).willReturn(Flux.just(
                Category.builder().description("Fruits").build(),
                Category.builder().description("Nuts").build()));

        webTestClient.get()
                .uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);

    }

    @Test
    void findCategoryById() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("Fruits").build()));

        webTestClient.get()
                .uri("/api/v1/categories/someId")
                .exchange()
                .expectBody(Category.class);
    }


    @Test
    void createCategory() {
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().description("descrp").build()));

        Mono<Category> catToSaveMono = Mono.just(Category.builder().description("Some Cat").build());

        webTestClient.post()
                .uri("/api/v1/categories")
                .body(catToSaveMono, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void updateCategory() {
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().description("Category Description").build()));

        Mono<Category> catToUpdateMono = Mono.just(Category.builder().description("Some Cat").build());

        webTestClient.put()
                .uri("/api/v1/categories/someId")
                .body(catToUpdateMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}