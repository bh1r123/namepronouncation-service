package com.wellsafrgo.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wells.constants.ActionEnum;
import com.wells.constants.NotificationTypeEnum;
import com.wells.constants.NotificationsStatusEnum;
import com.wells.constants.OptedEnum;
import com.wells.constants.OptedFormatEnum;
import com.wells.constants.OverridenStatusEnum;
import com.wells.constants.StatusEnum;
import com.wellsafrgo.exception.InvalidArgumentException;
import com.wellsafrgo.exception.ResourceNotFoundException;
import com.wellsafrgo.model.CountryData;
import com.wellsafrgo.model.CountryRecord;
import com.wellsafrgo.model.NPSDomain;
import com.wellsafrgo.model.NamePronounciationRecord;
import com.wellsafrgo.model.NotificationsRequest;
import com.wellsafrgo.model.empUpdateRecord;
import com.wellsfargo.response.EmpRecordResponse;
import com.wellsfargo.response.NameSearchResponse;

@Service
@Transactional
public class NPSService {

	@Autowired
	private NPSRepository npsRepository;
	
	@Autowired
	private NotificationsService notificationsService;
	
	@Autowired
	private CountryRepository countryRepository;
	
	public boolean save(NPSDomain userData) {
		try {
			if (userData.getEmpId() == null || userData.getEmpId().isEmpty()) {
				throw new InvalidArgumentException(" EmpID is required to save new record.");
			}

			NamePronounciationRecord npsData = new NamePronounciationRecord(userData.getEmpId(), userData.getfName().toLowerCase(),
					userData.getlName().toLowerCase(), userData.getpName().toLowerCase(), userData.getCountry_code(),
					userData.getMultipartFile().getBytes(), userData.getCreated_by(), userData.getModified_by(), userData.getOptedformat(), null, StatusEnum.ACTIVE.getValue(), 
					OverridenStatusEnum.NEW.getValue(),userData.getChannel(), null, OptedEnum.YES.getValue());
			NamePronounciationRecord record = npsRepository.save(npsData);
			if (record != null) {
				return true;
			}
		} catch (IOException e) {
              System.out.println("Exception occured trying to fetch the byte data of audio file.");
		}
		return false;
	}

	public NamePronounciationRecord getFilebyEmpId(String empId) {
		if (empId == null || empId.isEmpty()) {
			throw new InvalidArgumentException(" EmpID is required to save new record.");
		}
		NamePronounciationRecord record = npsRepository.findById(empId).get();
		if(record == null) {
			throw new ResourceNotFoundException("NO EMP RECORDS FOUND FOR THE NAME/ID : "+ empId);
		}
		return record;
	}
	
	public NameSearchResponse getAllEmpRecords(String status) {
		NameSearchResponse response = new NameSearchResponse();
		List<NamePronounciationRecord> records = npsRepository.findAll();
		
		List<EmpRecordResponse> allEmprecords = records.stream().map(record -> {
			 EmpRecordResponse empInfo = new EmpRecordResponse();
			  empInfo.setEmpId(record.getEmpid());
			  empInfo.setFirst_name(record.getFirst_name());
			  empInfo.setLast_name(record.getLast_name());
			  empInfo.setPreferred_name(record.getPreferred_name());
			  empInfo.setCountry(record.getCountry_code());
			  empInfo.setOptdFormat(record.getOpted_format());
			  empInfo.setOpted(record.getOpted());
			  return empInfo;
		  }).collect(Collectors.toList());
		
		List<EmpRecordResponse> newEmprecords = records.stream()
				.filter(record -> record.getOverriden_Status().equalsIgnoreCase(OverridenStatusEnum.NEW.getValue()))
				.map(record -> {
					EmpRecordResponse empInfo = new EmpRecordResponse();
					empInfo.setEmpId(record.getEmpid());
					empInfo.setFirst_name(record.getFirst_name());
					empInfo.setLast_name(record.getLast_name());
					empInfo.setPreferred_name(record.getPreferred_name());
					empInfo.setCountry(record.getCountry_code());
					empInfo.setOptdFormat(record.getOpted_format());
					empInfo.setOpted(record.getOpted());
					return empInfo;
				}).collect(Collectors.toList());
		
		List<EmpRecordResponse> approvedEmprecords = records.stream().filter(
				record -> record.getOverriden_Status().equalsIgnoreCase(OverridenStatusEnum.APPROVED.getValue()))
				.map(record -> {
					EmpRecordResponse empInfo = new EmpRecordResponse();
					empInfo.setEmpId(record.getEmpid());
					empInfo.setFirst_name(record.getFirst_name());
					empInfo.setLast_name(record.getLast_name());
					empInfo.setPreferred_name(record.getPreferred_name());
					empInfo.setCountry(record.getCountry_code());
					empInfo.setOptdFormat(record.getOpted_format());
					 empInfo.setOpted(record.getOpted());
					return empInfo;
				}).collect(Collectors.toList());
		
		List<EmpRecordResponse> rejectedEmprecords = records.stream().filter(
				record -> record.getOverriden_Status().equalsIgnoreCase(OverridenStatusEnum.REJECTED.getValue()))
				.map(record -> {
			 EmpRecordResponse empInfo = null;
			  empInfo = new EmpRecordResponse();
			  empInfo.setEmpId(record.getEmpid());
			  empInfo.setFirst_name(record.getFirst_name());
			  empInfo.setLast_name(record.getLast_name());
			  empInfo.setPreferred_name(record.getPreferred_name());
			  empInfo.setCountry(record.getCountry_code());
			  empInfo.setOptdFormat(record.getOpted_format());
			  empInfo.setOpted(record.getOpted());
			  return empInfo;
		  }).collect(Collectors.toList());
		
		List<EmpRecordResponse> pendingEmprecords = records.stream()
				.filter(record -> record.getOverriden_Status().equalsIgnoreCase(OverridenStatusEnum.PENDING.getValue()))
				.map(record -> {
					EmpRecordResponse empInfo = null;
					empInfo = new EmpRecordResponse();
					empInfo.setEmpId(record.getEmpid());
					empInfo.setFirst_name(record.getFirst_name());
					empInfo.setLast_name(record.getLast_name());
					empInfo.setPreferred_name(record.getPreferred_name());
					empInfo.setCountry(record.getCountry_code());
					empInfo.setOptdFormat(record.getOpted_format());
					 empInfo.setOpted(record.getOpted());
					return empInfo;
				}).collect(Collectors.toList());
		
		if(status == null || status.isEmpty()) {
			response.setNpsList(allEmprecords);
			response.setSearchNameCount(allEmprecords.size());
		} else if (status.equalsIgnoreCase(OverridenStatusEnum.APPROVED.getValue())) {
			response.setNpsList(approvedEmprecords);
			response.setSearchNameCount(approvedEmprecords.size());
		} else if (status.equalsIgnoreCase(OverridenStatusEnum.PENDING.getValue())) {
			response.setNpsList(pendingEmprecords);
			response.setSearchNameCount(pendingEmprecords.size());
		} else if (status.equalsIgnoreCase(OverridenStatusEnum.REJECTED.getValue())) {
			response.setNpsList(rejectedEmprecords);
			response.setSearchNameCount(rejectedEmprecords.size());
		} else if (status.equalsIgnoreCase(OverridenStatusEnum.NEW.getValue())) {
			response.setNpsList(newEmprecords);
			response.setSearchNameCount(newEmprecords.size());
		} else {
			response.setNpsList(allEmprecords);
			response.setSearchNameCount(allEmprecords.size());
		}
		return response;
	}
	
	public NameSearchResponse getFilesforName(String name, String countryCode) {
		Map<String, EmpRecordResponse> map = new HashMap<>();
		if (name != null && !name.isEmpty()) {
			List<String> list = Stream.of(name.split(" ")).collect(Collectors.toList());
			list.forEach(item -> {
				List<EmpRecordResponse> dlist = getFilesbyName(item,countryCode);
				dlist.forEach(i -> {
					map.put(i.getEmpId(), i);
				});
				//domainList.addAll(getFilesbyName(item)) ;
			});
		}
		List<EmpRecordResponse> domainList = (List<EmpRecordResponse>) new ArrayList(map.values());
		NameSearchResponse response = new NameSearchResponse();
		response.setNpsList(domainList);
		response.setSearchNameCount(domainList.size());
		return response;
	}
	
	public List<EmpRecordResponse> getFilesbyCountry(String cuntry) {
		List<NamePronounciationRecord> records = npsRepository.getFilesbycountry( cuntry);

		List<EmpRecordResponse> npsEmpList = records.stream().map(record -> {
			EmpRecordResponse empInfo = new EmpRecordResponse();
			empInfo.setEmpId(record.getEmpid());
			  empInfo.setFirst_name(record.getFirst_name());
			  empInfo.setLast_name(record.getLast_name());
			  empInfo.setPreferred_name(record.getPreferred_name());
			  empInfo.setCountry(record.getCountry_code());
			  empInfo.setOptdFormat(record.getOpted_format());
			  empInfo.setOpted(record.getOpted());
			  return empInfo;
		}).collect(Collectors.toList());

		if (npsEmpList.size() <= 0) {
			throw new ResourceNotFoundException("NO EMP RECORDS FOUND FOR THE NAME : " + cuntry);
		}
		return npsEmpList;
	}
	
	
	public List<EmpRecordResponse> getFilesbyName(String name, String countryCode)  {
		
		List<NamePronounciationRecord> records = npsRepository.getFilesbyName(name.toLowerCase(), countryCode);
		
		List<EmpRecordResponse> npsEmpList = records.stream().map(record -> {
			EmpRecordResponse empInfo = new EmpRecordResponse();
			empInfo.setEmpId(record.getEmpid());
			  empInfo.setFirst_name(record.getFirst_name());
			  empInfo.setLast_name(record.getLast_name());
			  empInfo.setPreferred_name(record.getPreferred_name());
			  empInfo.setCountry(record.getCountry_code());
			  empInfo.setOptdFormat(record.getOpted_format());
			  empInfo.setOpted(record.getOpted());
			  return empInfo;
        })
        .collect(Collectors.toList());
		
		if(npsEmpList.size() <= 0) {
			throw new ResourceNotFoundException("NO EMP RECORDS FOUND FOR THE NAME : "+ name);
		}
		return npsEmpList;
	}

	/*
	 * public Stream<NamePronounciationRecord> getAllFiles() {
	 * npsRepository.findAll().stream(); }
	 */

	public void deleteRecord(String empId) {
		npsRepository.deleteById(empId);
	}
	
	public void updateRecord(empUpdateRecord empdata) throws IOException {
		if (empdata.getEmpId() == null || empdata.getEmpId().isEmpty()) {
			throw new InvalidArgumentException(" EmpID is required to update record.");
		}
		NamePronounciationRecord emprecord = this.getFilebyEmpId(empdata.getEmpId());
		try {
			if (emprecord != null) {
				emprecord.setOverriden_file(empdata.getMultipartFile().getBytes());
				emprecord.setOverriden_Status(OverridenStatusEnum.PENDING.getValue());
				emprecord.setChannel(empdata.getChannel());
				emprecord.setAudio_url(empdata.getAudio_file_url());
				npsRepository.save(emprecord);
				
				NotificationsRequest request  = new NotificationsRequest();
				request.setAction(ActionEnum.ADMIN.getValue());
				request.setEmpId(emprecord.getEmpid());
				request.setMessage("Waiting for Admin approval");
				request.setStatus(NotificationsStatusEnum.UNREAD.getValue());
				request.setTypeofNotification(NotificationTypeEnum.ADMIN_PENDING.getValue());
				notificationsService.insertNotification(request);
				
			} else {
				throw new ResourceNotFoundException(
						"Please register the employee in NamepronounciationTool before uploading the custom record");
			}
		} catch (Exception e) {
			throw new ResourceNotFoundException(
					"Exception processing the update.please try later.");
		}
		
		//send update to notification table? notification to be received by admin
	}
	
	public void approveRecord(String empId) throws IOException {
		if (empId == null || empId.isEmpty()) {
			throw new InvalidArgumentException(" EmpID is required to update record.");
		}
		NamePronounciationRecord emprecord = this.getFilebyEmpId(empId);
		
		if (emprecord.getOverriden_Status().equalsIgnoreCase(OverridenStatusEnum.PENDING.getValue())) {
			emprecord.setAudio_file(emprecord.getOverriden_file());
			emprecord.setOverriden_Status(OverridenStatusEnum.APPROVED.getValue());
			emprecord.setOpted_format(OptedFormatEnum.CUSTOM.getValue());
			emprecord.setOverriden_file(null);
			npsRepository.save(emprecord);
			
			NotificationsRequest request  = new NotificationsRequest();
			request.setAction(emprecord.getEmpid());
			request.setEmpId(emprecord.getEmpid());
			request.setMessage("Approved Custom record");
			request.setStatus(NotificationsStatusEnum.UNREAD.getValue());
			request.setTypeofNotification(NotificationTypeEnum.ADMIN_APPROVAL.getValue());
			notificationsService.insertNotification(request);
		} else {
			throw new InvalidArgumentException("EMP should be in PENDING state before it needs approval");
			
		}
		//send update to notification table? notification to be received by user
	}
	
	public void empOptout(String empId) throws IOException {
		if (empId == null || empId.isEmpty()) {
			throw new InvalidArgumentException(" EmpID is required to update record.");
		}
		NamePronounciationRecord emprecord = this.getFilebyEmpId(empId);
		
		if (emprecord != null && emprecord.getStatus().equalsIgnoreCase(StatusEnum.ACTIVE.getValue())) {
			emprecord.setOpted(OptedEnum.NO.getValue());
			npsRepository.save(emprecord);
		} else {
			throw new InvalidArgumentException(" Unabe to OPTOUT of Pronouncation tool. Please try later");
			
		}
		//send update to notification table? notification to be received by user
	}
	
	public String empOptIn(String empId) throws IOException {
		if (empId == null || empId.isEmpty()) {
			throw new InvalidArgumentException(" EmpID is required to update record.");
		}
		NamePronounciationRecord emprecord = this.getFilebyEmpId(empId);
		
		if (emprecord != null && emprecord.getStatus().equalsIgnoreCase(StatusEnum.ACTIVE.getValue()) && emprecord.getOpted().equalsIgnoreCase(OptedEnum.NO.getValue())) {
			emprecord.setOpted(OptedEnum.YES.getValue());
			npsRepository.save(emprecord);
			return  "Emp is opted in successfully name pronouncation tool"; 
		}  else {
				return "Emp is already opted in for name pronouncation tool ";
			}
		//send update to notification table? notification to be received by user
	}
	 
	public void rejectRecord(String empId) throws IOException {
		if (empId == null || empId.isEmpty()) {
			throw new InvalidArgumentException(" EmpID is required to update record.");
		}
		NamePronounciationRecord emprecord = this.getFilebyEmpId(empId);
		emprecord.setOverriden_Status(OverridenStatusEnum.REJECTED.getValue());
		emprecord.setOverriden_file(null);
		emprecord.setOpted_format(OptedFormatEnum.STANDARD.getValue());//actually to be set to prev format
		npsRepository.save(emprecord);
		
		NotificationsRequest request  = new NotificationsRequest();
		request.setAction(emprecord.getEmpid());
		request.setEmpId(emprecord.getEmpid());
		request.setMessage("Admin Rejected record. Contact admin for more info");
		request.setStatus(NotificationsStatusEnum.UNREAD.getValue());
		request.setTypeofNotification(NotificationTypeEnum.ADMIN_REJECTION.getValue());
		notificationsService.insertNotification(request);
		//send update to notification table? notification to be received by user
	}
	
	public void uploadCountries(List<CountryRecord> countries) {
		countryRepository.saveAll(countries);
	}
	
	public List<CountryData> getCountries() {
		List<CountryRecord> countries = countryRepository.findAll();
		List<CountryData> ctrydata = countries.stream().map(item ->{
			CountryData data  = new CountryData();
			data.setCode(item.getCode());
			data.setName(item.getName());
			return data;
			
		}).collect(Collectors.toList());
		
		return ctrydata;
	}
}
