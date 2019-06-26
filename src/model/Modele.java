/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.*;
import java.util.*;

/**
 *
 * @author Stéphane ROUX
 */
public class Modele {

    private Map<Integer, ElementGeometrique> ensemble;

    public Modele() {
        ensemble = new TreeMap<Integer, ElementGeometrique>();
    }

    public ElementGeometrique get(Integer i) {
        return ensemble.get(i);
    }

    public int add(ElementGeometrique e) {
        ensemble.put(e.getID(), e);
        return e.getID();
    }

    public int getNbElts() {
        return ensemble.size();
    }

    public List<ElementGeometrique> remove(Integer i) {
        List<ElementGeometrique> ret = new ArrayList<ElementGeometrique>();
        ElementGeometrique e = ensemble.remove(i);

        if (e != null) {
            ret.add(e);
            ensemble.values().forEach((d) -> {
                d.getDependants().remove(i);
            });
            List<Integer> toRemove = e.getDependants();
            toRemove.stream().map((r) -> this.remove(r)).forEachOrdered((dep) -> {
                dep.forEach((d) -> {
                    ret.add(d);
                });
            });
        }
        return ret;
    }

    public Set<Integer> getKey() {
        return ensemble.keySet();
    }

    public void dessine(Dessinateur d) {
        d.blanchit();
        for (ElementGeometrique e : ensemble.values()) {
            e.dessine(d);
        }
    }

    public List<Integer> getFromPosition(double x, double y) {
        List<Integer> ret = new ArrayList<Integer>();
        for (Integer e : ensemble.keySet()) {
            if (ensemble.get(e).isOn(x, y)) {
                ret.add(e);
            }
        }
        return ret;
    }

    public int addPoint(int x, int y) {
        double newX = x;
        double newY = y;
        List<Integer> near = new ArrayList<Integer>();
        for (Integer e : ensemble.keySet()) {
            if (ensemble.get(e).isOn(x, y)) {
                near.add(e);
            }
        }
        if (near.size() == 1) {
            newX = ensemble.get(near.get(0)).getXnear(x, y);
            newY = ensemble.get(near.get(0)).getYnear(x, y);
        } else if (!near.isEmpty()) {
            Intersector i = new Intersector();
            ensemble.get(near.get(0)).accept(i);
            ensemble.get(near.get(1)).accept(i);
            if (i.calculIntersection()) {
                newX = i.getX_intersection(x, y);
                newY = i.getY_intersection(x, y);
            } else {
                newX = ensemble.get(near.get(0)).getXnear(x, y);
                newY = ensemble.get(near.get(0)).getYnear(x, y);
            }
        }
        Point p = new Point(newX, newY);
        this.add(p);
        return p.getID();
    }

    public List<Integer> sommeVecteur(int vec1, int vec2, int po) {
        Vecteur v1 = (Vecteur) (ensemble.get(vec1));
        Vecteur v2 = (Vecteur) (ensemble.get(vec2));
        Point p = (Point) (ensemble.get(po));
        double npx = p.getX() + v1.getX() + v2.getX();
        double npy = p.getY() + v1.getY() + v2.getY();
        Point p2 = new Point(npx, npy);
        this.add(p2);
        Vecteur nv = new Vecteur(p, p2);
        this.add(nv);
        List<Integer> ret = new ArrayList<Integer>();
        ret.add(p2.getID());
        ret.add(nv.getID());
        return ret;
    }

    public List<Integer> droiteParallele(int droite, int point) {
        Droite d = (Droite) (ensemble.get(droite));
        Point p = (Point) (ensemble.get(point));
        double nx = p.getX() + (d.getBX() - d.getAX());
        double ny = p.getY() + (d.getBY() - d.getAY());
        Point p2 = new Point(nx, ny);
        this.add(p2);
        Droite dp = new Droite(p, p2);
        this.add(dp);
        List<Integer> ret = new ArrayList<Integer>();
        ret.add(p2.getID());
        ret.add(dp.getID());
        return ret;
    }

    public List<Integer> droitePerpendiculaire(int droite, int point) {
        Droite d = (Droite) (ensemble.get(droite));
        Point p = (Point) (ensemble.get(point));
        double nx = p.getX() - (d.getBY() - d.getAY());
        double ny = p.getY() + (d.getBX() - d.getAX());
        Point p2 = new Point(nx, ny);
        this.add(p2);
        Droite dp = new Droite(p, p2);
        this.add(dp);
        List<Integer> ret = new ArrayList<Integer>();
        ret.add(p2.getID());
        ret.add(dp.getID());
        return ret;
    }

    public int milieu(int pointA, int pointB) {
        Point A = (Point) (ensemble.get(pointA));
        Point B = (Point) (ensemble.get(pointB));
        Point M = new Point((A.getX() + B.getX()) / 2.0, (A.getY() + B.getY()) / 2.0);
        this.add(M);
        return M.getID();
    }

    public List<Integer> mediatrice(int pointA, int pointB) {
        Point A = (Point) (ensemble.get(pointA));
        Point B = (Point) (ensemble.get(pointB));
        Point M = new Point((A.getX() + B.getX()) / 2.0, (A.getY() + B.getY()) / 2.0);
        this.add(M);
        double nx = M.getX() - (B.getY() - A.getY());
        double ny = M.getY() + (B.getX() - A.getX());
        Point p2 = new Point(nx, ny);
        this.add(p2);
        Droite dp = new Droite(M, p2);
        this.add(dp);
        List<Integer> ret = new ArrayList<Integer>();
        ret.add(M.ID);
        ret.add(p2.ID);
        ret.add(dp.ID);
        return ret;
    }

    public List<Integer> bissectrice(int pointA, int pointB, int pointC) {
        Point A = (Point) (ensemble.get(pointA));
        Point B = (Point) (ensemble.get(pointB));
        Point C = (Point) (ensemble.get(pointC));
        double AB = Math.sqrt((A.getX() - B.getX()) * (A.getX() - B.getX()) + (A.getY() - B.getY()) * (A.getY() - B.getY()));
        double CB = Math.sqrt((C.getX() - B.getX()) * (C.getX() - B.getX()) + (C.getY() - B.getY()) * (C.getY() - B.getY()));
        double xBA = A.getX() - B.getX();
        double yBA = A.getY() - B.getY();
        double xBC = C.getX() - B.getX();
        double yBC = C.getY() - B.getY();
        double cosAngle = (xBA * xBC + yBA * yBC) / (AB * CB);
        double sinAngle = (xBA * yBC - yBA * xBC) / (AB * CB);
        double angle = Math.acos(cosAngle);
        if (sinAngle < 0) {
            angle = -angle;
        }
        Point D = (Point) A.rotate(B, angle / 2);
        this.add(D);
        Droite bis = new Droite(B, D);
        this.add(bis);
        List<Integer> ret = new ArrayList<Integer>();
        ret.add(D.ID);
        ret.add(bis.ID);
        return ret;
    }

    public void loadFile(String chemin) throws Exception {
        ensemble = ElementGeometrique.loadFile(chemin);
    }

    public void saveFile(String chemin) throws IOException {
        BufferedWriter file = new BufferedWriter(new FileWriter(new File(chemin), false));
        for (Integer k : ensemble.keySet()) {
            file.append(ensemble.get(k).toString());
            file.newLine();
        }
        file.close();
    }
}
