package service;

import exception.FilmeDuplicadoException;
import model.Filme;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class LerArquivoServiceImpl implements LerArquivoService {
    @Override
    public List<String> arquivoToListStr(Path path) throws IOException {
        return Files.readAllLines(path);
    }

    @Override
    public HashSet<Filme> arquivoToFilme(Path[] arquivos) throws IOException {
        //Leitura dos Arquivos
        return
                Arrays.stream(arquivos)
                .parallel()
                .map(path -> {
                    try {
                        return Files.lines(path)
                                .map(linha -> {
                                    Filme filme = new LerFilmeServiceImpl().filmeStr(linha);
                                    return filme;
                                })
                                //.collect(Collectors.toList());
                                //.collect(Collectors.toSet());
                                .collect(Collectors.toCollection(HashSet::new));
                    } catch (IOException ioException){
                        throw new RuntimeException(ioException);
                    }
                })
                .flatMap(Collection::stream)
                //.collect(Collectors.toList());
                //.collect(Collectors.toSet());
                .filter(filme -> filme != null)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public HashSet<Filme> arquivoToFilme(Path path) throws IOException {
        HashSet<Filme> filmes = new HashSet<>();
        List<String> list = arquivoToListStr(path);
        for (String s :
                list) {
            Filme filme = new LerFilmeServiceImpl().filmeStr(s);
            try {
                if(filme != null) {
                    filmes.add(filme);
                }
            }
            catch (FilmeDuplicadoException filmeDuplicadoException) {
                System.out.println("Esse filme já foi inserido na coleção" + filmeDuplicadoException.getMessage());
            }
        }
        return filmes;
    }
}
