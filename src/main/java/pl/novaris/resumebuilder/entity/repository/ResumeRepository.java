package pl.novaris.resumebuilder.entity.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ResumeRepository {
    private static Map<String,String> resumeData = new HashMap<>();

    public Map<String,String> getResumeData(){
        return resumeData;
    }

    public void add(String key, String value){
        resumeData.put(key,value);
    }
}
