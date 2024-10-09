package Classes.Control;

import Classes.Constantes;
import Instances.EventETC.EventExt;
import Instances.Incidents.AccidentImp;
import Instances.Incidents.FailureImp;
import Instances.Incidents.IncidentImp;
import Instances.Incidents.MachineAccident;
import estgconstroi.enums.EventPriority;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

/**
 * Classe responsável pelo controle e manipulação de eventos, incidentes, acidentes, falhas e acidentes de máquinas.
 * Fornece métodos para adicionar, recuperar e remover esses objetos.
 */
public class ControlEvents {
    // Arrays para armazenar os diferentes tipos de eventos e incidentes
    private static EventExt[] eventChecker = new EventExt[Constantes.INITIAL_ARRAY_SIZE];
    private static IncidentImp[] incidentCheker = new IncidentImp[Constantes.INITIAL_ARRAY_SIZE];
    private static AccidentImp[] accidentCheker = new AccidentImp[Constantes.INITIAL_ARRAY_SIZE];
    private static FailureImp[] failureCheker = new FailureImp[Constantes.INITIAL_ARRAY_SIZE];
    private static MachineAccident[] machineAccidentCheker = new MachineAccident[Constantes.INITIAL_ARRAY_SIZE];

    // Contadores para rastrear o número de elementos em cada array
    private static int eventCKCnt;
    private static int incidentCKCnt;
    private static int accidentCKCnt;
    private static int failureCKCnt;
    private static int machineAccidentCKCnt;

    /**
     * Adiciona um evento à lista de eventos.
     * Verifica se o evento já existe antes de adicioná-lo.
     * Se o array de eventos estiver cheio, aumenta seu tamanho.
     *
     * @param newEvent O evento a ser adicionado.
     */
    public static void addEventExt(EventExt newEvent) {
        if (newEvent == null) {
            return;
        }
        if (eventCKCnt == 0) {
            eventChecker[0] = newEvent;
            eventCKCnt++;
            return;
        }
        for (int i = 0; i < eventCKCnt; i++) {
            if (eventChecker[i] == null) {
                break;
            }
            if (eventChecker[i].equals(newEvent)) {
                return;
            }
        }
        if (eventCKCnt == eventChecker.length) {
            eventChecker = evenExtAddSpace(Constantes.ARRAY_SIZE_MULTIPLIER);
        }
        eventChecker[eventCKCnt] = newEvent;
        eventCKCnt++;
    }

    /**
     * Obtém todos os eventos armazenados.
     *
     * @return Um array contendo todos os eventos.
     */
    public static EventExt[] getAllEvents() {
        return eventChecker;
    }

    /**
     * Converte uma string de prioridade em um enum EventPriority.
     *
     * @param priority A string representando a prioridade do evento.
     * @return O enum EventPriority correspondente à string fornecida. Se a string não corresponder a nenhuma prioridade conhecida, retorna EventPriority.IMMEDIATE.
     */
    public static EventPriority getPriority(String priority) {
        if (priority.equals(EventPriority.HIGH.toString())) {
            return EventPriority.HIGH;
        } else if (priority.equals(EventPriority.LOW.toString())) {
            return EventPriority.LOW;
        } else if (priority.equals(EventPriority.NORMAL.toString())) {
            return EventPriority.NORMAL;
        } else {
            return EventPriority.IMMEDIATE;
        }
    }

    public static MachineAccident[] getMachineAccident(){
        return machineAccidentCheker;
    }

    /**
     * Recupera um acidente com base em detalhes e mensagem de notificação.
     *
     * @param details             Os detalhes do acidente.
     * @param notificationMessage A mensagem de notificação do acidente.
     * @return O objeto AccidentImp correspondente se encontrado, ou null se nenhum acidente for encontrado.
     */
    public static AccidentImp getAccidentByData(String details, String notificationMessage) {
        if (details == null || notificationMessage == null) {
            return null;
        }

        for (int i = 0; i < accidentCKCnt; i++) {
            if (accidentCheker[i] == null) {
                break;
            }
            if (accidentCheker[i].getDetails().equals(details) && accidentCheker[i].getNotificationMessage().equals(notificationMessage)) {
                return accidentCheker[i];
            }
        }

        return null;

    }

    /**
     * Recupera uma falha com base em detalhes e mensagem de notificação.
     *
     * @param details             Os detalhes da falha.
     * @param notificationMessage A mensagem de notificação da falha.
     * @return O objeto FailureImp correspondente se encontrado, ou null se nenhuma falha for encontrada.
     */
    public static FailureImp getFailureByData(String details, String notificationMessage) {
        if (details == null || notificationMessage == null) {
            return null;
        }
        for (int i = 0; i < failureCKCnt; i++) {
            if (failureCheker[i] == null) {
                break;
            }
            if (failureCheker[i].getDetails().equals(details) && failureCheker[i].getNotificationMessage().equals(notificationMessage)) {
                return failureCheker[i];
            }
        }

        return null;

    }

    /**
     * Recupera um acidente de máquina com base em detalhes e mensagem de notificação.
     *
     * @param details             Os detalhes do acidente de máquina.
     * @param notificationMessage A mensagem de notificação do acidente de máquina.
     * @return O objeto MachineAccident correspondente se encontrado, ou null se nenhum acidente de máquina for encontrado.
     */
    public static MachineAccident getMachineAccidentByData(String details, String notificationMessage) {
        if (details == null || notificationMessage == null) {
            return null;
        }
        for (int i = 0; i < machineAccidentCKCnt; i++) {
            if (machineAccidentCheker[i] == null) {
                break;
            }
            if (machineAccidentCheker[i].getDetails().equals(details) && machineAccidentCheker[i].getNotificationMessage().equals(notificationMessage)) {
                return machineAccidentCheker[i];
            }
        }

        return null;

    }

    /**
     * Adiciona um incidente à lista de incidentes.
     * Verifica se o incidente já existe antes de adicioná-lo.
     * Se o array de incidentes estiver cheio, aumenta seu tamanho.
     *
     * @param incident O incidente a ser adicionado.
     */
    public static void addIncident(IncidentImp incident) {
        if (incident == null) {
            return;
        }
        if (incidentCKCnt == 0) {
            incidentCheker[0] = incident;
            incidentCKCnt++;
            return;
        }
        for (int i = 0; i < incidentCKCnt; i++) {
            if (incidentCheker[i] == null) {
                break;
            }
            if (incidentCheker[i].equals(incident)) {
                return;
            }
        }
        if (incidentCKCnt == incidentCheker.length) {
            incidentCheker = incidentCheckerAddSpace(Constantes.ARRAY_SIZE_MULTIPLIER);
        }
        incidentCheker[incidentCKCnt] = incident;
        incidentCKCnt++;
    }

    /**
     * Recupera um incidente com base em detalhes e mensagem de notificação.
     *
     * @param details             Os detalhes do incidente.
     * @param notificationMessage A mensagem de notificação do incidente.
     * @return O objeto IncidentImp correspondente se encontrado, ou null se nenhum incidente for encontrado.
     */
    public static IncidentImp getInsidentByData(String details, String notificationMessage) {
        if (details == null || notificationMessage == null) {
            return null;
        }
        for (int i = 0; i < incidentCKCnt; i++) {
            if (incidentCheker[i] == null) {
                break;
            }
            if (incidentCheker[i].getDetails().equals(details) && incidentCheker[i].getNotificationMessage().equals(notificationMessage)) {
                return incidentCheker[i];
            }
        }

        return null;

    }

    /**
     * Adiciona um acidente à lista de acidentes.
     * Verifica se o acidente já existe antes de adicioná-lo.
     * Se o array de acidentes estiver cheio, aumenta seu tamanho.
     *
     * @param Accident O acidente a ser adicionado.
     */
    public static void addAccident(AccidentImp Accident) {
        if (Accident == null) {
            return;
        }
        if (accidentCKCnt == 0) {
            accidentCheker[0] = Accident;
            accidentCKCnt++;
            return;
        }
        for (int i = 0; i < accidentCKCnt; i++) {
            if (accidentCheker[i] == null) {
                break;
            }
            if (accidentCheker[i].equals(Accident)) {
                return;
            }
        }
        if (accidentCKCnt == accidentCheker.length) {
            accidentCheker = accidentCheckerAddSpace(Constantes.ARRAY_SIZE_MULTIPLIER);
        }
        accidentCheker[accidentCKCnt] = Accident;
        accidentCKCnt++;
    }

    /**
     * Remove um evento da lista de eventos.
     *
     * @param event O evento a ser removido.
     */
    public static void removeEvent(EventExt event) {
        if (event == null) {
            return;
        }
        if (eventCKCnt == 0) {
            return;
        }
        for (int i = 0; i < eventCKCnt; i++) {
            if (eventChecker[i] == null) {
                break;
            }
            if (eventChecker[i].equals(event)) {
                for (int removeIndex = i; removeIndex < eventCKCnt; removeIndex++) {
                    eventChecker[removeIndex] = eventChecker[removeIndex + 1];
                }
                eventChecker[i] = null;
                eventCKCnt--;
                return;
            }
        }

    }

    /**
     * Adiciona uma falha à lista de falhas.
     * Verifica se a falha já existe antes de adicioná-la.
     * Se o array de falhas estiver cheio, aumenta seu tamanho.
     *
     * @param failure A falha a ser adicionada.
     */
    public static void addFailure(FailureImp failure) {
        if (failure == null) {
            return;
        }
        if (failureCKCnt == 0) {
            failureCheker[0] = failure;
            failureCKCnt++;
            return;
        }
        for (int i = 0; i < failureCKCnt; i++) {
            if (failureCheker[i] == null) {
                break;
            }
            if (failureCheker[i].equals(failure)) {
                return;
            }
        }
        if (failureCKCnt == failureCheker.length) {
            failureCheker = failureChekerAddSpace(Constantes.ARRAY_SIZE_MULTIPLIER);
        }
        failureCheker[failureCKCnt] = failure;
        failureCKCnt++;
    }

    /**
     * Adiciona um acidente de máquina à lista de acidentes de máquina.
     * Verifica se o acidente de máquina já existe antes de adicioná-lo.
     * Se o array de acidentes de máquina estiver cheio, aumenta seu tamanho.
     *
     * @param machineAccident O acidente de máquina a ser adicionado.
     */
    public static void addMachineAccident(MachineAccident machineAccident) {
        if (machineAccident == null) {
            return;
        }
        if (machineAccidentCKCnt == 0) {
            machineAccidentCheker[0] = machineAccident;
            machineAccidentCKCnt++;
            return;
        }
        for (int i = 0; i < machineAccidentCKCnt; i++) {
            if (machineAccidentCheker[i] == null) {
                break;
            }
            if (machineAccidentCheker[i].equals(machineAccident)) {
                return;
            }
        }
        if (machineAccidentCKCnt == machineAccidentCheker.length) {
            machineAccidentCheker = machineAccidentAddSpace(Constantes.ARRAY_SIZE_MULTIPLIER);
        }
        machineAccidentCheker[machineAccidentCKCnt] = machineAccident;
        machineAccidentCKCnt++;
    }


    /**
     * Aumenta o tamanho do array de incidentes.
     *
     * @param times O fator de multiplicação para aumentar o tamanho do array.
     * @return Um novo array de incidentes com o tamanho aumentado.
     */
    private static IncidentImp[] incidentCheckerAddSpace(int times) {
        int newSize = incidentCKCnt * times;
        IncidentImp[] temp = new IncidentImp[newSize];

        for (int i = 0; i < incidentCKCnt; i++) {
            temp[i] = incidentCheker[i];
        }

        return temp;
    }

    /**
     * Aumenta o tamanho do array de acidentes.
     *
     * @param times O fator de multiplicação para aumentar o tamanho do array.
     * @return Um novo array de acidentes com o tamanho aumentado.
     */
    private static AccidentImp[] accidentCheckerAddSpace(int times) {
        int newSize = accidentCKCnt * times;
        AccidentImp[] temp = new AccidentImp[newSize];

        for (int i = 0; i < accidentCKCnt; i++) {
            temp[i] = accidentCheker[i];
        }

        return temp;
    }

    /**
     * Aumenta o tamanho do array de falhas.
     *
     * @param times O fator de multiplicação para aumentar o tamanho do array.
     * @return Um novo array de falhas com o tamanho aumentado.
     */
    private static FailureImp[] failureChekerAddSpace(int times) {
        int newSize = failureCKCnt * times;
        FailureImp[] temp = new FailureImp[newSize];

        for (int i = 0; i < failureCKCnt; i++) {
            temp[i] = failureCheker[i];
        }

        return temp;
    }

    /**
     * Aumenta o tamanho do array de acidentes de máquina.
     *
     * @param times O fator de multiplicação para aumentar o tamanho do array.
     * @return Um novo array de acidentes de máquina com o tamanho aumentado.
     */
    private static MachineAccident[] machineAccidentAddSpace(int times) {
        int newSize = machineAccidentCKCnt * times;
        MachineAccident[] temp = new MachineAccident[newSize];

        for (int i = 0; i < machineAccidentCKCnt; i++) {
            temp[i] = machineAccidentCheker[i];
        }

        return temp;
    }

    /**
     * Aumenta o tamanho do array de eventos.
     *
     * @param times O fator de multiplicação para aumentar o tamanho do array.
     * @return Um novo array de eventos com o tamanho aumentado.
     */
    private static EventExt[] evenExtAddSpace(int times) {
        int newSize = eventCKCnt * times;
        EventExt[] temp = new EventExt[newSize];

        for (int i = 0; i < eventCKCnt; i++) {
            temp[i] = eventChecker[i];
        }

        return temp;
    }
}
