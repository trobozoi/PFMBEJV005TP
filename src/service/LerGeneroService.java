package service;

import model.Genero;

import java.util.HashSet;

public interface LerGeneroService {
    Genero generoStr(String s);

    HashSet<Genero> generoStrToHastSet(String s);
}
