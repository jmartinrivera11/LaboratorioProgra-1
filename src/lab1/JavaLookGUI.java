
//JOSE MARTIN RIVERA SOLIS, SOFIA JIMENA CASTELAR PINEDA, JAYMAR NESHAWN WARREN MCGLOCKLIN

package lab1;

import javax.swing.*;
import java.awt.*;

public class JavaLookGUI {
    static EmailAccount[] Emails = new EmailAccount[50];
    EmailAccount accountActual = null;
    private JFrame frame;

    public JavaLookGUI() {
        frame = new JFrame("JavaLook");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JButton loginButton = new JButton("LOGIN");
        JButton createAccountButton = new JButton("CREAR ACCOUNT");
        JButton exitButton = new JButton("SALIR");

        loginButton.addActionListener(e -> showLogin());
        createAccountButton.addActionListener(e -> showCreateAccount());
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(loginButton);
        panel.add(createAccountButton);
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void showLogin() {
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
            "Email:", emailField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            if (login(email, password)) {
                JOptionPane.showMessageDialog(frame, "Se inició sesión");
                accountActual = buscar(email);
                showMain();
            } else {
                JOptionPane.showMessageDialog(frame, "La dirección no existe o las credenciales son incorrectas");
            }
        }
    }

    private void showCreateAccount() {
        JTextField emailField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
            "Email:", emailField,
            "Nombre Completo:", nameField,
            "Username:", usernameField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Crear Account", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String name = nameField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (buscar(email) == null) {
                if (espacio() != -1) {
                    Emails[espacio()] = new EmailAccount(email, password, username, name);
                    accountActual = buscar(email);
                    JOptionPane.showMessageDialog(frame, "Se creó exitosamente");
                    showMain();
                } else {
                    JOptionPane.showMessageDialog(frame, "No hay espacio para más cuentas");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No se pudo crear porque ya existe esta dirección");
            }
        }
    }

    private void showMain() {
        Object[] options = {"Ver Inbox", "Mandar Correo", "Leer Correo", "Limpiar Inbox", "Cerrar Sesión"};
        while (true) {
            int option = JOptionPane.showOptionDialog(frame, "----Menu Principal----", "Menu Principal",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (option == 0) {
                showInbox();
            } else if (option == 1) {
                enviarEmail();
            } else if (option == 2) {
                leerEmail();
            } else if (option == 3) {
                accountActual.borrarLeido();
                JOptionPane.showMessageDialog(frame, "Inbox limpiado exitosamente!");
            } else if (option == 4) {
                accountActual = null;
                break;
            }
        }
    }

    private void showInbox() {
        JTextArea textArea = new JTextArea(15, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        accountActual.printInbox(textArea);

        JOptionPane.showMessageDialog(frame, scrollPane, "Inbox", JOptionPane.INFORMATION_MESSAGE);
    }

    private void enviarEmail() {
        JTextField recipientField = new JTextField();
        JTextField subjectField = new JTextField();
        JTextArea contentArea = new JTextArea();
        Object[] message = {
            "Recipient:", recipientField,
            "Subject:", subjectField,
            "Content:", new JScrollPane(contentArea)
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Send Email", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String recipient = recipientField.getText();
            String subject = subjectField.getText();
            String content = contentArea.getText();

            Email emailSent = new Email(accountActual.getEmail(), subject, content);
            EmailAccount recipientAccount = buscar(recipient);

            if (recipientAccount == null || recipientAccount.espacio() == -1) {
                JOptionPane.showMessageDialog(frame, "Dirección de email no existe o el inbox del destinatario está lleno");
            } else {
                JOptionPane.showMessageDialog(frame, "El correo fue enviado exitosamente");
                recipientAccount.recibirEmail(emailSent);
            }
        }
    }

    private void leerEmail() {
        JTextField posField = new JTextField();
        Object[] message = {
            "Ingrese la posición del correo que desea leer:", posField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Leer Correo", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int pos = Integer.parseInt(posField.getText());
                JTextArea textArea = new JTextArea(10, 40);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);

                accountActual.leerEmail(pos, textArea);

                JOptionPane.showMessageDialog(frame, scrollPane, "Leer Correo", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Número de posición inválido");
            }
        }
    }

    public static EmailAccount buscar(String em) {
        for (EmailAccount email : Emails) {
            if (email != null && em.equals(email.getEmail())) {
                return email;
            }
        }
        return null;
    }

    public static boolean login(String email, String password) {
        for (EmailAccount em : Emails) {
            if (email != null && email.equals(em.getEmail()) && password.equals(em.getPassword())) {
                return true;
            }
        } 
        return false;
    }

    public static int espacio() {
        for (int i = 0; i < Emails.length; i++) {
            if (Emails[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JavaLookGUI gui = new JavaLookGUI();
        });
    }
}
