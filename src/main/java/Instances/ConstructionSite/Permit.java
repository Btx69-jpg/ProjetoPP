package Instances.ConstructionSite;

import java.time.LocalDate;
import java.util.Objects;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class Permit {
    private String identifier;
    private LocalDate ExpirationDate;

    public Permit(String identifier, LocalDate ExpirationDate) {
        this.identifier = identifier;
        this.ExpirationDate = ExpirationDate;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public LocalDate getExpirationDate() {
        return this.ExpirationDate;
    }

    public void setExpirationDate(LocalDate ExpirationDate) {
        this.ExpirationDate = ExpirationDate;
    }

    public boolean isValid() {
        return !(LocalDate.now().isAfter(ExpirationDate));
    }

    public String toString() {
        return "Permit{" + "identifier='" + identifier + '\'' + ", ExpirationDate=" + ExpirationDate + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Permit)) return false;
        Permit permit = (Permit) o;
        return Objects.equals(getIdentifier(), permit.getIdentifier()) && Objects.equals(getExpirationDate(), permit.getExpirationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier(), getExpirationDate());
    }
}
