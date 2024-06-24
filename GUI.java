/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package notiz.app.test;

import com.mysql.jdbc.PreparedStatement;
import notiz.app.test.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import notiz.app.test.DatabaseConnection;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import static notiz.app.test.login.valUser;


/**
 *
 * @author Moritz
 */
public class GUI extends javax.swing.JFrame {
    
    public ResultSet result;
    private DatabaseConnection konnektor;
    public DefaultListModel<String> ordnerListe;
    public DefaultListModel<String> notizenListe;
    /**
     * Creates new form GUI
     */
    public GUI() {
        try {
            initComponents();
            ordnerListe = (DefaultListModel<String>) lOrdner.getModel();

            this.konnektor = new DatabaseConnection("localhost", "notizapp", "root", "");
            this.konnektor.open();


        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Fehler! Verbindung konnte nicht aufgebaut werden: " + ex);
        }
        lUsername.setText(login.uebergebeuser().getName());
        initOrdner();
        
    }
    
    ArrayList<Ordner> ordnerArrayListe = new ArrayList();
    ArrayList<Notiz> notizenArrayListe = new ArrayList();

    
    public String valPW(char[] password, char[] confirmPassword){
        String passwordStr = new String(password);
        String confirmPasswordStr = new String(confirmPassword);
        
        if (!passwordStr.equals(confirmPasswordStr)) {
            return "The new passwords do not match";
        }
        
        if (passwordStr.length() < 8) {
            return "The new password must be at least 8 characters long";
        }
        
        boolean hasLetter = false;
        boolean hasDigit = false;
        
        for (char ch : password) {
            if (Character.isLetter(ch)) {
                hasLetter = true;
            }
            if (Character.isDigit(ch)) {
                hasDigit = true;
            }
        }

        if (!hasLetter) {
            return "The new password must contain at least one letter";
        }

        if (!hasDigit) {
            return "The new password must contain at least one number";
        }

        return "The new password is valid";
    }

    
    
    public void initNotizen(String ordnerName){
    try {
        result = this.konnektor.fuehreAbfrageAus("SELECT n.Titel, n.Inhalt FROM notiz n JOIN Ordner o ON n.Ordner_ID = o.Ordner_ID JOIN user u ON o.Benutzername = u.Benutzername WHERE u.Benutzername = '" + login.uebergebeuser().getUsername() + "' AND o.Name = '" + ordnerName + "';");
        while(result.next()) {
            String titel = result.getString("Titel");
            String inhalt = result.getString("Inhalt");
            System.out.println(titel + inhalt);
            Notiz notiz = new Notiz(ordnerName, titel, inhalt);
            notizenArrayListe.add(notiz);
        }
    } catch(SQLException ex){
        JOptionPane.showMessageDialog(rootPane, "Error during database query:" + ex);
    }
}
    
    public void updateNotiz(String notiztitel){
        taNotizInhalt.setText("No note selected");
        notizenListe.clear();
        for(int i = 0; i<notizenArrayListe.size(); i++){
            if(notizenArrayListe.get(i).getTitle().equals(notiztitel)){
                notizenArrayListe.remove(i);
            }
        }
        for(int i = 0; i<notizenArrayListe.size(); i++){
            
            notizenListe.addElement(notizenArrayListe.get(i).getTitle());
        }
    }
    
    public void updateOrdner(String ordnername){
        ordnerListe.clear();
        for(int i = 0; i<ordnerArrayListe.size(); i++){
            if(ordnerArrayListe.get(i).getName().equals(ordnername)){
                ordnerArrayListe.remove(i);
            }
        }
        for(int i = 0; i<ordnerArrayListe.size(); i++){
            
            ordnerListe.addElement(ordnerArrayListe.get(i).getName());
        }
    }
    
    public void initOrdner() {
    try {
        // SQL-Abfrage, um die Ordner aus der Datenbank zu laden
        result = this.konnektor.fuehreAbfrageAus("SELECT `Name` FROM `Ordner` WHERE Benutzername = '"+ login.uebergebeuser().getUsername()+ "';");

        // Das Modell der JList für die Ordner holen

        ordnerListe.clear(); // Das Modell leeren, bevor neue Elemente hinzugefügt werden
        System.out.println(result);
        // Die Ergebnisse der Abfrage durchlaufen
        while (result.next()) {
            // Ordnername aus dem ResultSet holen
            String ordnerName = result.getString("Name");

            // Neuen Ordner erstellen und zur ArrayListe hinzufügen
            Ordner ordner = new Ordner(ordnerName);
            ordnerArrayListe.add(ordner);

            // Den Ordner zur JList hinzufügen
            ordnerListe.addElement(ordner.getName());
            System.out.println("error");
        }
    } catch (SQLException ex) {
        // Fehlermeldung anzeigen, wenn die Abfrage fehlschlägt
        JOptionPane.showMessageDialog(rootPane, "Fehler bei der Datenbankabfrage: " + ex);
    }
}
    
    public int getOrdner_ID(String ordnerName){
        int ordner_ID = 0;
        
        try {
            result = this.konnektor.fuehreAbfrageAus("SELECT `Ordner_ID` FROM `Ordner` WHERE `Name` = '" + ordnerName + "';");
            if(result.next()){
                ordner_ID = Integer.parseInt(result.getString("Ordner_ID"));
            }
        }catch (SQLException ex){
        // Fehlermeldung anzeigen, wenn die Abfrage fehlschlägt
        JOptionPane.showMessageDialog(rootPane, "Fehler bei der Datenbankabfrage für den Ordner_ID: " + ex);
    }
        
        return ordner_ID;
    }


    
    boolean printOrdner = true;
    boolean printNotiz = true;
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pkOrdnerErstellen = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnPKOrdnerErstellen = new javax.swing.JButton();
        PKaddTf = new javax.swing.JTextField();
        btnPKSchliessen = new javax.swing.JButton();
        pkNotizErstellen = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        tfNotizErstellenTitel = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taNotizErstellen = new javax.swing.JTextArea();
        btnPKNotizErstellen = new javax.swing.JButton();
        btnPKNotizErstellenAbbrechen = new javax.swing.JButton();
        label1 = new java.awt.Label();
        pkNotizBearbeiten = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taNotizBearbeiten = new javax.swing.JTextArea();
        btnNotizBearbeitenSpeichern = new javax.swing.JButton();
        btnNotizBearbeitenAbbrechen = new javax.swing.JButton();
        tfTitel = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        pkChangePW = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btnPKOrdnerErstellen1 = new javax.swing.JButton();
        btnPKSchliessen1 = new javax.swing.JButton();
        pwField = new javax.swing.JPasswordField();
        pwField1 = new javax.swing.JPasswordField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        showPasswordChk1 = new javax.swing.JCheckBox();
        pwField2 = new javax.swing.JPasswordField();
        jLabel12 = new javax.swing.JLabel();
        LStatus = new javax.swing.JLabel();
        pkChangePWE = new javax.swing.JDialog();
        panel2 = new java.awt.Panel();
        jLabel13 = new javax.swing.JLabel();
        btnCreateAccountUserOK = new javax.swing.JButton();
        pkChangePWC = new javax.swing.JDialog();
        panel3 = new java.awt.Panel();
        jLabel14 = new javax.swing.JLabel();
        btnCreateAccountUserOK1 = new javax.swing.JButton();
        pkChangeN = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        btnPKOrdnerErstellen2 = new javax.swing.JButton();
        btnPKSchliessen2 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        tfChangeName = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        LStatus2 = new javax.swing.JLabel();
        pwField3 = new javax.swing.JPasswordField();
        showPasswordChk2 = new javax.swing.JCheckBox();
        pkChangeNE = new javax.swing.JDialog();
        panel4 = new java.awt.Panel();
        jLabel19 = new javax.swing.JLabel();
        btnCreateAccountUserOK2 = new javax.swing.JButton();
        pkChangeNC = new javax.swing.JDialog();
        panel5 = new java.awt.Panel();
        jLabel20 = new javax.swing.JLabel();
        btnCreateAccountUserOK3 = new javax.swing.JButton();
        pkFolderE = new javax.swing.JDialog();
        panel6 = new java.awt.Panel();
        jLabel22 = new javax.swing.JLabel();
        btnCreateAccountUserOK4 = new javax.swing.JButton();
        pkNoteE = new javax.swing.JDialog();
        panel7 = new java.awt.Panel();
        jLabel23 = new javax.swing.JLabel();
        btnCreateAccountUserOK5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnNotizLoeschen = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taNotizInhalt = new javax.swing.JTextArea();
        btnNoitzBearbeiten = new javax.swing.JButton();
        scOrdner = new javax.swing.JScrollPane();
        lOrdner = new javax.swing.JList<>();
        spNotizen = new javax.swing.JScrollPane();
        lNotizen = new javax.swing.JList<>();
        btnopenPKadd = new javax.swing.JButton();
        btnRemoveOrdner = new javax.swing.JButton();
        btnNotizErstellen = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btnLogout = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        lUsername = new javax.swing.JLabel();
        btnChangePW = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnChangeN = new javax.swing.JButton();

        pkOrdnerErstellen.setTitle("JMM NOTE ADD FOLDER");
        pkOrdnerErstellen.setAlwaysOnTop(true);
        pkOrdnerErstellen.setIconImage(new ImageIcon(getClass().getResource("/notiz/app/test/source/icon.png")).getImage());
        pkOrdnerErstellen.setLocation(new java.awt.Point(0, 0));
        pkOrdnerErstellen.setModal(true);
        pkOrdnerErstellen.setPreferredSize(new java.awt.Dimension(290, 215));
        pkOrdnerErstellen.setResizable(false);
        pkOrdnerErstellen.setSize(new java.awt.Dimension(290, 215));
        pkOrdnerErstellen.setType(java.awt.Window.Type.POPUP);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("FOLDER");

        btnPKOrdnerErstellen.setBackground(java.awt.Color.lightGray);
        btnPKOrdnerErstellen.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnPKOrdnerErstellen.setForeground(java.awt.Color.white);
        btnPKOrdnerErstellen.setText("add");
        btnPKOrdnerErstellen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPKOrdnerErstellenActionPerformed(evt);
            }
        });

        PKaddTf.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));
        PKaddTf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PKaddTfActionPerformed(evt);
            }
        });

        btnPKSchliessen.setBackground(java.awt.Color.lightGray);
        btnPKSchliessen.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnPKSchliessen.setForeground(java.awt.Color.white);
        btnPKSchliessen.setText("cancel");
        btnPKSchliessen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPKSchliessenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnPKOrdnerErstellen)
                        .addGap(18, 18, 18)
                        .addComponent(btnPKSchliessen)
                        .addGap(50, 50, 50))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(PKaddTf, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PKaddTf, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPKSchliessen)
                    .addComponent(btnPKOrdnerErstellen))
                .addContainerGap(72, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pkOrdnerErstellenLayout = new javax.swing.GroupLayout(pkOrdnerErstellen.getContentPane());
        pkOrdnerErstellen.getContentPane().setLayout(pkOrdnerErstellenLayout);
        pkOrdnerErstellenLayout.setHorizontalGroup(
            pkOrdnerErstellenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pkOrdnerErstellenLayout.setVerticalGroup(
            pkOrdnerErstellenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pkOrdnerErstellen.setLocationRelativeTo(jPanel1);

        pkNotizErstellen.setTitle("JMM NOTE ADD NOTE");
        pkNotizErstellen.setIconImage(new ImageIcon(getClass().getResource("/notiz/app/test/source/icon.png")).getImage());
        pkNotizErstellen.setModal(true);
        pkNotizErstellen.setResizable(false);
        pkNotizErstellen.setSize(new java.awt.Dimension(390, 330));
        pkNotizErstellen.setType(java.awt.Window.Type.POPUP);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        tfNotizErstellenTitel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));
        tfNotizErstellenTitel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNotizErstellenTitelActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("titel:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("content:");

        taNotizErstellen.setColumns(20);
        taNotizErstellen.setRows(5);
        taNotizErstellen.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));
        jScrollPane2.setViewportView(taNotizErstellen);

        btnPKNotizErstellen.setBackground(java.awt.Color.lightGray);
        btnPKNotizErstellen.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnPKNotizErstellen.setText("create");
        btnPKNotizErstellen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPKNotizErstellenActionPerformed(evt);
            }
        });

        btnPKNotizErstellenAbbrechen.setBackground(java.awt.Color.lightGray);
        btnPKNotizErstellenAbbrechen.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnPKNotizErstellenAbbrechen.setText("cancel");
        btnPKNotizErstellenAbbrechen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPKNotizErstellenAbbrechenActionPerformed(evt);
            }
        });

        label1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        label1.setText("NOTE");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(tfNotizErstellenTitel, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnPKNotizErstellen)
                                .addGap(18, 18, 18)
                                .addComponent(btnPKNotizErstellenAbbrechen))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfNotizErstellenTitel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPKNotizErstellen)
                    .addComponent(btnPKNotizErstellenAbbrechen))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        label1.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout pkNotizErstellenLayout = new javax.swing.GroupLayout(pkNotizErstellen.getContentPane());
        pkNotizErstellen.getContentPane().setLayout(pkNotizErstellenLayout);
        pkNotizErstellenLayout.setHorizontalGroup(
            pkNotizErstellenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pkNotizErstellenLayout.setVerticalGroup(
            pkNotizErstellenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pkNotizErstellen.setLocationRelativeTo(jPanel1);

        pkNotizBearbeiten.setTitle("JMM NOTE EDIT NOTE");
        pkNotizBearbeiten.setIconImage(new ImageIcon(getClass().getResource("/notiz/app/test/source/icon.png")).getImage());
        pkNotizBearbeiten.setModal(true);
        pkNotizBearbeiten.setResizable(false);
        pkNotizBearbeiten.setSize(new java.awt.Dimension(270, 290));
        pkNotizBearbeiten.setType(java.awt.Window.Type.POPUP);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("change content:");

        taNotizBearbeiten.setColumns(20);
        taNotizBearbeiten.setRows(5);
        taNotizBearbeiten.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));
        jScrollPane3.setViewportView(taNotizBearbeiten);

        btnNotizBearbeitenSpeichern.setBackground(java.awt.Color.lightGray);
        btnNotizBearbeitenSpeichern.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnNotizBearbeitenSpeichern.setText("add");
        btnNotizBearbeitenSpeichern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNotizBearbeitenSpeichernActionPerformed(evt);
            }
        });

        btnNotizBearbeitenAbbrechen.setBackground(java.awt.Color.lightGray);
        btnNotizBearbeitenAbbrechen.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnNotizBearbeitenAbbrechen.setText("cancel");
        btnNotizBearbeitenAbbrechen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNotizBearbeitenAbbrechenActionPerformed(evt);
            }
        });

        tfTitel.setEditable(false);
        tfTitel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("titel:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel6.setText("EDIT NOTE");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel6))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane3)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfTitel, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(btnNotizBearbeitenSpeichern)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnNotizBearbeitenAbbrechen)))))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfTitel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNotizBearbeitenSpeichern)
                    .addComponent(btnNotizBearbeitenAbbrechen))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout pkNotizBearbeitenLayout = new javax.swing.GroupLayout(pkNotizBearbeiten.getContentPane());
        pkNotizBearbeiten.getContentPane().setLayout(pkNotizBearbeitenLayout);
        pkNotizBearbeitenLayout.setHorizontalGroup(
            pkNotizBearbeitenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pkNotizBearbeitenLayout.setVerticalGroup(
            pkNotizBearbeitenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pkNotizBearbeiten.setLocationRelativeTo(jPanel1);

        pkChangePW.setTitle("CHANGE PASSWORD");
        pkChangePW.setIconImage(new ImageIcon(getClass().getResource("/notiz/app/test/source/icon.png")).getImage());
        pkChangePW.setModal(true);
        pkChangePW.setResizable(false);
        pkChangePW.setSize(new java.awt.Dimension(420, 270));
        pkChangePW.setType(java.awt.Window.Type.POPUP);

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("CHANGE PASSWORD");

        btnPKOrdnerErstellen1.setBackground(java.awt.Color.lightGray);
        btnPKOrdnerErstellen1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnPKOrdnerErstellen1.setForeground(java.awt.Color.white);
        btnPKOrdnerErstellen1.setText("change");
        btnPKOrdnerErstellen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPKOrdnerErstellen1ActionPerformed(evt);
            }
        });

        btnPKSchliessen1.setBackground(java.awt.Color.lightGray);
        btnPKSchliessen1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnPKSchliessen1.setForeground(java.awt.Color.white);
        btnPKSchliessen1.setText("cancel");
        btnPKSchliessen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPKSchliessen1ActionPerformed(evt);
            }
        });

        pwField.setToolTipText("password");
        pwField.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));
        pwField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                pwFieldCaretUpdate(evt);
            }
        });
        pwField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pwFieldActionPerformed(evt);
            }
        });

        pwField1.setToolTipText("password");
        pwField1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));
        pwField1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                pwField1CaretUpdate(evt);
            }
        });
        pwField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pwField1ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("new password");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setText("repeat password");

        showPasswordChk1.setBackground(new java.awt.Color(204, 204, 204));
        showPasswordChk1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        showPasswordChk1.setText("show Password");
        showPasswordChk1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPasswordChk1ActionPerformed(evt);
            }
        });

        pwField2.setToolTipText("password");
        pwField2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));
        pwField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pwField2ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setText("old password");

        LStatus.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(btnPKOrdnerErstellen1)
                .addGap(18, 18, 18)
                .addComponent(btnPKSchliessen1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pwField2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addComponent(pwField, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(121, 121, 121))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                    .addComponent(pwField1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(showPasswordChk1)))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(LStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pwField2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pwField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pwField1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(showPasswordChk1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPKOrdnerErstellen1)
                    .addComponent(btnPKSchliessen1))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout pkChangePWLayout = new javax.swing.GroupLayout(pkChangePW.getContentPane());
        pkChangePW.getContentPane().setLayout(pkChangePWLayout);
        pkChangePWLayout.setHorizontalGroup(
            pkChangePWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pkChangePWLayout.setVerticalGroup(
            pkChangePWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pkChangePW.setLocationRelativeTo(jPanel1);

        pkChangePWE.setIconImage(new ImageIcon(getClass().getResource("/notiz/app/test/source/icon.png")).getImage());
        pkChangePWE.setMinimumSize(new java.awt.Dimension(208, 80));
        pkChangePWE.setModal(true);
        pkChangePWE.setResizable(false);
        pkChangePWE.setSize(new java.awt.Dimension(208, 120));
        pkChangePWE.setType(java.awt.Window.Type.POPUP);

        panel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Wrong password");

        btnCreateAccountUserOK.setBackground(java.awt.Color.lightGray);
        btnCreateAccountUserOK.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCreateAccountUserOK.setForeground(java.awt.Color.white);
        btnCreateAccountUserOK.setText("OK");
        btnCreateAccountUserOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateAccountUserOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(btnCreateAccountUserOK)
                .addContainerGap(73, Short.MAX_VALUE))
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel13)
                .addGap(15, 15, 15)
                .addComponent(btnCreateAccountUserOK)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pkChangePWE.setLocationRelativeTo(pkChangePW);

        javax.swing.GroupLayout pkChangePWELayout = new javax.swing.GroupLayout(pkChangePWE.getContentPane());
        pkChangePWE.getContentPane().setLayout(pkChangePWELayout);
        pkChangePWELayout.setHorizontalGroup(
            pkChangePWELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pkChangePWELayout.setVerticalGroup(
            pkChangePWELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pkChangePWC.setIconImage(new ImageIcon(getClass().getResource("/notiz/app/test/source/icon.png")).getImage());
        pkChangePWC.setModal(true);
        pkChangePWC.setResizable(false);
        pkChangePWC.setSize(new java.awt.Dimension(208, 100));
        pkChangePWC.setType(java.awt.Window.Type.POPUP);

        panel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Password successfully changed");

        btnCreateAccountUserOK1.setBackground(java.awt.Color.lightGray);
        btnCreateAccountUserOK1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCreateAccountUserOK1.setForeground(java.awt.Color.white);
        btnCreateAccountUserOK1.setText("OK");
        btnCreateAccountUserOK1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateAccountUserOK1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(btnCreateAccountUserOK1)
                .addContainerGap(73, Short.MAX_VALUE))
            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel14)
                .addGap(15, 15, 15)
                .addComponent(btnCreateAccountUserOK1)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pkChangePWC.setLocationRelativeTo(pkChangePW);

        javax.swing.GroupLayout pkChangePWCLayout = new javax.swing.GroupLayout(pkChangePWC.getContentPane());
        pkChangePWC.getContentPane().setLayout(pkChangePWCLayout);
        pkChangePWCLayout.setHorizontalGroup(
            pkChangePWCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pkChangePWCLayout.setVerticalGroup(
            pkChangePWCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pkChangeN.setTitle("CHANGE NAME");
        pkChangeN.setIconImage(new ImageIcon(getClass().getResource("/notiz/app/test/source/icon.png")).getImage());
        pkChangeN.setLocation(new java.awt.Point(0, 0));
        pkChangeN.setModal(true);
        pkChangeN.setResizable(false);
        pkChangeN.setSize(new java.awt.Dimension(398, 270));
        pkChangeN.setType(java.awt.Window.Type.POPUP);

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("CHANGE NAME");

        btnPKOrdnerErstellen2.setBackground(java.awt.Color.lightGray);
        btnPKOrdnerErstellen2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnPKOrdnerErstellen2.setForeground(java.awt.Color.white);
        btnPKOrdnerErstellen2.setText("change");
        btnPKOrdnerErstellen2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPKOrdnerErstellen2ActionPerformed(evt);
            }
        });

        btnPKSchliessen2.setBackground(java.awt.Color.lightGray);
        btnPKSchliessen2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnPKSchliessen2.setForeground(java.awt.Color.white);
        btnPKSchliessen2.setText("cancel");
        btnPKSchliessen2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPKSchliessen2ActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel21.setText("Info: this only changes your display name, not your username");

        tfChangeName.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));
        tfChangeName.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                tfChangeNameCaretUpdate(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setText("new name");

        jLabel17.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel17.setText("password");

        LStatus2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LStatus2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        pwField3.setToolTipText("password");
        pwField3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));
        pwField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pwField3ActionPerformed(evt);
            }
        });

        showPasswordChk2.setBackground(new java.awt.Color(204, 204, 204));
        showPasswordChk2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        showPasswordChk2.setText("show Password");
        showPasswordChk2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPasswordChk2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(LStatus2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(btnPKOrdnerErstellen2)
                                .addGap(63, 63, 63)
                                .addComponent(btnPKSchliessen2))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel21))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel16))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfChangeName, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(pwField3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(showPasswordChk2)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel16))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(tfChangeName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pwField3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showPasswordChk2)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addGap(12, 12, 12)
                .addComponent(LStatus2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPKSchliessen2)
                    .addComponent(btnPKOrdnerErstellen2))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pkChangeNLayout = new javax.swing.GroupLayout(pkChangeN.getContentPane());
        pkChangeN.getContentPane().setLayout(pkChangeNLayout);
        pkChangeNLayout.setHorizontalGroup(
            pkChangeNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pkChangeNLayout.setVerticalGroup(
            pkChangeNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pkChangeN.setLocationRelativeTo(jPanel1);

        pkChangeNE.setIconImage(new ImageIcon(getClass().getResource("/notiz/app/test/source/icon.png")).getImage());
        pkChangeNE.setMinimumSize(new java.awt.Dimension(208, 80));
        pkChangeNE.setModal(true);
        pkChangeNE.setResizable(false);
        pkChangeNE.setSize(new java.awt.Dimension(208, 120));
        pkChangeNE.setType(java.awt.Window.Type.POPUP);

        panel4.setBackground(new java.awt.Color(204, 204, 204));

        jLabel19.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Wrong password");

        btnCreateAccountUserOK2.setBackground(java.awt.Color.lightGray);
        btnCreateAccountUserOK2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCreateAccountUserOK2.setForeground(java.awt.Color.white);
        btnCreateAccountUserOK2.setText("OK");
        btnCreateAccountUserOK2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateAccountUserOK2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel4Layout = new javax.swing.GroupLayout(panel4);
        panel4.setLayout(panel4Layout);
        panel4Layout.setHorizontalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel4Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(btnCreateAccountUserOK2)
                .addContainerGap(73, Short.MAX_VALUE))
            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel4Layout.setVerticalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel19)
                .addGap(15, 15, 15)
                .addComponent(btnCreateAccountUserOK2)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pkChangeNE.setLocationRelativeTo(pkChangeN);

        javax.swing.GroupLayout pkChangeNELayout = new javax.swing.GroupLayout(pkChangeNE.getContentPane());
        pkChangeNE.getContentPane().setLayout(pkChangeNELayout);
        pkChangeNELayout.setHorizontalGroup(
            pkChangeNELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pkChangeNELayout.setVerticalGroup(
            pkChangeNELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pkChangeNC.setIconImage(new ImageIcon(getClass().getResource("/notiz/app/test/source/icon.png")).getImage());
        pkChangeNC.setModal(true);
        pkChangeNC.setResizable(false);
        pkChangeNC.setSize(new java.awt.Dimension(208, 120));
        pkChangeNC.setType(java.awt.Window.Type.POPUP);

        panel5.setBackground(new java.awt.Color(204, 204, 204));

        jLabel20.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Name successfully changed");

        btnCreateAccountUserOK3.setBackground(java.awt.Color.lightGray);
        btnCreateAccountUserOK3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCreateAccountUserOK3.setForeground(java.awt.Color.white);
        btnCreateAccountUserOK3.setText("OK");
        btnCreateAccountUserOK3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateAccountUserOK3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel5Layout = new javax.swing.GroupLayout(panel5);
        panel5.setLayout(panel5Layout);
        panel5Layout.setHorizontalGroup(
            panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel5Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(btnCreateAccountUserOK3)
                .addContainerGap(73, Short.MAX_VALUE))
            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel5Layout.setVerticalGroup(
            panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel20)
                .addGap(15, 15, 15)
                .addComponent(btnCreateAccountUserOK3)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        pkChangeNC.setLocationRelativeTo(pkChangeN);

        javax.swing.GroupLayout pkChangeNCLayout = new javax.swing.GroupLayout(pkChangeNC.getContentPane());
        pkChangeNC.getContentPane().setLayout(pkChangeNCLayout);
        pkChangeNCLayout.setHorizontalGroup(
            pkChangeNCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pkChangeNCLayout.setVerticalGroup(
            pkChangeNCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pkFolderE.setAlwaysOnTop(true);
        pkFolderE.setIconImage(new ImageIcon(getClass().getResource("/notiz/app/test/source/icon.png")).getImage());
        pkFolderE.setModal(true);
        pkFolderE.setResizable(false);
        pkFolderE.setSize(new java.awt.Dimension(330, 130));
        pkFolderE.setType(java.awt.Window.Type.POPUP);

        panel6.setBackground(new java.awt.Color(204, 204, 204));

        jLabel22.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("You can't create multiple folders with the same name!");

        btnCreateAccountUserOK4.setBackground(java.awt.Color.lightGray);
        btnCreateAccountUserOK4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCreateAccountUserOK4.setForeground(java.awt.Color.white);
        btnCreateAccountUserOK4.setText("OK");
        btnCreateAccountUserOK4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateAccountUserOK4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel6Layout = new javax.swing.GroupLayout(panel6);
        panel6.setLayout(panel6Layout);
        panel6Layout.setHorizontalGroup(
            panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
            .addGroup(panel6Layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(btnCreateAccountUserOK4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel6Layout.setVerticalGroup(
            panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel22)
                .addGap(29, 29, 29)
                .addComponent(btnCreateAccountUserOK4)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pkFolderE.setLocationRelativeTo(pkChangeN);

        javax.swing.GroupLayout pkFolderELayout = new javax.swing.GroupLayout(pkFolderE.getContentPane());
        pkFolderE.getContentPane().setLayout(pkFolderELayout);
        pkFolderELayout.setHorizontalGroup(
            pkFolderELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pkFolderELayout.setVerticalGroup(
            pkFolderELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pkChangeNC.setLocationRelativeTo(pkOrdnerErstellen);

        pkFolderE.getAccessibleContext().setAccessibleParent(pkOrdnerErstellen);

        pkNoteE.setAlwaysOnTop(true);
        pkNoteE.setIconImage(new ImageIcon(getClass().getResource("/notiz/app/test/source/icon.png")).getImage());
        pkNoteE.setModal(true);
        pkNoteE.setResizable(false);
        pkNoteE.setSize(new java.awt.Dimension(330, 130));
        pkNoteE.setType(java.awt.Window.Type.POPUP);

        panel7.setBackground(new java.awt.Color(204, 204, 204));

        jLabel23.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("You can't create multiple notes with the same name!");

        btnCreateAccountUserOK5.setBackground(java.awt.Color.lightGray);
        btnCreateAccountUserOK5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCreateAccountUserOK5.setForeground(java.awt.Color.white);
        btnCreateAccountUserOK5.setText("OK");
        btnCreateAccountUserOK5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateAccountUserOK5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel7Layout = new javax.swing.GroupLayout(panel7);
        panel7.setLayout(panel7Layout);
        panel7Layout.setHorizontalGroup(
            panel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
            .addGroup(panel7Layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(btnCreateAccountUserOK5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel7Layout.setVerticalGroup(
            panel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel23)
                .addGap(29, 29, 29)
                .addComponent(btnCreateAccountUserOK5)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pkFolderE.setLocationRelativeTo(pkChangeN);

        javax.swing.GroupLayout pkNoteELayout = new javax.swing.GroupLayout(pkNoteE.getContentPane());
        pkNoteE.getContentPane().setLayout(pkNoteELayout);
        pkNoteELayout.setHorizontalGroup(
            pkNoteELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pkNoteELayout.setVerticalGroup(
            pkNoteELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pkChangeNC.setLocationRelativeTo(pkOrdnerErstellen);

        pkNoteE.getAccessibleContext().setAccessibleParent(pkNotizErstellen);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JMM NOTE");
        setIconImage(new ImageIcon(getClass().getResource("/notiz/app/test/source/icon.png")).getImage());
        setLocation(new java.awt.Point(600, 200));
        setResizable(false);
        setSize(new java.awt.Dimension(1028, 490));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        btnNotizLoeschen.setBackground(java.awt.Color.lightGray);
        btnNotizLoeschen.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnNotizLoeschen.setText("delete note");
        btnNotizLoeschen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNotizLoeschenActionPerformed(evt);
            }
        });

        taNotizInhalt.setEditable(false);
        taNotizInhalt.setColumns(20);
        taNotizInhalt.setRows(5);
        taNotizInhalt.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));
        jScrollPane1.setViewportView(taNotizInhalt);
        taNotizInhalt.getAccessibleContext().setAccessibleDescription("");

        btnNoitzBearbeiten.setBackground(java.awt.Color.lightGray);
        btnNoitzBearbeiten.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnNoitzBearbeiten.setText("edit");
        btnNoitzBearbeiten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNoitzBearbeitenActionPerformed(evt);
            }
        });

        lOrdner.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));
        lOrdner.setModel(new DefaultListModel<String>());
        lOrdner.setToolTipText("");
        lOrdner.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lOrdnerAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        lOrdner.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lOrdnerValueChanged(evt);
            }
        });
        scOrdner.setViewportView(lOrdner);

        lNotizen.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));
        lNotizen.setModel(new DefaultListModel<String>());
        lNotizen.setDragEnabled(true);
        lNotizen.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lNotizenValueChanged(evt);
            }
        });
        spNotizen.setViewportView(lNotizen);

        btnopenPKadd.setBackground(java.awt.Color.lightGray);
        btnopenPKadd.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnopenPKadd.setText("create folder");
        btnopenPKadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnopenPKaddActionPerformed(evt);
            }
        });

        btnRemoveOrdner.setBackground(java.awt.Color.lightGray);
        btnRemoveOrdner.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnRemoveOrdner.setText("delete folder");
        btnRemoveOrdner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveOrdnerActionPerformed(evt);
            }
        });

        btnNotizErstellen.setBackground(java.awt.Color.lightGray);
        btnNotizErstellen.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnNotizErstellen.setText("create note");
        btnNotizErstellen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNotizErstellenActionPerformed(evt);
            }
        });

        btnLogout.setBackground(java.awt.Color.lightGray);
        btnLogout.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnLogout.setText("logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        jLabel7.setBackground(java.awt.Color.lightGray);
        jLabel7.setForeground(java.awt.Color.lightGray);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/notiz/app/test/source/icon4.png"))); // NOI18N
        jLabel7.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/notiz/app/test/source/icon.png"))); // NOI18N

        lUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lUsername.setText("NAME");

        btnChangePW.setBackground(java.awt.Color.lightGray);
        btnChangePW.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnChangePW.setText("change password");
        btnChangePW.setPreferredSize(new java.awt.Dimension(132, 22));
        btnChangePW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePWActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Welcome");

        btnChangeN.setBackground(java.awt.Color.lightGray);
        btnChangeN.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnChangeN.setText("change name");
        btnChangeN.setPreferredSize(new java.awt.Dimension(132, 22));
        btnChangeN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnChangeN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnChangePW, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(4, 4, 4)
                .addComponent(lUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnChangeN, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnChangePW, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogout)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scOrdner, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnopenPKadd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRemoveOrdner, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spNotizen, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnNotizErstellen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNotizLoeschen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNoitzBearbeiten)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNoitzBearbeiten)
                        .addGap(54, 54, 54))
                    .addComponent(spNotizen, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                    .addComponent(scOrdner))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnopenPKadd)
                    .addComponent(btnNotizErstellen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemoveOrdner)
                    .addComponent(btnNotizLoeschen))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnopenPKaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnopenPKaddActionPerformed
        pkOrdnerErstellen.show();
    }//GEN-LAST:event_btnopenPKaddActionPerformed

    private void btnRemoveOrdnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveOrdnerActionPerformed
        if(lOrdner.isSelectionEmpty() || !(notizenArrayListe.isEmpty())){
            JOptionPane.showMessageDialog(rootPane, "Sie haben keinen leeren Ordner ausgewählt!");
        }else{
            try{
                int ergebnis = konnektor.fuehreUpdateAus("DELETE FROM `Ordner` WHERE Name = '" + lOrdner.getSelectedValue() + "' AND Benutzername = '" + login.uebergebeuser().getUsername() + "';");
                JOptionPane.showMessageDialog(rootPane, "Löschen des Ordners war erfolgreich!");
                updateOrdner(lOrdner.getSelectedValue());
            }catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "Fehler beim Löschen des Ordners asu der Datenbank: " + ex.getMessage());
            }
        }        
    }//GEN-LAST:event_btnRemoveOrdnerActionPerformed

    private void lOrdnerValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lOrdnerValueChanged
        if(printOrdner == true){
            String selectedValue = lOrdner.getSelectedValue();
            notizenArrayListe.clear();
            initNotizen(selectedValue);
            // Führen Sie die gewünschte Aktion aus
            System.out.println("Ausgewählter Eintrag: " + selectedValue);
            
            notizenListe = (DefaultListModel<String>) lNotizen.getModel();
            notizenListe.clear();
            taNotizInhalt.setText("No note selected");
            
            for(int i = 0; i<notizenArrayListe.size(); i++){
                notizenListe.addElement(notizenArrayListe.get(i).getTitle());
            }
        }
        printOrdner =! printOrdner;
    }//GEN-LAST:event_lOrdnerValueChanged

    private void btnNoitzBearbeitenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNoitzBearbeitenActionPerformed
        if(!(taNotizInhalt.getText().equals("No note selected") && !taNotizInhalt.getText().trim().isEmpty())){
            pkNotizBearbeiten.show();
            taNotizBearbeiten.setText(taNotizInhalt.getText());
            tfTitel.setText(lNotizen.getSelectedValue());
        }else{
            JOptionPane.showMessageDialog(rootPane, "Du hast No note selected!");
        }
    }//GEN-LAST:event_btnNoitzBearbeitenActionPerformed

    private void btnNotizErstellenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotizErstellenActionPerformed
        pkNotizErstellen.show();
    }//GEN-LAST:event_btnNotizErstellenActionPerformed

    private void btnPKNotizErstellenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPKNotizErstellenActionPerformed
        if(!tfNotizErstellenTitel.getText().trim().isEmpty()){    
            String ausgewaehlterOrdner = lOrdner.getSelectedValue();
            String content =  taNotizErstellen.getText();
            String title = tfNotizErstellenTitel.getText();
        
            Notiz neueNotiz = new Notiz(ausgewaehlterOrdner, title, content);
            notizenArrayListe.add(neueNotiz);
            DefaultListModel<String> notizenListe = (DefaultListModel<String>) lNotizen.getModel();
            notizenListe.addElement(title);
            taNotizInhalt.setText(content);
        
            try {
            
            int ergebnis = konnektor.fuehreUpdateAus("INSERT INTO `notiz` (`Titel`, `Inhalt`, `Ordner_ID`) VALUES ('"+title+"', '"+content+"', "+getOrdner_ID(ausgewaehlterOrdner)+");");
        
            JOptionPane.showMessageDialog(rootPane, "Notiz erfolgreich hinzugefügt!");
        } catch (SQLException ex) {
        JOptionPane.showMessageDialog(rootPane, "Fehler beim Hinzufügen der Notiz in die Datenbank: " + ex.getMessage());
        }
    
        
        taNotizErstellen.setText(null);
        tfNotizErstellenTitel.setText(null);
        pkNotizErstellen.hide();
        }
    }//GEN-LAST:event_btnPKNotizErstellenActionPerformed

    private void btnPKNotizErstellenAbbrechenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPKNotizErstellenAbbrechenActionPerformed
        pkNotizErstellen.hide();
        taNotizErstellen.setText(null);
        tfNotizErstellenTitel.setText(null);
    }//GEN-LAST:event_btnPKNotizErstellenAbbrechenActionPerformed

    private void tfNotizErstellenTitelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNotizErstellenTitelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNotizErstellenTitelActionPerformed

    private void lNotizenValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lNotizenValueChanged
        if(printNotiz == true){
            String selectedValue = lNotizen.getSelectedValue();
            // Führen Sie die gewünschte Aktion aus
            System.out.println("Ausgewählter Eintrag: " + selectedValue);
            
            for (int i = 0; i < notizenArrayListe.size(); i++) {
                if(notizenArrayListe.get(i).getTitle().equals(selectedValue)){
                    taNotizInhalt.setText(notizenArrayListe.get(i).getContent());
                }
            }
        }
        printNotiz =! printNotiz;
    }//GEN-LAST:event_lNotizenValueChanged

    private void lOrdnerAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lOrdnerAncestorAdded
        
    }//GEN-LAST:event_lOrdnerAncestorAdded

    private void btnNotizBearbeitenAbbrechenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotizBearbeitenAbbrechenActionPerformed
        pkNotizBearbeiten.hide();
    }//GEN-LAST:event_btnNotizBearbeitenAbbrechenActionPerformed

    private void btnNotizBearbeitenSpeichernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotizBearbeitenSpeichernActionPerformed
    if(!taNotizBearbeiten.getText().trim().isEmpty()){
        String content = taNotizBearbeiten.getText();
        try {
            
        int ergebnis = konnektor.fuehreUpdateAus("Update `notiz`  SET `Inhalt` = '" + content + "' WHERE `Ordner_ID` = " + getOrdner_ID(lOrdner.getSelectedValue()) + ";");
        taNotizInhalt.setText(content);
        JOptionPane.showMessageDialog(rootPane, "Notiz erfolgreich hinzugefügt!");
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(rootPane, "Fehler beim Hinzufügen der Notiz in die Datenbank: " + ex.getMessage());
    }
    }
    }//GEN-LAST:event_btnNotizBearbeitenSpeichernActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        dispose();
        login login = new login();
        login.setVisible(true);
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnPKSchliessenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPKSchliessenActionPerformed
        PKaddTf.setText(null);
        pkOrdnerErstellen.hide();
    }//GEN-LAST:event_btnPKSchliessenActionPerformed

    private void PKaddTfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PKaddTfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PKaddTfActionPerformed

    private void btnPKOrdnerErstellenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPKOrdnerErstellenActionPerformed
        String neuerOrdnerName = PKaddTf.getText().trim();
        for (int i = 0; i < ordnerArrayListe.size(); i++) {
            if(ordnerArrayListe.get(i).getName().equals(neuerOrdnerName)){
                pkFolderE.setVisible(true);
                return;
            }
        }
        if (neuerOrdnerName.isEmpty()) {
                
            return;
        }

        Ordner newOrdner = new Ordner(neuerOrdnerName);
        System.out.println("Objekt Ordner Name:" + newOrdner.getName());

        // Füge den neuen Ordner in die Datenbank ein
        try {
            String benutzername = login.uebergebeuser().getUsername();
            int ergebnis = konnektor.fuehreUpdateAus("INSERT INTO `Ordner` (`Name`, `Benutzername`) VALUES ('"+neuerOrdnerName+"', '"+benutzername+"');");

            // Ordner zur ArrayList und zum GUI-Model hinzufügen
            ordnerArrayListe.add(newOrdner);
            DefaultListModel<String> model = (DefaultListModel<String>) lOrdner.getModel();
            model.addElement(newOrdner.getName());
            PKaddTf.setText(null);
            pkOrdnerErstellen.hide();

            JOptionPane.showMessageDialog(rootPane, "Ordner erfolgreich hinzugefügt!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Fehler beim Hinzufügen des Ordners in die Datenbank: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnPKOrdnerErstellenActionPerformed

    private void btnChangePWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePWActionPerformed
        pkChangePW.setVisible(true);
    }//GEN-LAST:event_btnChangePWActionPerformed

    private void btnPKOrdnerErstellen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPKOrdnerErstellen1ActionPerformed
        String pwvm = valPW(pwField.getPassword(),pwField1.getPassword());
        if(pwvm.equals("The new password is valid")){
            try{
                int ergebnis = konnektor.fuehreUpdateAus("UPDATE `user` SET `Password` = '" + pwField.getText() + "' WHERE `Benutzername` = '" + login.uebergebeuser().getUsername() + "' AND `Password` = '" + pwField2.getText() +"';");
                //int ergebnis = konnektor.fuehreUpdateAus("UPDATE `user` SET `Password` = '123456789a' WHERE `Benutzername` = '" + login.uebergebeuser().getName() + "' AND 'Password' = '12345678a';");
                System.out.println(ergebnis);
                System.out.println("username"+login.uebergebeuser().getUsername());
                System.out.println("pw"+ pwField2.getText());
                if(ergebnis == 0){
                    pkChangePWE.setVisible(true);
                }
                else if(ergebnis == 1 ){
                    pkChangePWC.setVisible(true);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "Error during database query:" + ex);
            }
        }
    }//GEN-LAST:event_btnPKOrdnerErstellen1ActionPerformed

    private void btnPKSchliessen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPKSchliessen1ActionPerformed
        pkChangePW.dispose();
        pwField2.setText(null);
        pwField.setText(null);
        pwField1.setText(null);
    }//GEN-LAST:event_btnPKSchliessen1ActionPerformed

    private void pwFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pwFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pwFieldActionPerformed

    private void pwField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pwField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pwField1ActionPerformed

    private void showPasswordChk1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPasswordChk1ActionPerformed
        if(showPasswordChk1.isSelected()){
            pwField.setEchoChar('\u0000');
            pwField1.setEchoChar('\u0000');
            pwField2.setEchoChar('\u0000');
        }
        else if (!showPasswordChk1.isSelected()){
            pwField.setEchoChar('\u2022');
            pwField1.setEchoChar('\u2022');
            pwField2.setEchoChar('\u2022');
        }
    }//GEN-LAST:event_showPasswordChk1ActionPerformed

    private void pwField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pwField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pwField2ActionPerformed

    private void pwFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_pwFieldCaretUpdate
        String pwvm = valPW(pwField.getPassword(),pwField1.getPassword());
        LStatus.setText(pwvm);
    }//GEN-LAST:event_pwFieldCaretUpdate

    private void pwField1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_pwField1CaretUpdate
        String pwvm = valPW(pwField.getPassword(),pwField1.getPassword());
        LStatus.setText(pwvm);
    }//GEN-LAST:event_pwField1CaretUpdate

    private void btnCreateAccountUserOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateAccountUserOKActionPerformed
        pkChangePWE.dispose();
    }//GEN-LAST:event_btnCreateAccountUserOKActionPerformed

    private void btnCreateAccountUserOK1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateAccountUserOK1ActionPerformed
        pwField2.setText(null);
        pwField.setText(null);
        pwField1.setText(null);
        pkChangePWC.dispose();
        pkChangePW.dispose();
    }//GEN-LAST:event_btnCreateAccountUserOK1ActionPerformed

    private void btnPKOrdnerErstellen2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPKOrdnerErstellen2ActionPerformed
        String username = tfChangeName.getText();
        if(valUser(username))
            try{
                int ergebnis = konnektor.fuehreUpdateAus("UPDATE `user` SET `Anzeigename` = '" + username + "' WHERE `Benutzername` = '" + login.uebergebeuser().getUsername() + "' AND `Password` = '" + pwField3.getText() +"';");
                //int ergebnis = konnektor.fuehreUpdateAus("UPDATE `user` SET `Password` = '123456789a' WHERE `Benutzername` = '" + login.uebergebeuser().getName() + "' AND 'Password' = '12345678a';");
                System.out.println(ergebnis);
                System.out.println("username: "+login.uebergebeuser().getUsername());
                if(ergebnis == 0){
                    pkChangeNE.setVisible(true);
                }
                else if(ergebnis == 1 ){
                    pkChangeNC.setVisible(true);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "Error during database query:" + ex);
        }
    }//GEN-LAST:event_btnPKOrdnerErstellen2ActionPerformed

    private void btnPKSchliessen2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPKSchliessen2ActionPerformed
        pkChangeN.dispose();
        tfChangeName.setText(null);
        pwField3.setText(null);
    }//GEN-LAST:event_btnPKSchliessen2ActionPerformed

    private void btnCreateAccountUserOK2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateAccountUserOK2ActionPerformed
        pkChangeNE.dispose();
    }//GEN-LAST:event_btnCreateAccountUserOK2ActionPerformed

    private void btnCreateAccountUserOK3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateAccountUserOK3ActionPerformed
        
    }//GEN-LAST:event_btnCreateAccountUserOK3ActionPerformed

    private void pwField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pwField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pwField3ActionPerformed

    private void showPasswordChk2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPasswordChk2ActionPerformed
        if(showPasswordChk2.isSelected()){
            pwField3.setEchoChar('\u0000');
        }
        else if (!showPasswordChk1.isSelected()){
            pwField3.setEchoChar('\u2022');
        }
    }//GEN-LAST:event_showPasswordChk2ActionPerformed

    private void tfChangeNameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_tfChangeNameCaretUpdate
        if(!valUser(tfChangeName.getText())){
            LStatus2.setText("username must not be blank");
        }
        else{
            LStatus2.setText(null);
        }
    }//GEN-LAST:event_tfChangeNameCaretUpdate

    private void btnChangeNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeNActionPerformed
        pkChangeN.setVisible(true);
    }//GEN-LAST:event_btnChangeNActionPerformed

    private void btnCreateAccountUserOK4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateAccountUserOK4ActionPerformed
        pkFolderE.dispose();
    }//GEN-LAST:event_btnCreateAccountUserOK4ActionPerformed

    private void btnCreateAccountUserOK5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateAccountUserOK5ActionPerformed
        pkNoteE.dispose();
    }//GEN-LAST:event_btnCreateAccountUserOK5ActionPerformed

    private void btnNotizLoeschenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotizLoeschenActionPerformed
        try{
            System.out.println(lNotizen.getSelectedValue());
            int ergebnis = konnektor.fuehreUpdateAus("DELETE FROM `notiz` WHERE Titel = '" + lNotizen.getSelectedValue() + "' AND Ordner_ID = " + getOrdner_ID(login.uebergebeuser().getUsername()) + ";");
            JOptionPane.showMessageDialog(rootPane, "The note was deleted successfully!");
            updateNotiz(lNotizen.getSelectedValue());
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Error when deleting the note from the database: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnNotizLoeschenActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LStatus;
    private javax.swing.JLabel LStatus2;
    private javax.swing.JTextField PKaddTf;
    private javax.swing.JButton btnChangeN;
    private javax.swing.JButton btnChangePW;
    private javax.swing.JButton btnCreateAccountUserOK;
    private javax.swing.JButton btnCreateAccountUserOK1;
    private javax.swing.JButton btnCreateAccountUserOK2;
    private javax.swing.JButton btnCreateAccountUserOK3;
    private javax.swing.JButton btnCreateAccountUserOK4;
    private javax.swing.JButton btnCreateAccountUserOK5;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnNoitzBearbeiten;
    private javax.swing.JButton btnNotizBearbeitenAbbrechen;
    private javax.swing.JButton btnNotizBearbeitenSpeichern;
    private javax.swing.JButton btnNotizErstellen;
    private javax.swing.JButton btnNotizLoeschen;
    private javax.swing.JButton btnPKNotizErstellen;
    private javax.swing.JButton btnPKNotizErstellenAbbrechen;
    private javax.swing.JButton btnPKOrdnerErstellen;
    private javax.swing.JButton btnPKOrdnerErstellen1;
    private javax.swing.JButton btnPKOrdnerErstellen2;
    private javax.swing.JButton btnPKSchliessen;
    private javax.swing.JButton btnPKSchliessen1;
    private javax.swing.JButton btnPKSchliessen2;
    private javax.swing.JButton btnRemoveOrdner;
    private javax.swing.JButton btnopenPKadd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> lNotizen;
    private javax.swing.JList<String> lOrdner;
    private javax.swing.JLabel lUsername;
    private java.awt.Label label1;
    private java.awt.Panel panel2;
    private java.awt.Panel panel3;
    private java.awt.Panel panel4;
    private java.awt.Panel panel5;
    private java.awt.Panel panel6;
    private java.awt.Panel panel7;
    private javax.swing.JDialog pkChangeN;
    private javax.swing.JDialog pkChangeNC;
    private javax.swing.JDialog pkChangeNE;
    private javax.swing.JDialog pkChangePW;
    private javax.swing.JDialog pkChangePWC;
    private javax.swing.JDialog pkChangePWE;
    private javax.swing.JDialog pkFolderE;
    private javax.swing.JDialog pkNoteE;
    private javax.swing.JDialog pkNotizBearbeiten;
    private javax.swing.JDialog pkNotizErstellen;
    private javax.swing.JDialog pkOrdnerErstellen;
    private javax.swing.JPasswordField pwField;
    private javax.swing.JPasswordField pwField1;
    private javax.swing.JPasswordField pwField2;
    private javax.swing.JPasswordField pwField3;
    private javax.swing.JScrollPane scOrdner;
    private javax.swing.JCheckBox showPasswordChk1;
    private javax.swing.JCheckBox showPasswordChk2;
    private javax.swing.JScrollPane spNotizen;
    private javax.swing.JTextArea taNotizBearbeiten;
    private javax.swing.JTextArea taNotizErstellen;
    private javax.swing.JTextArea taNotizInhalt;
    private javax.swing.JTextField tfChangeName;
    private javax.swing.JTextField tfNotizErstellenTitel;
    private javax.swing.JTextField tfTitel;
    // End of variables declaration//GEN-END:variables
}
