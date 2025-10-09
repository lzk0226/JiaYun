package com.ruoyi.jiayun.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jiayun.mapper.ReviewadminMapper;
import com.ruoyi.jiayun.domain.Reviewadmin;
import com.ruoyi.jiayun.service.IReviewadminService;

/**
 * 评价Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
@Service
public class ReviewadminServiceImpl implements IReviewadminService 
{
    @Autowired
    private ReviewadminMapper reviewadminMapper;

    /**
     * 查询评价
     * 
     * @param id 评价主键
     * @return 评价
     */
    @Override
    public Reviewadmin selectReviewadminById(Long id)
    {
        return reviewadminMapper.selectReviewadminById(id);
    }

    /**
     * 查询评价列表
     * 
     * @param reviewadmin 评价
     * @return 评价
     */
    @Override
    public List<Reviewadmin> selectReviewadminList(Reviewadmin reviewadmin)
    {
        return reviewadminMapper.selectReviewadminList(reviewadmin);
    }

    /**
     * 新增评价
     * 
     * @param reviewadmin 评价
     * @return 结果
     */
    @Override
    public int insertReviewadmin(Reviewadmin reviewadmin)
    {
        return reviewadminMapper.insertReviewadmin(reviewadmin);
    }

    /**
     * 修改评价
     * 
     * @param reviewadmin 评价
     * @return 结果
     */
    @Override
    public int updateReviewadmin(Reviewadmin reviewadmin)
    {
        return reviewadminMapper.updateReviewadmin(reviewadmin);
    }

    /**
     * 批量删除评价
     * 
     * @param ids 需要删除的评价主键
     * @return 结果
     */
    @Override
    public int deleteReviewadminByIds(Long[] ids)
    {
        return reviewadminMapper.deleteReviewadminByIds(ids);
    }

    /**
     * 删除评价信息
     * 
     * @param id 评价主键
     * @return 结果
     */
    @Override
    public int deleteReviewadminById(Long id)
    {
        return reviewadminMapper.deleteReviewadminById(id);
    }
}
