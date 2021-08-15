package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    /**
     * 最新问答
     *   1）先客户端写好SQL
     *     SELECT * FROM tb_problem p WHERE p.id IN
             (SELECT pl.problemid FROM tb_pl pl WHERE pl.labelid = '1131050418500083712')
               ORDER BY p.replytime DESC
     *
     *   2）把SQL翻译成JPQL （把表名改为类名，把字段名称改为属性名称）
     *        SELECT p FROM Problem p WHERE p.id IN
                 (SELECT pl.problemid FROM Pl pl WHERE pl.labelid = ?1)
                 ORDER BY p.replytime DESC
     *
     */
    @Query("SELECT p FROM Problem p WHERE p.id IN" +
            "                 (SELECT pl.problemid FROM Pl pl WHERE pl.labelid = ?1)" +
            "                 ORDER BY p.replytime DESC")
    public Page<Problem> findNewList(String labelid, Pageable pageable);


    /**
     * 热门问答
     * @param labelid
     * @param pageable
     * @return
     */
    @Query("SELECT p FROM Problem p WHERE p.id IN" +
            "                 (SELECT pl.problemid FROM Pl pl WHERE pl.labelid = ?1)" +
            "                 ORDER BY p.reply DESC,p.createtime DESC")
    public Page<Problem> findHotList(String labelid, Pageable pageable);

    /**
     * 等待问答
     * @param labelid
     * @param pageable
     * @return
     */
    @Query("SELECT p FROM Problem p WHERE p.id IN" +
            "                 (SELECT pl.problemid FROM Pl pl WHERE pl.labelid = ?1)" +
            "                AND p.reply = 0 ORDER BY p.createtime DESC")
    public Page<Problem> findWaitList(String labelid, Pageable pageable);
}
