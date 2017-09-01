package pl.novaris.resumebuilder.dao.entity;

import javax.persistence.*;

@Entity
@Table(name = "res_education")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "time")
    private String time;

    @Column(name = "university_name")
    private String universityName;

    @Column(name = "university_course")
    private String universityCourse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="id_res",referencedColumnName="id",nullable=false,unique=true)
    private Resume resume;

    public Education() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityCourse() {
        return universityCourse;
    }

    public void setUniversityCourse(String universityCourse) {
        this.universityCourse = universityCourse;
    }

    @Override
    public String toString() {
        return "Education{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", universityName='" + universityName + '\'' +
                ", universityCourse='" + universityCourse + '\'' +
                '}';
    }
}
