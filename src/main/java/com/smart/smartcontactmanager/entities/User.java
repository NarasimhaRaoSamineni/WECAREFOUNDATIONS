package com.smart.smartcontactmanager.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="USER")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", enabled=" + enabled +
                ", imageurl='" + imageurl + '\'' +
                ", about='" + about + '\'' +
                ", contacts=" + contacts +
                '}';
    }

    @NotBlank(message="Name field is required !!")
    @Size(min=2,max=20,message="min 2 and max 20 characters are allowed !!")
    private String name;
//    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message = "Invalid email ):")
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",message="Minimum eight characters, at least one letter, one number and one special character")
    private String password;
    private String role;
    private boolean enabled;
    private String imageurl;
    @Column(length=500)
    private String about;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Contact> contacts=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Volunteer> volunteers=new ArrayList<>();


    public User() {
        super();
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }


    public List<Volunteer> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(List<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }


}
