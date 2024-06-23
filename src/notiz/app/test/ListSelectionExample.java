/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package notiz.app.test;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 *
 * @author Moritz
 */

public class ListSelectionExample extends JFrame {

    public ListSelectionExample() {
        // Erstellen Sie einige Beispiel-Daten
        String[] listData = {"Eintrag 1", "Eintrag 2", "Eintrag 3", "Eintrag 4"};

        // Erstellen Sie die JList und fügen Sie die Daten hinzu
        JList<String> list = new JList<>(listData);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Fügen Sie der Liste einen ListSelectionListener hinzu
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Stellen Sie sicher, dass der Benutzer die Auswahl wirklich abgeschlossen hat
                if (!e.getValueIsAdjusting()) {
                    // Holen Sie sich den ausgewählten Wert
                    String selectedValue = list.getSelectedValue();
                    // Führen Sie die gewünschte Aktion aus
                    System.out.println("Ausgewählter Eintrag: " + selectedValue);
                    // Beispielaktion: Ein Dialog anzeigen
                    JOptionPane.showMessageDialog(null, "Sie haben " + selectedValue + " ausgewählt.");
                }
            }
        });

        // Fügen Sie die JList zu einem JScrollPane hinzu
        JScrollPane scrollPane = new JScrollPane(list);

        // Fügen Sie den JScrollPane zum JFrame hinzu
        add(scrollPane, BorderLayout.CENTER);

        // JFrame-Einstellungen
        setTitle("JList Beispiel");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // Starten Sie das Beispiel in der Event-Dispatching-Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListSelectionExample().setVisible(true);
            }
        });
    }
}

