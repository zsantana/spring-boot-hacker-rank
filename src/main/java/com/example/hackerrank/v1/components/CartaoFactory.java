package com.example.hackerrank.v1.components;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CartaoFactory {
    
    @Autowired
    private ApplicationContext context;

    public CartaoGenerico getCartao(String tipoCartao) {
        try {
            return context.getBean(tipoCartao, CartaoGenerico.class);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }
}

