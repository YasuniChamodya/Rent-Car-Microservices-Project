package lk.codelabs.rentcloud.rentservice.rentservice.controller;

import lk.codelabs.rentcloud.model.rent.Rent;
import lk.codelabs.rentcloud.rentservice.rentservice.model.Response;
import lk.codelabs.rentcloud.rentservice.rentservice.model.SimpleResponse;
import lk.codelabs.rentcloud.rentservice.rentservice.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/services/rents")
public class RentController {
    @Autowired
    RentService rentService;

    @PostMapping
    public Rent save(@RequestBody Rent rent){
        return rentService.save(rent);
    }
    @GetMapping(value = "/{id}")
    public Response getRent(@PathVariable int id, @RequestParam(required = false) String type) throws ExecutionException, InterruptedException {

        if (type == null){
            return new SimpleResponse(rentService.findById(id));
        } else {
            return rentService.findDetailResponse(id);
        }
    }

}
