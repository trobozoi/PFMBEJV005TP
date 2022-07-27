package model;

import lombok.*;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Year;
import java.util.HashSet;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@With
@Log
@EqualsAndHashCode
public class Filme {
    private int classificacao;
    private String título;
    private HashSet<Genero> generos;
    private String descricao;
    private String diretor;
    private HashSet<Ator> atores;
    private Year ano;
    private int tempoDeExecucao;
    private BigDecimal avaliacao;
    private BigInteger votos;
    private BigDecimal receita;
    private int metascore;
}
