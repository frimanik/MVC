package com.example.MVC_ObjectMapper.Repositories;

import com.example.MVC_ObjectMapper.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
