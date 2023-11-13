package com.backend.rest.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.rest.entity.TaskerDetails;
import com.backend.rest.repository.TaskerDetailsRepository;

@Service
public class TaskerDetailsManager {
	
	@Autowired
	private TaskerDetailsRepository detailsRepository;
	
	public List<TaskerDetails> getTaskersWithoutImage(Long serviceTypeId){
		List<TaskerDetails> list = detailsRepository.findByServiceTypeId(serviceTypeId);
		list.forEach(item -> {item.setCnicImg(null); item.setCnicNo(null);});
		return list;
	}
}
