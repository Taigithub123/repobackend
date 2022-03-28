package com.example.booking.service;

import com.example.booking.model.Product;
import com.example.booking.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    Product findById(long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Cant not find product id: " + id)
        );
    }

    public Product update(Product product, long id){
        Optional<Product> product1 = Optional.ofNullable(findById(id));
        if(product1.isPresent()){
            Product product2 = product1.get();
            product2.setCategory(product.getCategory());
            product2.setDescription(product.getDescription());
            product2.setPrice(product.getPrice());
            product2.setTitle(product.getTitle());
            product2.setImage(product.getImage());
            return productRepository.save(product2);
        }
        return null;
    }
    public void delete(long id){
        Optional<Product> product1 = Optional.ofNullable(findById(id));
        if(product1.isPresent()) {
            productRepository.deleteById(id);
        }
    }
}
