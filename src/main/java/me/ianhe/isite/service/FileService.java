package me.ianhe.isite.service;

import com.qiniu.storage.model.FileInfo;

import java.io.InputStream;
import java.util.List;

/**
 * @author iHelin
 * @since 2017/11/13 18:02
 */
public interface FileService {

    String uploadFile(String key, InputStream inputStream);

    List<FileInfo> getFileInfoList(String prefix, String delimiter);

    void deleteFile(String key);

}
