package pl.novaris.resumebuilder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.novaris.resumebuilder.dao.entity.repository.*;

import java.util.Map;

@Service
public class ResumeService {
    @Autowired
    ResumeRepository resumeRepository;

    public Map<String,String> getResumeData(){
        return resumeRepository.getResumeData();
    }

    public void addData(String key, String value){
        resumeRepository.addData(key,value);
    }

}
