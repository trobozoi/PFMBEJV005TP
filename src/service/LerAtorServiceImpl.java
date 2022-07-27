package service;

import exception.AtorDuplicadoException;
import model.Ator;

import java.util.Arrays;
import java.util.HashSet;

public class LerAtorServiceImpl implements LerAtorService {
    @Override
    public Ator atorStr(String s){
        return Ator.builder().ator(s).build();
    }

    @Override
    public HashSet<Ator> atorsStrToHastSet(String s){
        HashSet<Ator> ators = new HashSet<>();
        try {
            Arrays.stream(s.split(";")).forEach(s1 -> ators.add(atorStr(s1.trim())));
        } catch (AtorDuplicadoException atorDuplicadoException){
            System.out.println("Esse ator já foi inserido na coleção" + atorDuplicadoException.getMessage());
        }
        return ators;
    }
}
