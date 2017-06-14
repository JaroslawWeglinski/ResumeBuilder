package pl.novaris.resumebuilder.service;

import java.util.Map;

public interface ResumeService {
    public Map<String,String> getResumeData();
    public void addData(String key, String value);
}
