package com.briup.cms.common.util;

/**
 * @author YuYan
 * @date 2023-11-29 10:05:35
 */
public class GlobalConstants {

    /**
     * MyBatis映射接口所在路径
     */
    public final static String MYBATIS_MAPPER_SCAN
            = "com.briup.cms.dao";

    /**
     * Token字符串在请求头部中的字段名
     */
    public final static String TOKEN_HEADER_NAME = "Authorization";

    /**
     * 逻辑删除标志位值，1表示删除，0表示未删除
     */
    public final static Integer LOGIC_DELETED_FLAG_VALUE = 1;
    public final static Integer LOGIC_NOT_DELETED_FLAG_VALUE = 0;

    public final static String LOGIC_DELETED_STATUS_VALUE = "已删除";
    public final static String LOGIC_NOT_DELETED_STATUS_VALUE = "未删除";

    /**
     * 数据状态可用字段值
     */
    public final static String DATA_STATUS_ENABLE = "启用";
    /**
     * 数据状态不可用字段值
     */
    public final static String DATA_STATUS_DISABLE = "禁用";

    /**
     * 用户的默认性别
     */
    public final static String USER_DEFAULT_GENDER = "男";

    /**
     * 新建用户时默认的用户状态
     */
    public final static String USER_DEFAULT_STATUS = "启用";
    /**
     * 新建用户时默认的角色ID
     */
    public final static Integer USER_DEFAULT_ROLE_ID = 3;
    /**
     * 新建用户时默认的VIP状态
     */
    public final static Integer USER_DEFAULT_VIP = 0;

    /**
     * 文章默认的审核状态值
     */
    public final static String ARTICLE_DEFAULT_STATUS = "未审核";
    /**
     * 文章审核通过状态值
     */
    public final static String ARTICLE_STATUS_REVIEW_PASS = "审核通过";
    /**
     * 文章审核未通过状态值
     */
    public final static String ARTICLE_STATUS_REVIEW_NOT_PASS = "审核未通过";

    public final static int DEFAULT_ARTICLE_QUERY_INCLUDE_COMMENT_NUMBER = 4;

    public final static String CATEGORY_QUERY_TYPE_CHILD = "child";

    public final static String CATEGORY_QUERY_TYPE_PARENT = "parent";

    public final static String COMMENT_QUERY_TYPE = "parent";

    public final static String SUB_COMMENT_QUERY_TYPE = "child";


}
