package model;

public class Pupil implements Comparable<Pupil>{
    private String surname;
    private String name;
    private int level;
    private int point;
    private int school;

    public Pupil(String surname, String name, int level, int point) {
        this.surname = surname;
        this.name = name;
        this.level = level;
        this.point = point;
    }

    public Pupil(String surname, String name, int school, int point, int level) {
        this.surname = surname;
        this.name = name;
        this.level = level;
        this.point = point;
        this.school = school;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getPoint() {
        return point;
    }

    @Override
    public String toString() {
        return "Pupil{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", point=" + point +
                ", school=" + school +
                '}';
    }

    @Override
    public int compareTo(Pupil o) {
        if (this.point == o.point) {
            if (this.surname.equals(o.surname)){
                return this.name.compareTo(o.name);
            }
            return this.surname.compareTo(o.surname);
        }
        return Integer.compare(o.point, this.point);
    }
}
