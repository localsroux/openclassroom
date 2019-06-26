/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.IOException;
import java.util.*;
import java.util.Map;
import model.*;
import vue.*;

/**
 *
 * @author Stéphane ROUX
 */
public class Controleur {

    private boolean redoPossible;
    private boolean modelModified;
    private String currentPath;

    private enum Etat {

        CREATION_POINT,
        CREATION_DROITE_POINT_A,
        CREATION_DROITE_POINT_B,
        CREATION_DEMI_DROITE_POINT_A,
        CREATION_DEMI_DROITE_POINT_B,
        CREATION_SEGMENT_POINT_A,
        CREATION_SEGMENT_POINT_B,
        CREATION_CERCLE_POINT_A,
        CREATION_CERCLE_POINT_B,
        CREATION_VECTEUR_POINT_A,
        CREATION_VECTEUR_POINT_B,
        SYM_CENTR_CENTR,
        SYM_CENTR_ORIGIN,
        SYM_AX_AX,
        SYM_AX_ORIGIN,
        TRANS_VEC,
        TRANS_ORIGIN,
        ROT_CENTR,
        ROT_ORIGIN,
        HOM_CENTR,
        HOM_ORIGIN,
        SUPPRESSION_DEPLACEMENT,
        SOMME_VECTEUR_VA,
        SOMME_VECTEUR_VB,
        SOMME_VECTEUR_P,
        DROITE_PAR_DROITE,
        DROITE_PAR_POINT,
        DROITE_PERP_DROITE,
        DROITE_PERP_POINT,
        MILIEU_POINT_A,
        MILIEU_POINT_B,
        MEDIATRICE_POINT_A,
        MEDIATRICE_POINT_B,
        BISSECTRICE_POINT_A,
        BISSECTRICE_POINT_B,
        BISSECTRICE_POINT_C,
    }
    private Etat etat;
    private Modele modele;
    private VuePrincipale vue;
    private int PointA;
    private int PointB;
    private int PointC;
    private int Axe;
    private int Centre;
    private int Vec;
    private int Vec2;
    private int Selected;
    private int xDep;
    private int yDep;
    private int xInit;
    private int yInit;
    private List<Commande> commandes;
    private int last;

    public Controleur() {
        modele = new Modele();
        etat = Etat.CREATION_POINT;
        vue = new VuePrincipale(this);
        Selected = -1;
        last = -1;
        commandes = new ArrayList<Commande>();
        modelModified = false;
        currentPath = "";

    }

    public void cliqueVueDessin(int x, int y) {

        List<Integer> code_cand;
        switch (etat) {
            case CREATION_POINT:
                int nbPoint = modele.addPoint(x, y);
                ElementGeometrique eg = modele.get(nbPoint);
                List<ElementGeometrique> l = new ArrayList<ElementGeometrique>();
                l.add(eg);
                this.add(new CommandeAdd(l));
                break;
            case CREATION_DROITE_POINT_A:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e) instanceof Point) {
                        PointA = e;
                        etat = Etat.CREATION_DROITE_POINT_B;
                        break;
                    }
                }

                break;
            case CREATION_DROITE_POINT_B:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e) instanceof Point) {
                        PointB = e;
                        int droite = modele.add(new Droite((Point) modele.get(PointA), (Point) modele.get(PointB)));
                        List<ElementGeometrique> ld = new ArrayList<ElementGeometrique>();
                        ld.add(modele.get(droite));
                        this.add(new CommandeAdd(ld));
                        etat = Etat.CREATION_DROITE_POINT_A;
                        break;
                    }
                }
                break;
            case CREATION_DEMI_DROITE_POINT_A:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e) instanceof Point) {
                        PointA = e;
                        etat = Etat.CREATION_DEMI_DROITE_POINT_B;
                        break;
                    }
                }
                break;
            case CREATION_DEMI_DROITE_POINT_B:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e) instanceof Point) {
                        PointB = e;
                        int dd = modele.add(new DemiDroite((Point) modele.get(PointA), (Point) modele.get(PointB)));
                        List<ElementGeometrique> ld = new ArrayList<ElementGeometrique>();
                        ld.add(modele.get(dd));
                        this.add(new CommandeAdd(ld));
                        etat = Etat.CREATION_DEMI_DROITE_POINT_A;
                        break;
                    }
                }
                break;
            case CREATION_SEGMENT_POINT_A:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e) instanceof Point) {
                        PointA = e;
                        etat = Etat.CREATION_SEGMENT_POINT_B;
                        break;
                    }
                }
                break;
            case CREATION_SEGMENT_POINT_B:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e) instanceof Point) {
                        PointB = e;
                        int s = modele.add(new Segment((Point) modele.get(PointA), (Point) modele.get(PointB)));
                        List<ElementGeometrique> ld = new ArrayList<ElementGeometrique>();
                        ld.add(modele.get(s));
                        this.add(new CommandeAdd(ld));
                        etat = Etat.CREATION_SEGMENT_POINT_A;
                        break;
                    }
                }
                break;
            case CREATION_CERCLE_POINT_A:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e) instanceof Point) {
                        PointA = e;
                        etat = Etat.CREATION_CERCLE_POINT_B;
                        break;
                    }
                }
                break;
            case CREATION_CERCLE_POINT_B:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e) instanceof Point) {
                        PointB = e;

                        int c = modele.add(new Cercle((Point) modele.get(PointA), (Point) modele.get(PointB)));
                        List<ElementGeometrique> ld = new ArrayList<ElementGeometrique>();
                        ld.add(modele.get(c));
                        this.add(new CommandeAdd(ld));
                        etat = Etat.CREATION_CERCLE_POINT_A;
                        break;
                    }
                }
                break;
            case CREATION_VECTEUR_POINT_A:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e) instanceof Point) {
                        PointA = e;
                        etat = Etat.CREATION_VECTEUR_POINT_B;
                        break;
                    }
                }
                break;
            case CREATION_VECTEUR_POINT_B:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e) instanceof Point) {
                        PointB = e;
                        int v = modele.add(new Vecteur((Point) modele.get(PointA), (Point) modele.get(PointB)));
                        List<ElementGeometrique> ld = new ArrayList<ElementGeometrique>();
                        ld.add(modele.get(v));
                        this.add(new CommandeAdd(ld));
                        etat = Etat.CREATION_VECTEUR_POINT_A;
                        break;
                    }
                }
                break;

            case SYM_CENTR_CENTR:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeCenter()) {
                        Centre = e;
                        etat = Etat.SYM_CENTR_ORIGIN;
                        break;
                    }
                }
                break;
            case SYM_CENTR_ORIGIN:
                code_cand = modele.getFromPosition(x, y);
                if (!code_cand.isEmpty()) {
                    ElementGeometrique e = modele.get(code_cand.get(0)).symCentre(modele.get(Centre));
                    Map<Integer, ElementGeometrique> cont = e.getContenus();
                    for (Integer d : cont.keySet()) {
                        if (!modele.getKey().contains(d)) {
                            modele.add(cont.get(d));
                        }
                    }
                    modele.add(e);
                    List<ElementGeometrique> ld = new ArrayList<ElementGeometrique>();
                    ld.add(modele.get(e.getID()));
                    ld.addAll(cont.values());
                    this.add(new CommandeAdd(ld));
                    etat = Etat.SYM_CENTR_CENTR;

                }
                break;
            case SYM_AX_AX:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeAxis()) {
                        Axe = e;
                        etat = Etat.SYM_AX_ORIGIN;
                        break;
                    }
                }
                break;
            case SYM_AX_ORIGIN:
                code_cand = modele.getFromPosition(x, y);
                if (!code_cand.isEmpty()) {
                    ElementGeometrique e = modele.get(code_cand.get(0)).symAx(modele.get(Axe));
                    Map<Integer, ElementGeometrique> cont = e.getContenus();
                    for (Integer d : cont.keySet()) {
                        if (!modele.getKey().contains(d)) {
                            modele.add(cont.get(d));
                        }
                    }
                    modele.add(e);
                    List<ElementGeometrique> ld = new ArrayList<ElementGeometrique>();
                    ld.add(modele.get(e.getID()));
                    ld.addAll(cont.values());
                    this.add(new CommandeAdd(ld));
                    etat = Etat.SYM_AX_AX;

                }
                break;
            case TRANS_VEC:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeVector()) {
                        Vec = e;
                        etat = Etat.TRANS_ORIGIN;
                        break;
                    }
                }
                break;
            case TRANS_ORIGIN:
                code_cand = modele.getFromPosition(x, y);
                if (!code_cand.isEmpty()) {
                    ElementGeometrique e = modele.get(code_cand.get(0)).translate(modele.get(Vec));
                    Map<Integer, ElementGeometrique> cont = e.getContenus();
                    for (Integer d : cont.keySet()) {
                        if (!modele.getKey().contains(d)) {
                            modele.add(cont.get(d));
                        }
                    }
                    modele.add(e);
                    List<ElementGeometrique> ld = new ArrayList<ElementGeometrique>();
                    ld.add(modele.get(e.getID()));
                    ld.addAll(cont.values());
                    this.add(new CommandeAdd(ld));
                    etat = Etat.TRANS_VEC;

                }
                break;
            case ROT_CENTR:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeCenter()) {
                        Centre = e;
                        etat = Etat.ROT_ORIGIN;
                        break;
                    }
                }
                break;
            case ROT_ORIGIN:
                code_cand = modele.getFromPosition(x, y);
                if (!code_cand.isEmpty()) {
                    double angle = vue.getAngle();
                    ElementGeometrique e = modele.get(code_cand.get(0)).rotate(modele.get(Centre), -angle);
                    Map<Integer, ElementGeometrique> cont = e.getContenus();
                    for (Integer d : cont.keySet()) {
                        if (!modele.getKey().contains(d)) {
                            modele.add(cont.get(d));
                        }
                    }
                    modele.add(e);
                    List<ElementGeometrique> ld = new ArrayList<ElementGeometrique>();
                    ld.add(modele.get(e.getID()));
                    ld.addAll(cont.values());
                    this.add(new CommandeAdd(ld));
                    etat = Etat.ROT_CENTR;

                }
                break;
            case HOM_CENTR:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeCenter()) {
                        Centre = e;
                        etat = Etat.HOM_ORIGIN;
                        break;
                    }
                }
                break;
            case HOM_ORIGIN:
                code_cand = modele.getFromPosition(x, y);
                if (!code_cand.isEmpty()) {
                    double rap = vue.getRapport();
                    ElementGeometrique e = modele.get(code_cand.get(0)).homothetie(modele.get(Centre), rap);
                    Map<Integer, ElementGeometrique> cont = e.getContenus();
                    for (Integer d : cont.keySet()) {
                        if (!modele.getKey().contains(d)) {
                            modele.add(cont.get(d));
                        }
                    }
                    modele.add(e);
                    List<ElementGeometrique> ld = new ArrayList<ElementGeometrique>();
                    ld.add(modele.get(e.getID()));
                    ld.addAll(cont.values());
                    this.add(new CommandeAdd(ld));
                    etat = Etat.HOM_CENTR;

                }
                break;
            case SUPPRESSION_DEPLACEMENT:
                code_cand = modele.getFromPosition(x, y);
                if (!code_cand.isEmpty()) {
                    Commande c = new CommandeSuppr(modele.remove(code_cand.get(0)));
                    
                    this.add(c);
                }

                break;
            case SOMME_VECTEUR_VA:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeVector()) {
                        Vec = e;
                        etat = Etat.SOMME_VECTEUR_VB;
                        break;
                    }
                }
                break;
            case SOMME_VECTEUR_VB:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeVector()) {
                        Vec2 = e;
                        etat = Etat.SOMME_VECTEUR_P;
                        break;
                    }
                }
                break;
            case SOMME_VECTEUR_P:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeCenter()) {
                        Centre = e;
                        etat = Etat.SOMME_VECTEUR_VA;
                        break;

                    }
                }
                List<Integer> nbVec = modele.sommeVecteur(Vec, Vec2, Centre);
                List<ElementGeometrique> ld = new ArrayList<ElementGeometrique>();
                for (int n : nbVec) {
                    ld.add(modele.get(n));
                }
                this.add(new CommandeAdd(ld));
                break;
            case DROITE_PAR_DROITE:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeAxis()) {
                        Axe = e;
                        etat = Etat.DROITE_PAR_POINT;
                        break;

                    }
                }
                break;
            case DROITE_PAR_POINT:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeCenter()) {
                        Centre = e;
                        etat = Etat.DROITE_PAR_DROITE;
                        List<Integer> nbd = modele.droiteParallele(Axe, Centre);
                        List<ElementGeometrique> ldd = new ArrayList<ElementGeometrique>();
                        for (Integer i : nbd) {
                            ldd.add(modele.get(i));
                        }
                        this.add(new CommandeAdd(ldd));
                        break;
                    }
                }

                break;
            case DROITE_PERP_DROITE:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeAxis()) {
                        Axe = e;
                        etat = Etat.DROITE_PERP_POINT;
                        break;

                    }
                }
                break;
            case DROITE_PERP_POINT:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeCenter()) {
                        Centre = e;
                        etat = Etat.DROITE_PERP_DROITE;
                        List<Integer> nbd = modele.droitePerpendiculaire(Axe, Centre);
                        List<ElementGeometrique> ldd = new ArrayList<ElementGeometrique>();
                        for (Integer i : nbd) {
                            ldd.add(modele.get(i));
                        }
                        this.add(new CommandeAdd(ldd));
                        break;

                    }
                }
                break;
            case MILIEU_POINT_A:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeCenter()) {
                        PointA = e;
                        etat = Etat.MILIEU_POINT_B;
                        break;

                    }
                }
                break;
            case MILIEU_POINT_B:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeCenter()) {
                        PointB = e;
                        etat = Etat.MILIEU_POINT_A;
                        int nbm = modele.milieu(PointA, PointB);
                        List<ElementGeometrique> ldd = new ArrayList<ElementGeometrique>();
                        ldd.add(modele.get(nbm));
                        this.add(new CommandeAdd(ldd));
                        break;

                    }
                }
                break;
            case MEDIATRICE_POINT_A:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeCenter()) {
                        PointA = e;
                        etat = Etat.MEDIATRICE_POINT_B;
                        break;

                    }
                }
                break;
            case MEDIATRICE_POINT_B:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeCenter()) {
                        PointB = e;
                        etat = Etat.MEDIATRICE_POINT_A;

                        List<Integer> nbm = modele.mediatrice(PointA, PointB);

                        List<ElementGeometrique> ldd = new ArrayList<ElementGeometrique>();
                        for (Integer i : nbm) {
                            ldd.add(modele.get(i));
                        }
                        this.add(new CommandeAdd(ldd));

                        break;

                    }
                }
                break;
            case BISSECTRICE_POINT_A:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeCenter()) {
                        PointA = e;
                        etat = Etat.BISSECTRICE_POINT_B;
                        break;

                    }
                }
                break;
            case BISSECTRICE_POINT_B:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeCenter()) {
                        PointB = e;
                        etat = Etat.BISSECTRICE_POINT_C;
                        break;

                    }
                }
                break;
            case BISSECTRICE_POINT_C:
                code_cand = modele.getFromPosition(x, y);
                for (Integer e : code_cand) {
                    if (modele.get(e).canBeCenter()) {
                        PointC = e;
                        etat = Etat.BISSECTRICE_POINT_A;
                        List<Integer> nbd = modele.bissectrice(PointA, PointB, PointC);
                        List<ElementGeometrique> ldd = new ArrayList<ElementGeometrique>();
                        for (Integer i : nbd) {
                            ldd.add(modele.get(i));

                        }
                        this.add(new CommandeAdd(ldd));
                        break;

                    }
                }
                break;
        }

        vue.update(modele);
    }

    public void changeBouton(SituationBouton bouton) {
        switch (bouton) {
            case FIGURES_POINT:
                etat = Etat.CREATION_POINT;
                break;
            case FIGURES_DROITE:
                etat = Etat.CREATION_DROITE_POINT_A;
                break;
            case FIGURES_DEMI_DROITE:
                etat = Etat.CREATION_DEMI_DROITE_POINT_A;
                break;
            case FIGURES_SEGMENT:
                etat = Etat.CREATION_SEGMENT_POINT_A;
                break;
            case FIGURES_VECTEUR:
                etat = Etat.CREATION_VECTEUR_POINT_A;
                break;
            case FIGURES_CERCLE:
                etat = Etat.CREATION_CERCLE_POINT_A;
                break;
            case TRANSFO_SYM_CEN_:
                etat = Etat.SYM_CENTR_CENTR;
                break;
            case TRANSFO_SYM_AX:
                etat = Etat.SYM_AX_AX;
                break;
            case TRANSFO_TRANS:
                etat = Etat.TRANS_VEC;
                break;
            case TRANSFO_ROT:
                etat = Etat.ROT_CENTR;
                break;
            case TRANSFO_HOM:
                etat = Etat.HOM_CENTR;
                break;
            case SUPPR_DEP:
                etat = Etat.SUPPRESSION_DEPLACEMENT;
                break;
            case SOMME_VEC:
                etat = Etat.SOMME_VECTEUR_VA;
                break;
            case DROITE_PAR:
                etat = Etat.DROITE_PAR_DROITE;
                break;
            case DROITE_PERP:
                etat = Etat.DROITE_PERP_DROITE;
                break;
            case MILIEU:
                etat = Etat.MILIEU_POINT_A;
                break;
            case MEDIATRICE:
                etat = Etat.MEDIATRICE_POINT_A;
                break;
            case BISSECTRICE:
                etat = Etat.BISSECTRICE_POINT_A;
        }

        vue.update(modele);
    }

    public String getEtat() {
        switch (etat) {
            case CREATION_POINT:
                return "POINT";
            case CREATION_DROITE_POINT_A:
                return "DROITE POINT A";
            case CREATION_DROITE_POINT_B:
                return "DROITE POINT B";
            case CREATION_DEMI_DROITE_POINT_A:
                return "DEMI-DROITE POINT A";
            case CREATION_DEMI_DROITE_POINT_B:
                return "DEMI-DROITE POINT B";
            case CREATION_SEGMENT_POINT_A:
                return "SEGMENT POINT A";
            case CREATION_SEGMENT_POINT_B:
                return "SEGMENT POINT B";
            case CREATION_CERCLE_POINT_A:
                return "CERCLE POINT A";
            case CREATION_CERCLE_POINT_B:
                return "CERCLE POINT B";
            case CREATION_VECTEUR_POINT_A:
                return "VECTEUR POINT A";
            case CREATION_VECTEUR_POINT_B:
                return "VECTEUR POINT B";
            case SYM_CENTR_CENTR:
                return "CENTRE SYMETRIE CENTRALE";
            case SYM_CENTR_ORIGIN:
                return "ORIGINE SYMETRIE CENTRALE";
            case SYM_AX_AX:
                return "AXE SYMETRIE AXIALE";
            case SYM_AX_ORIGIN:
                return "ORIGINE SYMETRIE AXIALE";
            case TRANS_VEC:
                return "VECTEUR TRANSLATION";
            case TRANS_ORIGIN:
                return "ORIGINE TRANSLATION";
            case ROT_CENTR:
                return "CENTRE ROTATION";
            case ROT_ORIGIN:
                return "ORIGINE ROTATION";
            case HOM_CENTR:
                return "CENTRE HOMOTHETIE";
            case HOM_ORIGIN:
                return "ORIGINE HOMOTHETIE";
            case SUPPRESSION_DEPLACEMENT:
                return "SUPPRESSION - DEPLACEMENT";
            case SOMME_VECTEUR_VA:
                return "PREMIER VECTEUR SOMME";
            case SOMME_VECTEUR_VB:
                return "SECOND VECTEUR SOMME";
            case SOMME_VECTEUR_P:
                return "ORIGINE VECTEUR SOMME";
            case DROITE_PAR_DROITE:
                return "DROITE PARALELLE A";
            case DROITE_PAR_POINT:
                return "PASSANT PAR";
            case DROITE_PERP_DROITE:
                return "DROITE PERPENDICULAIRE A";
            case DROITE_PERP_POINT:
                return "PASSANT PAR";
            case MILIEU_POINT_A:
                return "MILIEU POINT A";
            case MILIEU_POINT_B:
                return "MILIEU POINT B";
            case MEDIATRICE_POINT_A:
                return "MEDIATRICE POINT A";
            case MEDIATRICE_POINT_B:
                return "MEDIATRICE POINT B";
            case BISSECTRICE_POINT_A:
                return "BISSECTRICE POINT A";
            case BISSECTRICE_POINT_B:
                return "BISSECTRICE POINT B";
            case BISSECTRICE_POINT_C:
                return "BISSECTRICE POINT C";
        }

        return "";
    }

    public void selectionne(int x, int y) {
        List<Integer> code_cand = modele.getFromPosition(x, y);
        if (!code_cand.isEmpty()) {
            Selected = code_cand.get(0);
            xDep = x;
            yDep = y;
            xInit = x;
            yInit = y;
        } else {
            Selected = -1;
        }
    }

    public boolean bouge(int x, int y) {
        redoPossible = false;
        if (Selected >= 0 && etat == Etat.SUPPRESSION_DEPLACEMENT) {

            modele.get(Selected).move(x - xDep, y - yDep);
            xDep = x;
            yDep = y;
            vue.update(modele);

            return true;
        }
        return false;
    }

    public void relache(int x, int y) {
        if (Selected >= 0 && etat == Etat.SUPPRESSION_DEPLACEMENT) {
            
            Commande commeMove = new CommandeMove(Selected, x, y, xInit, yInit);
            this.add(commeMove);
            Selected = -1;
            modelModified = true;
        }
        
        vue.update(modele);
    }

    public void saveModele(String path) throws IOException {
        modele.saveFile(path);
        modelModified = false;
        currentPath = path;
    }

    public void loadModele(String path) throws Exception {
        modele.loadFile(path);
        commandes.clear();
        last = -1;
        redoPossible = false;
        vue.update(modele);
        modelModified = false;
        currentPath = path;
    }

    public void newModele() {
        modele = new Modele();
        commandes.clear();
        last = -1;
        redoPossible = false;
        vue.update(modele);
        modelModified = false;
        currentPath = "";

    }

    public void cliqueRedo() {
        if (redoPossible()) {
            commandes.get(last + 1).execute(modele);
            last++;
            redoPossible = (last + 1 < commandes.size());
        }

        vue.update(modele);
        modelModified = true;
    }

    public void cliqueUndo() {
        if (last >= 0) {
            commandes.get(last).undo(modele);
            last--;
            redoPossible = true;
        }
        etat = Etat.SUPPRESSION_DEPLACEMENT;
        vue.update(modele);
        vue.setDefault();
        modelModified = true;


    }

    public boolean undoPossible() {
        return (last >= 0);
    }

    public boolean redoPossible() {
        return redoPossible && last <= commandes.size() - 1;
    }

    private void add(Commande c) {
        modelModified = true;
        last++;
        commandes.add(last, c);
        int i = last + 1;
        while (i < commandes.size()) {
            commandes.remove(i);
        }


        redoPossible = false;

    }

    public boolean isModelModified() {
        return modelModified;
    }

    public String getCurrentPath() {
        return this.currentPath;
    }
}
