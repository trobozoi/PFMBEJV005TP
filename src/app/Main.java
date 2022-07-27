package app;

import model.Filme;
import service.EscreverArquivoAnoService;
import service.EscreverArquivoTop20Service;
import service.LerArquivoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
        HashSet<Filme> filmes = new LerArquivoService().arquivoToFilme(arquivos);
        //Escrica dos Arquivos
        //MELHORES 20
        List<Filme> filmesTop20 = filmes
                .stream()
                .filter(filme -> filme
                        .getGeneros()
                        .stream()
                        .filter(genero -> runTest("Horror", genero
                                .getGenero()) > 0)
                        .count() > 0)
                .collect(Collectors.toList());

        filmesTop20.sort(Comparator.comparing(Filme::getClassificacao));
        while (filmesTop20.stream().count()> 20){filmesTop20.remove(20);}
        String destino = new EscreverArquivoTop20Service().escreverArquivo(filmesTop20);
        System.out.println("O arquivo " + destino + " foi criado com sucesso");
        //Criar um arquivo para cada ano,
        // no conteudo dos arquivos incluir os 50 melhores filmes daquele ano ordenados por rating.
        HashSet<Year> anos = new HashSet<>();
        filmes.forEach(filme -> anos.add(filme.getAno()));
        for (Year year :
                anos) {
            List<Filme> filmes1 = filmes.stream().filter(filme -> filme.getAno().equals(year)).collect(Collectors.toList());
            filmes1.sort(Comparator.comparing(Filme::getClassificacao));
            while (filmes1.stream().count()> 50){filmes1.remove(50);}
            String s = new EscreverArquivoAnoService().escreverArquivo(filmes1, year.toString());
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
