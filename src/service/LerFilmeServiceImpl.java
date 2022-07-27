package service;

import model.Filme;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Year;

public class LerFilmeServiceImpl implements LerFilmeService {
    @Override
    public Filme filmeStr(String s){
        //Rank,Title,Genre,Description,Director,Actors,Year,Runtime (Minutes),Rating,Votes,Revenue (Millions),Metascore
        while (s.indexOf("\"") >= 0 ){
            int index01 = s.indexOf("\"");
            int index02 = s.substring(index01 + 1).indexOf("\"") + (index01 + 1);
            String conteudo = s.substring(index01 + 1, index02).replace(",", ";");
            s = s.substring(0, index01) + conteudo + s.substring(index02 + 1);
        }

        String[] strings = s.split(",");

        if(!isNumeric(strings[0])){
           return null;
        }

        return Filme.builder()
                .classificacao(Integer.parseInt(strings[0]))
                .título(strings[1])
                .generos(new LerGeneroServiceImpl().generoStrToHastSet(strings[2]))
                .descricao(strings[3])
                .diretor(strings[4])
                .atores(new LerAtorServiceImpl().atorsStrToHastSet(strings[5]))
                .ano(Year.of(Integer.parseInt(strings[6])))
                .tempoDeExecucao(Integer.parseInt(strings[7]))
                .avaliacao(BigDecimal.valueOf(Double.parseDouble(strings[8])))
                .votos(BigInteger.valueOf(Integer.parseInt(strings[9])))
                .receita(BigDecimal.valueOf(Double.parseDouble(strings.length <= 10?"0":strings[10].isEmpty()?"0":strings[10])))
                .metascore(Integer.parseInt(strings.length <= 11?"0":strings[11]))
                .build();
    }

}
