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
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户token编号
     */
    @TableId(value = "user_token_no", type = IdType.AUTO)
    private Long user_token_no;

    /**
     * token
     */
    @TableField("user_token")
    private String user_token;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long user_id;

    /**
     * token的类型：1APP、2小程序、3web
     */
    @TableField("token_type")
    private Integer token_type;


}
