package Instances.Management;

import Classes.Constantes;
import Classes.Control.ControlClass;
import Classes.Control.ControlEvents;
import Classes.Logger.ExceptionLogger;
import Instances.EventETC.EventExt;
import Instances.EventETC.NotifierImp;
import estgconstroi.Event;
import estgconstroi.EventManager;
import estgconstroi.Notifier;
import estgconstroi.enums.EventPriority;
import estgconstroi.exceptions.EventManagerException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;

/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */
public class EventManagerImp implements EventManager {
    private NotifierImp[] notifiers = new NotifierImp[Constantes.INITIAL_ARRAY_SIZE];
    private EventExt[] events = new EventExt[Constantes.INITIAL_ARRAY_SIZE];
    private int eventosCount;
    private int notifierCount;

    public EventManagerImp() {
        notifiers[0] = new NotifierImp(Constantes.INSURANCE_REPORT_ID);
        notifierCount++;
        addNotifiersLM();

        ControlClass.addEventManager(this);
    }

    @Override
    public void addNotifier(Notifier notifier) throws EventManagerException {
        if (notifier == null) {
            throw new EventManagerException("Não é possivel adicionar um destinatario nulo");
        }
        if (this.notifierCount != 0) {
            for (int i = 0; i < this.notifierCount; i++) {
                if (this.notifiers[i] == null) {
                    break;
                }
                if (notifier.equals(this.notifiers[i])) {
                    throw new EventManagerException("Já existe um destinatario com esse nome");
                }
            }
        }

        if (notifierCount == notifiers.length) {
            notifierAddSpace(Constantes.ARRAY_SIZE_MULTIPLIER);
        }
        this.notifiers[notifierCount] = (NotifierImp) notifier;
        this.notifierCount++;

    }

    public Notifier[] getNotifiers() {
        return notifiers;
    }

    @Override
    public void removeNotifier(Notifier notifier) throws EventManagerException {

        if (notifier == null) {
            throw new EventManagerException("Não é possivel remover um destinatario nulo");
        }

        boolean found = false;
        for (int i = 0; i < this.notifierCount; i++) {
            if (this.notifiers[i] == null) {
                break;
            }
            if (this.notifiers[i] == notifier) {
                for (int j = i; j < this.notifierCount - 1; j++) {
                    this.notifiers[j] = this.notifiers[j + 1];
                }
                this.notifiers[this.notifierCount - 1] = null;
                this.notifierCount--;
                found = true;
                break;
            }
        }

        if (!found) {
            throw new EventManagerException("O destinatario não existe no gestor de eventos.");
        }
        ControlClass.removeNotifier((NotifierImp) notifier);
    }

    @Override
    public void reportEvent(Event event) throws EventManagerException {
        addNotifiersLM();
        boolean notified = false;
        if (event == null) {
            throw new EventManagerException("Não é possivel reportar um evento nulo");
        }

        if (this.eventosCount != 0) {
            for (int thisEvents = 0; thisEvents < this.eventosCount; thisEvents++) {
                if (this.events[thisEvents] == null) {
                    break;
                }
                if (this.events[thisEvents].equals(event)) {
                    throw new EventManagerException("O evento ja foi reportado.");
                }
            }
        }

        for (int i = 0; i < this.notifierCount; i++) {
            if (this.notifiers[i] == null) {
                break;
            }

            notified = this.notifiers[i].notify(event);

            if (!notified) {
                throw new EventManagerException("Não foi possivel agora notificar o destinatario: " + this.notifiers[i].getNotifier());
            }

        }
        if (eventosCount == events.length) {
            eventAddSpace(Constantes.ARRAY_SIZE_MULTIPLIER);
        }
        this.events[eventosCount] = (EventExt) event;
        eventosCount++;
    }

    @Override
    public void removeAllEvents() {
        for (int notifierCnt = 0; notifierCnt < notifierCount; notifierCnt++) {
            try {
                this.notifiers[notifierCnt].removeAllEvents();
            } catch (IOException | InterruptedException e) {
                ExceptionLogger.logException(e);
                throw new RuntimeException(e);
            }
        }
        // Clear the events array
        for (int i = 0; i < this.eventosCount; i++) {
            ControlEvents.removeEvent(this.events[i]);
            this.events[i] = null;
        }

        // Reset the count of events
        this.eventosCount = 0;


    }

    @Override
    public void removeEvent(Event event) throws EventManagerException {
        boolean found = false;
        int removeIndex = -1;

        // Find the index of the event
        for (int i = 0; i < this.eventosCount; i++) {
            if (this.events[i].equals(event)) {
                found = true;
                removeIndex = i;
                break;
            }
        }

        if (!found) {
            throw new EventManagerException("Event not found.");
        }

        // Remove the event from notifiers
        for (int notifierCnt = 0; notifierCnt < notifierCount; notifierCnt++) {
            try {

                this.notifiers[notifierCnt].removeEvent(event);
            } catch (IOException | InterruptedException | ParseException e) {
                ExceptionLogger.logException(e);
                throw new RuntimeException(e);
            }
        }

        // Rearrange the events array
        for (int i = removeIndex; i < this.eventosCount - 1; i++) {
            this.events[i] = this.events[i + 1];

        }

        ControlEvents.removeEvent((EventExt) event);
        // Set the last element to null and decrement the event count
        this.events[this.eventosCount - 1] = null;
        this.eventosCount--;

    }

    @Override
    public Event[] getEvent(EventPriority eventPriority) {
        Event[] events = new Event[eventosCount];
        int eventIndex = 0;
        if (eventPriority == null) {
            return null;
        }
        if (this.eventosCount == 0) {
            return null;
        }
        for (int i = 0; i < this.eventosCount; i++) {
            if (this.events[i] == null) {
                break;
            }
            if (this.events[i].getPriority() == eventPriority) {
                events[eventIndex] = this.events[i];
                eventIndex++;
            }
        }

        if (eventIndex == 0) {
            return null;
        }

        return events;

    }

    @Override
    public Event[] getEvent(Class aClass) {
        if (this.eventosCount == 0) {
            return null;
        }
        return new Event[0];
    }

    @Override
    public Event[] getEvent(LocalDate localDate) {
        Event[] events = new Event[eventosCount];
        int eventIndex = 0;
        if (localDate == null) {
            return null;
        }
        for (int i = 0; i < this.eventosCount; i++) {
            if (this.events[i] == null) {
                break;
            }
            if (this.events[i].getDate().equals(localDate)) {
                events[eventIndex] = this.events[i];
                eventIndex++;
            }
        }
        return events;
    }

    @Override
    public Event[] getEvent(LocalDate from, LocalDate to) {
        Event[] events = new Event[eventosCount];
        int eventIndex = 0;
        if (from == null || to == null) {
            return null;
        }
        for (int i = 0; i < this.eventosCount; i++) {
            if (this.events[i] == null) {
                break;
            }
            if (this.events[i].getDate().isAfter(from) && this.events[i].getDate().isBefore(to)) {
                events[eventIndex] = this.events[i];
                eventIndex++;
            }
        }
        return events;
    }

    public Event[] getAllEvents() {
        return this.events;
    }


    private void addNotifiersLM() {
        if (ControlClass.getTotalNotifierCnt() == 0) {
            return;
        }

        NotifierImp[] LM = ControlClass.getNotifierManagerLeader();

        if (LM == null) {
            return;
        }

        removeExistingNotifiers(LM);

        for (int LMIndex = 0; LMIndex < ControlClass.getTotalNotifierCnt(); LMIndex++) {
            NotifierImp notifier = LM[LMIndex];
            if (notifier == null) {
                continue;
            }

            boolean alreadyExists = false;
            for (int thisIndex = 0; thisIndex < notifierCount; thisIndex++) {
                if (this.notifiers[thisIndex] != null && this.notifiers[thisIndex].equals(notifier)) {
                    alreadyExists = true;
                    break;
                }
            }

            if (!alreadyExists) {
                if (notifierCount >= this.notifiers.length) {
                    notifierAddSpace(Constantes.ARRAY_SIZE_MULTIPLIER);
                }
                this.notifiers[notifierCount] = notifier;
                notifierCount++;
            }
        }
    }

    private void removeExistingNotifiers(NotifierImp[] LM) {
        boolean Break = false;

        for (int thisNIndex = 0; thisNIndex < this.notifierCount; thisNIndex++) {
            if (this.notifiers[thisNIndex] == null || Break) {
                break;
            }
            for (int LMIndex = 0; LMIndex < ControlClass.getTotalNotifierCnt(); LMIndex++) {
                if (LM[LMIndex] == null) {
                    Break = true;
                    break;
                }
                if (this.notifiers[thisNIndex].equals(LM[LMIndex])) {
                    // Shift elements to the left to remove the notifier
                    for (int shiftIndex = LMIndex; shiftIndex < ControlClass.getTotalNotifierCnt() - 1; shiftIndex++) {
                        LM[shiftIndex] = LM[shiftIndex + 1];
                    }
                    LM[ControlClass.getTotalNotifierCnt() - 1] = null; // Set the last element to null
                    LMIndex--; // Decrement index to recheck the current position
                }
            }
        }

    }

    private void notifierAddSpace(int times) {
        NotifierImp[] temp = new NotifierImp[notifierCount * times];

        for (int i = 0; i < this.notifierCount; i++) {
            temp[i] = this.notifiers[i];
        }

        this.notifiers = temp;
    }

    private void eventAddSpace(int times) {
        EventExt[] temp = new EventExt[eventosCount * times];

        for (int i = 0; i < this.eventosCount; i++) {
            temp[i] = this.events[i];
        }

        this.events = temp;
    }
}
