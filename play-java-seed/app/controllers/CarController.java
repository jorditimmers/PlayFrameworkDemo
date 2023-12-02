package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.BodyParser;
import play.mvc.Http;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class CarController extends Controller {

    private static List<Car> cars = new ArrayList<>();

    // Seeding some data
    static {
        cars.add(new Car(1L, "Model S", "Tesla"));
        cars.add(new Car(2L, "911", "Porsche"));
        cars.add(new Car(3L, "Civic", "Honda"));
        cars.add(new Car(4L, "Accord", "Honda"));
        cars.add(new Car(5L, "Mustang", "Ford"));
        cars.add(new Car(6L, "Camry", "Toyota"));
        cars.add(new Car(7L, "Corvette", "Chevrolet"));
        cars.add(new Car(8L, "Cherokee", "Jeep"));
        cars.add(new Car(9L, "Wrangler", "Jeep"));
        cars.add(new Car(10L, "F-150", "Ford"));
        cars.add(new Car(11L, "Cayenne", "Porsche"));
        cars.add(new Car(12L, "Leaf", "Nissan"));
    }

    // Get all cars
    public Result getAllCars() {
        return ok(play.libs.Json.toJson(cars));
    }

    // Get a car by ID
    public Result getCar(Long id) {
        Car car = findCarById(id);
        if (car != null) {
            return ok(play.libs.Json.toJson(car));
        } else {
            return notFound("Car not found");
        }
    }

    // Add a new car
    @BodyParser.Of(BodyParser.Json.class)
    public Result addCar() {
        JsonNode json = request().body().asJson();
        Car newCar = play.libs.Json.fromJson(json, Car.class);
        cars.add(newCar);
        return created("Car added successfully");
    }

    // Update an existing car
    @BodyParser.Of(BodyParser.Json.class)
    public Result updateCar(Long id) {
        JsonNode json = request().body().asJson();
        Car updatedCar = play.libs.Json.fromJson(json, Car.class);

        Car existingCar = findCarById(id);
        if (existingCar != null) {
            existingCar.setModel(updatedCar.getModel());
            existingCar.setManufacturer(updatedCar.getManufacturer());
            return ok("Car updated successfully");
        } else {
            return notFound("Car not found");
        }
    }

    // Delete a car
    public Result deleteCar(Long id) {
        Car carToRemove = findCarById(id);
        if (carToRemove != null) {
            cars.remove(carToRemove);
            return ok("Car deleted successfully");
        } else {
            return notFound("Car not found");
        }
    }

    // Find a car by ID helper function
    private Car findCarById(Long id) {
        return cars.stream()
                .filter(car -> car.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Dummy Car class
    public static class Car {
        private Long id;
        private String model;
        private String manufacturer;

        public Car(Long id, String model, String manufacturer) {
            this.id = id;
            this.model = model;
            this.manufacturer = manufacturer;
        }

        public Long getId() {
            return id;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }
    }
}
