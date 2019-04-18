package cn.ifreehub.viewer.service;

import cn.ifreehub.viewer.domain.FileIndexReference;
import cn.ifreehub.viewer.domain.UserConfig;

public interface IConfigService {
    UserConfig getUserConfig();

    boolean addFileIndexConfig(FileIndexReference reference);

    boolean removeFileIndex(FileIndexReference reference);
}
