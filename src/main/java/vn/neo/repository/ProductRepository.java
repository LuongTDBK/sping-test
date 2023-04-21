package vn.neo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.neo.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIdIn(List<Long> idList);
//    List<Product> findAll();
}
