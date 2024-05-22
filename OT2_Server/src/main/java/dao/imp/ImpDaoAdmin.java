package dao.imp;

import entity.Admin;

import java.util.Map;

public interface ImpDaoAdmin {
    public Map<Admin,Long> listAdminsAndNumberOfPosts();
}
