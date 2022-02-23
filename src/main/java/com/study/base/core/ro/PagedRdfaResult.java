package com.study.base.core.ro;

import com.study.base.core.base.RdfaObject;
import com.study.base.core.constants.RdfaResultConstants;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: PagedRdfaResult
 * Description:
 *
 * @Author: luohx
 * Date: 2022/2/22 下午5:02
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public class PagedRdfaResult<T extends Serializable> implements Serializable{
    private static final long serialVersionUID = 3411472428234824701L;

    private boolean success = true;
    private String code;
    private String message;

    private int pageNum = 0;
    private int pageSize = 1;  //default set to 1 not 0 for division
    private long totalPages = 0;
    private long totalCount = 0;
    private List<T> data;


    public PagedRdfaResult(){

    }

    /**
     * Constructs a new PagedRdfaResult with the specified success, code, message, usually in fail status.
     *
     * @param success   the return result is defined by the user. The detail
     *                  success is saved for later retrieval by the {@link #isSuccess()} method.
     * @param code      the return code.The detail code is saved for
     *                  later retrieval by the {@link #getCode()} method.
     * @param message   the detail message. The detail message is saved for
     *                  later retrieval by the {@link #getMessage()} method.
     */
    public PagedRdfaResult(boolean success, String code, String message) {
        this(success, code, message, null);
    }

    /**
     * Constructs a new PagedRdfaResult with the specified success, code, message, usually in fail status.
     *
     * @param success   the return result is defined by the user. The detail
     *                  success is saved for later retrieval by the {@link #isSuccess()} method.
     * @param code      the return code.The detail code is saved for
     *                  later retrieval by the {@link #getCode()} method.
     * @param message   the detail message. The detail message is saved for
     *                  later retrieval by the {@link #getMessage()} method.
     * @param data      the data in response. The detail data is saved for
     *                  later retrieval by the {@link #getData()} method.
     */
    public PagedRdfaResult(boolean success, String code, String message, List<T> data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T extends Serializable>  PagedRdfaResult<T> success(List<T> data) {
        PagedRdfaResult rest = new PagedRdfaResult();
        rest.setCode(RdfaResultConstants.SUCCESS_CODE);
        rest.setMessage(RdfaResultConstants.SUCCESS_MESSAGE);
        rest.setData(data);
        rest.setSuccess(true);
        return rest;
    }

    public static <T extends Serializable>  PagedRdfaResult<T> success(int pageNum, int pageSize, long totalCount, List<T> data) {
        PagedRdfaResult rest = new PagedRdfaResult();
        rest.setCode(RdfaResultConstants.SUCCESS_CODE);
        rest.setMessage(RdfaResultConstants.SUCCESS_MESSAGE);
        rest.setData(data);
        rest.setPageSize(pageSize);
        rest.setTotalCount(totalCount);
        rest.setPageNum(pageNum);
        rest.setSuccess(true);
        return rest;
    }

    public static<T extends Serializable> PagedRdfaResult<T> fail(String code,String message) {
        PagedRdfaResult<T> rest = new PagedRdfaResult<T>();
        rest.setCode(code);
        rest.setMessage(message);
        rest.setSuccess(false);
        return rest;
    }

    /**
     * 获取当前页码
     * @return
     */
    public int getPageNum() {
        return pageNum;
    }

    /**
     * 设置当前页码
     * @param pageNum
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
        if(pageNum < 1) {
            this.pageNum = 1;
        } else if(pageNum > getTotalPages()) {
            this.pageNum = (int)getTotalPages();
        }
    }

    /**
     * 获取每页显示的数据量
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页显示的数据量
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        // ignore pageSize value when pageSize is less than zero
        if (pageSize <= 0 ) {
            return;
        }

        this.pageSize = pageSize;
    }

    /**
     * 获取总页数
     * @return
     */
    public long getTotalPages() {
        return totalPages;
    }

    /**
     * 设置总页数
     * @param totalPages
     */
    private void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * 获取总记录数
     * @return
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总记录数
     * @param totalCount
     */
    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
        //根据总记录数自动计算总页数
        long result = totalCount / getPageSize();
        if(totalCount % pageSize != 0) {
            result += 1;
        }
        setTotalPages(result);
    }

    /**
     * 获取当前页的数据集
     * @return
     */
    public List<T> getData() {
        return data;
    }

    /**
     * 设置当前页的数据集
     * @param data
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toLog() {
        int length=20;
        String data_str=null;
        if(code!=null){
            length +=code.length();
        }
        if(message!=null){
            length +=message.length();
        }
        if(data != null){
            for(T o : data){
                if(o == null){
                    continue;
                }else{
                    if(o instanceof RdfaObject) {
                        data_str = ((RdfaObject) o).toLog();
                    } else {
                        data_str = o.toString();
                    }
                    length += data_str.length();
                }
            }

        }
        StringBuilder sb = new StringBuilder(length);
        sb.append("code:");
        sb.append(code);
        sb.append(" message:");
        sb.append(message);
        sb.append(" data:");
        sb.append(data_str);

        return  sb.toString();
    }
}