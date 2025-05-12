package com.example.hackerrank.v1.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hackerrank.v1.dto.ValorDTO;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/api/v1/hacker-rank")
@Tag(name = "OOP", description = "Programação Orientada a Objeto")
public class HackerRankOOPController {

    private static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(HackerRankOOPController.class);

    @Autowired
    private CartaoFactory cartaoFactory;

    @PostMapping("/oop/heranca1")
    public ResponseEntity<String> calcularValor1(@RequestBody ValorDTO valorDTO) {
        
        class Arithmetic{

            public int add(int a, int b){
                return a + b;
            }    
        }
        
        class Adder extends Arithmetic{
           
        }

        Adder a = new Adder();
        var resultado = "My superclass is: " + 
                a.getClass().getSuperclass().getName() + " " + 
                a.add(valorDTO.valor1(), valorDTO.valor2());

        return ResponseEntity.ok(resultado);
    }


    @PostMapping("/oop/heranca2")
    public ResponseEntity<String> calcularValor2(@RequestBody ValorDTO valorDTO) {
        
        abstract class Book{
            String title;
            abstract void setTitle(String s);
            String getTitle(){
                return title;
            }
        }
        
        class MyBook extends Book {
            // Implementing the abstract method setTitle
            @Override
            void setTitle(String s) {
                this.title = s;
            }
        }

        MyBook myBook = new MyBook();
        myBook.setTitle("A tale of two cities");

        var resultado = "The title is: " + myBook.getTitle();
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/oop/interface1/{valor}")
    public ResponseEntity<Integer> calcularInterface1(@PathVariable int valor) {
        
        interface AdvancedArithmetic{
            int divisor_sum(int n);
        }
        
        class MyCalculator implements AdvancedArithmetic {

            // Implementing the abstract method setTitle
            public int divisor_sum(int n){

                int sum = 0;
        
                // Loop to find all divisors of n and sum them
                for (int i = 1; i <= n; i++) {
                    if (n % i == 0) {
                        sum += i;
                    }
                }
                
                return sum;

            }
        }

        MyCalculator myCalculator = new MyCalculator();
        return ResponseEntity.ok(myCalculator.divisor_sum(valor));
    }

    @PostMapping("/oop/interface2/{tipocartao}/{valor}")
    public ResponseEntity<String> calcularCashBack1(@PathVariable String tipocartao, @PathVariable BigDecimal valor) {
        
        interface CartaoGenerico{
            BigDecimal calcularCashBack(BigDecimal valorCompra);
        }
        
        class CartaoVisa implements CartaoGenerico {

            @Override
            public BigDecimal calcularCashBack(BigDecimal valorCompra) {
                return valor.multiply(new BigDecimal("0.10"));
            }
            
        }

        class CartaoAmex implements CartaoGenerico {

            @Override
            public BigDecimal calcularCashBack(BigDecimal valorCompra) {
                return valor.multiply(new BigDecimal("0.05"));
            }
            
        }

        class CartaoElo implements CartaoGenerico {

            @Override
            public BigDecimal calcularCashBack(BigDecimal valorCompra) {
                return valor.multiply(new BigDecimal("0.02"));
            }
            
        }
        
        CartaoGenerico cartao = null;

        switch (tipocartao) {
            case "VISA":
                cartao = new CartaoVisa();
                break;
            case "AMEX":
                cartao = new CartaoAmex();
                break;
            case "ELO":
                cartao = new CartaoElo();
                break;
            default:
                return ResponseEntity.badRequest().body("Tipo de cartão não encontrado");
        }        

        BigDecimal cashback = cartao.calcularCashBack(valor);
        return ResponseEntity.ok("O cashback é: " + cashback);

    }

    @PostMapping("/oop/interface3/{tipocartao}/{valor}")
    public ResponseEntity<String> calcularCashBack2(@PathVariable String tipocartao, @PathVariable BigDecimal valor) {
        
        interface CartaoGenerico{
            BigDecimal calcularCashBack(BigDecimal valorCompra);
        }
        
        class CartaoVisa implements CartaoGenerico {

            @Override
            public BigDecimal calcularCashBack(BigDecimal valorCompra) {
                return valor.multiply(new BigDecimal("0.10"));
            }
            
        }

        class CartaoAmex implements CartaoGenerico {

            @Override
            public BigDecimal calcularCashBack(BigDecimal valorCompra) {
                return valor.multiply(new BigDecimal("0.05"));
            }
            
        }

        class CartaoElo implements CartaoGenerico {

            @Override
            public BigDecimal calcularCashBack(BigDecimal valorCompra) {
                return valor.multiply(new BigDecimal("0.02"));
            }
            
        }

        class CartaoFactory {
            public CartaoGenerico getCartao(String tipoCartao) {
                return switch (tipoCartao.toUpperCase()) {
                    case "VISA" -> new CartaoVisa();
                    case "AMEX" -> new CartaoAmex();
                    case "ELO" -> new CartaoElo();
                    default -> null;
                };
            }
        }
        
        var factory = new CartaoFactory();
        var cartao = factory.getCartao(tipocartao);

        if (cartao == null) {
            return ResponseEntity.badRequest().body("Tipo de cartão não encontrado");
        }

        var cashback = cartao.calcularCashBack(valor);
        return ResponseEntity.ok("O cashback é: " + cashback);

    }

    @PostMapping("/oop/interface4/{tipocartao}/{valor}")
    public ResponseEntity<String> calcularCashBack3(@PathVariable String tipocartao, @PathVariable BigDecimal valor) {
        
        @Component("ELO")
        class CartaoElo implements CartaoGenerico {

            public BigDecimal calcularCashBack(BigDecimal valorCompra) {
                return valor.multiply(new BigDecimal("0.02"));
            }
            
        }
        
        var cartao = cartaoFactory.getCartao(tipocartao);
        LOGGER.info("Cartão: {}", cartao);

        if (cartao == null) {
            return ResponseEntity.badRequest().body("Tipo de cartão não encontrado");
        }

        var cashback = cartao.calcularCashBack(valor);
        return ResponseEntity.ok("O cashback é: " + cashback);

    }
}
