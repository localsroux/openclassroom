/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import controleur.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Stéphane ROUX
 */
public class VueMenu extends JPanel {

    private Controleur controleur;
    private ButtonGroup radio;
    private JRadioButton boutonCree;
    private JRadioButton boutonTransfo;
    private JRadioButton boutonSuppr;
    private JComboBox listCree;
    private JComboBox listTransfo;
    private JButton centrer;
    private JButton save;
    private JButton saveIm;
    private JButton load;
    private JButton newFile;
    private JFormattedTextField paramRotHomot;
    private VueDessin dessin;
    private VuePrincipale vue;
    private JPanel undoRedo;
    private JButton undo;
    private JButton redo;
    private boolean isSaved;

    public VueMenu(Controleur c, VueDessin d, final VuePrincipale v) {
        isSaved = false;
        dessin = d;
        controleur = c;
        vue = v;

        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new GridLayout(2, 6));
        boutonCree = new JRadioButton("figures");
        boutonTransfo = new JRadioButton("transformation");
        boutonSuppr = new JRadioButton("suppression/déplacement");
        centrer = new JButton("centrer");
        save = new JButton("sauvegarder");
        saveIm = new JButton("sauvegarder en tant qu'image");
        load = new JButton("ouvrir");
        newFile = new JButton("nouveau");
        listCree = new JComboBox();
        listCree.addItem("Point");
        listCree.addItem("Droite");
        listCree.addItem("Demi-Droite");
        listCree.addItem("Segment");
        listCree.addItem("Cercle");
        listCree.addItem("Vecteur");
        listTransfo = new JComboBox();
        listTransfo.addItem("Symétrie centrale");
        listTransfo.addItem("Symétrie axiale");
        listTransfo.addItem("Translation");
        listTransfo.addItem("Rotation");
        listTransfo.addItem("Homothétie");
        listTransfo.addItem("Somme de deux vecteurs");
        listTransfo.addItem("Droite parallèle");
        listTransfo.addItem("Droite perpendiculaire");
        listTransfo.addItem("Milieu");
        listTransfo.addItem("Médiatrice");
        listTransfo.addItem("Bissectrice");
        radio = new ButtonGroup();
        radio.add(boutonCree);
        radio.add(boutonTransfo);
        radio.add(boutonSuppr);
        paramRotHomot = new JFormattedTextField(DecimalFormat.getNumberInstance());
        this.add(boutonCree);
        this.add(boutonTransfo);
        this.add(boutonSuppr);
        this.add(centrer);
        this.add(save);
        this.add(saveIm);
        this.add(listCree);
        this.add(listTransfo);
        this.add(paramRotHomot);

        this.add(load);
        this.add(newFile);
        paramRotHomot.setText("0");
        paramRotHomot.setFocusLostBehavior(JFormattedTextField.PERSIST);
        boutonCree.setSelected(true);
        undoRedo = new JPanel();
        undo = new JButton("Annuler");
        redo = new JButton("Refaire");
        undoRedo.setLayout(new GridLayout(1, 2));
        undoRedo.add(undo);
        undoRedo.add(redo);
        this.add(undoRedo);
        undo.setEnabled(false);
        redo.setEnabled(false);
        this.paramRotHomot.setEnabled(false);
        this.setVisible(true);
        boutonCree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paramRotHomot.setEnabled(false);
                String item = listCree.getSelectedItem().toString();
                if (item.equals("Point")) {
                    controleur.changeBouton(SituationBouton.FIGURES_POINT);
                } else if (item.equals("Droite")) {
                    controleur.changeBouton(SituationBouton.FIGURES_DROITE);
                } else if (item.equals("Segment")) {
                    controleur.changeBouton(SituationBouton.FIGURES_SEGMENT);
                } else if (item.equals("Vecteur")) {
                    controleur.changeBouton(SituationBouton.FIGURES_VECTEUR);
                } else if (item.equals("Cercle")) {
                    controleur.changeBouton(SituationBouton.FIGURES_CERCLE);
                } else if (item.equals("Demi-Droite")) {
                    controleur.changeBouton(SituationBouton.FIGURES_DEMI_DROITE);
                }
            }
        });

        listCree.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boutonCree.setSelected(true);
                paramRotHomot.setEnabled(false);
                String item = listCree.getSelectedItem().toString();
                if (item.equals("Point")) {
                    controleur.changeBouton(SituationBouton.FIGURES_POINT);
                } else if (item.equals("Droite")) {
                    controleur.changeBouton(SituationBouton.FIGURES_DROITE);
                } else if (item.equals("Segment")) {
                    controleur.changeBouton(SituationBouton.FIGURES_SEGMENT);
                } else if (item.equals("Vecteur")) {
                    controleur.changeBouton(SituationBouton.FIGURES_VECTEUR);
                } else if (item.equals("Cercle")) {
                    controleur.changeBouton(SituationBouton.FIGURES_CERCLE);
                } else if (item.equals("Demi-Droite")) {
                    controleur.changeBouton(SituationBouton.FIGURES_DEMI_DROITE);
                }
            }
        });
        listCree.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                boutonCree.setSelected(true);
                paramRotHomot.setEnabled(false);
                String item = listCree.getSelectedItem().toString();
                if (item.equals("Point")) {
                    controleur.changeBouton(SituationBouton.FIGURES_POINT);
                } else if (item.equals("Droite")) {
                    controleur.changeBouton(SituationBouton.FIGURES_DROITE);
                } else if (item.equals("Segment")) {
                    controleur.changeBouton(SituationBouton.FIGURES_SEGMENT);
                } else if (item.equals("Vecteur")) {
                    controleur.changeBouton(SituationBouton.FIGURES_VECTEUR);
                } else if (item.equals("Cercle")) {
                    controleur.changeBouton(SituationBouton.FIGURES_CERCLE);
                } else if (item.equals("Demi-Droite")) {
                    controleur.changeBouton(SituationBouton.FIGURES_DEMI_DROITE);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                boutonCree.setSelected(true);
                paramRotHomot.setEnabled(false);
                String item = listCree.getSelectedItem().toString();
                if (item.equals("Point")) {
                    controleur.changeBouton(SituationBouton.FIGURES_POINT);
                } else if (item.equals("Droite")) {
                    controleur.changeBouton(SituationBouton.FIGURES_DROITE);
                } else if (item.equals("Segment")) {
                    controleur.changeBouton(SituationBouton.FIGURES_SEGMENT);
                } else if (item.equals("Vecteur")) {
                    controleur.changeBouton(SituationBouton.FIGURES_VECTEUR);
                } else if (item.equals("Cercle")) {
                    controleur.changeBouton(SituationBouton.FIGURES_CERCLE);
                } else if (item.equals("Demi-Droite")) {
                    controleur.changeBouton(SituationBouton.FIGURES_DEMI_DROITE);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        boutonTransfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String item = listTransfo.getSelectedItem().toString();
                paramRotHomot.setEnabled(false);
                if (item.equals("Symétrie centrale")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_SYM_CEN_);
                } else if (item.equals("Symétrie axiale")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_SYM_AX);
                } else if (item.equals("Translation")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_TRANS);
                } else if (item.equals("Rotation")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_ROT);
                    paramRotHomot.setEnabled(true);
                } else if (item.equals("Homothétie")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_HOM);
                    paramRotHomot.setEnabled(true);
                } else if (item.equals("Somme de deux vecteurs")) {
                    controleur.changeBouton(SituationBouton.SOMME_VEC);
                } else if (item.equals("Droite parallèle")) {
                    controleur.changeBouton(SituationBouton.DROITE_PAR);
                } else if (item.equals("Droite perpendiculaire")) {
                    controleur.changeBouton(SituationBouton.DROITE_PERP);
                } else if (item.equals("Milieu")) {
                    controleur.changeBouton(SituationBouton.MILIEU);
                } else if (item.equals("Médiatrice")) {
                    controleur.changeBouton(SituationBouton.MEDIATRICE);
                } else if (item.equals("Bissectrice")) {
                    controleur.changeBouton(SituationBouton.BISSECTRICE);
                }
            }
        });

        listTransfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boutonTransfo.setSelected(true);
                paramRotHomot.setEnabled(false);
                String item = listTransfo.getSelectedItem().toString();
                if (item.equals("Symétrie centrale")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_SYM_CEN_);
                } else if (item.equals("Symétrie axiale")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_SYM_AX);
                } else if (item.equals("Translation")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_TRANS);
                } else if (item.equals("Rotation")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_ROT);
                    paramRotHomot.setEnabled(true);
                } else if (item.equals("Homothétie")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_HOM);
                    paramRotHomot.setEnabled(true);
                } else if (item.equals("Somme de deux vecteurs")) {
                    controleur.changeBouton(SituationBouton.SOMME_VEC);
                } else if (item.equals("Droite parallèle")) {
                    controleur.changeBouton(SituationBouton.DROITE_PAR);
                } else if (item.equals("Droite perpendiculaire")) {
                    controleur.changeBouton(SituationBouton.DROITE_PERP);
                } else if (item.equals("Milieu")) {
                    controleur.changeBouton(SituationBouton.MILIEU);
                } else if (item.equals("Médiatrice")) {
                    controleur.changeBouton(SituationBouton.MEDIATRICE);
                } else if (item.equals("Bissectrice")) {
                    controleur.changeBouton(SituationBouton.BISSECTRICE);
                }
            }
        });

        listTransfo.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                boutonTransfo.setSelected(true);
                paramRotHomot.setEnabled(false);
                String item = listTransfo.getSelectedItem().toString();
                if (item.equals("Symétrie centrale")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_SYM_CEN_);
                } else if (item.equals("Symétrie axiale")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_SYM_AX);
                } else if (item.equals("Translation")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_TRANS);
                } else if (item.equals("Rotation")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_ROT);
                    paramRotHomot.setEnabled(true);
                } else if (item.equals("Homothétie")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_HOM);
                    paramRotHomot.setEnabled(true);
                } else if (item.equals("Somme de deux vecteurs")) {
                    controleur.changeBouton(SituationBouton.SOMME_VEC);
                } else if (item.equals("Droite parallèle")) {
                    controleur.changeBouton(SituationBouton.DROITE_PAR);
                } else if (item.equals("Droite perpendiculaire")) {
                    controleur.changeBouton(SituationBouton.DROITE_PERP);
                } else if (item.equals("Milieu")) {
                    controleur.changeBouton(SituationBouton.MILIEU);
                } else if (item.equals("Médiatrice")) {
                    controleur.changeBouton(SituationBouton.MEDIATRICE);
                } else if (item.equals("Bissectrice")) {
                    controleur.changeBouton(SituationBouton.BISSECTRICE);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                boutonTransfo.setSelected(true);
                paramRotHomot.setEnabled(false);
                String item = listTransfo.getSelectedItem().toString();
                if (item.equals("Symétrie centrale")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_SYM_CEN_);
                } else if (item.equals("Symétrie axiale")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_SYM_AX);
                } else if (item.equals("Translation")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_TRANS);
                } else if (item.equals("Rotation")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_ROT);
                    paramRotHomot.setEnabled(true);
                } else if (item.equals("Homothétie")) {
                    controleur.changeBouton(SituationBouton.TRANSFO_HOM);
                    paramRotHomot.setEnabled(true);
                } else if (item.equals("Somme de deux vecteurs")) {
                    controleur.changeBouton(SituationBouton.SOMME_VEC);
                } else if (item.equals("Droite parallèle")) {
                    controleur.changeBouton(SituationBouton.DROITE_PAR);
                } else if (item.equals("Droite perpendiculaire")) {
                    controleur.changeBouton(SituationBouton.DROITE_PERP);
                } else if (item.equals("Milieu")) {
                    controleur.changeBouton(SituationBouton.MILIEU);
                } else if (item.equals("Médiatrice")) {
                    controleur.changeBouton(SituationBouton.MEDIATRICE);
                } else if (item.equals("Bissectrice")) {
                    controleur.changeBouton(SituationBouton.BISSECTRICE);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        boutonSuppr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controleur.changeBouton(SituationBouton.SUPPR_DEP);
                paramRotHomot.setEnabled(false);
            }
        });
        centrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dessin.centrer();
                dessin.repaint();
            }
        });
        centrer.setMnemonic(KeyEvent.VK_C);
        AbstractAction actionSave;
        actionSave = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (controleur.getCurrentPath().equals("")) {
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("geom", "ge");

                        JFileChooser jf = new JFileChooser(".");
                        jf.addChoosableFileFilter(filter);
                        jf.setFileFilter(filter);
                        if (jf.showSaveDialog(null)
                                == JFileChooser.APPROVE_OPTION) {
                            File fichier = jf.getSelectedFile();
                            String path = fichier.getAbsolutePath();
                            if (path.length() < 3 || !path.substring(path.length() - 3, path.length()).equals(".ge")) {
                                path += ".ge";
                            }
                            controleur.saveModele(path);
                            vue.setTitle("MyCabri - " + path);
                            isSaved = true;
                        }
                    } else {

                        vue.enableWaiting();
                        controleur.saveModele(controleur.getCurrentPath());
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(VueMenu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        isSaved = true;
                        vue.disableWaiting();
                    }

                } catch (IOException ex) {
                    new FenErreur((JFrame) load.getParent().getParent().getParent().getParent().getParent(), "Erreur fichier", true);
                }
            }
        };
        save.addActionListener(actionSave);
        save.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK), "sauver");
        save.getActionMap().put("sauver", actionSave);
        save.setToolTipText(" ");
        AbstractAction asi = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    FileNameExtensionFilter filter = new FileNameExtensionFilter("image", "png");

                    JFileChooser jf = new JFileChooser(".");
                    jf.addChoosableFileFilter(filter);
                    jf.setFileFilter(filter);
                    if (jf.showDialog(null, "Sauvegarder en tant qu'image")
                            == JFileChooser.APPROVE_OPTION) {
                        File fichier = jf.getSelectedFile();
                        String path = fichier.getAbsolutePath();
                        if (path.length() < 4 || !path.substring(path.length() - 4, path.length()).equals(".png")) {
                            path += ".png";
                        }
                        dessin.saveAsImage(path);
                        isSaved = true;
                    }
                } catch (IOException ex) {
                    new FenErreur((JFrame) save.getParent().getParent().getParent().getParent().getParent(), "Erreur fichier", true);
                }
            }
        };
        saveIm.addActionListener(asi);
        saveIm.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK), "image");
        saveIm.getActionMap().put("image", asi);
        saveIm.setToolTipText(" ");
        AbstractAction actionOuvrir = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controleur.isModelModified()) {
                    DialogSaveOuvrir dsn = new DialogSaveOuvrir(v);
                } else {
                    ouvrir();
                }
            }
        };
        load.addActionListener(actionOuvrir);
        load.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK), "ouvrir");
        load.getActionMap().put("ouvrir", actionOuvrir);
        load.setToolTipText(" ");
        AbstractAction actionNouveau = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controleur.isModelModified()) {
                    DialogSaveNouveau d = new DialogSaveNouveau(v);
                    d.setFocusable(true);
                    d.requestFocus();
                } else {
                    nouveau();
                }

            }
        };
        newFile.addActionListener(actionNouveau);
        newFile.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK), "nouveau");
        newFile.getActionMap().put("nouveau", actionNouveau);
        newFile.setToolTipText(" ");
        AbstractAction actionUndo = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controleur.cliqueUndo();

            }
        };
        undo.addActionListener(actionUndo);
        undo.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK), "annuler");
        undo.getActionMap().put("annuler", actionUndo);
        undo.setToolTipText(" ");
        AbstractAction actionRedo = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controleur.cliqueRedo();

            }
        };
        redo.addActionListener(actionRedo);
        redo.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK), "refaire");
        redo.getActionMap().put("refaire", actionRedo);
        redo.setToolTipText(" ");
    }

    public double getAngle() {
        return Double.parseDouble(paramRotHomot.getText()) * Math.PI / 180;
    }

    public double getRapport() {
        return Double.parseDouble(paramRotHomot.getText());
    }

    public void setDefault() {
        boutonSuppr.setSelected(true);
    }

    public void update() {
        undo.setEnabled(controleur.undoPossible());
        redo.setEnabled(controleur.redoPossible());
    }

    public boolean save() {
        save.doClick();
        return isSaved;
    }

    public void nouveau() {
        controleur.newModele();
        vue.setTitle("MyCabri - nouveau");
    }

    public void ouvrir() {

        try {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("geom", "ge");

            JFileChooser jf = new JFileChooser(".");
            jf.addChoosableFileFilter(filter);
            jf.setFileFilter(filter);
            if (jf.showOpenDialog(null)
                    == JFileChooser.APPROVE_OPTION) {
                File fichier = jf.getSelectedFile();

                controleur.loadModele(fichier.getPath());
                vue.setTitle("MyCabri - " + fichier.getAbsolutePath());
            }
        } catch (Exception ex) {
            JDialog err = new FenErreur((JFrame) load.getParent().getParent().getParent().getParent().getParent(), "Erreur fichier", true);

        }
    }
}

class FenErreur extends JDialog implements ActionListener {

    public FenErreur(JFrame j, String m, boolean modal) {
        super(j, m, modal);
        this.setSize(300, 100);

        this.setLocationRelativeTo(j);
        JButton ok = new JButton("OK");
        this.add(ok);
        AbstractAction a = new MyAction(this);
        ok.addActionListener(a);
        ok.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("ENTER"), "enter");
        ok.getActionMap().put("enter", a);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
    }
}

class MyAction extends AbstractAction {

    private ActionListener cible;

    public MyAction(ActionListener c) {
        cible = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cible.actionPerformed(e);
    }
}

class DialogSaveNouveau extends JDialog implements ActionListener, KeyListener {

    private VuePrincipale fen;
    private JButton oui;
    private JButton non;
    private JButton annuler;
    private JPanel boutons;
    private JLabel question;

    public DialogSaveNouveau(VuePrincipale f) {
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

        oui.addActionListener(this);
        non.addActionListener(this);
        annuler.addActionListener(this);

        this.addKeyListener(this);
        this.setModal(true);
        this.setFocusable(true);
        this.requestFocus();

        this.setLocationRelativeTo(f);
        this.setSize(300, 100);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() != annuler) {
            if ((e.getSource() == oui && fen.save()) || e.getSource() == non) {

                fen.nouveau();
            }

        }
        this.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (fen.save()) {

                fen.nouveau();
            }

            this.dispose();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            this.dispose();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (fen.save()) {
                fen.nouveau();
            }

            this.dispose();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            this.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

class DialogSaveOuvrir extends JDialog implements ActionListener, KeyListener {

    private VuePrincipale fen;
    private JButton oui;
    private JButton non;
    private JButton annuler;
    private JPanel boutons;
    private JLabel question;

    public DialogSaveOuvrir(VuePrincipale f) {
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
        this.addKeyListener(this);
        this.setModal(true);
        this.setFocusable(true);
        this.requestFocus();

        oui.addActionListener(this);
        non.addActionListener(this);
        annuler.addActionListener(this);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() != annuler) {
            if (e.getSource() == non || (e.getSource() == oui && fen.save())) {

                fen.ouvrir();
            }

        }
        this.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (fen.save()) {

                fen.ouvrir();
            }

            this.dispose();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            this.dispose();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (fen.save()) {
                fen.ouvrir();
            }

            this.dispose();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            this.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
