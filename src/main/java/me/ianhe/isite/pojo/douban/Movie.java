package me.ianhe.isite.pojo.douban;

import java.util.List;

/**
 * @author iHelin
 * @since 2017/12/1 15:00
 */
public class Movie {

    private List<Subject> subjects;

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
}
