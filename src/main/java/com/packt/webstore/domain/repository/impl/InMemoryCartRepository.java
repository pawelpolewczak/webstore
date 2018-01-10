package com.packt.webstore.domain.repository.impl;

import com.packt.webstore.domain.Cart;
import com.packt.webstore.domain.repository.CartRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryCartRepository implements CartRepository{
    private Map<String, Cart> listOfCarts;

    public InMemoryCartRepository() {
        this.listOfCarts = new HashMap<>();
    }

    @Override
    public Cart create(Cart cart) {
        if(listOfCarts.keySet().contains(cart.getCartId())){
            throw new IllegalArgumentException(String.format("Nie mozna utworzyc koszyka. Koszyk o wskazanym id (%) juz istnieje.", cart.getCartId()));
        }
        listOfCarts.put(cart.getCartId(), cart);
        return cart;
    }

    @Override
    public Cart read(String cartId) {
        return listOfCarts.get(cartId);
    }

    @Override
    public void update(String cartId, Cart cart) {
        if(!listOfCarts.keySet().contains(cartId)){
            throw new IllegalArgumentException(String.format("Nie mozna zaktualizowac koszyka. Koszyk o wskazanym id (%) nie istnieje.", cartId));
        }
        listOfCarts.put(cartId, cart);
    }

    @Override
    public void delete(String cartId) {
        if(!listOfCarts.keySet().contains(cartId)){
            throw new IllegalArgumentException(String.format("Nie mozna usunac koszyka. Koszyk o wskazanym id (%) nie istnieje.", cartId));
        }
        listOfCarts.remove(cartId);
    }
}
