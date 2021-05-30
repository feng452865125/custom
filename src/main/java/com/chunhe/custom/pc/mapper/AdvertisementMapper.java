package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.Advertisement;

import java.util.List;

public interface AdvertisementMapper extends MyMapper<Advertisement> {

    List<Advertisement> findAdvertisementList(Advertisement advertisement);

    Advertisement getAdvertisement(Advertisement advertisement);
}