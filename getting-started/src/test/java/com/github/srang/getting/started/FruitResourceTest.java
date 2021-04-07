package com.github.srang.getting.started;

import com.github.javafaker.Faker;
import com.github.srang.datafactory.BaseFactory;
import com.github.srang.datafactory.DataFactory;
import com.github.srang.getting.started.model.Fruit;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class FruitResourceTest {
    @Test
    public void testFruitGet() throws Exception {
        Faker faker = new Faker();
        DataFactory<Fruit> fruitFactory = new BaseFactory<>(Fruit.class);
        fruitFactory.addFilter((Field f) -> f.getName().equals("name"), () -> faker.food().fruit());
        Fruit fruit = fruitFactory.generate();
        given()
            .when().post("/fruits", fruit)
            .then()
                .statusCode(200);

        given()
            .when().get("/fruits")
            .then()
                .statusCode(200)
                .body(containsString(fruit.name));
    }
}
