package vn.neo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.neo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
