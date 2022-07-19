package model;

import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Year;
import java.util.List;


public class Filme {
    int classificacao;
    String título;
    List<Genero> generos;
    String descricao;
    String diretor;
    List<Ator> atores;
    Year ano;
    int tempoDeExecucao;
    BigDecimal avaliacao;
    BigInteger votos;
    BigDecimal receita;
    int metascore;
}
