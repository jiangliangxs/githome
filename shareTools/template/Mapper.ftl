package {mapperPackage=mapper包路径,默认:project.msthdb.mapper};

import project.msthdb.common.IMsthDbBaseMapper;
import {modelPackage=model包路径,默认:project.msthdb.model}.{entityName=实体类名,默认:TestTable};

/**
 * {description=描述信息,默认:民生系统测试生成模块}
 * 
 * @author {author=作者,默认:民生系统}
 * @date {date=日期,默认:当前时间}
 * @modifiyNote
 * @version 1.0
 */
public interface {entityName=实体类名,默认:TestTable}Mapper extends IMsthDbBaseMapper<{entityName=实体类名,默认:TestTable}> {
}