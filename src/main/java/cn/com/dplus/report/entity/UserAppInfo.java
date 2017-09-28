package cn.com.dplus.report.entity;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @Description:
 * @Author: 詹军政|zhanjz@sondon.net
 * @Date: Created in 上午10:54 17-7-18
 * @Modified By:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserAppInfo extends BaseEntity {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名/用户账号
     */
    private String userName;

    /**
     * 联系人名字
     */
    private String contact;

    /**
     * 联系人号码
     */
    private String phone;

    /**
     * 所属公司名称
     */
    private String companyName;

    /**
     * 公司地址
     */
    private String companyAddress;

    /**
     * QQ 号码
     */
    private String qq;

    /**
     * 行业ID
     */
    private String industryIds;
}
