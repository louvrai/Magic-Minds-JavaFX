package controllers;

import Entity.Categorie;
import Entity.Cours;

import java.util.ArrayList;
import java.util.List;

public class Progress {
    private List<Categorie> FCPL = new ArrayList<>();
    private List<Cours> FCOPL=new ArrayList<>();
    public void fetchPData(List<Categorie> cpl, List<Cours> copl) {
        FCOPL=copl;
        FCPL=cpl;
    }
}
