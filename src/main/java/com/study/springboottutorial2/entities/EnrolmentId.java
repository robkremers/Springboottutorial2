package com.study.springboottutorial2.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Embeddable
 *
 * Specifies a class whose instances are stored as an intrinsic part of an owning entity
 * and share the identity of the entity.
 * Each of the persistent properties or fields of the embedded object is mapped to the
 * database table for the entity.
 * In this case EnrolmentId is owned by Enrolment
 */
@Getter
@Setter
@Embeddable
public class EnrolmentId implements Serializable {

    @Column( name = "student_id")
    private Long studentId;

    @Column( name = "course_id")
    private Long courseId;

    public EnrolmentId(){
    }

    public EnrolmentId(Long studentId, Long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrolmentId that = (EnrolmentId) o;
        return studentId.equals(that.studentId) && courseId.equals(that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }
}
