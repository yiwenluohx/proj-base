package ${package};

import ${entityPackage};
import ${mapperPackage};
import ${servicePackage};
import cn.quality.framework.mybatis.service.impl.BaseService;
import org.springframework.stereotype.Service;

/**
* @Description: ${tableName}
* @author ${author}
* @date ${time}
*/
@Service("${iocName}")
public class ${implName} extends BaseService<${entityName},${mapperName}> implements ${serviceName} {

}