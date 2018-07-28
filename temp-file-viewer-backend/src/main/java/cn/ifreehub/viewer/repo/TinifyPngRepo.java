package cn.ifreehub.viewer.repo;

import io.github.mrdear.tinify.TinifyClient;
import io.github.mrdear.tinify.request.ShrinkRequest;
import io.github.mrdear.tinify.response.ShrinkResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

import cn.ifreehub.viewer.config.EnvironmentContext;
import cn.ifreehub.viewer.constant.AppConfig;
import cn.ifreehub.viewer.constant.FileType;
import cn.ifreehub.viewer.domain.FileIndexReference;
import cn.ifreehub.viewer.util.HTTPUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

/**
 * @author Quding Ding
 * @since 2018/7/28
 */
@Repository
public class TinifyPngRepo {

  private static Logger logger = LoggerFactory.getLogger(TinifyPngRepo.class);

  @Resource
  private TinifyClient tinifyClient;
  @Resource
  private ExecutorService taskExecutor;

  /**
   * 异步图片瘦身
   * @param reference 图片
   */
  public void asyncShrinkPicture(FileIndexReference reference) {
    taskExecutor.execute(() -> shrinkPicture(reference));
  }

  /**
   * 同步图片瘦身
   * @param reference 图片
   */
  public void shrinkPicture(FileIndexReference reference) {
    // 未开启压缩则返回
    if (!EnvironmentContext.getBooleanValue(AppConfig.TINYPNG_ENABLE)) {
      return;
    }

    // 只压缩png与jpg
    FileType type = reference.getFileType();
    if (type != FileType.PNG && type != FileType.JPG) {
      return;
    }

    logger.info("start shrink file {}", reference);
    try {
      ShrinkRequest request = new ShrinkRequest();
      request.setFileData(new FileInputStream(reference.getFileAbsolutePath()));
      ShrinkResponse shrinkResponse = tinifyClient.execute(request);
      if (null != shrinkResponse) {
        logger.info("shrink result is {}", shrinkResponse);
        String url = shrinkResponse.getOutput().getUrl();
        // 下载新文件
        InputStream downFile = HTTPUtils.downFile(url);
        if (null != downFile) {
          FileCopyUtils.copy(downFile, new FileOutputStream(new File(reference.getFileAbsolutePath())));
        }
      }
    } catch (FileNotFoundException e) {
      logger.error("file not found, file is {}", reference);
    } catch (IOException e) {
      logger.error("file copy fail, file is {}", reference);
    }
  }

}
