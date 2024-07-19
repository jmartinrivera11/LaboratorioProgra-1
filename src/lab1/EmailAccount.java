
package lab1;

import javax.swing.JTextArea;

public class EmailAccount {
    private String email, password, username, nombre;
    Email inbox[];
    
    public EmailAccount(String email, String password, String username, String nombre) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nombre = nombre;
        inbox = new Email[50];
    }
    
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getNombre() {
        return nombre;
    }
    
    public boolean recibirEmail(Email em) {
        int espacio = espacio();
        if (espacio != -1) {
            inbox[espacio] = em;
            return true;
        }
        return false;
    }
    
    public void printInbox(JTextArea textArea) {
        int TotalRecibidos = 0, Sinleer = 0;
        textArea.setText("");
        textArea.append("Dirección: " + email + "\n");
        textArea.append("Nombre completo: " + nombre + "\n");

        for (int i = 0; i < inbox.length; i++) {
            if (inbox[i] != null) {
                String mensaje = inbox[i].getLeido() ? "Leído" : "Sin Leer";
                textArea.append((i + 1) + " - " + inbox[i].getEmailE() + " - " + inbox[i].getAsunto() + " - " + mensaje + "\n");
                if (!inbox[i].getLeido()) {
                    Sinleer++;
                }
                TotalRecibidos++;
            }
        }
        textArea.append("Correos sin leer: " + Sinleer + "\n");
        textArea.append("Total de correos recibidos: " + TotalRecibidos + "\n");
    }
    
    public void leerEmail(int pos, JTextArea textArea) {
        if (inbox[pos - 1] != null) {
            textArea.setText("");
            inbox[pos - 1].print(textArea);
            inbox[pos - 1].leido();
        } else {
            textArea.setText("Correo No Existe");
        }
    }
    
    public void borrarLeido() {
        for (int i = 0; i < inbox.length; i++) {
            if (inbox[i] != null) {
                inbox[i] = null;
            }
        }
    }

    public int espacio() {
        for (int i = 0; i < inbox.length; i++) {
            if (inbox[i] == null) {
                return i;
            }
        }
        return -1;
    }
}
