package com.roberteftene.dc.invetory_service.controller;

import com.roberteftene.dc.invetory_service.domain.repository.InventoryItemRepository;
import com.roberteftene.dc.invetory_service.domain.repository.OrderRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class OrderControllerIT {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Test
    void shouldCreateOrder_whenValidRequest_thenStatus200() {
        String requestBody = """
            {
              "orderItems": [{"productId": 1, "dcId": 1, "quantityRequested": 5}],
              "billingAddress": {"street": "Main Street", "city": "City", "zipCode": "000000"},
              "shippingAddress": {"street": "Main Street", "city": "City", "zipCode": "000000"}
            }
        """;

        given()
                .auth().preemptive().basic("admin", "adminpass")
                .contentType("application/json")
                .body(requestBody)
            .when()
                .post("/api/orders")
            .then()
                .statusCode(200)
                .body("id", org.hamcrest.Matchers.notNullValue());
    }
}