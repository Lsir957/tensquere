package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LabelService {
    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    //查询所有
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    //根据id查询
    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    //添加
    public void add(Label label) {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    //修改
    public void update(Label label) {
        labelDao.save(label);
    }

    //根据id删除
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }


    public Specification<Label> createSpecification(Map searchMap) {
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> preList = new ArrayList<>();
                if (searchMap.get("labelname") != null && !(searchMap.get("labelname").equals(""))) {
                    preList.add(criteriaBuilder.like(root.get("labelname"), "%" + searchMap.get("labelname") + "%"));
                }
                if (searchMap.get("state") != null ) {
                    preList.add(criteriaBuilder.equal(root.get("state"), searchMap.get("state")));
                }
                if (searchMap.get("recommend") != null
                        && !(searchMap.get("recommend")).equals("")) {
                    preList.add(criteriaBuilder.equal(root.get("recommend"), searchMap.get("recommend")));
                }
                Predicate[] preArray = new Predicate[preList.size()];
                return criteriaBuilder.and(preList.toArray(preArray));
            }
        };

    }

    //条件查询
    public Page<Label> findSearch(Map searchMap, Integer page, Integer size) {
        Specification<Label> specification = createSpecification(searchMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return labelDao.findAll(specification, pageRequest);
    }

    //条件查询
    public List<Label> findSearch(Map searchMap) {
        Specification<Label> specification = createSpecification(searchMap);
        return labelDao.findAll(specification);
    }
}
