package pl.novaris.resumebuilder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.novaris.resumebuilder.dao.ResumeDao;
import pl.novaris.resumebuilder.dao.entity.Resume;
import pl.novaris.resumebuilder.service.ResumeService;

import java.util.List;
import java.util.Map;

@Service
@Qualifier("resumeService")
public class ResumeServiceImpl implements ResumeService {
    @Autowired
    @Qualifier("postgre")
    private ResumeDao resumeDao;

    public Map<String, String> getResumeData() {
        return resumeDao.getResumeData();
    }

    public void addData(String key, String value) {
        resumeDao.addData(key, value);
    }

    @Override
    @Transactional
    public List<Resume> getResumes() {
        return resumeDao.getResumes();
    }

    @Override
    @Transactional
    public void saveResume(Resume theResume) {
        resumeDao.saveResume(theResume);
    }

    @Override
    @Transactional
    public Resume getResume(int theId) {
        return resumeDao.getResume(theId);
    }

    @Override
    @Transactional
    public void deleteResume(int theId) {
        resumeDao.deleteResume(theId);
    }

}
