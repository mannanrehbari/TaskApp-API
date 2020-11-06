package com.backend.rest.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.rest.entity.ServiceRequest;
import com.backend.rest.repository.ServiceRequestRepository;
import com.backend.rest.transfer.RequestFilter;

@Service
public class ServiceRequestManager {
	
	@Autowired
	private ServiceRequestRepository srvcReqRepository;
	
	public List<ServiceRequest> requestsByCriteria(RequestFilter ct){
		// find all cases
		//location, serviceType, requestStatus
		System.out.println(ct);
		if(ct.getLocationId() != null && ct.getServiceTypeId() == null && ct.getRequestStatus() == null) {
			return srvcReqRepository.findByLocationIdAndRequestDateBetween(ct.getLocationId(), ct.getMinDate(), ct.getMaxDate());
		} else if (ct.getLocationId() == null && ct.getServiceTypeId() != null && ct.getRequestStatus() == null) {
			return srvcReqRepository.findByServiceTypeIdAndRequestDateBetween(ct.getServiceTypeId(), ct.getMinDate(), ct.getMaxDate());
		} else if (ct.getLocationId() == null && ct.getServiceTypeId() == null && ct.getRequestStatus() != null) {
			return srvcReqRepository.findByRequestStatusAndRequestDateBetween(ct.getRequestStatus(), ct.getMinDate(), ct.getMaxDate());
		} else if (ct.getLocationId() != null && ct.getServiceTypeId() != null && ct.getRequestStatus() == null) {
			return srvcReqRepository.findByLocationIdAndServiceTypeIdAndRequestDateBetween(ct.getLocationId(), ct.getServiceTypeId(), ct.getMinDate(), ct.getMaxDate());
		} else if (ct.getLocationId() != null && ct.getServiceTypeId() == null && ct.getRequestStatus() != null) {
			return srvcReqRepository.findByLocationIdAndRequestStatusAndRequestDateBetween(ct.getLocationId(), ct.getRequestStatus(), ct.getMinDate(), ct.getMaxDate());
		} else if (ct.getLocationId() == null && ct.getServiceTypeId() != null && ct.getRequestStatus() != null) {
			return srvcReqRepository.findByServiceTypeIdAndRequestStatusAndRequestDateBetween(ct.getServiceTypeId(), ct.getRequestStatus(), ct.getMinDate(), ct.getMaxDate());
		} else if (ct.getLocationId() != null && ct.getServiceTypeId() != null && ct.getRequestStatus() != null) {
			return srvcReqRepository.findByLocationIdAndServiceTypeIdAndRequestStatusAndRequestDateBetween(ct.getLocationId(), ct.getServiceTypeId(), ct.getRequestStatus(), ct.getMinDate(), ct.getMaxDate());
		} else if (ct.getLocationId() == null && ct.getServiceTypeId() == null && ct.getRequestStatus() == null) {
			return srvcReqRepository.findAllByRequestDateBetween(ct.getMinDate(), ct.getMaxDate());
		}
		return null;
	}

}
