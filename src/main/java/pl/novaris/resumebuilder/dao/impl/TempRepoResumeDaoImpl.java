package pl.novaris.resumebuilder.dao.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import pl.novaris.resumebuilder.dao.ResumeDao;

import java.util.HashMap;
import java.util.Map;

@Repository
@Qualifier("tempRepo")
public class TempRepoResumeDaoImpl implements ResumeDao {
    private static Map<String,String> resumeData = new HashMap<>();

    public Map<String,String> getResumeData(){
        return resumeData;
    }

    public void addData(String key, String value){
        resumeData.put(key,value);
    }
}
