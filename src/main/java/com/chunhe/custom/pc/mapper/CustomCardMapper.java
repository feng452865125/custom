package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.CustomCard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomCardMapper extends MyMapper<CustomCard> {

    List<CustomCard> findCustomCardList(CustomCard customCardTask);

    CustomCard getCustomCard(CustomCard customCardTask);

    CustomCard getCustomCardByPassword(@Param("password") String password);

    //区间，连续，冻结，解冻
    int customCardEnableByBatchAction(@Param("cardTaskId") Integer cardTaskId,
                                      @Param("cardEnabled") Integer cardEnabled,
                                      @Param("cardCodeBefore") String cardCodeBefore,
                                      @Param("cardCodeStart") Integer cardCodeStart,
                                      @Param("cardCodeEnd") Integer cardCodeEnd,
                                      @Param("userName") String userName);

    //区间，连续，激活
    int customCardActivateByBatchAction(@Param("cardCodeBefore") String cardCodeBefore,
                                        @Param("cardCodeStart") Integer cardCodeStart,
                                        @Param("cardCodeEnd") Integer cardCodeEnd,
                                        @Param("userName") String userName);

    //不连续，冻结，解冻
    int customCardEnableByBatchActionFile(@Param("cardEnabled") Integer cardEnabled,
                                          @Param("cardCodeArr") String cardCodeArr,
                                          @Param("userName") String userName);

    //不连续，激活
    int customCardActivateByBatchActionFile(@Param("cardCodeArr") String cardCodeArr,
                                            @Param("userName") String userName);
}