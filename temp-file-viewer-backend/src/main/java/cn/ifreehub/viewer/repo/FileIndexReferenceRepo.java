package cn.ifreehub.viewer.repo;

import cn.ifreehub.viewer.domain.FileIndexReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileIndexReferenceRepo extends JpaRepository<FileIndexReference,Integer> {

}
