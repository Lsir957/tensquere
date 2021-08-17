package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;

@Service
public class SpitService {
    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;
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
    public void save(Spit spit) {
        spit.setId(idWorker.nextId()+"");
        spitDao.save(spit);
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
}
