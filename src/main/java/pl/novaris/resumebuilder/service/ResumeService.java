package pl.novaris.resumebuilder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.novaris.resumebuilder.entity.repository.*;

import java.util.Map;

@Service
public class ResumeService {
    @Autowired
    ResumeRepository resumeRepository;

    public Map<String,String> getResumeData(){
        return resumeRepository.getResumeData();
    }

    public void add(String key, String value){
        resumeRepository.add(key,value);
    }

}
