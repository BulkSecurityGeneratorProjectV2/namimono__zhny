package org.rcisoft.business.system.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * @author 土豆儿
 * @date 2019/3/11 16:12
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DeviceBriefInfo {

    /**
     * 设备ID
     */
    private String id;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备类型
     */
    private String type;

    /**
     * 设备参数
     */
    private String info;

    /**
     * 设备厂家
     */
    private String factoryName;

    /**
     * 设备位置
     */
    private String location;


}
