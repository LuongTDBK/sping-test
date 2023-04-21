package vn.neo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.neo.model.Product;
import vn.neo.repository.ProductRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public List<Product> findByIdList(List<Long> idList){
        return productRepository.findByIdIn(idList);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

}
