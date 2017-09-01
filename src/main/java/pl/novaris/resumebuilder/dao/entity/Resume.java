package pl.novaris.resumebuilder.dao.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Configuration(value="resume")
@Entity
@Table(name = "resume")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "location_one")
    private String locationOne;

    @Column(name = "location_two")
    private String locationTwo;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "target")
    private String target;

    @Column(name = "certificate")
    private String certificate;

    @Column(name = "hobby")
    private String hobby;

    public Resume() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(targetEntity=Education.class, mappedBy="resume")
    private List<Education> educations = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(targetEntity=Experience.class, mappedBy="resume")
    private List<Experience> experiences = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(targetEntity=Skill.class, mappedBy="resume")
    private List<Skill> skills = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(targetEntity=Language.class, mappedBy="resume")
    private List<Language> languages = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLocationOne() {
        return locationOne;
    }

    public void setLocationOne(String locationOne) {
        this.locationOne = locationOne;
    }

    public String getLocationTwo() {
        return locationTwo;
    }

    public void setLocationTwo(String locationTwo) {
        this.locationTwo = locationTwo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday='" + birthday + '\'' +
                ", locationOne='" + locationOne + '\'' +
                ", locationTwo='" + locationTwo + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", target='" + target + '\'' +
                ", certificate='" + certificate + '\'' +
                ", hobby='" + hobby + '\'' +
                ", educations=" + educations +
                ", experiences=" + experiences +
                ", skills=" + skills +
                ", languages=" + languages +
                '}';
    }
}
