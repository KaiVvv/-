package com.briup.cms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.briup.cms.common.model.entity.Slideshow;
import com.briup.cms.common.model.ext.SlideshowExt;
import com.briup.cms.service.SlideshowService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YuYan
 * @date 2024-01-09 19:42:47
 */
@Service
public class SlideshowServiceImpl implements SlideshowService {
    @Override
    public List<SlideshowExt> listByStatus(String status) {
        return null;
    }

    @Override
    public IPage<SlideshowExt> pageQueryByClause(SlideshowExt slideshowExt, IPage<Slideshow> page) {
        return null;
    }

    @Override
    public SlideshowExt getById(Integer id) {
        return null;
    }

    @Override
    public void saveOrUpdate(SlideshowExt slideshowExt) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void delete(List<Integer> ids) {

    }
}
