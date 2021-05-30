package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper extends MyMapper<Product> {

    List<Product> findProductList(Product product);

    Product getProduct(Product product);

    List<Product> findProductListByPartsCode(Product product);

    Product getProductKtCode(Product product);

    Product getProductByHtJbYs(Product product);

    List<Product> findProductListByStyleHt(Product product);

    List<Product> findProductListByStyle(Product product);

    /**
     * 判断是否存在
     * @param code SKU
     * @param ktCode 客定码
     * @return
     */
    Product getProductId(@Param("code") String code, @Param("ktCode") String ktCode,@Param("exSc")String exSc);

    void updateProductNxbs(@Param("styleId") Long styleId, @Param("nxbs") String nxbs);

    Product getProductListByPartBig(Product product);

}