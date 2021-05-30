package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.pc.mapper.PartsMapper;
import com.chunhe.custom.pc.mapper.ProductMapper;
import com.chunhe.custom.pc.mapper.RelYsMapper;
import com.chunhe.custom.pc.model.Parts;
import com.chunhe.custom.pc.model.Product;
import com.chunhe.custom.pc.model.RelYs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by white 2018年8月2日14:32:49
 */
@Service
public class RelYsService extends BaseService<RelYs> {

    @Autowired
    private RelYsMapper relYsMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private PartsMapper partsMapper;

    /**
     * 戒臂和花头的所有关联表
     *
     * @return
     */
    public List<RelYs> findRelYsList() {
        //花头组的列表
        Parts parts = new Parts();
        parts.setType(Parts.TYPE_FLOWER_HEAD);
        parts.setIsShow(Parts.IS_SHOW_TRUE);
        List<Parts> htList = partsMapper.findPartsList(parts);
        if(htList == null){
            System.out.println("htList = null");
        }else {
            System.out.println("htList.size=" + htList.size());
        }
        //所有的匹配列表
        List<RelYs> relYsList = relYsMapper.findRelYsList();
        if(relYsList == null){
            System.out.println("relYsList = null");
        }else {
            System.out.println("relYsList.size=" + relYsList.size());
        }
        for (int i = 0; i < relYsList.size(); i++) {
            RelYs rel = relYsList.get(i);
            Product product = new Product();
            product.setExSc(rel.getProductExSc());
            rel.setHandList(productService.checkHandList(product));
            //检查最佳匹配
            if (this.checkInList(htList, rel)) {
                rel.setBest(RelYs.BEST_TRUE);
            } else {
                rel.setBest(RelYs.BEST_FALSE);
            }
        }
        return relYsList;
    }

    /**
     * @param relYs
     * @return
     */
    public RelYs getRelYs(RelYs relYs) {
        RelYs ys = relYsMapper.getRelYs(relYs);
        return ys;
    }

    /**
     * 当前组合是否最佳匹配的戒臂样式
     *
     * @param htList
     * @param rel
     * @return
     */
    public Boolean checkInList(List<Parts> htList, RelYs rel) {
        for (int i = 0; i < htList.size(); i++) {
            Parts ht = htList.get(i);
            if (ht.getYs().equals(rel.getHtYs())) {
                if (ht.getJbYsRecommend().contains(rel.getJbYs())) {
                    return true;
                }
            }
        }
        return false;
    }

}
