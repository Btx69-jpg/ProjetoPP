package org.example.testesgerais;

import Classes.Logger.ExceptionLogger;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class ExceptionsLogTest {
    public static void main(String[] args) {
        // Exemplo de uso da classe ExceptionLogger


        // Simular algumas exceções
        try {
            throw new NullPointerException("Objeto é nulo");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            ExceptionLogger.logException(e);
        }

        try {
            throw new ArrayIndexOutOfBoundsException("Índice fora do limite");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            ExceptionLogger.logException(e);
        }

        // Mostrar o log de exceções armazenado no arquivo
        ExceptionLogger.showLogFromFile();

        // Limpar o arquivo de log
        ExceptionLogger.clearLogFile();

        // Mostrar o log após a limpeza
        ExceptionLogger.showLogFromFile();
    }
}
