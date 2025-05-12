package com.example.hackerrank.v1.components;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.example.hackerrank.v1.controller.CartaoGenerico;

@Component("AMEX")
public class CartaoAmex implements CartaoGenerico {

    public BigDecimal calcularCashBack(BigDecimal valorCompra) {
        return valorCompra.multiply(new BigDecimal("0.10"));
    }
    
}
