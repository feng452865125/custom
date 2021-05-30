package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.Parts;
import com.chunhe.custom.pc.model.PartsMiddle;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface PartsMapper extends MyMapper<Parts> {

    List<Parts> findPartsList(Parts parts);

    Parts getParts(Parts parts);

    List<Parts> findRelPartsList(Parts parts);

    Parts getYsRecommend(Parts parts);

    List<Parts> findPartsDiamondList(Parts parts);

    List<Parts> findPartsListByYsGroup(Parts parts);

    Parts getDiamondByBaseScore(Parts parts);

    Parts getMinParts(Parts parts);

    /**
     * 获取列表
     *
     * @param parts
     * @return
     */
    List<Parts> partsList(Parts parts);


    /**
     * 获取创建时间在24小时内的列表
     *
     * @param parts
     * @return
     */
    List<Parts> findCreatePartsList(Parts parts);


    /**
     * 获取失效时间在24小时内的列表
     *
     * @param parts
     * @return
     */
    List<PartsMiddle> findExpirePartsList(Parts parts);


    /**
     * 根据钻石编号获取砖石信息
     *
     * @param exZsBh
     * @return
     */
    Parts findPartsid(@Param("exZsBh") String exZsBh, @Param("code") String code);


    Parts priceInquiry(Parts parts);

    public void setDiamondExpire(Parts parts);

    public Parts getThirdStone(Parts parts);

    public int enablePartsByImport(@Param("zsbhArr") String zsbhArr, @Param("enableType") Integer enableType);

    public int enablePartsByOver(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    public List<Parts> findThirdStoneList(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("enableOver") Integer enableOver);

    public void setDiamondEnableOff(Parts parts);

    public void setDiamondEnableOn(Parts parts);

    public void setDiamondExpireByFilterAddress(Parts parts);

    public List<Parts> findAllLockEnableDiamondList();

    public int updateEnableByLocation(@Param("location") String location,
                                      @Param("enableStatus") Integer enableStatus,
                                      @Param("company") String company);

    public Parts getLatelyExpireParts(Parts parts);
}