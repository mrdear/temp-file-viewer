package cn.ifreehub.viewer.service;

import com.google.common.collect.Lists;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.ifreehub.viewer.domain.FileIndexReference;
import cn.ifreehub.viewer.domain.UserConfig;
import cn.ifreehub.viewer.exception.ServiceException;
import cn.ifreehub.viewer.repo.ConfigRepo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 领域对应的服务
 *
 * @author Quding Ding
 * @since 2018/6/17
 */
@Service
public class FileApplicationService {

  @Resource
  private ConfigRepo configRepo;

  private static Logger logger = LoggerFactory.getLogger(FileApplicationService.class);

  /**
   * 添加一个文件
   *
   * @param file 要添加的文件信息
   */
  public void addFileIndex(FileIndexReference reference, MultipartFile file) {
    // 保存config
    if (configRepo.addFileIndexConfig(reference)) {
      // 保存文件
      try {
        file.transferTo(new File(reference.getFileAbsolutePath()));
      } catch (IOException e) {
        logger.error("save file fail, file is {}", reference, e);
        throw new ServiceException(e);
      }
    }
  }

  /**
   * 删除文件
   *
   * @param reference 文件索引
   */
  public void removeFileIndex(FileIndexReference reference) {
    // 删除文件成功,清理磁盘
    if (configRepo.removeFileIndex(reference)) {
      try {
        FileUtils.forceDelete(new File(reference.getFileAbsolutePath()));
        return;
      } catch (IOException e) {
        logger.error("delete file fail, file is {}", reference.getFileAbsolutePath(), e);
        throw new ServiceException(e);
      }
    }

    throw new ServiceException("delete file fail");
  }

  /**
   * 查询索引中全部的文件
   *
   * @return 结果
   */
  public List<FileIndexReference> queryAllFile() {
    UserConfig userConfig = configRepo.getUserConfig();
    Map<String, FileIndexReference> files = userConfig.getFiles();

    ArrayList<FileIndexReference> references = Lists.newArrayList(files.values());

    // 根据过期时间排序
    Collections.sort(references);
    return references;
  }
}
