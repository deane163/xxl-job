/**
 * 
 */
package com.xxl.job.core.biz.model;

import java.io.Serializable;

/**
 *
 *
 * @Description : 返回的jobInfo数据 ---------------------------------
 * @Author : deane.administrator
 * @Date : Create in 2018年1月6日 上午8:38:44
 * 
 *       Copyright (C)2013-2018 小树盛凯科技 All rights reserved.
 */
public class JobInfoModel implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int quartz_id;  // job id

    private int quartz_group;   // group id

    private String quartz_desc;  // the quartz description;
    
    /**
     * 
     */
    public JobInfoModel() {
        super();
    }

    /**
     * @param quartz_id
     * @param quartz_group
     * @param quartz_desc
     */
    public JobInfoModel(int quartz_id, int quartz_group, String quartz_desc) {
        super();
        this.quartz_id = quartz_id;
        this.quartz_group = quartz_group;
        this.quartz_desc = quartz_desc;
    }

    /**
     * @return the quartz_id
     */
    public int getQuartz_id() {
        return quartz_id;
    }

    /**
     * @param quartz_id
     *            the quartz_id to set
     */
    public void setQuartz_id(int quartz_id) {
        this.quartz_id = quartz_id;
    }

    /**
     * @return the quartz_group
     */
    public int getQuartz_group() {
        return quartz_group;
    }

    /**
     * @param quartz_group
     *            the quartz_group to set
     */
    public void setQuartz_group(int quartz_group) {
        this.quartz_group = quartz_group;
    }

    /**
     * @return the quartz_desc
     */
    public String getQuartz_desc() {
        return quartz_desc;
    }

    /**
     * @param quartz_desc
     *            the quartz_desc to set
     */
    public void setQuartz_desc(String quartz_desc) {
        this.quartz_desc = quartz_desc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String str = "{\"quartz_id\":" + quartz_id + ",\"quartz_group\":"
                + quartz_group + ",\"quartz_desc\":\"" + quartz_desc + "\"}";
        return str.replaceAll("\\\\", "");
    }
}
