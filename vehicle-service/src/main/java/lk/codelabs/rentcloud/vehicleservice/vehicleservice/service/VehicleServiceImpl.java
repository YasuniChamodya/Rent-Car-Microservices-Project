package lk.codelabs.rentcloud.vehicleservice.vehicleservice.service;

import lk.codelabs.rentcloud.model.vehicle.Vehicle;
import lk.codelabs.rentcloud.vehicleservice.vehicleservice.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class VehicleServiceImpl implements VehicleService{

    @Autowired
    VehicleRepository vehicleRepository;

    @Override
    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle findById(int id) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);

        if (vehicle.isPresent())
            return vehicle.get();
        else
            return new Vehicle();
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }
}
