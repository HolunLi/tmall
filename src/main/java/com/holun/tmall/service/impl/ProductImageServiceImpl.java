package com.holun.tmall.service.impl;

import com.holun.tmall.entity.ProductImage;
import com.holun.tmall.entity.ProductImageExample;
import com.holun.tmall.mapper.ProductImageMapper;
import com.holun.tmall.service.ProductImageService;
import com.holun.tmall.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    private ProductImageMapper productImageMapper;

    @Autowired
    public void setProductImageMapper(ProductImageMapper productImageMapper) {
        this.productImageMapper = productImageMapper;
    }

    @Override
    public int addProductImage(ProductImage productImage) {
        return productImageMapper.insertSelective(productImage);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    @Override
    public void uploadProductImage(ProductImage productImage, MultipartFile[] images, String... imagesLocation) {
        for (MultipartFile image : images) {
            addProductImage(productImage);

            String imageName = productImage.getId() + ".jpg";
            File file = new File(imagesLocation[0], imageName);
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            ImageUtil.uploadImageToDestination(image, file);
            //如果产品图片的类型是单个类型，还需要更改产品图片的大小尺寸
            if (ProductImageService.type_single.equals(productImage.getType())) {
                //将上传的单个产品图片大小改为小尺寸，并写入到指定路径下存储
                ImageUtil.resizeImage(file, 56, 56, new File(imagesLocation[1], imageName));
                //将上传的单个产品图片大小改为中尺寸，并写入到指定路径下存储
                ImageUtil.resizeImage(file, 217, 190, new File(imagesLocation[2], imageName));
            }
        }
    }

    @Override
    public int deleteProductImageById(int id) {
        return productImageMapper.deleteByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    @Override
    public void deleteUploadProductImage(ProductImage productImage, String... imagesLocation) {
        deleteProductImageById(productImage.getId());

        String imageName = productImage.getId() + ".jpg";
        ImageUtil.deleteUploadImage(imagesLocation[0] + "/" + imageName);
        //删除小号图片以及中号图片
        if (ProductImageService.type_single.equals(productImage.getType()))
            ImageUtil.deleteUploadImage(imagesLocation[1] + "/" + imageName, imagesLocation[2] + "/" + imageName);
    }

    @Override
    public ProductImage queryProductImageById(int id) {
        return productImageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ProductImage> list(int pid, String type) {
        ProductImageExample example = new ProductImageExample();
        //根据pid（产品id），图片类型（type）查找对应的产品图片
        example.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type);

        return productImageMapper.selectByExample(example);
    }
}
