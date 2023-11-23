package com.example.javalock.application.facade;

import com.example.javalock.application.service.ProductService;
import com.example.javalock.infra.Product;
import com.example.javalock.infra.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductFacadeTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ProductFacade productFacade;

    @BeforeEach
    void setUp() {
        Product product = Product.of(1L, "운두령 감자", 10);
        productRepository.save(product);
    }

    @AfterEach
    void afterEach() {
        productRepository.deleteAll();
    }

    @Test
    void 낙관적락_테스트() throws InterruptedException {
        int numberOfThreads = 10;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < numberOfThreads; i++) {
            service.submit(() -> {
                try {
                    productFacade.decreaseInventoryWithOptimisticLock(1L);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        Product product = productRepository.findById(1L).orElse(null);
        assertEquals(0, product.getInventory());
    }

}
