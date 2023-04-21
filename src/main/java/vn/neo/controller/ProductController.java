package vn.neo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.neo.model.Product;
import vn.neo.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getListProduct(@RequestParam("id") List<Long> idList) {
        return productService.findByIdList(idList);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findAll() {
        return productService.findAll();
    }
}
