/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package notiz.app.test;

import java.util.ArrayList;
import notiz.app.test.Notiz;

/**
 *
 * @author julius
 */
public class Ordner {
    private String ordnerName;
    private ArrayList<Notiz> notizen;

    public Ordner(String ordnerName) {
        this.ordnerName = ordnerName;
        this.notizen = new ArrayList<>();
    }

    public String getName() {
        return ordnerName;
    }

    public void addNotiz(Notiz notiz) {
        this.notizen.add(notiz);
    }

    
}
