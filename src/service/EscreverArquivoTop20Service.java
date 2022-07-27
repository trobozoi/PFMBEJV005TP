package service;

import model.Filme;

import java.util.List;

public interface EscreverArquivoTop20Service {
    String escreverArquivo(List<Filme> linhas);
}
