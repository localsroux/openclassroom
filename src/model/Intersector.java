/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.*;

/**
 *
 * @author St ROUX
 */
public class Intersector {

    private Cercle cercle1;
    private Droite droite1;
    private Cercle cercle2;
    private Droite droite2;
    private DemiDroite demidroite1;
    private Vecteur vecteur1;
    private Segment segment1;
    private DemiDroite demidroite2;
    private Vecteur vecteur2;
    private Segment segment2;
    private List<Double> x_intersection;
    private List<Double> y_intersection;

    private enum Type {

        NULL, CERCLE, DROITE, DEMIDROITE, VECTEUR, SEGMENT
    }
    private Type type1;
    private Type type2;

    public Intersector() {
        type1 = Type.NULL;
        type2 = Type.NULL;
        cercle1 = null;
        cercle2 = null;
        droite1 = null;
        droite2 = null;
        vecteur1 = null;
        vecteur2 = null;
        demidroite1 = null;
        demidroite2 = null;
        segment1 = null;
        segment2 = null;
        x_intersection = new ArrayList<>();
        y_intersection = new ArrayList<>();
    }

    public void visitePoint(Point p) {
    }

    public void visiteDemiDroite(DemiDroite d) {
        if (demidroite1 == null) {
            demidroite1 = d;

        } else {
            demidroite2 = d;

        }
        if (type1 == Type.NULL) {
            type1 = Type.DEMIDROITE;
        } else {
            type2 = Type.DEMIDROITE;
        }

    }

    public void visiteCercle(Cercle c) {
        if (cercle1 == null) {
            cercle1 = c;
        } else {
            cercle2 = c;
        }
        if (type1 == Type.NULL) {
            type1 = Type.CERCLE;
        } else {
            type2 = Type.CERCLE;
        }
    }

    public void visiteDroite(Droite d) {
        if (droite1 == null) {
            droite1 = d;
        } else {
            droite2 = d;
        }
        if (type1 == Type.NULL) {
            type1 = Type.DROITE;
        } else {
            type2 = Type.DROITE;
        }
    }

    public void visiteSegment(Segment s) {
        
        if (segment1 == null) {
            segment1 = s;
        } else {
            segment2 = s;
        }
        if (type1 == Type.NULL) {
            type1 = Type.SEGMENT;
        } else {
            type2 = Type.SEGMENT;
        }
    }

    public void visiteVecteur(Vecteur v) {
        if (vecteur1 == null) {
            vecteur1 = v;
        } else {
            vecteur2 = v;
        }
        if (type1 == Type.NULL) {
            type1 = Type.VECTEUR;
        } else {
            type2 = Type.VECTEUR;
        }
    }

    public boolean calculIntersection() {
        double x;
        double y;
        
        switch (type1) {
            case VECTEUR:
                switch (type2) {
                    case VECTEUR:
                        if (parallele_ligne_ligne(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), vecteur2.getAX(), vecteur2.getAY(), vecteur2.getBX(), vecteur2.getBY())) {
                            return false;
                        }
                        x = intersection_ligne_ligne_X(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), vecteur2.getAX(), vecteur2.getAY(), vecteur2.getBX(), vecteur2.getBY());
                        y = intersection_ligne_ligne_Y(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), vecteur2.getAX(), vecteur2.getAY(), vecteur2.getBX(), vecteur2.getBY());
                        if (point_entre(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), x, y) && point_entre(vecteur2.getAX(), vecteur2.getAY(), vecteur2.getBX(), vecteur2.getBY(), x, y)) {
                            x_intersection.add(x);
                            y_intersection.add(y);
                            return true;
                        }
                        return false;
                    case DROITE:
                        if (parallele_ligne_ligne(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY())) {
                            return false;
                        }
                        x = intersection_ligne_ligne_X(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY());
                        y = intersection_ligne_ligne_Y(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY());
                        if (point_entre(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), x, y)) {
                            x_intersection.add(x);
                            y_intersection.add(y);
                            return true;
                        }
                        return false;
                    case DEMIDROITE:
                        if (parallele_ligne_ligne(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY())) {
                            return false;
                        }
                        x = intersection_ligne_ligne_X(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY());
                        y = intersection_ligne_ligne_Y(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY());
                        if (point_entre(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), x, y) && point_on_demi_droite(demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY(), x, y)) {
                            x_intersection.add(x);
                            y_intersection.add(y);
                            return true;
                        }
                        break;
                    case SEGMENT:
                        if (parallele_ligne_ligne(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY())) {
                            return false;
                        }
                        x = intersection_ligne_ligne_X(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY());
                        y = intersection_ligne_ligne_Y(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY());
                        if (point_entre(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), x, y) && point_entre(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), x, y)) {
                            x_intersection.add(x);
                            y_intersection.add(y);
                            return true;
                        }
                        return false;
                    case CERCLE:
                        List<Double> xInter = X_interCercleLigne(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), cercle1.xC(), cercle1.yC(), cercle1.rayon());
                        List<Double> yInter = Y_interCercleLigne(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), cercle1.xC(), cercle1.yC(), cercle1.rayon());
                        for (int i = 0; i < xInter.size(); i++) {
                            if (point_entre(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), xInter.get(i), yInter.get(i))) {
                                x_intersection.add(xInter.get(i));
                                y_intersection.add(yInter.get(i));

                            }
                        }
                        return !x_intersection.isEmpty();

                }
                break;
            case DROITE:
                switch (type2) {
                    case VECTEUR:
                        if (parallele_ligne_ligne(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY())) {
                            return false;
                        }
                        x = intersection_ligne_ligne_X(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY());
                        y = intersection_ligne_ligne_Y(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY());
                        if (point_entre(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), x, y)) {
                            x_intersection.add(x);
                            y_intersection.add(y);
                            return true;
                        }
                        return false;

                    case DROITE:
                        if (parallele_ligne_ligne(droite2.getAX(), droite2.getAY(), droite2.getBX(), droite2.getBY(), droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY())) {
                            return false;
                        }
                        x = intersection_ligne_ligne_X(droite2.getAX(), droite2.getAY(), droite2.getBX(), droite2.getBY(), droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY());
                        y = intersection_ligne_ligne_Y(droite2.getAX(), droite2.getAY(), droite2.getBX(), droite2.getBY(), droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY());
                        x_intersection.add(x);
                        y_intersection.add(y);
                        return true;

                    case DEMIDROITE:
                        if (parallele_ligne_ligne(demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY(), droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY())) {
                            return false;
                        }
                        x = intersection_ligne_ligne_X(demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY(), droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY());
                        y = intersection_ligne_ligne_Y(demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY(), droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY());
                        if (point_on_demi_droite(demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY(), x, y)) {
                            x_intersection.add(x);
                            y_intersection.add(y);
                            return true;
                        }
                        return false;
                    case SEGMENT:
                        if (parallele_ligne_ligne(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY())) {
                            return false;
                        }
                        x = intersection_ligne_ligne_X(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY());
                        y = intersection_ligne_ligne_Y(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY());
                        if (point_entre(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), x, y)) {
                            x_intersection.add(x);
                            y_intersection.add(y);
                            return true;
                        }
                        return false;
                    case CERCLE:
                        List<Double> xInter = X_interCercleLigne(droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY(), cercle1.xC(), cercle1.yC(), cercle1.rayon());
                        List<Double> yInter = Y_interCercleLigne(droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY(), cercle1.xC(), cercle1.yC(), cercle1.rayon());

                        x_intersection.addAll(xInter);
                        y_intersection.addAll(yInter);
                        return !x_intersection.isEmpty();

                }
                break;
            case DEMIDROITE:
                switch (type2) {
                    case VECTEUR:
                        if (parallele_ligne_ligne(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY())) {
                            return false;
                        }
                        x = intersection_ligne_ligne_X(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY());
                        y = intersection_ligne_ligne_Y(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY());
                        if (point_entre(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), x, y) && point_on_demi_droite(demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY(), x, y)) {
                            x_intersection.add(x);
                            y_intersection.add(y);
                            return true;
                        }
                        return false;
                    case DROITE:
                        if (parallele_ligne_ligne(droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY(), demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY())) {
                            return false;
                        }
                        x = intersection_ligne_ligne_X(droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY(), demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY());
                        y = intersection_ligne_ligne_Y(droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY(), demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY());
                        if (point_on_demi_droite(demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY(), x, y)) {
                            x_intersection.add(x);
                            y_intersection.add(y);
                            return true;
                        }
                        return false;
                    case DEMIDROITE:
                        if (parallele_ligne_ligne(demidroite2.getAX(), demidroite2.getAY(), demidroite2.getBX(), demidroite2.getBY(), demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY())) {
                            return false;
                        }
                        x = intersection_ligne_ligne_X(demidroite2.getAX(), demidroite2.getAY(), demidroite2.getBX(), demidroite2.getBY(), demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY());
                        y = intersection_ligne_ligne_Y(demidroite2.getAX(), demidroite2.getAY(), demidroite2.getBX(), demidroite2.getBY(), demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY());
                        if (point_on_demi_droite(demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY(), x, y) && point_on_demi_droite(demidroite2.getAX(), demidroite2.getAY(), demidroite2.getBX(), demidroite2.getBY(), x, y)) {
                            x_intersection.add(x);
                            y_intersection.add(y);
                            return true;
                        }
                        return false;
                    case SEGMENT:
                        if (parallele_ligne_ligne(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY())) {
                            return false;
                        }
                        x = intersection_ligne_ligne_X(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY());
                        y = intersection_ligne_ligne_Y(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY());
                        if (point_entre(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), x, y) && point_on_demi_droite(demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY(), x, y)) {
                            x_intersection.add(x);
                            y_intersection.add(y);
                            return true;
                        }
                        return false;
                    case CERCLE:
                        List<Double> xInter = X_interCercleLigne(demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY(), cercle1.xC(), cercle1.yC(), cercle1.rayon());
                        List<Double> yInter = Y_interCercleLigne(demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY(), cercle1.xC(), cercle1.yC(), cercle1.rayon());
                        for (int i = 0; i < xInter.size(); i++) {
                            if (point_on_demi_droite(demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY(), xInter.get(i), yInter.get(i))) {
                                x_intersection.add(xInter.get(i));
                                y_intersection.add(yInter.get(i));
                            }


                        }
                        return !x_intersection.isEmpty();
                }
                break;
            case SEGMENT:
                switch (type2) {
                    case VECTEUR:
                        if (parallele_ligne_ligne(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY())) {
                            return false;
                        }
                        x = intersection_ligne_ligne_X(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY());
                        y = intersection_ligne_ligne_Y(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY());
                        if (point_entre(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), x, y) && point_entre(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), x, y)) {
                            x_intersection.add(x);
                            y_intersection.add(y);
                            return true;
                        }
                        return false;
                    case DROITE:
                        if (parallele_ligne_ligne(droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY(), segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY())) {
                            return false;
                        }
                        x = intersection_ligne_ligne_X(droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY(), segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY());
                        y = intersection_ligne_ligne_Y(droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY(), segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY());
                        if (point_entre(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), x, y)) {
                            x_intersection.add(x);
                            y_intersection.add(y);
                            return true;
                        }
                        return false;
                    case DEMIDROITE:
                        if (parallele_ligne_ligne(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY())) {
                            return false;
                        }
                        x = intersection_ligne_ligne_X(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY());
                        y = intersection_ligne_ligne_Y(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY());
                        if (point_entre(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), x, y) && point_on_demi_droite(demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY(), x, y)) {
                            x_intersection.add(x);
                            y_intersection.add(y);
                            return true;
                        }
                        return false;
                    case SEGMENT:
                        if (parallele_ligne_ligne(segment2.getAX(), segment2.getAY(), segment2.getBX(), segment2.getBY(), segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY())) {
                            return false;
                        }
                        x = intersection_ligne_ligne_X(segment2.getAX(), segment2.getAY(), segment2.getBX(), segment2.getBY(), segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY());
                        y = intersection_ligne_ligne_Y(segment2.getAX(), segment2.getAY(), segment2.getBX(), segment2.getBY(), segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY());
                        if (point_entre(segment2.getAX(), segment2.getAY(), segment2.getBX(), segment2.getBY(), x, y) && point_entre(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), x, y)) {
                            x_intersection.add(x);
                            y_intersection.add(y);
                            return true;
                        }
                        return false;
                    case CERCLE:
                        List<Double> xInter = X_interCercleLigne(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), cercle1.xC(), cercle1.yC(), cercle1.rayon());
                        List<Double> yInter = Y_interCercleLigne(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), cercle1.xC(), cercle1.yC(), cercle1.rayon());
                        for (int i = 0; i < xInter.size(); i++) {
                            if (point_entre(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), xInter.get(i), yInter.get(i))) {
                                x_intersection.add(xInter.get(i));
                                y_intersection.add(yInter.get(i));

                            }
                        }
                        return !x_intersection.isEmpty();

                }
                break;
            case CERCLE:
                List<Double> xInter;
                List<Double> yInter;
                switch (type2) {
                    case VECTEUR:
                        xInter = X_interCercleLigne(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), cercle1.xC(), cercle1.yC(), cercle1.rayon());
                        yInter = Y_interCercleLigne(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), cercle1.xC(), cercle1.yC(), cercle1.rayon());
                        for (int i = 0; i < xInter.size(); i++) {
                            if (point_entre(vecteur1.getAX(), vecteur1.getAY(), vecteur1.getBX(), vecteur1.getBY(), xInter.get(i), yInter.get(i))) {
                                x_intersection.add(xInter.get(i));
                                y_intersection.add(yInter.get(i));

                            }
                        }
                        return !x_intersection.isEmpty();
                    case DROITE:
                        xInter = X_interCercleLigne(droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY(), cercle1.xC(), cercle1.yC(), cercle1.rayon());
                        yInter = Y_interCercleLigne(droite1.getAX(), droite1.getAY(), droite1.getBX(), droite1.getBY(), cercle1.xC(), cercle1.yC(), cercle1.rayon());
                        for (int i = 0; i < xInter.size(); i++) {

                            x_intersection.add(xInter.get(i));
                            y_intersection.add(yInter.get(i));


                        }
                        return !x_intersection.isEmpty();

                    case DEMIDROITE:
                        xInter = X_interCercleLigne(demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY(), cercle1.xC(), cercle1.yC(), cercle1.rayon());
                        yInter = Y_interCercleLigne(demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY(), cercle1.xC(), cercle1.yC(), cercle1.rayon());
                        for (int i = 0; i < xInter.size(); i++) {
                            if (point_on_demi_droite(demidroite1.getAX(), demidroite1.getAY(), demidroite1.getBX(), demidroite1.getBY(), xInter.get(i), yInter.get(i))) {
                                x_intersection.add(xInter.get(i));
                                y_intersection.add(yInter.get(i));
                            }


                        }
                        return !x_intersection.isEmpty();
                    case SEGMENT:
                        xInter = X_interCercleLigne(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), cercle1.xC(), cercle1.yC(), cercle1.rayon());
                        yInter = Y_interCercleLigne(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), cercle1.xC(), cercle1.yC(), cercle1.rayon());
                        for (int i = 0; i < xInter.size(); i++) {
                            if (point_entre(segment1.getAX(), segment1.getAY(), segment1.getBX(), segment1.getBY(), xInter.get(i), yInter.get(i))) {
                                x_intersection.add(xInter.get(i));
                                y_intersection.add(yInter.get(i));

                            }
                        }
                        return !x_intersection.isEmpty();
                    case CERCLE:
                        x_intersection = X_interCercleCercle(cercle1.xC(), cercle1.yC(), cercle1.rayon(), cercle2.xC(), cercle2.yC(), cercle2.rayon());
                        y_intersection = Y_interCercleCercle(cercle1.xC(), cercle1.yC(), cercle1.rayon(), cercle2.xC(), cercle2.yC(), cercle2.rayon());
                        return !x_intersection.isEmpty();
                }
                break;
        }
        return false;
    }

    private static double intersection_ligne_ligne_X(double xa1, double ya1, double xb1, double yb1, double xa2, double ya2, double xb2, double yb2) {//On suppose que les droites ne sont pas parrallèles

        if (xa1 == xb1) {
            return xa1;
        }
        if (xa2 == xb2) {
            return xa2;
        }
        double a1 = (yb1 - ya1) / (xb1 - xa1);
        double b1 = ya1 - a1 * xa1;
        double a2 = (yb2 - ya2) / (xb2 - xa2);
        double b2 = ya2 - a2 * xa2;
        return (b2 - b1) / (a1 - a2);

    }

    private static double intersection_ligne_ligne_Y(double xa1, double ya1, double xb1, double yb1, double xa2, double ya2, double xb2, double yb2) {//On suppose que les droites ne sont pas parrallèles
        if (xa1 == xb1) {
            double m = (ya2 - yb2) / (xa2 - xb2);
            double p = yb2 - m * xa2;
            return m * xa1 + p;
        }
        if (xa2 == xb2) {
            double m = (ya1 - yb1) / (xa1 - xb1);
            double p = yb1 - m * xa1;
            return m * xa2 + p;
        }
        double x = intersection_ligne_ligne_X(xa1, ya1, xb1, yb1, xa2, ya2, xb2, yb2);
        double m = (ya1 - yb1) / (xa1 - xb1);
        double p = ya1 - m * xa1;
        return m * x + p;

    }

    private static boolean parallele_ligne_ligne(double xa1, double ya1, double xb1, double yb1, double xa2, double ya2, double xb2, double yb2) {//On suppose que les droites ne sont pas parrallèles
        double x1 = xb1 - xa1;
        double y1 = yb1 - ya1;
        double x2 = xb2 - xa2;
        double y2 = yb2 - ya2;
        return x1 * y2 - x2 * y1 == 0;

    }

    private static boolean point_on_demi_droite(double xA, double yA, double xB, double yB, double x, double y) {
        if (xA == xB) {
            return (yA < yB) ? (yA < y) : (yA > y);
        }
        return (xA < xB) ? (xA < x) : (xA > x);
    }

    private static boolean point_entre(double xA, double yA, double xB, double yB, double x, double y) {
        if ((xA < xB) && (yA < yB)) {
            return (xA < x) && (x < xB) && (yA < y) && (y < yB);
        }
        if ((xA < xB) && (yA > yB)) {
            return (xA < x) && (x < xB) && (yA > y) && (y > yB);
        }
        if ((xA > xB) && (yA < yB)) {
            return (xA > x) && (x > xB) && (yA < y) && (y < yB);
        }
        return (xA > x) && (x > xB) && (yA > y) && (y > yB);
    }

    private static boolean coupeCercleLigne(double xA, double yA, double xB, double yB, double xC, double yC, double r) {
        double xP = 0;
        double yP = 0;
        if (xA == xB) {
            xP = xA;
            yP = yC;
        } else if (yA == yB) {
            xP = xC;
            yP = yA;
        } else {
            double xv = xA - xB;
            double yv = yA - yB;
            double n = Math.sqrt(xv * xv + yv * yv);
            xv /= n;
            yv /= n;
            double BH = (xC - xB) * xv + (yC - yB) * yv;
            xP = xB + BH * xv;
            yP = yB + BH * yv;

        }
        double dist = Math.sqrt((xC - xP) * (xC - xP) + (yC - yP) * (yC - yP));
        return dist <= r;

    }

    private static List<Double> X_interCercleLigne(double xA, double yA, double xB, double yB, double xC, double yC, double r) {
        List<Double> ret = new ArrayList<>();

        if (xA == xB) {
            if (coupeCercleLigne(xA, yA, xB, yB, xC, yC, r)) {
                ret.add(xA);
                ret.add(xA);
            }
            return ret;
        }
        double m = (yA - yB) / (xA - xB);
        double p = yA - m * xA;
        double A = 1 + m * m;
        double B = 2 * (m * (p - yC) - xC);
        double C = xC * xC + (p - yC) * (p - yC) - r * r;
        double delta = B * B - 4 * A * C;
        if (delta >= 0) {
            double x1 = (-B - Math.sqrt(delta)) / (2 * A);
            double x2 = (-B + Math.sqrt(delta)) / (2 * A);

            ret.add(x1);
            ret.add(x2);
        }
        return ret;
    }

    private static List<Double> Y_interCercleLigne(double xA, double yA, double xB, double yB, double xC, double yC, double r) {
        List<Double> ret = new ArrayList<>();
        List<Double> X = X_interCercleLigne(xA, yA, xB, yB, xC, yC, r);
        if (xA == xB && !X.isEmpty()) {
            double x = X.get(0);
            double y1 = yC + Math.sqrt(r * r - (x - xC) * (x - xC));
            double y2 = yC - Math.sqrt(r * r - (x - xC) * (x - xC));
            ret.add(y1);
            ret.add(y2);
        } else {
            X.stream().map((x) -> {
                double m = (yB - yA) / (xB - xA);
                double p = yB - m * xB;
                double y = m * x + p;
                return y;
            }).forEachOrdered((y) -> {
                ret.add(y);
            });
        }
        return ret;
    }

    private static List<Double> X_interCercleCercle(double xC, double yC, double rayon, double xC0, double yC0, double rayon0) {
        List<Double> ret = new ArrayList<>();
        double a = 2 * (xC0 - xC);
        double b = 2 * (yC0 - yC);
        double c = (xC0 - xC) * (xC0 - xC) + (yC0 - yC) * (yC0 - yC) + rayon * rayon - rayon0 * rayon0;
        double delta = 4 * a * a * c * c - 4 * (a * a + b * b) * (c * c - b * b * rayon * rayon);

        if (b == 0) {
            double xP = xC + (a * c) / (a * a + b * b);
            double yP1 = yC + b / 2 + Math.sqrt(rayon * rayon - ((2 * c - a * a) / (2 * a)) * ((2 * c - a * a) / (2 * a)));
            double yP2 = yC + b / 2 - Math.sqrt(rayon * rayon - ((2 * c - a * a) / (2 * a)) * ((2 * c - a * a) / (2 * a)));
            ret.add(xP);
            ret.add(xP);
        } else if (delta >= 0) {
            double xP1 = xC + (2 * (a * c) - Math.sqrt(delta)) / (2 * (a * a + b * b));
            double xP2 = xC + (2 * (a * c) + Math.sqrt(delta)) / (2 * (a * a + b * b));
            double yP1 = yC + (c - a * (xP1 - xC)) / b;
            double yP2 = yC + (c - a * (xP2 - xC)) / b;
            ret.add(xP1);
            ret.add(xP2);
        }
        return ret;
    }

    private static List<Double> Y_interCercleCercle(double xC, double yC, double rayon, double xC0, double yC0, double rayon0) {

        List<Double> ret = new ArrayList<>();
        double a = 2 * (xC0 - xC);
        double b = 2 * (yC0 - yC);
        double c = (xC0 - xC) * (xC0 - xC) + (yC0 - yC) * (yC0 - yC) + rayon * rayon - rayon0 * rayon0;
        double delta = 4 * a * a * c * c - 4 * (a * a + b * b) * (c * c - b * b * rayon * rayon);

        if (b == 0) {
            double xP = xC + (a * c) / (a * a + b * b);
            double yP1 = yC + b / 2 + Math.sqrt(rayon * rayon - ((2 * c - a * a) / (2 * a)) * ((2 * c - a * a) / (2 * a)));
            double yP2 = yC + b / 2 - Math.sqrt(rayon * rayon - ((2 * c - a * a) / (2 * a)) * ((2 * c - a * a) / (2 * a)));
            ret.add(yP1);
            ret.add(yP2);
        } else if (delta >= 0) {
            double xP1 = xC + (2 * (a * c) - Math.sqrt(delta)) / (2 * (a * a + b * b));
            double xP2 = xC + (2 * (a * c) + Math.sqrt(delta)) / (2 * (a * a + b * b));
            double yP1 = yC + (c - a * (xP1 - xC)) / b;
            double yP2 = yC + (c - a * (xP2 - xC)) / b;
            ret.add(yP1);
            ret.add(yP2);
        }
        return ret;
    }

    public Double getX_intersection(double x, double y) {
        if (x_intersection.isEmpty()) {
            return null;
        }

        Double ret = x_intersection.get(0);
        Double dist = Double.MAX_VALUE;
        for (int i = 0; i < x_intersection.size(); i++) {

            Double xi = x_intersection.get(i);
            Double yi = y_intersection.get(i);
            Double d = Math.sqrt((xi - x) * (xi - x) + (yi - y) * (yi - y));

            if (d < dist) {
                ret = xi;
                dist = d;

            }

        }
        return ret;
    }

    public Double getY_intersection(double x, double y) {
        if (y_intersection.isEmpty()) {
            return null;
        }
        Double ret = y_intersection.get(0);
        Double dist = Double.MAX_VALUE;
        for (int i = 0; i < y_intersection.size(); i++) {

            Double xi = x_intersection.get(i);
            Double yi = y_intersection.get(i);
            Double d = Math.sqrt((xi - x) * (xi - x) + (yi - y) * (yi - y));
            if (d < dist) {
                ret = yi;
                dist = d;

            }

        }
        return ret;
    }
}
