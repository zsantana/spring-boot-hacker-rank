package com.example.hackerrank.v1.components;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component("ELO")
public class CartaoElo  implements CartaoGenerico {

    public BigDecimal calcularCashBack(BigDecimal valorCompra) {
        return valorCompra.multiply(new BigDecimal("0.05"));
    }
    
}
