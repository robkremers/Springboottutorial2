package com.study.springboottutorial2.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "Enrolment")
@Table(name = "enrolment")
public class Enrolment {

    /**
     * This is actually a composite key formed by student_id, course_id. See the class EnrolmentId.
     * This class solves the ManyToMany relation between Student and Course.
     */
    @EmbeddedId
    private EnrolmentId id;

    /**
     * MapsId links to EnrolmentId.studentId.
     * https://javadoc.io/static/javax.persistence/javax.persistence-api/2.2/index.html?javax/persistence/MapsId.html
     * <p>
     * The value element specifies the attribute within a composite key to which
     * the relationship attribute corresponds.
     * If the entity's primary key is of the same Java type as the primary key of
     * the entity referenced by the relationship, the value attribute is not specified.
     */
    @ManyToOne
    @MapsId(value = "studentId")
    @JoinColumn(
            name = "student_id",
            foreignKey = @ForeignKey(
                    name = "enrolment_student_id_fk"
            )
    )
    private Student student;

    // MapsId Links to EnrolmentId.courseId.
    @ManyToOne
    @MapsId(value = "courseId")
    @JoinColumn(
            name = "course_id",
            foreignKey = @ForeignKey(
                    name = "enrolment_course_id_fk"
            )
    )
    private Course course;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime createdAt;

    public Enrolment() {
    }

    public Enrolment(Student student, Course course, LocalDateTime createdAt) {
        this.student = student;
        this.course = course;
        this.createdAt = createdAt;
    }

    /**
     * In order to prevent
     * nested exception is org.hibernate.PropertyAccessException: Could not set field value [1] value by reflection
     * it is necessary to actively set EnrolmentId id (see StudentConfig).
     * In practice EnrolmentId should be autoset in the constructor defined above.
     *
     * @param id
     * @param student
     * @param course
     */
    public Enrolment(EnrolmentId id, Student student, Course course, LocalDateTime createdAt) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.createdAt = createdAt;
    }
}
