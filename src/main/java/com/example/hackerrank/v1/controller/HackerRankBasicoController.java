package com.example.hackerrank.v1.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hackerrank.v1.dto.CalendarioDTO;
import com.example.hackerrank.v1.dto.RequestFormataValorDTO;
import com.example.hackerrank.v1.dto.RequestValorGrandeDTO;
import com.example.hackerrank.v1.dto.ResponseFormataValorDTO;
import com.example.hackerrank.v1.dto.ResponseValorGrandeDTO;
import com.example.hackerrank.v1.dto.ResquestCartaoDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/api/v1/hacker-rank")
@Tag(name = "Básico", description = "Exemplos gerais")
public class HackerRankBasicoController {


    @PostMapping("/formata-valores")
    public ResponseEntity<ResponseFormataValorDTO> formatarValor(@RequestBody RequestFormataValorDTO requisicaoDTO) {
        
        var usFormat = NumberFormat.getCurrencyInstance(Locale.US);
        var indiaFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-IN"));
        var chinaFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
        var franceFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);

        var valor = requisicaoDTO.valor();
        var retornoDTO = new ResponseFormataValorDTO(
            usFormat.format(valor),
            indiaFormat.format(valor),
            chinaFormat.format(valor),
            franceFormat.format(valor)
        );
        
        return new ResponseEntity<ResponseFormataValorDTO>(retornoDTO, HttpStatus.OK);
    }
    
    
    @PostMapping("/calcula-valor-grande")
    public ResponseEntity<ResponseValorGrandeDTO> calcularValorGrande(@RequestBody RequestValorGrandeDTO requisicaoDTO) {
        
        var valor1 = requisicaoDTO.valor1();
        var valor2 = requisicaoDTO.valor2();

        var retValorSomado = valor1.add(valor2);
        var retValorMultiplicado = valor1.multiply(valor2);

        var retornoDTO = new ResponseValorGrandeDTO(
            retValorSomado,
            retValorMultiplicado);
        
        return new ResponseEntity<ResponseValorGrandeDTO>(retornoDTO, HttpStatus.OK);
    }

    @PostMapping("/ordenacao-valores-crescente")
    public ResponseEntity<String> ordenarValoresCrescente(@RequestBody List<String> listagemValores) {
        
        var valoresOrdenados = listagemValores
                                .stream()
                                .sorted(Comparator.comparing(BigDecimal::new, Comparator.naturalOrder()))
                                .collect(Collectors.joining("\n"));

        return ResponseEntity.ok(valoresOrdenados);

    }

    @PostMapping("/ordenacao-valores-decrescente")
    public ResponseEntity<String> ordenarValoresDecrescente(@RequestBody List<String> listagemValores) {
        
        var valoresOrdenados = listagemValores
                                .stream()
                                .sorted(Comparator.comparing(BigDecimal::new, Comparator.reverseOrder()))
                                .collect(Collectors.joining("\n"));

        return ResponseEntity.ok(valoresOrdenados);

    }


    @PostMapping("/totalizador-valores")
    public ResponseEntity<BigDecimal> totalizarValores(@RequestBody List<String> listagemValores) {
        
        var valoresOrdenados = listagemValores
                                .stream()
                                .map(BigDecimal::new)  // Converte os valores para BigDecimal
                                .reduce(BigDecimal.ZERO, BigDecimal::add);  // Soma todos os valores

        return ResponseEntity.ok(valoresOrdenados);

    }

    @PostMapping("/validador-nomes")
    public ResponseEntity<String> validarNomes(@RequestBody List<String> listagemNomes) {

        // ^: Indica o início da string.
        // [a-zA-Z]: A string deve começar com uma letra, seja maiúscula (A-Z) ou minúscula (a-z).
        // [a-zA-Z0-9_]{7,7}: Após a primeira letra, a string deve conter exatamente 7 caracteres adicionais. Esses caracteres podem ser letras (a-z, A-Z), números (0-9) ou o sublinhado (_).
        // $: Indica o fim da string.
        
        var expressaoRegular = "^[a-zA-Z][a-zA-Z0-9_]{7,7}$";
        var pattern = Pattern.compile(expressaoRegular);
        
        // Mapeia cada nome para uma string com o nome e a indicação se é "Válido" ou "Inválido"
        var nomesComStatus = listagemNomes
                                .stream()
                                .map(nome -> {
                                    Matcher matcher = pattern.matcher(nome);
                                    String status = matcher.matches() ? "Válido" : "Inválido";
                                    return nome + ": " + status;
                                })
                                .collect(Collectors.joining("\n"));

        return ResponseEntity.ok(nomesComStatus);

    }

    @PostMapping("/filtro-cartao-visa")
    public ResponseEntity<List<ResquestCartaoDTO>> filtarCartaoVisa(@RequestBody List<ResquestCartaoDTO> listagemCartoes) {
        
        var cartao = "VISA";
        var resultado = listagemCartoes
                            .stream()
                            .filter( c -> c.tipoCartao().equals(cartao))
                            .collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }


    @PostMapping("/filtro-cartao/{tipocartao}")
    public ResponseEntity<List<ResquestCartaoDTO>> filtarCartao(@PathVariable String tipocartao, @RequestBody List<ResquestCartaoDTO> listagemCartoes) {
        
        var resultado = listagemCartoes
                            .stream()
                            .filter( c -> c.tipoCartao().equals(tipocartao))
                            .collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }


    @PostMapping("/calendario/dia-semana")
    public ResponseEntity<String> obterDiaSemana(@RequestBody CalendarioDTO calendarioDTO) {
        
        var calendario = Calendar.getInstance();
        calendario.set(calendarioDTO.ano(), calendarioDTO.mes() -1, calendarioDTO.dia());

        int diaSemana = calendario.get(Calendar.DAY_OF_WEEK);
        String[] diasSemana = { "DOMINGO", "SEGUNDA", "TERÇA", "QUARTA", "QUINTA", "SEXTA", "SÁBADO" };

        return ResponseEntity.ok(diasSemana[diaSemana -1]);
    }

}
