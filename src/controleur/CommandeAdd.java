/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.util.List;
import model.*;

/**
 *
 * @author St ROUX
 */
public class CommandeAdd extends Commande {

    private List<ElementGeometrique> toAdd;

    public CommandeAdd(List<ElementGeometrique> a) {
        toAdd = a;
    }

    @Override
    public void execute(Modele m) {
        for (ElementGeometrique e : toAdd) {
            m.add(e);
        }
    }

    @Override
    public void undo(Modele m) {
        for (ElementGeometrique e : toAdd) {
            m.remove(e.getID());
        }
    }
}
