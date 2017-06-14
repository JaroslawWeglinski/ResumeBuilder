package pl.novaris.resumebuilder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.novaris.resumebuilder.dao.ResumeDao;
import pl.novaris.resumebuilder.service.ResumeService;

import java.util.Map;

@Service
@Qualifier("resumeService")
public class ResumeServiceImpl implements ResumeService{
    @Autowired
    @Qualifier("tempRepo")
    private ResumeDao resumeDao;

    public Map<String,String> getResumeData(){
        return resumeDao.getResumeData();
    }

    public void addData(String key, String value){
        resumeDao.addData(key,value);
    }

}
