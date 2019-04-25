package org.rcisoft.business.whole.right.service.Impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.rcisoft.base.result.ServiceResult;
import org.rcisoft.base.util.UuidUtil;
import org.rcisoft.business.whole.right.dao.AnalysisReportDao;
import org.rcisoft.business.whole.right.service.AnalysisReportService;
import org.rcisoft.entity.BusReport;
import org.rcisoft.entity.SysImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author Minghui Xu
 * @Description:
 * @Date: Created in 15:13 2019/4/12
 */
@Service
public class AnalysisReportServiceImpl implements AnalysisReportService {

    @Value("${location.path}")
    private String path;
    @Value("${location.analysisReport}")
    private String analysisReport;

    @Autowired
    private AnalysisReportDao analysisReportDao;


    @Override
    public List<BusReport> queryAnalysisReport(int year) {
        return analysisReportDao.queryAnalysisReport(year);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    @Override
    public ServiceResult uploadAnalysisReport(MultipartFile file, String proId, int year, int month) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String now = df.format(new Date());
        // 返回值
        Integer result = 0;
        // 后缀
        String suffix = "";
        String[] fileNameArray = StringUtils.split(file.getOriginalFilename(), ".");
        if (fileNameArray.length > 1) {
            suffix = "." + fileNameArray[fileNameArray.length - 1];
        }
        // 新的文件名
        String fileName = month + suffix;
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path + analysisReport + "/" + year + "/" + fileName ));
            result = analysisReportDao.insert(new BusReport(UuidUtil.create32(), proId,now, year, month, fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ServiceResult(result, path + analysisReport + "/" + year + "/" + fileName);
    }

    @Override
    public String downloadAnalysisReport(HttpServletRequest request, HttpServletResponse response, String proId, int year, int month) throws Exception {
        String fileName = analysisReportDao.queryFileName(year, month);
        String filePath = path + analysisReport + "/" + year + "/" + fileName;
        System.out.println(filePath);
        File file = new File(filePath);
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName="+URLEncoder.encode(fileName, "UTF-8"));
        int index=0;
        InputStream input=new FileInputStream(file);
        OutputStream out = response.getOutputStream();
        byte[] buff =new byte[1024];
        while((index= input.read(buff))!= -1){
            out.write(buff, 0, index);
            out.flush();
        }
        return null;
    }

    @Override
    public Integer deleteAnalysisReport(String id) {
        BusReport busReport = analysisReportDao.queryFile(id);
        int year = busReport.getTimeYear();
        int month = busReport.getTimeMonth();

        String fileName = analysisReportDao.queryFileName(year, month);
        String filePath = path + analysisReport + "/" + year + "/" + fileName;
        System.out.println(filePath);
        File file = new File(filePath);
        if(file.exists()&&file.isFile()){
            file.delete();
        }
        Integer status = analysisReportDao.deleteByPrimaryKey(id);
        return status;
    }
}

