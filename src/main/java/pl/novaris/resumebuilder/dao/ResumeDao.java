package pl.novaris.resumebuilder.dao;

import pl.novaris.resumebuilder.dao.entity.Resume;

import java.util.List;
import java.util.Map;

public interface ResumeDao {
    public Map<String,String> getResumeData();
    public void addData(String key, String value);

    public List<Resume> getResumes();

    public void saveResume(Resume theResume);

    public Resume getResume(int theId);

    public void deleteResume(int theId);
}
