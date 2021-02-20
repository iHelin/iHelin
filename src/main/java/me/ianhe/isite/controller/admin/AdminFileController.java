package me.ianhe.isite.controller.admin;

import com.qiniu.storage.model.FileInfo;
import me.ianhe.isite.model.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * FileUploadController
 *
 * @author iHelin
 * @since 2017/10/17 15:29
 */
@RestController
@RequestMapping("/admin/files")
public class AdminFileController extends BaseAdminController {

    /**
     * 上传到七牛服务器
     *
     * @param file 待上传的文件
     * @return
     */
    @PostMapping("/upload")
    public Map<String, Object> handleUpload(MultipartFile file) {
        if (file.isEmpty()) {
            return R.error("请选择文件...");
        }
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            logger.error("文件上传失败", e);
        }
        return R.ok(fileService.uploadFile("admin/image/" + UUID.randomUUID().toString(), inputStream));
    }

    @GetMapping
    public List<FileInfo> getFileInfoList() {
        return fileService.getFileInfoList("", "");
    }

    /**
     * 删除文件
     *
     * @author iHelin
     * @since 2017/11/13 23:08
     */
    @DeleteMapping
    public Map<String, Object> deleteFile(String key) {
        fileService.deleteFile(key);
        return R.ok();
    }

}
