package org.rcisoft.business.system.project.service.Impl;

import org.rcisoft.base.util.UuidUtil;
import org.rcisoft.dao.EnergyPriceDao;
import org.rcisoft.dao.EnergyStandardDao;
import org.rcisoft.business.system.project.dao.BasicDataDao;
import org.rcisoft.entity.EnergyPrice;
import org.rcisoft.entity.EnergyStandard;
import org.rcisoft.business.system.project.service.BasicDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Create by 土豆儿
 * Time：2019/3/5 9:27
 **/
@Service
public class BasicDataServiceImpl implements BasicDataService {

    @Autowired
    private BasicDataDao basicDataDao;
    @Autowired
    private EnergyStandardDao energyStandardDao;
    @Autowired
    private EnergyPriceDao energyPriceDao;

    /**
     *获取水电气24小时单价信息
     */
    @Override
    public int addPerHourPrice(List<EnergyPrice> list){
//        Map<String,List<String>> mapList = new HashMap<>();
//        List<Map<String,String>> listMap = new ArrayList<>();
//        String[] array = {"0","0","0","0","0","0","0","0","0","0","0","0",
//                "0","0","0","0","0","0","0","0","0","0","0","0"};
//        List<String> waterList = new ArrayList<>(Arrays.asList(array));
//        listMap = basicDataDao.queryWaterPerHourPrice(energyPrice);
//        for (Map<String, String> map : listMap){}
        int sum = 0;
        for(EnergyPrice energyPrice : list){
            energyPrice.setId(UuidUtil.create32());
            energyPriceDao.insert(energyPrice);
            sum++;
        }
        return sum;
    }

    /**
     * 新增能源标准
     */
    @Override
    public int addEnergyStandard(List<EnergyStandard> list){
        int sum = 0;
        for(EnergyStandard energyStandard : list){
            energyStandard.setId(UuidUtil.create32());
            energyStandardDao.insert(energyStandard);
            sum++;
        }
        return sum;
    }

    /**
     * 上传基准碳排放量模板
     */
    @Override
    public String upload(MultipartFile file){
        try {
            if (file.isEmpty()) {
                return "文件为空";
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            //String fileNames = file.getName();
            //logger.info("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            //logger.info("文件的后缀名为：" + suffixName);

            // 设置文件存储路径
            String filePath = "G:/IDEA_Proj/zhny-back-2nd/src/main/resources/excel/";
            String path = filePath + fileName;

            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            // 文件写入
            file.transferTo(dest);
            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    /**
     * 下载基准碳排放量模板
     */
    @Override
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //"新建文本文档.txt";// 设置文件名，根据业务需要替换成要下载的文件名
        String fileName = "碳排放量.xls";
        if (fileName != null) {
            //设置文件路径
            String realPath = "G:/IDEA_Proj/zhny-back-2nd/src/main/resources/excel/";
            File file = new File(realPath , fileName);
            if (file.exists()) {
                // 设置强制下载不打开
                response.setContentType("application/force-download");
                // 设置文件名
                response.addHeader("Content-Disposition", "attachment;fileName=" +  java.net.URLEncoder.encode(fileName, "UTF-8"));
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
}
