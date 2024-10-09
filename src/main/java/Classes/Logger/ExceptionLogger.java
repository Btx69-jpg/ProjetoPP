package Classes.Logger;

import Classes.Constantes;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.Date;

/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */
public class ExceptionLogger {

    private static final String logFilePath = Constantes.LOGFILENAME; // Caminho para o arquivo de log

    // Método para registrar exceções
    public static void logException(Exception e) {
        Date now = new Date(); // Captura a data e hora atuais com Date.toString()
        String logEntry = now.toString() + " - Exception: " + e.getClass().getName() + " - " + e.getMessage();

        // Escrever diretamente no arquivo
        try (FileWriter writer = new FileWriter(logFilePath, true)) {
            writer.write(logEntry + "\n");
        } catch (IOException ioException) {
            System.out.println("Erro ao escrever no arquivo de log: " + ioException.getMessage());
        }
    }

    // Método para mostrar as exceções armazenadas no arquivo
    public static void showLogFromFile() {
        File file = new File(logFilePath);

        // Verificar se o arquivo existe
        if (!file.exists()) {
            System.out.println("Arquivo de log não encontrado.");
            return;
        }

        // Ler o conteúdo do arquivo e exibir
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("Log de Exceções no Arquivo:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de log: " + e.getMessage());
        }
    }

    // Método para limpar o arquivo de log
    public static void clearLogFile() {
        try (FileWriter writer = new FileWriter(logFilePath)) {
            writer.write(""); // Sobrescrever o arquivo com um conteúdo vazio
            System.out.println("O arquivo de log foi limpo.");
        } catch (IOException e) {
            System.out.println("Erro ao limpar o arquivo de log: " + e.getMessage());
        }
    }

    // Método para exportar o log para um arquivo JSON
    public static void exportLogToJSON(String jsonFilePath) {
        File file = new File(logFilePath);

        if (!file.exists()) {
            System.out.println("Arquivo de log não encontrado.");
            return;
        }

        JSONArray exceptionArray = new JSONArray();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] logParts = line.split(" - ");
                if (logParts.length == 3) {
                    JSONObject logEntry = new JSONObject();
                    logEntry.put("timestamp", logParts[0]);
                    logEntry.put("exceptionType", logParts[1]);
                    logEntry.put("message", logParts[2]);

                    exceptionArray.add(logEntry);
                }
            }

            // Escrever o JSON no arquivo
            try (FileWriter fileWriter = new FileWriter(jsonFilePath)) {
                fileWriter.write(exceptionArray.toJSONString());
                System.out.println("Log exportado para " + jsonFilePath);
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de log: " + e.getMessage());
        }
    }

    // Método para filtrar exceções por período utilizando Date
    public static void showExceptionsBetween(Date startDate, Date endDate) {
        File file = new File(logFilePath);

        if (!file.exists()) {
            System.out.println("Arquivo de log não encontrado.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean hasLogs = false;

            while ((line = reader.readLine()) != null) {
                String[] logParts = line.split(" - ");
                if (logParts.length >= 3) {
                    try {
                        Date logDate = new Date(logParts[0]); // Usa a conversão direta de string para Date
                        if (logDate.after(startDate) && logDate.before(endDate)) {
                            System.out.println(line);
                            hasLogs = true;
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro ao converter a data do log: " + e.getMessage());
                    }
                }
            }

            if (!hasLogs) {
                System.out.println("Nenhuma exceção encontrada no período especificado.");
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de log: " + e.getMessage());
        }

    }
}