package myProject;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    private int number;

    private int students;

    /*
        One course must have many assignments. Hence One-to-many mapping.
        @JoinColumn annotation is used to join assignment with course id.
        Spring will automatically add the column called "assignment_id" to assignment table,
        where assignment's id and Course's number are associated.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="assignment_id")
    private List<Assignment> assignments;

    public int getNumber() {
        return number;
    }

    public int getStudents() {
        return students;
    }

    public List<Assignment> getAssignment() {
        return assignments;
    }
}
