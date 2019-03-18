package org.rcisoft.business.equipment.report.service;

import org.rcisoft.business.equipment.report.entity.VariableAndParam;
import org.rcisoft.entity.BusFormula;
import org.rcisoft.entity.BusParamSecond;
import org.rcisoft.entity.BusVariable;
import org.rcisoft.entity.SysSource;

import java.util.List;

/**
 * @author 土豆儿
 * @date 2019/3/12 16:36
 **/

public interface FormulaOperationService {

    /**
     * 根据项目ID查询公式信息
     */
    List<BusFormula> queryFormula(String projectId);

    /**
     * 根据公式ID和项目ID查询变量
     */
    List<VariableAndParam> queryVariable(String projectId, String formulaId);

    /**
     * 查询参数来源
     */
    List<SysSource> querySource();

    /**
     * 增加变量信息
     */
    int addVariable(BusVariable busVariable);

    /**
     * 删除变量信息
     */
    int deleteVariable(BusVariable busVariable);

    /**
     * 修改变量信息
     */
    int updateVariable(BusVariable busVariable);

    /**
     * 根据项目ID和参数来源查询二级参数信息
     */
    List<BusParamSecond> queryParamSecondByProId(String projectId,String sourceId);
}
