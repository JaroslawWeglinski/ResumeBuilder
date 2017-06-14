package pl.novaris.resumebuilder.dao;

import java.util.Map;

public interface ResumeDao {
    public Map<String,String> getResumeData();
    public void addData(String key, String value);
}
