# Overview of the functionality

[[_TOC_]]

# Introduction

This project has been setup as a vehicle for the course  
- Amigoscode: Spring Boot Tutorial | Full Course [2021] [NEW]
  - https://www.youtube.com/watch?v=9SGDpanrc8U

This Spring Boot Tutorial will teach how to get started with Spring Boot and Java.

# Implementation

The application is built around an entity **student**.
- Controller
  - GET
  - POST
  - PUT
  - DELETE
- Dto
- Service
- StudentRepository extends JpaRepository<Student, Long>
- Config
  - Adding some data into the database
- application.properties
  - server.port = 9090
  - Connectivity for a postgresql database

# The use of Postgresql as backend data storage

Before execution of the application the postgresql environment
needs to be online.  
Currently PostgreSQL 13 is installed and can be started
via the Launchpad.

## Basic instructions for working with postgresql from the command line:
```
$ psql

rkremers=# help
You are using psql, the command-line interface to PostgreSQL.
Type:  \copyright for distribution terms
       \h for help with SQL commands
       \? for help with psql commands
       \g or terminate with semicolon to execute query
       \q to quit

rkremers=# create database student;
CREATE DATABASE

rkremers=# \du
                                   List of roles
 Role name |                         Attributes                         | Member of 
-----------+------------------------------------------------------------+-----------
 postgres  | Superuser, Create role, Create DB, Replication, Bypass RLS | {}
 rkremers  | Superuser, Create role, Create DB                          | {}

rkremers=# GRANT ALL PRIVILEGES ON DATABASE "student" to rkremers;
GRANT

Also give it to Postgres:

rkremers=# GRANT ALL PRIVILEGES ON DATABASE "student" to postgres;;
GRANT

rkremers=# \l
                                  List of databases
   Name    |  Owner   | Encoding |   Collate   |    Ctype    |   Access privileges   
-----------+----------+----------+-------------+-------------+-----------------------
 postgres  | postgres | UTF8     | en_US.UTF-8 | en_US.UTF-8 | 
 rkremers  | rkremers | UTF8     | en_US.UTF-8 | en_US.UTF-8 | 
 student   | rkremers | UTF8     | en_US.UTF-8 | en_US.UTF-8 | 
 template0 | postgres | UTF8     | en_US.UTF-8 | en_US.UTF-8 | =c/postgres          +
           |          |          |             |             | postgres=CTc/postgres
 template1 | postgres | UTF8     | en_US.UTF-8 | en_US.UTF-8 | =c/postgres          +
           |          |          |             |             | postgres=CTc/postgres
rkremers=# \c student
You are now connected to database "student" as user "rkremers".

student=# \d student
                 Table "public.student"
    Column     |  Type  | Collation | Nullable | Default 
---------------+--------+-----------+----------+---------
 id            | bigint |           | not null | 
 date_of_birth | date   |           | not null | 
 email         | text   |           | not null | 
 first_name    | text   |           | not null | 
 last_name     | text   |           | not null | 
Indexes:
    "student_pkey" PRIMARY KEY, btree (id)
    "student_email" UNIQUE CONSTRAINT, btree (email)

student=# select * from student;
 id | date_of_birth |         email          |  name  
----+---------------+------------------------+--------
  1 | 2004-03-07    | alex@hotmail.com       | Alex
  2 | 2000-01-05    | mariam.jamal@gmail.com | Mariam
(2 rows)
```

## Accessing the database via IntelliJ

In the database overview a connection has been made with the postgresql database student.  
It was necessary to use a database account with password so I have created:
- username: rkremers
- password: rkremers

The database connection is visible as student@localhost.  
This connection gives access to the postgres database student.
The table STUDENT is present under the schema PUBLIC.  
Right-click on student@localhost and open a query-console.  
Here queries can be executed: 
- select * from student;

The result will be visible in the console.

Note:
- Sometimes you need to refresh the Database overview several times in order to
  see the actual table structure.

# Execution

- Execute by starting the application.  
- URL: http://localhost:9090/api/v1/student
- Result:
```json
[
  {
    "id": 1,
    "firstName": "Alex",
    "lastName": "Barns",
    "email": "alex@hotmail.com",
    "dateOfBirth": "2004-03-07",
    "age": 17
  },
  {
    "id": 2,
    "firstName": "Mariam",
    "lastName": "Jamal",
    "email": "mariam.jamal@gmail.com",
    "dateOfBirth": "2000-01-05",
    "age": 21
  }
]
```

Other commands, as defined in class StudentController, can be executed in Postman
or via annotations in the StudentController.

# Bi-directional associations

https://stackoverflow.com/questions/30464782/how-to-maintain-bi-directional-relationships-with-spring-data-rest-and-jpa

1 rule of bi-directional associations: don't use them… :)

In the case for a One-To-One relationship everything works fine in the 
Java code, but the the REST call returns an everlasting loop
(until the cache is full).

So don't do this.

If necessary use DTO's for the REST exposure.

# Database Transaction

A database transaction symbolizes a unit of work within a database management system
against a database, and treated in a coherent and reliable way independent of other transactions.

The main goal of a transaction is to provide **ACID** characteristics to ensure the consistency and
vality of your data.

- Atomicity
  - All or Nothing principle  
    Either all operations performed within the transaction get executed of none of them.  
    That means if you commit the transaction successfully you can be sure that all 
    transactions have been performed. It also enables abortion of a transaction and roll back
    all operations if an error occurs.
- Consistency
  - Ensures that your transaction takes a system from one consistent state to another consistent
    state.
    That means that either alle operations were rolled back and the data was set back to the 
    state your started with or the changed data passed all consistency checks.  
    In a relational database that means that the modified data needs to pass all constraint
    checks, like foreign key or unique constraints, defined in your database.
- Isolation
  - Changes performed within a transaction are not visible to any other transactions until
    you commit them successfully.
- Durability
  - Ensures that your committed changes are persisted.

https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#transactions

```java
Example 108. Using a facade to define transactions for multiple repository calls

@Service
public class UserManagementImpl implements UserManagement {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  public UserManagementImpl(UserRepository userRepository,
    RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  @Transactional
  public void addRoleToAllUsers(String roleName) {

    Role role = roleRepository.findByName(roleName);

    for (User user : userRepository.findAll()) {
      user.addRole(role);
      userRepository.save(user);
    }
  }
}

```

Possibly dive into locking for further study.


# Resources

- Amigoscode: Spring Boot Tutorial | Full Course [2021] [NEW]
  - https://www.youtube.com/watch?v=9SGDpanrc8U
  - Uses jdk14.
- https://enterprise.arcgis.com/en/server/10.3/cloud/amazon/change-default-database-passwords-on-linux.htm
- https://www.postgresql.org/docs/13/sql-grant.html
- https://www.jetbrains.com/help/idea/cannot-find-a-database-object-in-the-database-tree-view.html
- https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#preface
- Amigoscode: Spring Boot Tutorial | Spring Data JPA | 2021
  - https://www.youtube.com/watch?v=8SGI_XS5OPw
  - Uses jdk15.
- https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference
  - Example 57. Query creation from method names
  - Study this reference of Spring JPA.
- https://javadoc.io/static/javax.persistence/javax.persistence-api/2.2/index.html
- SpringDeveloper: A Spring Data’s Guide to Persistence
  - https://www.youtube.com/watch?v=gmDMkFA03UM
  - Study this tutorial.
- https://github.com/DiUS/java-faker
- https://www.baeldung.com/java-faker
- https://howtodoinjava.com/spring-boot2/pagination-sorting-example/
- https://stackoverflow.com/questions/30464782/how-to-maintain-bi-directional-relationships-with-spring-data-rest-and-jpa
- https://stackoverflow.com/questions/5031614/the-jpa-hashcode-equals-dilemma?rq=1
  - Contains ideas about the reason for fixing the problem
  that id's in Embeddable classes should be set before saving the entities
    (equals() and hashCode() will crash).
  - The problem is solved manually in this course; see classes EnrolmentId / StudentConfig.
- https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#transactions
- https://www.baeldung.com/java-bean-validation-not-null-empty-blank
- https://www.baeldung.com/javax-validation
- https://howtodoinjava.com/spring-boot2/testing/datajpatest-annotation/