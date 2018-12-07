package com.guangde.business.dao;

import java.util.List;

import com.guangde.business.entry.Capitalinout;

public interface CapitalinoutMapper extends BaseMapper<Capitalinout>
{
    Double countByParam(Capitalinout capitalinout);
    
    List<Capitalinout> queryCapitalinoutList(Capitalinout capitalinout);

    
    Integer countChargeNumByParam(Capitalinout capitalinout);
    
    
    /**根据param查询资金明细*/
    Capitalinout queryCapitalinoutByParam(Capitalinout param);
}
