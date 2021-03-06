package com.packt.webstore.service;

import com.packt.webstore.domain.Product;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductService {

    List<Product> getAllProducts();
    Product getProductById(String productId);
    List<Product> getProductsByCategory(String category);
    Set<Product> getProductsByFilter(Map<String, List<String>> filterParams);
    List<Product> getProductsByManufacturer(String manufacturer);
    List<Product> getProductsByPriceRange(Map<String, List<String>> priceFilterParams);
    List<Product> getProductsByMultipleFilters(String category, String manufacturer, Map<String, List<String>> priceFilterParams);
    void addProduct(Product product);
    List<String> getAllowedCategories();
}
