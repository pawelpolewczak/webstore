package com.packt.webstore.exception;

public class ProductNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -694354952032299587L;
    private String productID;

    public ProductNotFoundException(String productID){
        this.productID = productID;
    }

    public String getProductID() {
        return productID;
    }
}
