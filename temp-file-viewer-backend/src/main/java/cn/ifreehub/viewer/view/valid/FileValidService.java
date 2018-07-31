package cn.ifreehub.viewer.view.valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.ifreehub.viewer.domain.FileIndexReference;
import cn.ifreehub.viewer.service.FileApplicationService;

import javax.annotation.Resource;

/**
 * @author Quding Ding
 * @since 2018/7/30
 */
@Service
public class FileValidService {

  @Resource
  private FileApplicationService fileApplicationService;

  public void validFile(FileIndexReference reference, String passwd, String frontRoute) {
    Assert.notNull(reference, "文件已过期或已删除");
    Assert.isTrue(reference.getFileType().frontRoute.equals(frontRoute), "不支持的文件");
    Assert.isTrue(StringUtils.equals(passwd, reference.getPasswd()), "密码错误");
    // 文件过期则删除
    if (!reference.valid()) {
      fileApplicationService.removeFileIndex(reference);
      throw new IllegalArgumentException("fileApplicationService");
    }
  }

}
