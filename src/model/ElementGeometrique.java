/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Stéphane ROUX
 */
public abstract class ElementGeometrique {

    protected static int lastID = 0;
    public final static int delta = 10;
    protected int ID;
    protected List<Integer> dependants;
    protected Map<Integer, ElementGeometrique> contenus;

    protected ElementGeometrique() {
        this.ID = lastID;
        lastID++;
        dependants = new ArrayList<Integer>();
        contenus = new TreeMap<Integer, ElementGeometrique>();
    }

    public final int getID() {
        return ID;
    }

    public final List<Integer> getDependants() {
        return dependants;
    }

    public final void addDependant(int d) {
        dependants.add(d);
    }

    public final Map<Integer, ElementGeometrique> getContenus() {
        return contenus;
    }

    public final void addContenus(ElementGeometrique e) {
        contenus.put(e.getID(), e);
    }

    public abstract void move(double dx, double dy);

    protected abstract ElementGeometrique translateSpec(ElementGeometrique source);

    protected abstract ElementGeometrique rotateSpec(ElementGeometrique source, double angle);

    protected abstract ElementGeometrique symCentreSpec(ElementGeometrique source);

    protected abstract ElementGeometrique symAxSpec(ElementGeometrique source);

    protected abstract ElementGeometrique translateReel(Vecteur v);

    protected abstract ElementGeometrique rotateReel(Point centre, double angle);

    protected abstract ElementGeometrique symCentreReel(Point centre);

    protected abstract ElementGeometrique symAxReel(Droite axe);

    protected abstract ElementGeometrique homothSpec(ElementGeometrique source, double rapport);

    protected abstract ElementGeometrique homothReel(Point aThis, double rapport);

    public final ElementGeometrique translate(ElementGeometrique vecteurTranslation) {
        return vecteurTranslation.translateSpec(this);
    }

    
    public final ElementGeometrique rotate(ElementGeometrique centre, double angle) {
        return centre.rotateSpec(this, angle);
    }

     
    public final ElementGeometrique symCentre(ElementGeometrique centre) {
        return centre.symCentreSpec(this);
    }

    public final ElementGeometrique symAx(ElementGeometrique axe) {
        return axe.symAxSpec(this);
    }

    public final ElementGeometrique homothetie(ElementGeometrique centre, double rapport) {
        return centre.homothSpec(this, rapport);
    }

    public static Map<Integer, ElementGeometrique> loadFile(String chemin) throws Exception {
        Map<Integer, ElementGeometrique> ret = new TreeMap<Integer, ElementGeometrique>();
        Map<Integer, List<Integer>> relationContenus = new TreeMap<Integer, List<Integer>>();
        Map<Integer, List<Integer>> relationDependants = new TreeMap<Integer, List<Integer>>();
        BufferedReader file = new BufferedReader(new FileReader(chemin));
        String line = file.readLine();
        while (line != null) {
            String[] parts = line.split(" ");
            if (parts.length > 1) {
                if (parts[0].equals("POINT")) {
                    if (parts.length < 6) {
                        throw new Exception();
                    }

                    int newId = Integer.parseInt(parts[1]);
                    double px = Double.parseDouble(parts[2]);
                    double py = Double.parseDouble(parts[3]);
                    Point next = new Point(px, py);
                    next.ID = newId;
                    if (lastID <= newId) {
                        lastID = newId + 1;
                    }
                    int posC = -1;
                    int posD = -1;
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].equals("C")) {
                            posC = i;
                        }
                        if (parts[i].equals("D")) {
                            posD = i;
                        }
                    }
                    if (posC == -1 || posD != 4 || posC <= posD) {
                        throw new Exception();
                    }

                    if (!relationDependants.containsKey(newId)) {
                        relationDependants.put(newId, new ArrayList<Integer>());
                    }
                    for (int i = posD + 1; i < posC; i++) {
                        relationDependants.get(newId).add(Integer.parseInt(parts[i]));
                    }

                    if (!relationContenus.containsKey(newId)) {
                        relationContenus.put(newId, new ArrayList<Integer>());
                    }
                    for (int i = posC + 1; i < parts.length; i++) {
                        relationContenus.get(newId).add(Integer.parseInt(parts[i]));
                    }
                    ret.put(newId, next);
                } else if (parts[0].equals("CERCLE")) {
                    if (parts.length < 6) {
                        throw new Exception();
                    }
                    int newId = Integer.parseInt(parts[1]);
                    int idCentre = Integer.parseInt(parts[2]);
                    int idPointDef = Integer.parseInt(parts[3]);
                    Point centre = (Point) ret.get(idCentre);
                    Point def = (Point) ret.get(idPointDef);
                    Cercle next = new Cercle(centre, def);
                    next.ID = newId;
                    if (lastID <= newId) {
                        lastID = newId + 1;
                    }
                    int posC = -1;
                    int posD = -1;
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].equals("C")) {
                            posC = i;
                        }
                        if (parts[i].equals("D")) {
                            posD = i;
                        }
                    }
                    if (posC == -1 || posD != 4 || posC <= posD) {
                        throw new Exception();
                    }
                    if (!relationDependants.containsKey(newId)) {
                        relationDependants.put(newId, new ArrayList<Integer>());
                    }
                    for (int i = posD + 1; i < posC; i++) {
                        relationDependants.get(newId).add(Integer.parseInt(parts[i]));
                    }

                    if (!relationContenus.containsKey(newId)) {
                        relationContenus.put(newId, new ArrayList<Integer>());
                    }
                    for (int i = posC + 1; i < parts.length; i++) {
                        relationContenus.get(newId).add(Integer.parseInt(parts[i]));
                    }
                    ret.put(newId, next);
                } else if (parts[0].equals("DEMIDROITE")) {
                    if (parts.length < 6) {
                        throw new Exception();
                    }
                    int newId = Integer.parseInt(parts[1]);
                    int idA = Integer.parseInt(parts[2]);
                    int idB = Integer.parseInt(parts[3]);
                    Point A = (Point) ret.get(idA);
                    Point B = (Point) ret.get(idB);
                    DemiDroite next = new DemiDroite(A, B);
                    next.ID = newId;
                    if (lastID <= newId) {
                        lastID = newId + 1;
                    }
                    int posC = -1;
                    int posD = -1;
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].equals("C")) {
                            posC = i;
                        }
                        if (parts[i].equals("D")) {
                            posD = i;
                        }
                    }
                    if (posC == -1 || posD != 4 || posC <= posD) {
                        throw new Exception();
                    }
                    if (!relationDependants.containsKey(newId)) {
                        relationDependants.put(newId, new ArrayList<Integer>());
                    }
                    for (int i = posD + 1; i < posC; i++) {
                        relationDependants.get(newId).add(Integer.parseInt(parts[i]));
                    }

                    if (!relationContenus.containsKey(newId)) {
                        relationContenus.put(newId, new ArrayList<Integer>());
                    }
                    for (int i = posC + 1; i < parts.length; i++) {
                        relationContenus.get(newId).add(Integer.parseInt(parts[i]));
                    }
                    ret.put(newId, next);
                } else if (parts[0].equals("DROITE")) {
                    if (parts.length < 6) {
                        throw new Exception();
                    }
                    int newId = Integer.parseInt(parts[1]);
                    int idA = Integer.parseInt(parts[2]);
                    int idB = Integer.parseInt(parts[3]);
                    Point A = (Point) ret.get(idA);
                    Point B = (Point) ret.get(idB);
                    Droite next = new Droite(A, B);
                    next.ID = newId;
                    if (lastID <= newId) {
                        lastID = newId + 1;
                    }
                    int posC = -1;
                    int posD = -1;
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].equals("C")) {
                            posC = i;
                        }
                        if (parts[i].equals("D")) {
                            posD = i;
                        }
                    }
                    if (posC == -1 || posD != 4 || posC <= posD) {
                        throw new Exception();
                    }
                    if (!relationDependants.containsKey(newId)) {
                        relationDependants.put(newId, new ArrayList<Integer>());
                    }
                    for (int i = posD + 1; i < posC; i++) {
                        relationDependants.get(newId).add(Integer.parseInt(parts[i]));
                    }

                    if (!relationContenus.containsKey(newId)) {
                        relationContenus.put(newId, new ArrayList<Integer>());
                    }
                    for (int i = posC + 1; i < parts.length; i++) {
                        relationContenus.get(newId).add(Integer.parseInt(parts[i]));
                    }
                    ret.put(newId, next);
                } else if (parts[0].equals("SEGMENT")) {
                    if (parts.length < 6) {
                        throw new Exception();
                    }
                    int newId = Integer.parseInt(parts[1]);
                    int idA = Integer.parseInt(parts[2]);
                    int idB = Integer.parseInt(parts[3]);
                    Point A = (Point) ret.get(idA);
                    Point B = (Point) ret.get(idB);
                    Segment next = new Segment(A, B);
                    next.ID = newId;
                    if (lastID <= newId) {
                        lastID = newId + 1;
                    }
                    int posC = -1;
                    int posD = -1;
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].equals("C")) {
                            posC = i;
                        }
                        if (parts[i].equals("D")) {
                            posD = i;
                        }
                    }
                    if (posC == -1 || posD != 4 || posC <= posD) {
                        throw new Exception();
                    }
                    if (!relationDependants.containsKey(newId)) {
                        relationDependants.put(newId, new ArrayList<Integer>());
                    }
                    for (int i = posD + 1; i < posC; i++) {
                        relationDependants.get(newId).add(Integer.parseInt(parts[i]));
                    }

                    if (!relationContenus.containsKey(newId)) {
                        relationContenus.put(newId, new ArrayList<Integer>());
                    }
                    for (int i = posC + 1; i < parts.length; i++) {
                        relationContenus.get(newId).add(Integer.parseInt(parts[i]));
                    }
                    ret.put(newId, next);
                } else if (parts[0].equals("VECTEUR")) {
                    if (parts.length < 6) {
                        throw new Exception();
                    }
                    int newId = Integer.parseInt(parts[1]);
                    int idA = Integer.parseInt(parts[2]);
                    int idB = Integer.parseInt(parts[3]);
                    Point A = (Point) ret.get(idA);
                    Point B = (Point) ret.get(idB);
                    Vecteur next = new Vecteur(A, B);
                    next.ID = newId;
                    if (lastID <= newId) {
                        lastID = newId + 1;
                    }
                    int posC = -1;
                    int posD = -1;
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].equals("C")) {
                            posC = i;
                        }
                        if (parts[i].equals("D")) {
                            posD = i;
                        }
                    }
                    if (posC == -1 || posD != 4 || posC <= posD) {
                        throw new Exception();
                    }
                    if (!relationDependants.containsKey(newId)) {
                        relationDependants.put(newId, new ArrayList<Integer>());
                    }
                    for (int i = posD + 1; i < posC; i++) {
                        relationDependants.get(newId).add(Integer.parseInt(parts[i]));
                    }

                    if (!relationContenus.containsKey(newId)) {
                        relationContenus.put(newId, new ArrayList<Integer>());
                    }
                    for (int i = posC + 1; i < parts.length; i++) {
                        relationContenus.get(newId).add(Integer.parseInt(parts[i]));
                    }
                    ret.put(newId, next);
                } else {
                    throw new Exception();
                }

            } else {
                throw new Exception();
            }

            line = file.readLine();
        }
        for (Integer e : ret.keySet()) {
            ret.get(e).contenus.clear();
            ret.get(e).dependants.clear();
        }
        for (Integer e : relationContenus.keySet()) {
            for (Integer c : relationContenus.get(e)) {
                if (!ret.keySet().contains(c)) {
                    throw new Exception();
                }
                ret.get(e).addContenus(ret.get(c));
            }
        }
        for (Integer e : relationDependants.keySet()) {
            for (Integer c : relationDependants.get(e)) {
                if (!ret.keySet().contains(c)) {
                    throw new Exception();
                }
                ret.get(e).addDependant(c);
            }
        }
        return ret;
    }

    public abstract void dessine(Dessinateur d);

    public abstract boolean canBeCenter();

    public abstract boolean canBeAxis();

    public abstract boolean canBeVector();

    public abstract boolean isOn(double x, double y);

    public abstract double getXnear(double x, double y);

    public abstract double getYnear(double x, double y);

    public abstract void accept(Intersector i);
}
