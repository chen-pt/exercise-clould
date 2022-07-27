package ${package.Service};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${entity}Service : ${superServiceImplClass}<${table.mapperName}, ${entity}>() {

}
<#else>
public class ${entity}Service extends ${superServiceImplClass}<${table.mapperName}, ${entity}>{

}
</#if>
