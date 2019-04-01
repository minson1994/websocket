package com.example.demo.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Minson.
 * @since 2019-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long user_id;

    /**
     * 账号
     */
    @TableField("user_code")
    private String user_code;

    /**
     * 密码
     */
    @TableField("user_password")
    private String user_password;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;


}
