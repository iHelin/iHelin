package me.ianhe.isite.service;

import me.ianhe.isite.dao.PoemMapper;
import me.ianhe.isite.entity.Poem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author iHelin
 * @since 2018/5/12 14:33
 */
@Service
public class CommonService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PoemMapper poemMapper;

    public Poem getPoemByRandom() {
        return poemMapper.getByRandom();
    }
}
