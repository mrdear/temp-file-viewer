package cn.ifreehub.viewer.service;

import cn.ifreehub.viewer.constant.CurrentUserHolder;
import cn.ifreehub.viewer.domain.FileIndexReference;
import cn.ifreehub.viewer.domain.UserConfig;
import cn.ifreehub.viewer.domain.User;
import cn.ifreehub.viewer.repo.FileIndexReferenceRepo;
import cn.ifreehub.viewer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileIndexReferenceService implements IConfigService {
    @Autowired
    private FileIndexReferenceRepo fileIndexReferenceRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserConfig getUserConfig() {
        String userName = CurrentUserHolder.getUserName();
        FileIndexReference exampleFile = new FileIndexReference();
        exampleFile.setUserId(userRepo.getUserIdByName(userName));

        List<FileIndexReference> fileIndexReferenceList = fileIndexReferenceRepo.findAll(Example.of(exampleFile));
        Map<String,FileIndexReference> referenceMap = new HashMap<>();
        for (FileIndexReference indexReference : fileIndexReferenceList) {
            referenceMap.put(indexReference.getMd5Name(),indexReference);
        }
        UserConfig userConfig = new UserConfig();
        userConfig.setFiles(referenceMap);
        return userConfig;
    }

    @Override
    public boolean addFileIndexConfig(FileIndexReference reference) {
        String userName = CurrentUserHolder.getUserName();
        reference.setUserId(userRepo.getUserIdByName(userName));
        fileIndexReferenceRepo.save(reference);
        return true;
    }

    @Override
    public boolean removeFileIndex(FileIndexReference reference) {
        fileIndexReferenceRepo.delete(reference);
        return true;
    }
}
