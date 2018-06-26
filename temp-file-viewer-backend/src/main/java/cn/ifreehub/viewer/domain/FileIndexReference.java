package cn.ifreehub.viewer.domain;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import cn.ifreehub.viewer.config.EnvironmentContext;
import cn.ifreehub.viewer.constant.AppConstantConfig;
import cn.ifreehub.viewer.constant.FileType;
import cn.ifreehub.viewer.util.ShortPasswdUtil;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * 上传文件索引引用,以该数据为准
 *
 * @author Quding Ding
 * @since 2018/6/13
 */
public class FileIndexReference implements Serializable, Comparable<FileIndexReference> {
  /**
   * 文件md5名
   */
  private String md5Name;
  /**
   * 文件名
   */
  private String fileName;
  /**
   * 文件绝对路径
   */
  private String fileAbsolutePath;
  /**
   * 文件类型
   */
  private FileType fileType;
  /**
   * 查看秘钥
   */
  private String passwd;
  /**
   * 过期时间
   */
  private Date expireDate;

  /**
   * 根据文件创建对应的索引
   *
   * @param file 上传文件
   * @return 文件索引
   */
  public static FileIndexReference newInstance(MultipartFile file) {
    Assert.notNull(file, "upload file shouldn't null");
    Assert.notNull(file.getOriginalFilename(), "upload file shouldn't null");

    FileIndexReference reference = new FileIndexReference();
    String utf8FileName = new String(file.getOriginalFilename().getBytes(), Charset.forName("UTF-8"));
    // 文件名不包括扩展名
    reference.md5Name = DigestUtils.md5Hex(utf8FileName);
    reference.fileType = FileType.selectByFile(file.getOriginalFilename());
    reference.fileName = reference.fileType.getFileNameWithoutSuffix(utf8FileName);
    // 默认过期时间为一周
    reference.expireDate = DateUtils.addWeeks(new Date(), 1);
    reference.fileAbsolutePath = caleAbsolutePath(reference);
    reference.passwd = ShortPasswdUtil.generateShareKey(reference.md5Name);
    return reference;
  }

  /**
   * 修改文件密码
   * @param passwd 密码
   */
  public void modifyPasswd(String passwd) {
    this.passwd = passwd;
  }

  /**
   * 修改文件名
   * @param fileName 文件名
   */
  public void modifyFileName(String fileName) {
    this.fileName = fileName;
  }

  /**
   * 是否有效
   * @return true有效
   */
  public boolean valid() {
    long current = System.currentTimeMillis();
    return this.expireDate.getTime() > current;
  }

  /**
   * 计算文件绝对路径
   */
  private static String caleAbsolutePath(FileIndexReference reference) {
    return EnvironmentContext.getFolderPath(AppConstantConfig.TEMP_FILE_DIST)
        + reference.md5Name + reference.fileType.getSuffix();
  }

  @Override
  public int compareTo(FileIndexReference reference) {
    if (null == reference) {
      return 1;
    }
    return reference.getExpireDate().compareTo(this.expireDate);
  }

  // --------- get

  public String getMd5Name() {
    return md5Name;
  }

  public String getFileName() {
    return fileName;
  }

  public String getFileAbsolutePath() {
    return fileAbsolutePath;
  }

  public FileType getFileType() {
    return fileType;
  }

  public String getPasswd() {
    return passwd;
  }

  public Date getExpireDate() {
    return expireDate;
  }

  @Override
  public String toString() {
    return "FileIndexReference{" +
        "md5Name='" + md5Name + '\'' +
        ", fileName='" + fileName + '\'' +
        ", fileAbsolutePath='" + fileAbsolutePath + '\'' +
        ", fileType=" + fileType +
        ", passwd='" + passwd + '\'' +
        ", expireDate=" + expireDate +
        '}';
  }
}
