package cn.com.dplus.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.dplus.report.entity.mysql.DevBind;
import ex.cn.com.dplus.mysql.base.BaseMapper;

public interface IDevBind extends BaseMapper<DevBind>{

	public List<DevBind> selectUserBind(@Param("dSn")String dSn,@Param("userId")String userId);
}
