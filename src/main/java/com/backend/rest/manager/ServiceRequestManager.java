package com.backend.rest.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.rest.entity.ServiceRequest;
import com.backend.rest.repository.ServiceRequestRepository;
import com.backend.rest.transfer.RequestSearchCriteria;

@Service
public class ServiceRequestManager {
	
	@Autowired
	private ServiceRequestRepository srvcReqRepository;
	
	public List<ServiceRequest> requestsByCriteria(RequestSearchCriteria ct){
		// find all cases
		//location, serviceType, requestStatus
		System.out.println(ct);
		if(ct.getLocationId() != null && ct.getServiceTypeId() == null && ct.getRequestStatus() == null) {
			return srvcReqRepository.findByLocationId(ct.getLocationId());
		} else if (ct.getLocationId() == null && ct.getServiceTypeId() != null && ct.getRequestStatus() == null) {
			return srvcReqRepository.findByServiceTypeId(ct.getServiceTypeId());
		} else if (ct.getLocationId() == null && ct.getServiceTypeId() == null && ct.getRequestStatus() != null) {
			return srvcReqRepository.findByRequestStatus(ct.getRequestStatus());
		} else if (ct.getLocationId() != null && ct.getServiceTypeId() != null && ct.getRequestStatus() == null) {
			return srvcReqRepository.findByLocationIdAndServiceTypeId(ct.getLocationId(), ct.getServiceTypeId());
		} else if (ct.getLocationId() != null && ct.getServiceTypeId() == null && ct.getRequestStatus() != null) {
			return srvcReqRepository.findByLocationIdAndRequestStatus(ct.getLocationId(), ct.getRequestStatus());
		} else if (ct.getLocationId() == null && ct.getServiceTypeId() != null && ct.getRequestStatus() != null) {
			return srvcReqRepository.findByServiceTypeIdAndRequestStatus(ct.getServiceTypeId(), ct.getRequestStatus());
		} else if (ct.getLocationId() != null && ct.getServiceTypeId() != null && ct.getRequestStatus() != null) {
			return srvcReqRepository.findByLocationIdAndServiceTypeIdAndRequestStatus(ct.getLocationId(), ct.getServiceTypeId(), ct.getRequestStatus());
		} else if (ct.getLocationId() == null && ct.getServiceTypeId() == null && ct.getRequestStatus() == null ) {
			return srvcReqRepository.findAll();
		}
		return null;
	}
	
	

}
