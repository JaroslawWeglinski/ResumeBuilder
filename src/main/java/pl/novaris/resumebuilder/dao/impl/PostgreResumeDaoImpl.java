package pl.novaris.resumebuilder.dao.impl;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import pl.novaris.resumebuilder.dao.ResumeDao;
import pl.novaris.resumebuilder.dao.entity.Resume;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Qualifier("postgre")
public class PostgreResumeDaoImpl implements ResumeDao {

    //@Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public PostgreResumeDaoImpl(EntityManagerFactory factory) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    private static Map<String,String> resumeData = new HashMap<>();

    @Override
    public Map<String, String> getResumeData() {
        return resumeData;
    }

    @Override
    public void addData(String key, String value) {
        resumeData.put(key,value);
    }

    @Override
    public List<Resume> getResumes() {
       Session currentSession = sessionFactory.getCurrentSession();

       Query<Resume> theQuery = currentSession.createQuery("from Resume order by name", Resume.class);

       List<Resume> resumes = theQuery.getResultList();

       return resumes;
    }

    @Override
    public void saveResume(Resume theResume) {
        Session currentSession = sessionFactory.getCurrentSession();

        currentSession.saveOrUpdate(theResume);
    }

    @Override
    public Resume getResume(int theId) {
        Session currentSession = sessionFactory.getCurrentSession();

        Resume theResume = currentSession.get(Resume.class, theId);

        return theResume;
    }

    @Override
    public void deleteResume(int theId) {

        Session currentSession = sessionFactory.getCurrentSession();

        Query theQuery = currentSession.createQuery("delete from Resume where id=:resumeId");

        theQuery.setParameter("resumeId", theId);

        theQuery.executeUpdate();

    }
}
