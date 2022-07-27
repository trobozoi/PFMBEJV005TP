package service;

import exception.GeneroDuplicadoException;
import model.Genero;

import java.util.Arrays;
import java.util.HashSet;

public class LerGeneroServiceImpl implements LerGeneroService {
    @Override
    public Genero generoStr(String s){
        return Genero.builder().genero(s).build();
    }

    @Override
    public HashSet<Genero> generoStrToHastSet(String s){
        HashSet<Genero> generos = new HashSet<>();
        try {
            Arrays.stream(s.split(";")).forEach(s1 -> generos.add(generoStr(s1.trim())));
        } catch (GeneroDuplicadoException generoDuplicadoException){
            System.out.println("Esse genero já foi inserido na coleção" + generoDuplicadoException.getMessage());
        }
        return generos;
    }
}
