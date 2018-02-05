/**
 * 
 */
package com.xxl.job.admin.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.xxl.job.admin.controller.annotation.PermessionLimit;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.service.XxlJobService;
import com.xxl.job.core.biz.model.JobInfoModel;
import com.xxl.job.core.biz.model.ReturnT;

/**
 *
 * @Description : 通过接口的方式进行任务的添加、修改、执行、暂停、删除和批量删除等操作
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
	private XxlJobService xxlJobService;
	
	// 添加定时任务
	@PostMapping(value="/add", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PermessionLimit(limit = false)
	public ReturnT<JobInfoModel> add(HttpServletRequest request, @RequestBody XxlJobInfo jobInfo) {
		String token = request.getHeader("token");
		if(StringUtils.isEmpty(token) || !xxlJobService.getApiToken().equals(token)){
			return new ReturnT<JobInfoModel>(ReturnT.FAIL_CODE,"unauthorized token...");
		}
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
	public ReturnT<JobInfoModel> reschedule(HttpServletRequest request, @RequestBody XxlJobInfo jobInfo) {
		String token = request.getHeader("token");
		if(StringUtils.isEmpty(token) || !xxlJobService.getApiToken().equals(token)){
			return new ReturnT<JobInfoModel>(ReturnT.FAIL_CODE,"unauthorized token...");
		}
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
	@DeleteMapping(value = "/remove",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PermessionLimit(limit = false)
	public ReturnT<String> remove(HttpServletRequest request, @RequestParam(value ="id") int id) {
		String token = request.getHeader("token");
		if(StringUtils.isEmpty(token) || !xxlJobService.getApiToken().equals(token)){
			return new ReturnT<String>(ReturnT.FAIL_CODE,"unauthorized token...");
		}
	    logger.info("remove jobInfo by id is :{}", id);
		return xxlJobService.remove(id);
	}
	
    // 根据任务描述，删除任务
    @DeleteMapping(value = "/removeByJobDesc",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermessionLimit(limit = false)
    public ReturnT<String> removeByDesc(HttpServletRequest request, @RequestParam(value ="jobDesc") String jobDesc) throws UnsupportedEncodingException {
		String token = request.getHeader("token");
		if(StringUtils.isEmpty(token) || !xxlJobService.getApiToken().equals(token)){
			return new ReturnT<String>(ReturnT.FAIL_CODE,"unauthorized token...");
		}
    	logger.info("Remove jobInfo by desc is :{}", jobDesc);
        List<XxlJobInfo> jobs = xxlJobService.getJobsByJobDesc(jobDesc);
        ReturnT<String> result = new ReturnT<String>();
        if(CollectionUtils.isNotEmpty(jobs)){
            for(XxlJobInfo job : jobs){
                result =  xxlJobService.remove(job.getId());
            }
        }
        return result;
    }
    
    // 根据任务描述，批量删除任务
    @DeleteMapping(value = "/batchRemoveByJobDesc",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermessionLimit(limit = false)
    public ReturnT<String> batchRemoveByDesc(HttpServletRequest request, @RequestBody List<XxlJobInfo> jobInfoList) throws UnsupportedEncodingException {
    	// 判断token是否合法
    	String token = request.getHeader("token");
		if(StringUtils.isEmpty(token) || !xxlJobService.getApiToken().equals(token)){
			return new ReturnT<String>(ReturnT.FAIL_CODE,"unauthorized token...");
		}
		ReturnT<String> result = new ReturnT<String>();
		logger.info("Remove jobInfo by desc is :{}", jobInfoList);
		if(CollectionUtils.isNotEmpty(jobInfoList)){
			for(XxlJobInfo jobInfo : jobInfoList){
				 List<XxlJobInfo> jobs = xxlJobService.getJobsByJobDesc(jobInfo.getJobDesc());
			        if(CollectionUtils.isNotEmpty(jobs)){
			            for(XxlJobInfo job : jobs){
			                result =  xxlJobService.remove(job.getId());
			            }
			        }
			}
			
		}
        return result;
    }
	
	// 暂停任务
	@PostMapping(value = "/pause",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PermessionLimit(limit = false)
	public ReturnT<String> pause(HttpServletRequest request, @RequestParam(value ="id") int id) {
		String token = request.getHeader("token");
		if(StringUtils.isEmpty(token) || !xxlJobService.getApiToken().equals(token)){
			return new ReturnT<String>(ReturnT.FAIL_CODE,"unauthorized token...");
		}
	    logger.info("pause jobInfo by id is :{}", id);
		return xxlJobService.pause(id);
	}

	// 继续任务
	@PostMapping(value = "/resume",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PermessionLimit(limit = false)
	public ReturnT<String> resume(HttpServletRequest request, @RequestParam(value ="id") int id) {
		String token = request.getHeader("token");
		if(StringUtils.isEmpty(token) || !xxlJobService.getApiToken().equals(token)){
			return new ReturnT<String>(ReturnT.FAIL_CODE,"unauthorized token...");
		}
	    logger.info("resume remove jobInfo by id is :{}", id);
		return xxlJobService.resume(id);
	}
	
	// 执行任务
	@PostMapping(value = "/trigger",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PermessionLimit(limit = false)
	public ReturnT<String> triggerJob(HttpServletRequest request, @RequestParam(value ="id") int id) {
		String token = request.getHeader("token");
		if(StringUtils.isEmpty(token) || !xxlJobService.getApiToken().equals(token)){
			return new ReturnT<String>(ReturnT.FAIL_CODE,"unauthorized token...");
		}
	    logger.info("trigger  jobInfo by id is :{}", id);
		return xxlJobService.triggerJob(id);
	}
	
}
