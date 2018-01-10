package com.packt.webstore.controller;

import com.packt.webstore.domain.Cart;
import com.packt.webstore.domain.CartItem;
import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.CartService;
import com.packt.webstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "rest/cart")
public class CartRestController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Cart create(@RequestBody Cart cart){
        return cartService.create(cart);
    }

    @RequestMapping(value = "/{cartId}", method = RequestMethod.GET)
    public @ResponseBody Cart read(@PathVariable(value = "cartId") String cartId){
        return cartService.read(cartId);
    }

    @RequestMapping(value = "/{cartId}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable(value = "cartId") String cartId, @RequestBody Cart cart){
        cartService.update(cartId, cart);
    }

    @RequestMapping(value = "/{cartId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "cartId") String cartId){
        cartService.delete(cartId);
    }

    @RequestMapping(value = "/add/{productID}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addItem(@PathVariable String productID, HttpServletRequest request){
        String sessionId = request.getSession(true).getId();
        Cart cart = cartService.read(sessionId);
        if(cart == null){
            cart = cartService.create(new Cart(sessionId));
        }
        Product product = productService.getProductById(productID);
        if(product == null){
            throw new IllegalArgumentException(new ProductNotFoundException(productID));
        }
        cart.addCartItem(new CartItem(product));
        cartService.update(sessionId, cart);
    }

    @RequestMapping(value = "/remove/{productID}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeItem(@PathVariable String productID, HttpServletRequest request){
        String sessionId = request.getSession(true).getId();
        Cart cart = cartService.read(sessionId);
        if(cart == null){
            cart = cartService.create(new Cart(sessionId));
        }
        Product product = productService.getProductById(productID);
        if(product == null){
            throw new IllegalArgumentException(new ProductNotFoundException(productID));
        }
        cart.removeCartItem(new CartItem(product));
        cartService.update(sessionId, cart);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Niepoprawne zadanie, sprawdz przesylane dane.")
    public void handleClientErrors(Exception ex) {}

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Wewnetrzny blad serwera")
    public void handleServerErrors(Exception ex) {};

}
