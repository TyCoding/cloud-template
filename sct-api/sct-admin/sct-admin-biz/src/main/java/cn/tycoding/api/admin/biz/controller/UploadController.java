package cn.tycoding.api.admin.biz.controller;

import cn.tycoding.api.common.constant.enums.CommonEnums;
import cn.tycoding.api.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tycoding
 * @date 2019-05-27
 */
@Slf4j
@RestController
@RequestMapping("/storage/local")
@Api(value = "UploadController", tags = {"本地上传模块接口"})
public class UploadController {

    @Value("${server.port}")
    private String port;

    private final String ip = "http://127.0.0.1";

    /**
     * 文件上传
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传接口")
    public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws FileNotFoundException {
        String host = ip + ":" + port;
        try {
            //获取文件在服务器的储存位置
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            File filePath = new File(path.getAbsolutePath(), "static/upload/");
            log.info("文件的保存路径：" + filePath.getAbsolutePath());
            if (!filePath.exists() && !filePath.isDirectory()) {
                log.info("目录不存在，创建目录:" + filePath);
                filePath.mkdir();
            }

            //获取原始文件名称(包含格式)
            String originalFileName = file.getOriginalFilename();
            log.info("原始文件名称：" + originalFileName);

            //获取文件类型，以最后一个`.`为标识
            String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            log.info("文件类型：" + type);

            //设置文件新名称
            String fileName = ((long) ((Math.random() + 1) * 100000000)) + "." + type;
            log.info("新文件名称：" + fileName);

            //在指定路径下创建一个文件
            File targetFile = new File(filePath, fileName);

            //将文件保存到服务器指定位置
            file.transferTo(targetFile);
            log.info("上传成功");
            //将文件在服务器的存储路径返回

            Map map = new HashMap<>();
            map.put("name", fileName);
            map.put("url", host + "/upload/" + fileName);

            return new Result(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CommonEnums.SYSTEM_ERROR);
        }
    }
}
