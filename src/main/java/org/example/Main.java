package org.example;

import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String resp = "";
        do {
            Scanner scan = new Scanner(System.in);
            System.out.println("""
                    ╔════════════════════════════════════════════╗
                    ║     Olá! Aqui você pode agendar ou         ║
                    ║     cancelar um desligamento automático    ║
                    ║     do seu computador! =D                  ║
                    ║                                            ║
                    ║  1 - Agendar um desligamento automático    ║
                    ║  2 - Cancelar agendamento existente        ║
                    ║                                            ║
                    ║   Digite 1 ou 2 conforme a opção desejada. ║
                    ║   Se você só apertar enter o valor padrão  ║
                    ║   é 2 horas.                               ║
                    ╚════════════════════════════════════════════╝""");
            String response = scan.nextLine();
            if (response.isBlank()) {
                System.out.println("Desligamento padrão selecionado, o desligamento acontecerá em 2 horas...");
                scheduleShutdown(hourToSeconds(2));
            } else {
                int optionSelected = Integer.parseInt(response);
                if (optionSelected == 1) {
                    System.out.println("""
                            ╔═════════════════════════════════════════════════════╗
                            ║   Por favor, digite o valor em horas para agendar   ║
                            ║   o desligamento.                                   ║
                            ║   Exemplos:                                         ║
                            ║   1 = 1 hora                                        ║
                            ║   0.5 = 30 minutos                                  ║
                            ║                                                     ║
                            ║   Digite o valor desejado:                          ║
                            ╚═════════════════════════════════════════════════════╝""");

                    double timeInput = Double.parseDouble(scan.nextLine()) ;
                    scheduleShutdown(hourToSeconds(timeInput));
                } else if (optionSelected == 2) {
                    cancelShutdown();
                } else {
                    System.out.println("Opção inválida. Por favor, digite 1 ou 2.");
                }
            }
            System.out.println("Deseja sair? S/N");
            resp = scan.nextLine();
        } while (!"s".equalsIgnoreCase(resp));
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
            System.out.println("Desligamento agendado para " + secondsToHours(timeInSeconds) + " horas.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void cancelShutdown() {
        try {
            String command = "shutdown -a";
            Runtime.getRuntime().exec(command);
            System.out.println("Desligamento cancelado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
