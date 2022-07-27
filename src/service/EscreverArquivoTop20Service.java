package service;

import model.Filme;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class EscreverArquivoTop20Service {
    public String escreverArquivo(List<Filme> linhas) {

        System.out.println("Criando arquivo os 20 melhores Filmes de Horror");

        final Path destino = Path.of("top20Horror.txt");

        try {
            Files.write(
                    destino,
                    linhas.stream().map(Filme::getTítulo).collect(Collectors.toList())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return destino.toString();
    }
}
