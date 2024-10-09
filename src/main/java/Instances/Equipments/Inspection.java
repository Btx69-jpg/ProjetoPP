package Instances.Equipments;

import Enums.InspectionStatus;

import java.time.LocalDate;
import java.util.Objects;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class Inspection {
    private LocalDate inspectionEndDate;
    private InspectionStatus status;
    private String details;

    public Inspection(LocalDate inspectionEndDate, InspectionStatus status) {
        this.inspectionEndDate = inspectionEndDate;
    }

    public LocalDate getInspectionEndDate() {
        return inspectionEndDate;
    }

    public void setInspectionEndDate(LocalDate inspectionEndDate) {
        this.inspectionEndDate = inspectionEndDate;
    }

    public InspectionStatus getStatus() {
        return status;
    }

    public void setStatus(InspectionStatus status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inspection)) return false;
        Inspection that = (Inspection) o;
        return Objects.equals(getInspectionEndDate(), that.getInspectionEndDate()) && getStatus() == that.getStatus() && Objects.equals(getDetails(), that.getDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInspectionEndDate(), getStatus(), getDetails());
    }

    @Override
    public String toString() {
        return "inspection{" +
                "inspectionEndDate=" + inspectionEndDate +
                ", status=" + status +
                ", details='" + details + '\'' +
                '}';
    }
}
