package com.tensquare.friend.service;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.Friend;
import pojo.NoFriend;

/**
 * 交友Service
 */
@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserClient userClient;

    /**
     * 添加好友
     */
    @Transactional
    public Integer addFriend(String userid,String friendid){
        //判断是否添加过好友
        if( friendDao.selectCount(userid,friendid)>0 ) {
            return 0;
        }

        //没有加过好友
        //保存好友信息
        Friend friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);

        //判断对方如果加过了当前用户，双方的好友记录都需要把islike改为1
        if( friendDao.selectCount(friendid,userid)>0  ){
            friendDao.updateLike(userid,friendid,"1");
            friendDao.updateLike(friendid,userid,"1");
        }

        //更新关注数和粉丝数
        userClient.updateFollowcount(userid,1);
        userClient.updateFanscount(friendid,1);

        return 1;
    }

    /**
     * 添加非好友
     */
    public void addNoFriend(String userid,String friendid){
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }

    /**
     * 删除好友
     */
    @Transactional
    public void deleteFriend(String userid,String friendid){
        //删除当前用户好友记录
        friendDao.deleteFriend(userid,friendid);
        //判断对方是否加过当前用户，加过的话，更新对方记录islike为0
        if( friendDao.selectCount(friendid,userid)>0 ){
            friendDao.updateLike(friendid,userid,"0");
        }

        //更新关注数和粉丝数
        userClient.updateFollowcount(userid,-1);
        userClient.updateFanscount(friendid,-1);
    }
}
