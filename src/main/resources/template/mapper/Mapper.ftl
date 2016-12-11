<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${javaMapperPackage}.${javaModelName}Mapper">
	<!-- 所有字段信息 -->
	<sql id="base_column_List">
		<#list tablePrimaryKeys as column>${column.columnName}<#if column_has_next>, </#if></#list><#assign keys=tableCommonKeys /><#if keys??>, </#if><#list tableCommonKeys as column>${column.columnName}<#if column_has_next>, </#if></#list>
	</sql>
	
	<!-- 定义resultMap -->
	<resultMap id="${javaModelName}ResultMap" type="${javaModelPackage}.${javaModelName}">
		<#list tablePrimaryKeys as x>
		<id column="${x.columnName}" property="${x.propertyName}" jdbcType="${x.jdbcTypeName}" />  
		</#list>  
		<#list tableCommonKeys as x>
		<result column="${x.columnName}" property="${x.propertyName}" jdbcType="${x.jdbcTypeName}" />  
		</#list>
	</resultMap>

	<!-- 保存信息 -->
	<#if hasMutiKey>
	<insert id="save"  parameterType="${javaModelPackage}.${javaModelName}">
	<#else>
	<insert id="save" useGeneratedKeys="true" keyProperty="<#list tablePrimaryKeys as column>${column.propertyName}</#list>"
		parameterType="${javaModelPackage}.${javaModelName}">
	</#if>
		INSERT INTO ${tableName}
		<trim prefix="(" suffix=")" suffixOverrides=",">
			<#list tablePrimaryKeys as x>
			<if test="${x.propertyName} != null">
				${x.columnName},
			</if>
			</#list>  
			<#list tableCommonKeys as x>
			<if test="${x.propertyName} != null">
				${x.columnName},
			</if>  
			</#list>
		</trim>
		<trim prefix="values (" suffix=")" suffixOverrides=",">
			<#list tablePrimaryKeys as x>
			<if test="${x.propertyName} != null">
				${r"#"}{${x.propertyName},jdbcType=${x.jdbcTypeName}},
			</if>
			</#list>  
			<#list tableCommonKeys as x>
			<if test="${x.propertyName} != null">
				${r"#"}{${x.propertyName},jdbcType=${x.jdbcTypeName}},
			</if>
			</#list>
		</trim>
	</insert>
	
	<!-- 更新信息 -->
	<update id="update" parameterType="${javaModelPackage}.${javaModelName}">
		UPDATE ${tableName}
		<set>
			<#list tableCommonKeys as x>
			${x.columnName} = ${r"#"}{${x.propertyName},jdbcType=${x.jdbcTypeName}},
			</#list>
		</set>
		<where>
			<#list tablePrimaryKeys as x>
			and ${x.columnName} = ${r"#"}{${x.propertyName},jdbcType=${x.jdbcTypeName}}
			</#list>
		</where>
	</update>
	
	<!-- 更新信息 -->
	<update id="updateSelective" parameterType="${javaModelPackage}.${javaModelName}">
		UPDATE ${tableName}
		<set>
			<#list tableCommonKeys as x>
			<if test="${x.propertyName} != null">
				${x.columnName} = ${r"#"}{${x.propertyName},jdbcType=${x.jdbcTypeName}},
			</if>
			</#list>
		</set>
		<where>
			<#list tablePrimaryKeys as x>
			and ${x.columnName} = ${r"#"}{${x.propertyName},jdbcType=${x.jdbcTypeName}}
			</#list>
		</where>
	</update>
	
	<!-- 删除信息 -->
	<delete id="delete" parameterType="${javaModelPackage}.${javaModelName}">
		DELETE FROM ${tableName}
		<where>
			<#list tablePrimaryKeys as x>
			and ${x.columnName} = ${r"#"}{${x.propertyName},jdbcType=${x.jdbcTypeName}}
			</#list>  
		</where>
	</delete>
	
	<!-- 查询信息 -->
	<select id="get" resultMap="${javaModelName}ResultMap">
		SELECT
		<include refid="base_column_List" />
		FROM ${tableName}
		<where>
			<#list tablePrimaryKeys as x>
			and ${x.columnName} = ${r"#"}{${x.propertyName},jdbcType=${x.jdbcTypeName}}
			</#list>  
		</where>
	</select>
	
	<!-- 查询数量 -->
	<select id="findCount" resultType="java.lang.Integer">
		SELECT COUNT(1) FROM ${tableName}
		<where>
			<include refid="search_fragement" />
		</where>
	</select>
	
	<!-- 查询(条件) -->
	<select id="find" resultMap="${javaModelName}ResultMap">
		SELECT
		<include refid="base_column_List" />
		FROM ${tableName}
		<where>
			<include refid="search_fragement" />
		</where>
		ORDER BY <trim prefix=" " suffix=" " suffixOverrides=","><#list tablePrimaryKeys as x>${x.columnName} DESC,</#list></trim>
	</select>
	
	<!-- Map拼接语句 -->
	<sql id="search_fragement">
		<#list tablePrimaryKeys as x>
		<if test="${x.propertyName} != null">
			AND ${x.columnName} = ${r"#"}{${x.propertyName},jdbcType=${x.jdbcTypeName}}
		</if>
		</#list>  
		<#list tableCommonKeys as x>
		<if test="${x.propertyName} != null">
			AND ${x.columnName} = ${r"#"}{${x.propertyName},jdbcType=${x.jdbcTypeName}}
		</if>
		</#list>
		<if test="noAuth != null">
			AND 1=2
		</if>
	</sql>
</mapper>
