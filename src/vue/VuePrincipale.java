/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import javax.swing.*;
import java.awt.*;
import model.*;
import controleur.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Stéphane ROUX
 */
public class VuePrincipale extends JFrame implements WindowListener {

    private Controleur controleur;
    private VueMenu menu;
    private VueDessin dessin;
    private VueInfo info;
    private Map<Component, Cursor> cursorTemp;

    public VuePrincipale(Controleur c) {

        super("My Cabri - nouveau");
        controleur = c;
        cursorTemp = new HashMap<Component, Cursor>();
        this.pack();
        this.setMinimumSize(new Dimension(100, 700));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        dessin = new VueDessin(c);
        info = new VueInfo(c);
        menu = new VueMenu(c, dessin, this);

        this.add(menu, BorderLayout.NORTH);
        this.add(dessin, BorderLayout.CENTER);
        this.add(info, BorderLayout.SOUTH);
        this.addWindowListener(this);
        this.setIconImage(new ImageIcon("icon.png").getImage());
        this.setVisible(true);
        this.setResizable(true);
        dessin.setFocusable(true);
        dessin.requestFocus();

    }

    public void update(Modele m) {
        dessin.update(m);
        info.update();
        menu.update();
    }

    public double getAngle() {
        return menu.getAngle();
    }

    public double getRapport() {
        return menu.getRapport();
    }

    public void setDefault() {
        menu.setDefault();
    }

    @Override
    protected void processWindowEvent(WindowEvent we) {
        we.setSource(null);
        if (WindowEvent.WINDOW_CLOSING == we.getID()) {
            if (controleur.isModelModified()) {
                DialogSave d = new DialogSave(this);
                d.setVisible(true);

            } else {
                this.dispose();
            }
        }

    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        e.setSource(null);

        if (controleur.isModelModified()) {
            DialogSave d = new DialogSave(this);
            d.setVisible(true);

        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    public boolean save() {
        return menu.save();
    }

    public void nouveau() {
        menu.nouveau();
    }

    public void ouvrir() {
        menu.ouvrir();
    }

    public void enableWaiting() {

        cursorTemp.put(menu, menu.getCursor());
        menu.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        cursorTemp.put(dessin, dessin.getCursor());
        dessin.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        cursorTemp.put(info, info.getCursor());
        info.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

    }

    public void disableWaiting() {
        for (Component comp : cursorTemp.keySet()) {

            comp.setCursor(cursorTemp.get(comp));
        }
        cursorTemp.clear();
    }

}

class DialogSave extends JDialog implements ActionListener, KeyListener {

    private VuePrincipale fen;
    private JButton oui;
    private JButton non;
    private JButton annuler;
    private JPanel boutons;
    private JLabel question;

    public DialogSave(VuePrincipale f) {
        super(f, true);
        fen = f;
        question = new JLabel("Voulez-vous sauvegarder?");
        boutons = new JPanel();
        boutons.setLayout(new GridLayout(1, 3));
        oui = new JButton("oui");
        non = new JButton("non");
        annuler = new JButton("annuler");
        boutons.add(oui);
        boutons.add(non);
        boutons.add(annuler);

        this.setLayout(new GridLayout(2, 1));
        this.add(question);
        this.add(boutons);
        this.setLocationRelativeTo(f);
        this.setSize(300, 100);
        this.setFocusable(true);
        this.requestFocus();

        oui.addActionListener(this);
        non.addActionListener(this);
        annuler.addActionListener(this);
        this.addKeyListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() != annuler) {
            if ((e.getSource() == oui && fen.save()) || e.getSource() == non) {

                fen.dispose();
            }

        }
        this.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            this.dispose();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            this.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
