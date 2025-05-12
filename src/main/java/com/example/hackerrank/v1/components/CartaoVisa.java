package com.example.hackerrank.v1.components;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component("VISA")
public class CartaoVisa implements CartaoGenerico {

    public BigDecimal calcularCashBack(BigDecimal valorCompra) {
        return valorCompra.multiply(new BigDecimal("0.10"));
    }
    
}
