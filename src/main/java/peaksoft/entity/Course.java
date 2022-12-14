package peaksoft.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
public class Course {

    @Id
    @SequenceGenerator(name = "course_gen", sequenceName = "course_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_gen")
    private Long id;
    @Column(name = "course_name")
    private String courseName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "data_of_start")
    private LocalDate dateOfStart;
    private String duration;
    @Column(length = 100000)
    private String image;
    private String description;

    @ManyToOne(cascade = {MERGE, DETACH, REFRESH},fetch = LAZY)
    private Company company;


    @ManyToMany(cascade = {DETACH, MERGE,
            PERSIST, REFRESH}, mappedBy = "courses")
    private List<Instructor> instructors;

    @OneToMany(cascade = {ALL}, mappedBy = "course")
    private List<Student> students;

    @ManyToMany(cascade = {DETACH, REFRESH, MERGE, REMOVE}, fetch = LAZY, mappedBy = "courses")
    private List<Group> groups;

    @OneToMany(cascade = ALL, mappedBy = "course")
    private List<Lesson> lessons;

    public Course(String courseName, LocalDate dateOfStart, String duration, String image, String description) {
        this.courseName = courseName;
        this.dateOfStart = dateOfStart;
        this.duration = duration;
        this.image = image;
        this.description = description;
    }

    public void addInstructor(Instructor instructor) {
        if (instructors==null){
            instructors=new ArrayList<>();
        }
        instructors.add(instructor);
    }
    public void addGroup(Group group) {
        if (groups == null) groups = new ArrayList<>();
        groups.add(group);
    }
    public void addStudent(Student student) {
        if(students==null){
            students=new ArrayList<>();
        }
        students.add(student);
    }

    public void addLesson(Lesson lesson) {
        if (lessons==null){
            lessons=new ArrayList<>();
        }
        lessons.add(lesson);
    }

}
