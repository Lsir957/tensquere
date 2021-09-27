package com.tensquare.friend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pojo.Friend;

/**
 * 好友dao
 */
public interface FriendDao extends JpaRepository<Friend,String>{

    /**
     * 查询好友记录数
     */
    @Query("select count(*) from Friend where userid = ?1 and friendid = ?2")
    public Long selectCount(String userid,String friendid);

    /**
     * 更新islike
     */
    @Modifying
    @Query("update Friend set islike = ?3 where userid = ?1 and friendid = ?2")
    public void updateLike(String userid,String friendid,String islike);

    /**
     * 删除好友
     */
    @Modifying
    @Query("delete from Friend  where userid = ?1 and friendid = ?2")
    public void deleteFriend(String userid,String friendid);
}
