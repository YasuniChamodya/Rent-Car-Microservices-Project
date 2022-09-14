package lk.codelabs.rentcloud.rentservice.rentservice.service;

import com.netflix.hystrix.HystrixCommand;
import lk.codelabs.rentcloud.model.customer.Customer;
import lk.codelabs.rentcloud.model.rent.Rent;
import lk.codelabs.rentcloud.model.vehicle.Vehicle;
import lk.codelabs.rentcloud.rentservice.rentservice.hystrix.CommonHystrixCommand;
import lk.codelabs.rentcloud.rentservice.rentservice.hystrix.VehicleCommand;
import lk.codelabs.rentcloud.rentservice.rentservice.model.DetailResponse;
import lk.codelabs.rentcloud.rentservice.rentservice.repository.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class RentServiceImpl implements RentService{

    @Autowired
    RentRepository rentRepository;

    @Autowired
    HystrixCommand.Setter setter;

    @Override
    public Rent save(Rent customer) {
        return rentRepository.save(customer);
    }

    @Override
    public Rent findById(int id) {
        Optional<Rent> rent = rentRepository.findById(id);
        if(rent.isPresent())
            return rent.get();
        else
            return new Rent();
    }

    @Override
    public List<Rent> findAll() {
        return rentRepository.findAll();
    }

    @Override
    public DetailResponse findDetailResponse(int id) throws ExecutionException, InterruptedException {
        Rent rent = findById(id);
        Vehicle vehicle = getVehicle(rent.getVehicleId());
        Customer customer = getCustomer(rent.getCustomerId());
        return new DetailResponse(rent,customer,vehicle);
    }

    @LoadBalanced
    @Bean
    RestTemplate getRestTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

    @Autowired
    RestTemplate restTemplate;

    private Customer getCustomer(int customerId) throws ExecutionException, InterruptedException {

        CommonHystrixCommand<Customer> customerCommonHystrixCommand = new CommonHystrixCommand<Customer>("default",() -> {
            return restTemplate.getForObject("http://customer/services/customers/"+customerId,Customer.class);
        },() -> {
            return new Customer();
        });

        Future<Customer> customerFuture = customerCommonHystrixCommand.queue();
        return customerFuture.get();

        //Customer customer = restTemplate.getForObject("http://customer/services/customers/"+customerId,Customer.class);
        //return customer;
    }

    private Vehicle getVehicle(int vehicleId) throws ExecutionException, InterruptedException {

        CommonHystrixCommand<Vehicle> vehicleCommonHystrixCommand = new CommonHystrixCommand<Vehicle>(setter,() -> {
            return restTemplate.getForObject("http://vehicle/services/vehicles/"+vehicleId,Vehicle.class);
        },() -> {
            return new Vehicle();
        });
        Future<Vehicle> vehicleFuture = vehicleCommonHystrixCommand.queue();
        return vehicleFuture.get();

        //VehicleCommand vehicleCommand= new VehicleCommand(restTemplate,vehicleId);
        //return vehicleCommand.execute();

        // return restTemplate.getForObject("http://vehicle/services/vehicles/"+vehicleId,Vehicle.class);


    }
}
