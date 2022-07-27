package service;

import model.Filme;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;

public interface LerArquivoService {
    List<String> arquivoToListStr(Path path) throws IOException;

    HashSet<Filme> arquivoToFilme(Path[] arquivos) throws IOException;

    HashSet<Filme> arquivoToFilme(Path path) throws IOException;
}
