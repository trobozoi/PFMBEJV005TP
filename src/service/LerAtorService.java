package service;

import model.Ator;

import java.util.HashSet;

public interface LerAtorService {
    Ator atorStr(String s);

    HashSet<Ator> atorsStrToHastSet(String s);
}
