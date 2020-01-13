package com.upgrad.quora.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "users")

@SqlResultSetMapping(name="deleteUserResult", columns = { @ColumnResult(name = "count")})
@NamedNativeQueries({
        @NamedNativeQuery(
                name    =   "deleteUserById",
                query   =   "DELETE FROM users WHERE id = ?1",resultSetMapping = "deleteUserResult"
        )
})
@NamedQueries(
        {
                @NamedQuery(name = "userByUuid", query = "select u from Users u where u.uuid = :uuid"),
                @NamedQuery(name = "userByEmail", query = "select u from Users u where u.email =:email"),
                @NamedQuery(name = "userByUserName", query = "select u from Users u where u.username =:userName"),
                @NamedQuery(name = "userById", query = "select u from Users u where u.id =:id")


        }
)
public class Users implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int id;

    @Column(name = "uuid")
    @NotNull
    @Size(max = 200)
    String uuid;
    @Column(name = "firstname")
    @NotNull
    @Size(max = 30)
    String firstname;
    @Column(name = "lastname")
    @NotNull
    @Size(max = 30)
    String lastname;

    @Column(name = "username")
    @NotNull
    @Size(max = 30)
    String username;
    @Column(name = "email")
    @NotNull
    @Size(max = 50)
    String email;
    @Column(name = "password")
    @NotNull
    @Size(max = 255)
    String password;
    @Column(name = "salt")
    @NotNull
    @Size(max = 200)
    String salt;
    @Column(name = "country")
    @Size(max = 30)
    String country;
    @Column(name = "aboutme")
    @Size(max = 50)
    String aboutme;
    @Column(name = "dob")
    @Size(max = 30)
    String dob;
    @Column(name = "role")
    @Size(max = 30)
    String role;
    @Column(name = "contactnumber")
    @Size(max = 30)
    String contactnumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
