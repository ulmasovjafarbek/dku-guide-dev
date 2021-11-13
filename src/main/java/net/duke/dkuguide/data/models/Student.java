package net.duke.dkuguide.data.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(force = true)
//@RequiredArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Student implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Pattern(regexp = "^([a-z]*[1-9]*@(duke\\.edu))|([a-z]*\\.[a-z]*@(dukekunshan\\.edu\\.cn))$",
            message = "Use your duke.edu or dukekunshan.edu.cn email")
    private String email;

    private String firstname;

    private String lastname;

    private int classYear;

    private boolean enabled = false;

    private Date joinedAt;

    @ManyToOne(targetEntity = University.class)
    private University university;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> roles = new HashSet<>();

    @OneToMany(targetEntity = Review.class, fetch = FetchType.LAZY)
    private Set<Review> reviews = new HashSet<>();

    @ManyToMany(targetEntity = Professor.class)
    private Set<Professor> savedProfessors = new HashSet<>();

    public Student(String username, String password, String email, String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @PrePersist
    public void joinedAt() {
        this.joinedAt = new Date();
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void saveProfessor(Professor professor) {
        savedProfessors.add(professor);
    }

}
