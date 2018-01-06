/**
 * 
 */
package com.xxl.job.admin.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.xxl.job.admin.controller.annotation.PermessionLimit;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.dao.XxlJobGroupDao;
import com.xxl.job.admin.service.XxlJobService;
import com.xxl.job.core.biz.model.JobInfoModel;
import com.xxl.job.core.biz.model.ReturnT;

/**
 *
 * @Description : 通过接口的方式进行任务的添加，修改，执行，暂停，删除等操作
 * ---------------------------------
 * @Author : deane.administrator
 * @Date : Create in 2018年1月5日 下午9:54:51
 * 
 * Copyright (C)2013-2018 小树盛凯科技 All rights reserved.
 */

@RestController
@RequestMapping(value ="/api")
public class ApiController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private XxlJobGroupDao xxlJobGroupDao;
	@Resource
	private XxlJobService xxlJobService;
	
	// 添加定时任务
	@PostMapping(value="/add", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PermessionLimit(limit = false)
	public ReturnT<JobInfoModel> add(@RequestBody XxlJobInfo jobInfo) {
		logger.info("start add jobInfo, the info is :{}",jobInfo.toString());
		ReturnT<String> returnT = xxlJobService.add(jobInfo);
		ReturnT<JobInfoModel> returnM = new ReturnT<JobInfoModel>(returnT.getCode(),returnT.getMsg());
		// if the content is not null , return the success info of save job information
		String content = returnT.getContent();
		if(StringUtils.isNotEmpty(content)){
		    JobInfoModel jobInfoModel = JSON.parseObject(content, JobInfoModel.class);
		    returnM.setContent(jobInfoModel);
		}
		return returnM;
	}
	
	// 编辑定时任务
	@PostMapping(value = "/reschedule",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PermessionLimit(limit = false)
	public ReturnT<JobInfoModel> reschedule(@RequestBody XxlJobInfo jobInfo) {
	    logger.info("reschedule edit jobInfo, the info is :{}",jobInfo);
	    // if the content is not null , return the success info of modify job information
	    ReturnT<String> returnT = xxlJobService.reschedule(jobInfo);
	    ReturnT<JobInfoModel> returnM = new ReturnT<JobInfoModel>(returnT.getCode(),returnT.getMsg());
        String content = returnT.getContent();
        if(StringUtils.isNotEmpty(content)){
            JobInfoModel jobInfoModel = JSON.parseObject(content, JobInfoModel.class);
            returnM.setContent(jobInfoModel);
        }
        return returnM;
	}
	
	// 删除任务
	@PostMapping(value = "/remove",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PermessionLimit(limit = false)
	public ReturnT<String> remove(@RequestParam(value ="id") int id) {
	    logger.info("remove jobInfo by id is :{}", id);
		return xxlJobService.remove(id);
	}
	
	// 暂停任务
	@PostMapping(value = "/pause",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PermessionLimit(limit = false)
	public ReturnT<String> pause(@RequestParam(value ="id") int id) {
	    logger.info("pause jobInfo by id is :{}", id);
		return xxlJobService.pause(id);
	}

	// 继续任务
	@PostMapping(value = "/resume",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PermessionLimit(limit = false)
	public ReturnT<String> resume(@RequestParam(value ="id") int id) {
	    logger.info("resume remove jobInfo by id is :{}", id);
		return xxlJobService.resume(id);
	}
	
	// 执行任务
	@PostMapping(value = "/trigger",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PermessionLimit(limit = false)
	public ReturnT<String> triggerJob(@RequestParam(value ="id") int id) {
	    logger.info("trigger  jobInfo by id is :{}", id);
		return xxlJobService.triggerJob(id);
	}
	
}
