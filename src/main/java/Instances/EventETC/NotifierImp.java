package Instances.EventETC;

import Classes.Constantes;
import Classes.Control.ControlClass;
import Classes.Logger.ExceptionLogger;
import estgconstroi.Employee;
import estgconstroi.Event;
import estgconstroi.InsuranceReporter;
import estgconstroi.Notifier;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class NotifierImp implements Notifier {
    //quem desejamos notificar ALTERAR PARA UUID
    //private EmployeeExt notifier;
    private final String notifierId;
    //eventos que não foram possivel comunicar no momento caso o se deseje notificar a apiWeb, caso seja um funcionario, são todos os eventos que recebeu
    private EventExt[] eventos = new EventExt[Constantes.INITIAL_ARRAY_SIZE];
    private int eventosCount = 0;

    public NotifierImp(String notifier) {
        this.notifierId = notifier;
    }


    //metodo para verificar se um evento foi notificado
    @Override
    public boolean notify(Event event) {
        EventExt[] tonotify = new EventExt[eventosCount + 1];


        boolean exception = false;
        //se o evento for nulo, o user foi notificado(não ha o que notificar)
        if (event == null) {
            return true;
        }
        if (this.notifierId.equals(Constantes.INSURANCE_REPORT_ID)) {
            /*caso se esteja a tentar notificar a seguradora, faz se o casting do evento para EventExt
            transforma se em uma string json
            envia a seguradora
            caso não seja possivel enviar a seguradora,
            salva se o evento no array de eventos, para notificar posteriormente
            caso va ser feita alguma notificação posterior e haja alguma não enviada, tenta novamente enviar junto com a nova

            * */
            if (eventosCount != 0) {
                for (int i = 0; i < eventosCount; i++) {
                    tonotify[i] = eventos[i];
                }
                tonotify[eventosCount] = (EventExt) event;
            } else {
                tonotify[0] = (EventExt) event;
            }

            try {
                for (EventExt eventExt : tonotify) {
                    if (eventExt == null) {
                        break;
                    }
                    InsuranceReporter.addEvent(eventExt.toString());
                }
            } catch (IOException | InterruptedException e) {
                ExceptionLogger.logException(e);
                exception = true;
                throw new RuntimeException(e);
            } finally {
                if (exception) {
                    if (eventosCount == this.eventos.length) {
                        eventsAddSpace(Constantes.ARRAY_SIZE_MULTIPLIER);
                    }
                    eventos[eventosCount] = (EventExt) event;
                    eventosCount++;
                }
            }
            if (eventosCount != 0) {
                return eventos[eventosCount - 1] != event;
            }
            return true;
        }

        // verdadeiro caso ao notificar algo adicionar a notificação a quem reportou
        if (event.getReporter().getUUID().equals(this.notifierId)) {
            return true;
        }

        for (int i = 0; i < eventosCount; i++) {
            if (event.equals(this.eventos[i])) {
                return true;
            }
        }

        if (eventosCount == this.eventos.length) {
            eventsAddSpace(Constantes.ARRAY_SIZE_MULTIPLIER);
        }

        addEventos(event);

        return true;
    }

    public void removeAllEvents() throws IOException, InterruptedException {
        if (this.notifierId.equals(Constantes.INSURANCE_REPORT_ID)) {
            InsuranceReporter.resetEvents(Constantes.GROUPKEY, Constantes.GROUPNAME);
        }

        this.eventos = null;

    }

    public void removeEvent(Event event) throws IOException, InterruptedException, ParseException {
        EventExt[] temp;
        if (event == null) {
            return;
        }
        if (this.notifierId.equals(Constantes.INSURANCE_REPORT_ID)) {
            temp = EventExt.fromJson(InsuranceReporter.getEvents(Constantes.GROUPKEY, Constantes.GROUPNAME));

            for (int tempIndex = 0; tempIndex < temp.length; tempIndex++) {
                if (temp[tempIndex] == null) {
                    break;
                }
                if (temp[tempIndex].equals(event)) {
                    InsuranceReporter.resetEvents(Constantes.GROUPKEY, Constantes.GROUPNAME);
                    for (int j = tempIndex; j < temp.length - 1; j++) {
                        temp[j] = temp[j + 1];
                    }
                    temp[temp.length - 1] = null;
                    this.eventosCount--;

                    for (EventExt eventExt : temp) {
                        InsuranceReporter.addEvent(eventExt.toString());
                    }

                    break;
                }
            }

        }

        for (int i = 0; i < eventosCount; i++) {
            if (event.equals(this.eventos[i])) {
                this.eventos[i] = null;
                eventosCount--;
                break;
            }
        }
    }

    public Event[] getEvents() {
        if (this.eventos == null) {
            return null;
        }

        Event[] copiedEvents = new Event[eventos.length];

        for (int i = 0; i < eventos.length; i++) {
            copiedEvents[i] = eventos[i].deepCopy();
        }
        return copiedEvents;
    }

    public void addEventos(Event evento) {
        if (eventosCount == this.eventos.length) {
            eventsAddSpace(Constantes.ARRAY_SIZE_MULTIPLIER);
        }

        this.eventos[eventosCount] = (EventExt) evento;
        eventosCount++;

    }

    private void eventsAddSpace(int times) {
        EventExt[] temp = new EventExt[eventosCount * times];

        for (int i = 0; i < this.eventosCount; i++) {
            temp[i] = this.eventos[i];
        }

        this.eventos = temp;
    }

    public void removeEventos(Event evento) {
        for (int i = 0; i < eventosCount; i++) {
            if (eventos[i].equals(evento)) {
                eventos[i] = null;
                eventosCount--;
                break;
            }
        }
    }

    public int getEventosCount() {
        return eventosCount;
    }

    public Employee getNotifier() {
        return ControlClass.getEmployeeByUUid(this.notifierId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotifierImp)) return false;
        NotifierImp that = (NotifierImp) o;
        return getEventosCount() == that.getEventosCount() && this.notifierId.equals(that.notifierId) && Objects.deepEquals(eventos, that.eventos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notifierId, Arrays.hashCode(eventos), getEventosCount());
    }

    @Override
    public String toString() {

        StringBuilder eventsString = new StringBuilder("[");
        for (Event event : this.eventos) {
            if (event != null) {
                if (eventsString.length() > 1) {
                    eventsString.append(", ");
                }
                eventsString.append(event);
            }
        }
        eventsString.append("]");

        return "NotifierImp{" +
                "Employee='" + getNotifier() + '\'' +
                ", eventos=" + eventsString +
                ", eventosCount=" + eventosCount +
                '}';
    }

}
