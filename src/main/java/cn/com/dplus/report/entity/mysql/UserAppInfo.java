package cn.com.dplus.report.entity.mysql;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @Description:
 * @Author: 詹军政|zhanjz@sondon.net
 * @Date: Created in 上午10:54 17-7-18
 * @Modified By:
 */
@Data
@Table(name="dp_app_user_info",catalog = "user_app")
@EqualsAndHashCode(callSuper = false)
public class UserAppInfo extends BaseEntity {

	private static final long serialVersionUID = -5021782954021495024L;

    @Id
    @Column(name = "user_id", nullable = true, updatable = false)
    private String userId;

    /** 用户名/用户账号 */
    @Column(name = "user_name", nullable = true, updatable = false,length = 32)
    private String userName;

    /** 用户的真实姓名*/
    @Column(name = "full_name")
    private String fullName;

    /** 联系人名字 */
    @Column(name = "contact", nullable = true)
    private String contact;

    /** 联系人号码  */
    @Column(name = "phone")
    private String phone;

    /** 所属公司名称  */
    @Column(name = "company_name")
    private String companyName;

    /** 公司地址  */
    @Column(name = "company_address")
    private String companyAddress;

    /** 职位*/
    @Column(name = "position")
    private String position;

    /** QQ 号码  */
    @Column(name = "qq")
    private String qq;

    /** 行业ID */
    @Column(name = "industry_ids")
    private String industryIds;
}
