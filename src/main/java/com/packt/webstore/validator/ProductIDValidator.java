package com.packt.webstore.validator;

import java.lang.annotation.Annotation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.ProductService;

public class ProductIDValidator implements ConstraintValidator<ProductID, String>{

    @Autowired
    private ProductService productService;

	@Override
	public void initialize(ProductID constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
        Product product;
        try{
            product = productService.getProductById(value);
        }
        catch(ProductNotFoundException e){
            return true;
        }

        if(product != null){
            return false;
        }

		return true;
	}
}
