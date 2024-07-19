
package lab1;

import javax.swing.*;

public class Email {
    private String email, asunto, contenido;
    private boolean leido;

    public Email(String email, String asunto, String contenido) {
        this.email = email;
        this.asunto = asunto;
        this.contenido = contenido;
        this.leido = false;
    }

    public boolean getLeido() {
        return leido;
    }
    
    public String getEmailE() {
        return email;
    }
    
    public String getAsunto() {
        return asunto;
    }
    
    public String getContenido() {
        return contenido;
    }
    
    public void leido() {
        leido = true;
    }
    
    public void showEmail() {
        JTextArea textArea = new JTextArea();
        textArea.setText("De: " + email + "\n" +
                         "Asunto: " + asunto + "\n" +
                         "Contenido: " + contenido);
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Correo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void print(JTextArea textArea) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
