package com.chunhe.custom.pc.model.DiamondData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HB2Response {

    private HB2Page page;

    private HB2Diamond[] list;

    private String app_check_code;//0 成功 1-999999 失败

    private String app_check_desc;//下单成功为空白，2020年11月9日21:40:59，下单成功后有值

    private String order_no;

}
