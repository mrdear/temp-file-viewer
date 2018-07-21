package cn.ifreehub.viewer.constant;

import static org.junit.Assert.*;

import org.junit.Test;

public class FileTypeTest {

  @Test
  public void selectByFile() {
    FileType test1Type = FileType.selectByFile("/quding/test.md");
    assertEquals(test1Type, FileType.MARKDOWN);

    FileType test2Type = FileType.selectByFile("/quding/test.PNG");
    assertEquals(test2Type, FileType.PNG);
  }
}