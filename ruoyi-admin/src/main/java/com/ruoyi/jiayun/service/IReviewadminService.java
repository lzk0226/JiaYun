package com.ruoyi.jiayun.service;

import java.util.List;
import com.ruoyi.jiayun.domain.Reviewadmin;

/**
 * 评价Service接口
 * 
 * @author ruoyi
 * @date 2025-10-09
 */
public interface IReviewadminService 
{
    /**
     * 查询评价
     * 
     * @param id 评价主键
     * @return 评价
     */
    public Reviewadmin selectReviewadminById(Long id);

    /**
     * 查询评价列表
     * 
     * @param reviewadmin 评价
     * @return 评价集合
     */
    public List<Reviewadmin> selectReviewadminList(Reviewadmin reviewadmin);

    /**
     * 新增评价
     * 
     * @param reviewadmin 评价
     * @return 结果
     */
    public int insertReviewadmin(Reviewadmin reviewadmin);

    /**
     * 修改评价
     * 
     * @param reviewadmin 评价
     * @return 结果
     */
    public int updateReviewadmin(Reviewadmin reviewadmin);

    /**
     * 批量删除评价
     * 
     * @param ids 需要删除的评价主键集合
     * @return 结果
     */
    public int deleteReviewadminByIds(Long[] ids);

    /**
     * 删除评价信息
     * 
     * @param id 评价主键
     * @return 结果
     */
    public int deleteReviewadminById(Long id);
}
