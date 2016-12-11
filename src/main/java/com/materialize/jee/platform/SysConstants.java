package com.materialize.jee.platform;

public class SysConstants {
	/**
	 * 资源类型，0-菜单 ; 1-method
	 */
	public static final Integer RESOURCE_TYPE_MENU = 0;
	public static final Integer RESOURCE_TYPE_METHOD = 1;
	
	/**
	 * 菜单是否为目录，0-页面;1-目录
	 */
	public static final Integer MENU_IS_DIRECTORY_NO = 0;
	public static final Integer MENU_IS_DIRECTORY_YES = 1;
	
	/**
	 * 是否有效#1:有效;0:无效
	 */
	public static final Integer ACTIVE_FLAG_NO = 0;
	public static final Integer ACTIVE_FLAG_YES = 1;
	
	/**
	 * MapreduceBatchJob 运行类型：1-自动调度；2-手工运行；3-调试运行
	 */
	public static final Integer BATCH_JOB_RUN_TYPE_SCHEDULE = 1;
	public static final Integer BATCH_JOB_RUN_TYPE_MANUAL = 2;
	public static final Integer BATCH_JOB_RUN_TYPE_DEBUG = 3;
	
	/**
	 * MapreduceBatchJob 任务类型：1-simple；2-flow
	 */
	public static final Integer BATCH_JOB_JOB_TYPE_SIMPLE = 1;
	public static final Integer BATCH_JOB_JOB_TYPE_FLOW = 2;
	
	/**
     * MapreduceBatchJobInstance 任务状态 0-创建 1-运行中 2-结束
     */
	public static final Integer BATCH_JOB_INSTANCE_JOB_STATE_CREATE = 0;
	public static final Integer BATCH_JOB_INSTANCE_JOB_STATE_RUNNING = 1;
	public static final Integer BATCH_JOB_INSTANCE_JOB_STATE_FINASH = 2;
	
	/**
	 * MapreduceBatchJobStepBranch 步骤状态代码
	 */
	public static final String BATCH_JOB_STEP_BRANCH_STEP_STATUS_UNKNOWN = "UNKNOWN";
	public static final String BATCH_JOB_STEP_BRANCH_STEP_STATUS_EXECUTING = "EXECUTING";
	public static final String BATCH_JOB_STEP_BRANCH_STEP_STATUS_COMPLETED = "COMPLETED";
	public static final String BATCH_JOB_STEP_BRANCH_STEP_STATUS_NOOP = "NOOP";
	public static final String BATCH_JOB_STEP_BRANCH_STEP_STATUS_FAILED = "FAILED";
	public static final String BATCH_JOB_STEP_BRANCH_STEP_STATUS_STOPPED = "STOPPED";
	public static final String BATCH_JOB_STEP_BRANCH_STEP_STATUS_ALL_MATCH = "*";
	
	/**
     * MapreduceBatchJobStepBranch 分支类型：0-next；1-end;2-fail;3-stop
     */
	public static final Integer BATCH_JOB_STEP_BRANCH_BRANCH_TYPE_NEXT = 0;
	public static final Integer BATCH_JOB_STEP_BRANCH_BRANCH_TYPE_END = 1;
	public static final Integer BATCH_JOB_STEP_BRANCH_BRANCH_TYPE_FAIL = 2;
	public static final Integer BATCH_JOB_STEP_BRANCH_BRANCH_TYPE_STOP = 3;
	
	/**
	 * MapreduceBatchJobStepInstance 步骤状态 0-创建 1-运行中 2-结束
	 */
	public static final Integer BATCH_JOB_STEP_INSTANCE_STEP_STATE_CREATE = 0;
	public static final Integer BATCH_JOB_STEP_INSTANCE_STEP_STATE_RUNNING = 1;
	public static final Integer BATCH_JOB_STEP_INSTANCE_STEP_STATE_FINISH = 2;
	
	/**
	 * 状态：0-废弃;1-正常
	 */
	public static final Integer STATUS_FORBADE = 0;
	public static final Integer STATUS_NORMAL = 1;
	
	/**
	 * 登录认证结果 0-认证失败 1-认证成功
	 */
	public static final Integer LOGIN_AUTH_RESULT_FAIL = 0;
	public static final Integer LOGIN_AUTH_RESULT_SUCCESS = 1;
	
	/**
	 * 性别，w-女；m-男；
	 */
	public static final String SEX_WOMEN = "W";
	public static final String SEX_MEN = "M";
	
	/**
	 * 是否锁定，0-否；1-是
	 */
	public static final Integer LOCKED_NO = 0;
	public static final Integer LOCKED_YES = 1;
	
	/**
	 * 定时任务允许并发执行，0-不允许；1-允许
	 */
	public static final Integer QUARTZ_CONCURRENT_NOTALLOW = 0;
	public static final Integer QUARTZ_CONCURRENT_ALLOW = 1;
	
	/**
	 * 定时任务运行结果，0-运行中；1-失败；2-成功
	 */
	public static final int QUARTZ_RESULT_FAIL = 0;
	public static final int QUARTZ_RESULT_RUNNING = 1;
	public static final int QUARTZ_RESULT_SUCCESS = 2;
	
	/**
	 * hbase定义类型，1-表；2-列族；3-列
	 */
	public static final int HBASE_TABLE_DEFINE_TYPE_TABLE = 1;
	public static final int HBASE_TABLE_DEFINE_TYPE_FAMILY = 2;
	public static final int HBASE_TABLE_DEFINE_TYPE_QUALIFIER = 3;
	
	/**
	 * hbase列值是否为数字类型，type=3时有效，0-否(默认字符串)；1-是(转换为double)
	 */
	public static final int HBASE_TABLE_VALUE_ISNOT_NUMBER = 0;
	public static final int HBASE_TABLE_VALUE_IS_NUMBER = 1;
	
	/**
	 * 系统角色代码
	 */
	public static final String ROLE_CODE_SUPER_ADMIN = "super_admin";//超级管理员
	public static final String ROLE_CODE_ADMIN = "admin";//场馆管理员
	public static final String ROLE_CODE_MC = "mc";//销售
	public static final String ROLE_CODE_MC_MANAGER = "mc_manager";//销售经理
	public static final String ROLE_CODE_MC_CHARGE = "mc_charge";//销售主管
	public static final String ROLE_CODE_PT = "pt";//教练角色代码
	public static final String ROLE_CODE_PT_MANAGER = "pt_manager";//教练经理
	public static final String ROLE_CODE_PT_CHARGE = "pt_charge";//教练主管
	public static final String ROLE_CODE_SUPERVISOR = "supervisor";//店长
	public static final String ROLE_CODE_RECEPTION = "reception";//前台
	public static final String ROLE_CODE_SPECIAL_APPROVE = "special_approve";//特殊终审
	public static final String ROLE_CODE_INVESTOR = "investor";//投资人
	
}
