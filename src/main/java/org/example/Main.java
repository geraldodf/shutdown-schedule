package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Agendamento de Desligamento");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("""
                <html><body><div style='text-align: center;'>
                <h1>Olá! Aqui você pode agendar ou cancelar um desligamento automático do seu computador!</h1>
                </div></body></html>""", SwingConstants.CENTER);
        panel.add(welcomeLabel);

        JButton scheduleButton = new JButton("Agendar um desligamento automático");
        JButton cancelButton = new JButton("Cancelar agendamento existente");

        panel.add(scheduleButton);
        panel.add(cancelButton);

        scheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, """
                        Por favor, digite o valor em horas para agendar o desligamento
                        Exemplos:
                        1 = 1 hora
                        0.5 = 30 minutos
                        """, "Agendar Desligamento", JOptionPane.QUESTION_MESSAGE);
                if (input != null && !input.isEmpty()) {
                    try {
                        double timeInput = Double.parseDouble(input);
                        scheduleShutdown(hourToSeconds(timeInput));
                        JOptionPane.showMessageDialog(frame, "Desligamento agendado para " + timeInput + " horas.");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Entrada inválida. Por favor, insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelShutdown();
                JOptionPane.showMessageDialog(frame, "Desligamento cancelado.");
            }
        });

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private static int hourToSeconds(double timeInHours) {
        return (int) Math.round(timeInHours * 3600);
    }

    private static double secondsToHours(int timeInHours) {
        return (double) timeInHours / 3600;
    }

    private static void scheduleShutdown(int timeInSeconds) {
        try {
            String command = "shutdown -s -t " + timeInSeconds;
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void cancelShutdown() {
        try {
            String command = "shutdown -a";
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
