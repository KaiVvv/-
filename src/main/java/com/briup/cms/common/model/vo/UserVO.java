package com.briup.cms.common.model.vo;

import com.briup.cms.common.model.ext.RoleExt;
import com.briup.cms.common.model.ext.SlideshowExt;
import com.briup.cms.common.model.ext.UserExt;
import com.briup.cms.common.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 视图对象 - 轮播图
 *
 * @author YuYan
 * @date 2023-11-30 09:22:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    @JsonProperty("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    @JsonIgnoreProperties(allowGetters = false)
    private String password;
    @JsonProperty("avatar")
    private String avatar;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("status")
    private String status;
    @JsonProperty("email")
    private String email;
    @JsonProperty("registerTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date registerTime;
    @JsonProperty("birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @JsonProperty("role")
    private RoleVO roleVO;

    public static List<UserVO> toVO(List<UserExt> userExts) {
        return userExts.stream()
                .map(UserVO::toVO)
                .collect(Collectors.toList());
    }

    public static UserVO toVO(UserExt userExt) {
        if (ObjectUtil.isNull(userExt)) {
            return null;
        }
        return UserVO.builder()
                .id(userExt.getId())
                .username(userExt.getUsername())
                // .password(userExt.getPassword())
                .avatar(userExt.getAvatar())
                .phone(userExt.getPhone())
                .gender(userExt.getGender())
                .status(userExt.getStatus())
                .email(userExt.getEmail())
                .registerTime(userExt.getRegisterTime())
                .birthday(userExt.getBirthday())
                .roleVO(RoleVO.toVO(userExt.getRoleExt()))
                .build();
    }

}
