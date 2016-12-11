package ${javaModelPackage};

import java.io.Serializable;

public class ${javaModelName} implements Serializable {
	private static final long serialVersionUID = 1L;
	
	<#list tablePrimaryKeys as x>
	/**
	 * ${x.remarks}
	 */
	private ${x.javaType} ${x.propertyName};
	
	</#list>
	<#list tableCommonKeys as x>
	/**
	 * ${x.remarks}
	 */
	private ${x.javaType} ${x.propertyName};
	
	</#list>
	<#list tablePrimaryKeys as x>
	public ${x.javaType} get${x.propertyInMethodName}() {
        return ${x.propertyName};
    }
    
    public void set${x.propertyInMethodName}(${x.javaType} ${x.propertyName}) {
        this.${x.propertyName} = ${x.propertyName};
    }
    
	</#list>  
	<#list tableCommonKeys as x>
	public ${x.javaType} get${x.propertyInMethodName}() {
        return ${x.propertyName};
    }
    
    public void set${x.propertyInMethodName}(${x.javaType} ${x.propertyName}) {
        this.${x.propertyName} = ${x.propertyName};
    }
    
	</#list>
}