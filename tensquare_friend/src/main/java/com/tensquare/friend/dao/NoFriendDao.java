package com.tensquare.friend.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pojo.NoFriend;

/**
 * 好友dao
 */
public interface NoFriendDao extends JpaRepository<NoFriend,String>{

}
