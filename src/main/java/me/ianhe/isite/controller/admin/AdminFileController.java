package me.ianhe.isite.controller.admin;

import com.qiniu.storage.model.FileInfo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * FileUploadController
 *
 * @author iHelin
 * @since 2017/10/17 15:29
 */
@RestController
public class AdminFileController extends BaseAdminController {

    /**
     * 上传到七牛服务器
     *
     * @param file 待上传的文件
     * @return
     */
    @PostMapping("upload")
    public String handleUpload(MultipartFile file) {
        if (file.isEmpty()) {
            return error("请选择文件...");
        }
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            logger.error("文件上传失败", e);
        }
        return fileService.uploadFile("admin/image/" + UUID.randomUUID().toString(), inputStream);
    }

    @GetMapping("files")
    public List<FileInfo> getFileInfoList() {
        return fileService.getFileInfoList("", "");
    }

    /**
     * 删除文件
     *
     * @author iHelin
     * @since 2017/11/13 23:08
     */
    @DeleteMapping("files")
    public String deleteFile(String key) {
        fileService.deleteFile(key);
        return success();
    }

}
