package app;

import model.Filme;
import service.EscreverArquivoAnoServiceImpl;
import service.EscreverArquivoTop20ServiceImpl;
import service.LerArquivoServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        LocalDateTime inicio = LocalDateTime.now();

        Path movie1 = Path.of("movies1.csv");
        Path movie2 = Path.of("movies2.csv");
        Path movie3 = Path.of("movies3.csv");
        Path[] arquivos = new Path[]{movie1, movie2, movie3};
        HashSet<Filme> filmes = new LerArquivoServiceImpl().arquivoToFilme(arquivos);
        //Escrita dos Arquivos
        //MELHORES 20
        List<Filme> filmesTop20 = filmes
                .stream()
                .filter(filme -> filme
                        .getGeneros()
                        .stream()
                        .filter(genero -> runTest("Horror", genero
                                .getGenero()) > 0)
                        .count() > 0)
                .sorted(Comparator.comparing(Filme::getClassificacao))
                .limit(20)
                .collect(Collectors.toList());


        String destino = new EscreverArquivoTop20ServiceImpl().escreverArquivo(filmesTop20);
        System.out.println("O arquivo " + destino + " foi criado com sucesso");
        //Criar um arquivo para cada ano,
        // no conteudo dos arquivos incluir os 50 melhores filmes daquele ano ordenados por rating.
        HashSet<Year> anos = new HashSet<>();
        filmes.forEach(filme -> anos.add(filme.getAno()));
        for (Year year :
                anos) {
            List<Filme> filmes1 = filmes
                    .stream()
                    .filter(filme -> filme
                            .getAno()
                            .equals(year))
                    .sorted(Comparator.comparing(Filme::getClassificacao))
                    .limit(50)
                    .collect(Collectors.toList());
            String s = new EscreverArquivoAnoServiceImpl().escreverArquivo(filmes1, year.toString());
            System.out.println("O arquivo " + s + " foi criado com sucesso");
        }

        LocalDateTime fim = LocalDateTime.now();
        List<String> linhas = new ArrayList<>();
        linhas.add("Incio processamento: " + inicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS")));
        linhas.add("Fim processamento: " + fim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS")));
        long milisegundo = ChronoUnit.MILLIS.between(inicio, fim);
        linhas.add("Tempo em milisegundos: " + milisegundo + " milisegundos");
        long segundo = ChronoUnit.SECONDS.between(inicio, fim);
        linhas.add("Tempo em segundos: " + segundo + " segundos");
        Files.write(Path.of("log.txt"), linhas);
    }

    private static int runTest(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        int matches = 0;
        while (matcher.find()) {
            matches++;
        }
        return matches;
    }
}
