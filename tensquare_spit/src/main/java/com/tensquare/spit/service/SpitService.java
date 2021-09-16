package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;
import java.util.Queue;

@Service
public class SpitService {
    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;
    /**
     * 查询全部
     * @return
     */
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }
    /**
     * 添加
     */
    public void add(Spit spit) {
        spit.setId(idWorker.nextId()+"");
        spitDao.save(spit);
        if (spit.getParentid() != null && !"".equals(spit.getParentid())){
            Query queue = new Query();
            queue.addCriteria(Criteria.where("_id").is(spit.getParentid()));

            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(queue,update,"spit");
        }

    }

    /**
     * 删除
     */
    public void deleteById(String id){
        spitDao.deleteById(id );
    }


    /**
     * 修改
     */
    public void update(Spit spit){
         spitDao.save(spit);
    }

    public Page<Spit> findByParentid(Integer parentid, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentid,pageRequest);
    }

    /**
     * 点赞
     * @param id
     */
    public void updateThumbup(String id) {
        Query queue = new Query();
        queue.addCriteria(Criteria.where("_id").is(id));

        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(queue,update,"spit");

    }

    /**
     * 取消点赞
     * @param id
     */
    public void cancelThumbup(String id) {
        Query queue = new Query();
        queue.addCriteria(Criteria.where("_id").is(id));

        Update update = new Update();
        update.inc("thumbup",-1);
        mongoTemplate.updateFirst(queue,update,"spit");
    }
}
