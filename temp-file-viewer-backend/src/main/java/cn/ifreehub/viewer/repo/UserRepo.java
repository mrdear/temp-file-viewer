package cn.ifreehub.viewer.repo;

import cn.ifreehub.viewer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    User findUserByUserName(String userName);
    @Query("select id from User u where u.userName = ?1")
    Integer getUserIdByName(String userName);
}
