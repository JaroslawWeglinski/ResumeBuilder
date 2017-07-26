package pl.novaris.resumebuilder.dao.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(value="resume")
public class Resume {
    private String name;
    private String surname;
    private String birthday;
    private String locationOne;
    private String locationTwo;
    private String email;
    private String phone;
    private String target;
    private String educationTimeOne;
    private String universityNameOne;
    private String universityCourseOne;
    private String educationTimeTwo;
    private String universityNameTwo;
    private String universityCourseTwo;
    private String experienceTime;
    private String experienceName;
    private String experienceRoleName;
    private String experienceDescription;
    private String skillName;
    private String skillDescription;
    private String languageName;
    private String languageLevel;
    private String certificate;
    private String hobby;

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

    public String getEducationTimeOne() {
        return educationTimeOne;
    }

    public void setEducationTimeOne(String educationTimeOne) {
        this.educationTimeOne = educationTimeOne;
    }

    public String getUniversityNameOne() {
        return universityNameOne;
    }

    public void setUniversityNameOne(String universityNameOne) {
        this.universityNameOne = universityNameOne;
    }

    public String getUniversityCourseOne() {
        return universityCourseOne;
    }

    public void setUniversityCourseOne(String universityCourseOne) {
        this.universityCourseOne = universityCourseOne;
    }

    public String getEducationTimeTwo() {
        return educationTimeTwo;
    }

    public void setEducationTimeTwo(String educationTimeTwo) {
        this.educationTimeTwo = educationTimeTwo;
    }

    public String getUniversityNameTwo() {
        return universityNameTwo;
    }

    public void setUniversityNameTwo(String universityNameTwo) {
        this.universityNameTwo = universityNameTwo;
    }

    public String getUniversityCourseTwo() {
        return universityCourseTwo;
    }

    public void setUniversityCourseTwo(String universityCourseTwo) {
        this.universityCourseTwo = universityCourseTwo;
    }

    public String getExperienceTime() {
        return experienceTime;
    }

    public void setExperienceTime(String experienceTime) {
        this.experienceTime = experienceTime;
    }

    public String getExperienceName() {
        return experienceName;
    }

    public void setExperienceName(String experienceName) {
        this.experienceName = experienceName;
    }

    public String getExperienceRoleName() {
        return experienceRoleName;
    }

    public void setExperienceRoleName(String experienceRoleName) {
        this.experienceRoleName = experienceRoleName;
    }

    public String getExperienceDescription() {
        return experienceDescription;
    }

    public void setExperienceDescription(String experienceDescription) {
        this.experienceDescription = experienceDescription;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillDescription() {
        return skillDescription;
    }

    public void setSkillDescription(String skillDescription) {
        this.skillDescription = skillDescription;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageLevel() {
        return languageLevel;
    }

    public void setLanguageLevel(String languageLevel) {
        this.languageLevel = languageLevel;
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
}
