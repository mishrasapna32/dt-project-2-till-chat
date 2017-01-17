package com.proj2.mvc.dao;

import java.util.List;

import com.proj2.mvc.model.Job;

public interface JobDao {
	void postJob(Job job);
	List<Job> getAllJobs();
	Job getJobDetail(int jobId);
}
