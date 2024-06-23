/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package notiz.app.test;

/**
 *
 * @author julius
 */
class Notiz extends Ordner {
    private String title;
    private String content;

    public Notiz(String ordnerName, String title, String content) {
        super(ordnerName);
        this.content = content;
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
     
}
