package pl.novaris.resumebuilder.dao.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import pl.novaris.resumebuilder.dao.ResumeDao;

import java.util.Map;

@Qualifier("postgre")
public class PostgreResumeDaoImpl implements ResumeDao {
    @Override
    public Map<String, String> getResumeData() {
        return null;
    }

    @Override
    public void addData(String key, String value) {

    }
}
