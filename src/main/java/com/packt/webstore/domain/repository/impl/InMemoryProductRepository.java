package com.packt.webstore.domain.repository.impl;

import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.exception.ProductNotFoundException;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class InMemoryProductRepository implements ProductRepository {


    private List<Product> listOfProducts = new ArrayList<Product>();

    public InMemoryProductRepository() {
        Product iphone = new Product("P1234", "iPhone 5s", new BigDecimal(500));
        iphone.setDescription("Apple iphone 5s, smartfon z 4-calowym wyswietlaczem o rozdzielczosci 640x1136 oraz 8-megapikselowym aparatem");
        iphone.setCategory("Smart Phone");
        iphone.setManufacturer("Apple");
        iphone.setUnitsInStock(1000);

        Product laptop_dell = new Product("P1235", "Dell inspiron", new BigDecimal(700));
        laptop_dell.setDescription("Dell inspiron, 14-calowy laptop (czarny) z procesorami Inter Core 3. generacji");
        laptop_dell.setCategory("Laptop");
        laptop_dell.setManufacturer("Dell");
        laptop_dell.setUnitsInStock(1000);

        Product tablet_Nexus = new Product("P1236", "Nexus 7", new BigDecimal(300));
        tablet_Nexus.setDescription("Google Nexus 7 jest najlzejszym 7-calowym tabletem z 4-rdzeniowym procesorem Qualcomm Snapdragon S4 Pro");
        tablet_Nexus.setCategory("Tablet");
        tablet_Nexus.setManufacturer("Google");
        tablet_Nexus.setUnitsInStock(1000);

        listOfProducts.add(iphone);
        listOfProducts.add(laptop_dell);
        listOfProducts.add(tablet_Nexus);
    }

    @Override
    public List<Product> getAllProducts() {
        return listOfProducts;
    }

    @Override
    public Product getProductById(String productId) {
        Product productById = null;
        for(Product product : listOfProducts){
            if(product!=null && product.getProductID() != null && product.getProductID().equals(productId)){
                productById = product;
                break;
            }
        }
        if(productById == null)
            throw new ProductNotFoundException(productId);
        return productById;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        List<Product> productsByCategory = new ArrayList<>();
        for(Product product: listOfProducts) {
            if(category.equalsIgnoreCase(product.getCategory())){
                productsByCategory.add(product);
            }
        }
        return productsByCategory;
    }

    @Override
    public Set<Product> getProductsByFilter(Map<String, List<String>> filterParams) {
        Set<Product> productsByBrand = new HashSet<>();
        Set<Product> productsByCategory = new HashSet<>();
        Set<String> criterias = filterParams.keySet();
        if(criterias.contains("brand")){
            for(String brandName: filterParams.get("brand")){
                for(Product product : listOfProducts){
                    if(brandName.equalsIgnoreCase(product.getManufacturer())){
                        productsByBrand.add(product);
                    }
                }
            }
        }
        if(criterias.contains("category")){
            for(String category: filterParams.get("category")){
                productsByCategory.addAll(this.getProductsByCategory(category));
            }
        }
        productsByCategory.retainAll(productsByBrand);
        return productsByCategory;
    }

    @Override
    public List<Product> getProductsByManufacturer(String manufacturer) {
        List<Product> productsByManufacturer = new ArrayList<>();
        for(Product product: listOfProducts) {
            if(manufacturer.equalsIgnoreCase(product.getManufacturer())){
                productsByManufacturer.add(product);
            }
        }
        return productsByManufacturer;
    }

    @Override
    public List<Product> getProductsByPriceRange(Map<String, List<String>> priceFilterParams) {
        List<Product> correctProducts = new ArrayList<>();
        Set<Product> productsByLowPrice = new HashSet<>();
        Set<Product> productsByHighPrice = new HashSet<>();
        Set<String> criterias = priceFilterParams.keySet();
        if(criterias.contains("low")){
            int lowRange = Integer.valueOf(priceFilterParams.get("low").get(0));
            for (Product product : listOfProducts) {
                if(product.getUnitPrice().intValue() > lowRange)
                    productsByLowPrice.add(product);
            }
        }
        if(criterias.contains("high")){
            int highRange = Integer.valueOf(priceFilterParams.get("high").get(0));
            for (Product product : listOfProducts){
                if(product.getUnitPrice().intValue() < highRange)
                    productsByHighPrice.add(product);
            }
        }
        productsByHighPrice.retainAll(productsByLowPrice);
        correctProducts.addAll(productsByHighPrice);
        return correctProducts;
    }

    @Override
    public List<Product> getProductsByMultipleFilters(String category, String manufacturer, Map<String, List<String>> priceFilterParams) {
        List<Product> listOfCompatybileProducts = new ArrayList<>();
        Set<Product> productsByCategory = new HashSet<>();
        Set<Product> productsByManufacturer = new HashSet<>();
        Set<Product> productsByPriceRange = new HashSet<>();

        productsByCategory.addAll(this.getProductsByCategory(category));
        productsByManufacturer.addAll(this.getProductsByManufacturer(manufacturer));
        productsByPriceRange.addAll(this.getProductsByPriceRange(priceFilterParams));

        productsByCategory.retainAll(productsByManufacturer);
        productsByCategory.retainAll(productsByPriceRange);
        listOfCompatybileProducts.addAll(productsByCategory);
        return listOfCompatybileProducts;
    }

    @Override
    public void addProduct(Product product) {
        listOfProducts.add(product);
    }


}
