package com.study.base.core.pager;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * ClassName: PagerModel
 * Description:
 * @Author: luohx
 * Date: 2022/2/22 下午3:40
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public class PagerModel {
    @Min(
            value = 1L,
            message = "当前页不能小于1"
    )
    @NotNull(message = "页码不能为空")
    private Integer pageNum = 1;

    @Min(
            value = 1L,
            message = "页码大小不能小于1"
    )
    @NotNull(message = "每页条数不能为空")
    private Integer pageSize = 20;

    public PagerModel() {
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PagerModel)) {
            return false;
        } else {
            PagerModel other = (PagerModel) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$pageNum = this.getPageNum();
                Object other$pageNum = other.getPageNum();
                if (this$pageNum == null) {
                    if (other$pageNum != null) {
                        return false;
                    }
                } else if (!this$pageNum.equals(other$pageNum)) {
                    return false;
                }

                Object this$pageSize = this.getPageSize();
                Object other$pageSize = other.getPageSize();
                if (this$pageSize == null) {
                    if (other$pageSize != null) {
                        return false;
                    }
                } else if (!this$pageSize.equals(other$pageSize)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof PagerModel;
    }

    @Override
    public int hashCode() {
        int result = 1;
        Object $pageNum = this.getPageNum();
        result = result * 59 + ($pageNum == null ? 43 : $pageNum.hashCode());
        Object $pageSize = this.getPageSize();
        result = result * 59 + ($pageSize == null ? 43 : $pageSize.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "PagerModel(pageNum=" + this.getPageNum() + ", pageSize=" + this.getPageSize() + ")";
    }
}
