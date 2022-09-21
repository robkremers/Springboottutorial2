package com.study.springboottutorial2.repository;

import com.study.springboottutorial2.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Documentation:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference
 * <p>
 * Note:
 * The @Query is not really necessary because the setup is standard.
 * However it shows how it is possible to add a custom query to a method.
 * <p>
 * If a method has been declared, using "option + enter" allows you to create the corresponding JPQL query.
 * JPQL = Java Persistent Query Language.
 * <p>
 * In @Query use cmd + P to see the options.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.email = :email")
    Optional<Student> findStudentByEmail(@Param("email") String email);


    /** An example of a native query
     * This is not recommended because it is a format that is specific for the current database that is being used.
     * If in future it is decided to use another database platform these native queries may be rewritten.
    **/
    @Query(value = "select * from student where first_name like %?1%", nativeQuery = true)
    Optional<List<Student>> findStudentByFirstNameContaining(String firstName);

    @Query("select s from Student s where s.firstName = :firstName and s.lastName = :lastName")
    Optional<List<Student>> findStudentByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    /**
     * The following can also be set at interface level.
     *
     * @param firstName
     * @param localDate
     * @return
     */
    @Transactional(
            readOnly = true
    )
    @Query("select s from Student s where s.firstName = :firstName and s.dateOfBirth = :localDate")
    Optional<List<Student>> findStudentByFirstNameEqualsAndDateOfBirthEquals(@Param("firstName") String firstName, @Param("localDate") LocalDate localDate);

    @Query("select s from Student s where s.firstName = ?1")
    Optional<List<Student>> findStudentByFirstNameEquals(String firstName);

    @Query("select s from Student s where s.dateOfBirth > :dateOfBirthYear")
    Optional<List<Student>> findStudentByDateOfBirthAfter(@Param("dateOfBirthYear") LocalDate dateOfBirthYear);

    /**
     * The background of this is required reading!
     * Resource:
     * https://docs.spring.io/spring-data/jpa/docs/current/api/index.html?org/springframework/data/jpa/repository/JpaRepository.html
     * So all annotations used in this interface are (by nature) spring annotations.
     * But they base on JPA, for this see the definition of the entity Student.
     * --> You have to understand both!
     *
     * A 'delete' action cannot only use @Query.
     * The annotation @Modify is required to indicate
     *
     * ###
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Modifying
    @Query("delete from Student s where s.id = :id")
    int deleteStudentById(@Param("id") Long id);
}