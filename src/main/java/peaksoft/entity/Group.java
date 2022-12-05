package peaksoft.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@Table(name = "groups")
@NoArgsConstructor
public class Group {

    @Id
    @SequenceGenerator(name = "group_seq", sequenceName = "group_seq", allocationSize = 1)
    @GeneratedValue(generator = "group_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "groupName")
    private String groupName;

    @Column(name = "dataOfStart")
    private String dataOfStart;

    @Column(name ="image")
    private String image;

    @ManyToMany(cascade = {DETACH, MERGE, REFRESH}, fetch = LAZY)
    private List<Course> courses;

    @OneToMany(cascade = {DETACH, MERGE, REFRESH, REMOVE}, fetch = LAZY, mappedBy = "group")
    private List<Student> students;

    public Group(String groupName, String dataOfStart, String image) {
        this.groupName = groupName;
        this.dataOfStart = dataOfStart;
        this.image = image;
    }

    public void addCourse(Course course) {
        if (courses == null) courses = new ArrayList<>();
        courses.add(course);
    }

    public void addStudent(Student student) {
        if (students == null) students = new ArrayList<>();
        students.add(student);
    }

}
