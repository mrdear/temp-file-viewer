package cn.ifreehub.viewer.repo;

import cn.ifreehub.viewer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    User findUserByUserName(String userName);
}
