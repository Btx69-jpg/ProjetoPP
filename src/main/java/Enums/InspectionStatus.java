package Enums;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public enum InspectionStatus {
    APROVED("Aprovado"),
    REPROVED("Reprovado");

    private final String status;

    InspectionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


    @Override
    public String toString() {
        return "InspectionStatus{" +
                "status='" + status + '\'' +
                '}';
    }
}
