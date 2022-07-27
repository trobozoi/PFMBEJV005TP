package service;

import model.Filme;

import java.util.List;

public interface EscreverArquivoAnoService {
    String escreverArquivo(List<Filme> linhas, String ano);
}
