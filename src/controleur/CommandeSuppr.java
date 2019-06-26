/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.util.List;
import model.ElementGeometrique;
import model.Modele;

/**
 *
 * @author St ROUX
 */
public class CommandeSuppr extends Commande {

    private final List<ElementGeometrique> remove;

    public CommandeSuppr(List<ElementGeometrique> remove) {
        this.remove = remove;
    }

    @Override
    public void execute(Modele m) {
        remove.forEach((e) -> {
            m.remove(e.getID());
        });
    }

    @Override
    public void undo(Modele m) {
        remove.forEach((e) -> {
            m.add(e);
        });
    }
}
