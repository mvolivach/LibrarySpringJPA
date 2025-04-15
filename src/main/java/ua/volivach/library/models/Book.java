package ua.volivach.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Author should not be empty")
    @Column(name = "author")
    private String author;

    @Min(value = 0, message = "Year should be greater than 0")
    @Max(value = 2025, message = "Birth year should be not greater than 2025")
    @Column(name = "year")
    private int year;

    @Column(name = "time_own")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date timeOwn;

    @Transient
    private boolean overdue;

    public boolean isOverdue() {
        if (this.timeOwn == null) return false;

        Instant timeOwnInstant = this.timeOwn.toInstant();
        Instant deadline = timeOwnInstant.plus(Duration.ofDays(10));

        return Instant.now().isAfter(deadline);
    }


    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book(String name, String author, int year, Person owner) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.owner = owner;
    }

    public Book(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getTimeOwn() {
        return timeOwn;
    }

    public void setTimeOwn(Date timeOwn) {
        this.timeOwn = timeOwn;
    }
}
