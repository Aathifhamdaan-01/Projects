package aathif;

public class StudentRecord {

    String rollNo;
    String name;
    int marks;

    StudentRecord(String rollNo, String name, int marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return rollNo + "," + name + "," + marks;
    }
}
