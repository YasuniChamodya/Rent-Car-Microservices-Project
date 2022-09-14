package lk.codelabs.rentcloud.rentservice.rentservice.repository;

import lk.codelabs.rentcloud.model.rent.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentRepository extends JpaRepository<Rent,Integer> {
}
