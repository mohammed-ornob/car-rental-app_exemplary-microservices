package de.uniba.dsg.carrental.carservice.controller.v1;

import de.uniba.dsg.carrental.carservice.exception.EntityNotFoundException;
import de.uniba.dsg.carrental.carservice.model.data.Car;
import de.uniba.dsg.carrental.carservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CarsController {
    private final CarService carService;

    @Autowired
    public CarsController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/api/v1/cars")
    public ResponseEntity<?> getCars() {
        try {
            List<Car> cars = carService.getAllCars();

            cars.forEach(car -> {
                car.add(linkTo(
                        methodOn(CarsController.class).getCar(car.getCode())
                ).withSelfRel());

                car.getManufacturer().add(linkTo(
                        methodOn(ManufacturersController.class).getManufacturerCars(car.getManufacturer().getName())).withRel("getCars")
                );
            });

            Link link = linkTo(
                    methodOn(CarsController.class).getCars()
            ).withSelfRel();

            return new ResponseEntity<>(CollectionModel.of(cars, link), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/v1/cars/{code}")
    public ResponseEntity<?> getCar(@PathVariable String code) {
        try {
            Car car = carService.getCar(code);

            car.add(linkTo(
                methodOn(CarsController.class).getCar(car.getCode())
            ).withSelfRel());

            car.getManufacturer().add(linkTo(
                methodOn(ManufacturersController.class).getManufacturerCars(car.getManufacturer().getName())).withRel("getCars")
            );

            return new ResponseEntity<>(car, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
