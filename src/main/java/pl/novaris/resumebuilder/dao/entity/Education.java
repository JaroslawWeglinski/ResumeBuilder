package pl.novaris.resumebuilder.dao.entity;

public class Education {
    private String time;
    private String universityName;
    private String universityCourse;

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
                "time='" + time + '\'' +
                ", universityName='" + universityName + '\'' +
                ", universityCourse='" + universityCourse + '\'' +
                '}';
    }
}
