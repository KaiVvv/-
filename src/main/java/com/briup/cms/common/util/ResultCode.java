package com.briup.cms.common.util;

/**
 * 统一并自定义返回状态码，如有需求可以另外增加
 */
public enum ResultCode {

	/* 成功状态码 */
	SUCCESS(200, "操作成功"),

	/* 参数错误：10001-19999 */
	PARAM_IS_INVALID(10001, "参数无效"),
	PARAM_IS_BLANK(10002, "参数为空"),
	PARAM_TYPE_BIND_ERROR(10003, "参数类型错误"),
	PARAM_NOT_COMPLETE(10004, "参数缺失"),

	/* 用户错误：20001-29999*/
	USER_NOT_LOGIN(20001, "用户未登录"),
	USER_USERNAME_NOT_EXIST(20002, "账号不存在"),
	USER_PASSWORD_INVALID(20003, "密码错误"),
	USER_ACCOUNT_FORBIDDEN(20004, "账号已被禁用"),
	USER_HAS_EXISTED(20005, "用户已存在"),


    TOKEN_EMPTY(20007, "Token令牌不存在！"),
    TOKEN_EXPIRED(20008, "Token令牌已过期！"),
    TOKEN_SIGNATURE_ERROR(20009, "Token令牌内容或格式非法！"),


	/* 业务错误：30001-39999 */
	SPECIFIED_QUESTIONED_USER_NOT_EXIST(30001, "业务逻辑出现问题"),

	/* 系统错误：40001-49999 */
	SYSTEM_INNER_ERROR(40001, "系统内部错误，请稍后重试"),

	/* 数据错误：50001-599999 */
	DATA_NONE(50001, "数据未找到"),
	DATA_WRONG(50002, "数据错误"),
	DATA_EXISTED(50003, "数据已存在"),
	SLIDESHOW_NOT_EXISTED(50004, "轮播图不存在"),
	SLIDESHOW_URL_EXISTED(50005, "轮播图url已存在"),
	CATEGORY_NAME_HAS_EXISTED(50006, "栏目名已存在"),
	PARENT_CATEGORY_IS_INVALID(50007, "父栏目不存在"),
	CATEGORY_NOT_EXIST(50008, "栏目不存在"),
	CATEGORY_HAS_EXISTED(50009, "栏目已存在"),
	CATEGORY_LEVEL_SETTING_ERROR(50010, "级别修改错误"),
	ARTICLE_NOT_EXIST(50011, "文章不存在"),
	ARTICLE_IS_NOT_VISIBLE(50012, "文章不可查看"),
	COMMENT_NOT_EXIST(50013, "评论不存在"),
	REPLY_COMMENT_NOT_EXIST(50014, "引用二级评论不存在"),
	SUB_COMMENT_NOT_EXIST(50015, "二级评论不存在"),

    /* 补充一个 */
    CATEGORY_DELETE_FAILED(50016, "栏目删除失败！"),
    ARTICLE_TITLE_CONTENT_UPDATE_FORBIDDEN(50017, "文章标题或内容禁止修改！"),
    CATEGORY_TEMPLATE_FILE_NOT_EXIST(50019, "模板文件不存在，下载失败！"),
    CATEGORY_EXCEL_CONTENT_ERROR(50020, "表格内数据格式错误，上传失败！"),

	/* 接口错误：60001-69999 */
	INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),
	INTERFACE_OUTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),
	INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
	INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
	INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),

	/* 权限错误：70001-79999 */
	PERMISSION_NO_ACCESS(70001, "无访问权限");


	private final int code;

	private final String message;

	ResultCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int code() {
		return this.code;
	}

	public String message() {
		return this.message;
	}
}
