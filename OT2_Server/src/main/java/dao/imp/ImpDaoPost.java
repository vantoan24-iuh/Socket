package dao.imp;

import entity.Post;

import java.util.List;

public interface ImpDaoPost {
    public List<Post> listPostsByApprovalStatusAndYear(String status, int year);
    public List<Post> listMostLikedPosts();
}
