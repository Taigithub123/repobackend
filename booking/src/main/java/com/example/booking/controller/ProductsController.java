package com.example.booking.controller;

import com.example.booking.dto.ProductDto;
import com.example.booking.model.Product;
import com.example.booking.repository.ProductRepository;
import com.example.booking.service.FilesStorageService;
import com.example.booking.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@Log4j2
@RestController
@RequestMapping("/api/products") //tro den API
public class ProductsController {
    @Autowired
    private ProductService productService; //autowired truc tiep ket noi voi productRepository
    @Autowired
    FilesStorageService storageService;
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        try{
            List<Product> products = new ArrayList<Product>();
            productService.findAll().forEach(products::add);
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> createProduct( @ModelAttribute ProductDto product){
        try {
            String fileName = storageService.save(product.getImage());
            Product newProduct = productService.save(new Product(product.getTitle(),product.getCategory(),product.getDescription(),product.getPrice(),fileName));
            return new ResponseEntity<>(newProduct,HttpStatus.CREATED); //HttpStatus.CREATED: tra ve httpstatus code 201:tao thanh cong
        }
        catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping("/update/{id}")
    public Product update(@RequestBody Product product, @PathVariable("id") long id){
        return productService.update(product, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        productService.delete(id);
        return ResponseEntity.ok("Nice");
    }
}
