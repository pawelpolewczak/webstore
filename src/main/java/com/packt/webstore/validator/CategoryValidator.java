package com.packt.webstore.validator;

import com.packt.webstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class CategoryValidator implements ConstraintValidator<Category, String>{

    @Autowired
    private ProductService productService;

    @Override
    public void initialize(Category constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> allowedCategories;
        try{
            allowedCategories = productService.getAllowedCategories();
        }
        catch (Exception e){
            return false;
        }

        if(allowedCategories.contains(value)){
            return true;
        }

        return false;
    }
}
