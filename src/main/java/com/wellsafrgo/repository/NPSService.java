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

import com.wellsafrgo.exception.InvalidArgumentException;
import com.wellsafrgo.exception.ResourceNotFoundException;
import com.wellsafrgo.model.NPSDomain;
import com.wellsafrgo.model.NamePronounciationRecord;
import com.wellsafrgo.model.empUpdateRecord;
import com.wellsfargo.response.NameSearchResponse;

@Service
@Transactional
public class NPSService {

	@Autowired
	private NPSRepository npsRepository;
	
	public boolean save(NPSDomain userData) {
		try {
			if (userData.getEmpId() == null || userData.getEmpId().isEmpty()) {
				throw new InvalidArgumentException(" EmpID is required to save new record.");
			}

			NamePronounciationRecord npsData = new NamePronounciationRecord(userData.getEmpId(), userData.getfName().toLowerCase(),
					userData.getlName().toLowerCase(), userData.getpName().toLowerCase(), userData.getCountry_code(),
					userData.getMultipartFile().getBytes(), userData.getCreated_by(), userData.getModified_by(), userData.getOptedformat(), null, userData.getStatus(), userData.getOverridenStatus(),userData.getChannel(), null);
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
	
	public NameSearchResponse getFilesforName(String name, String countryCode) {
		Map<String, NPSDomain> map = new HashMap<>();
		if (name != null && !name.isEmpty()) {
			List<String> list = Stream.of(name.split(" ")).collect(Collectors.toList());
			list.forEach(item -> {
				List<NPSDomain> dlist = getFilesbyName(item,countryCode);
				dlist.forEach(i -> {
					map.put(i.getEmpId(), i);
				});
				//domainList.addAll(getFilesbyName(item)) ;
			});
		}
		List<NPSDomain> domainList = (List<NPSDomain>) new ArrayList(map.values());
		NameSearchResponse response = new NameSearchResponse();
		response.setNpsList(domainList);
		response.setSearchNameCount(domainList.size());
		return response;
	}
	
	public List<NPSDomain> getFilesbyCountry(String cuntry) {
		List<NamePronounciationRecord> records = npsRepository.getFilesbycountry( cuntry);

		List<NPSDomain> npsEmpList = records.stream().map(record -> {
			NPSDomain empInfo = new NPSDomain();
			empInfo.setEmpId(record.getEmpid());
			empInfo.setfName(record.getFirst_name());
			empInfo.setlName(record.getLast_name());
			empInfo.setpName(record.getPreferred_name());
			empInfo.setCountry_code(record.getCountry_code());
			empInfo.setCreated_by(record.getCreated_by());
			empInfo.setModified_by(record.getModified_by());
			empInfo.setOptedformat(record.getOpted_format());
			empInfo.setStatus(record.getStatus());
			empInfo.setOverridenStatus(record.getOverriden_Status());
			return empInfo;
		}).collect(Collectors.toList());

		if (npsEmpList.size() <= 0) {
			throw new ResourceNotFoundException("NO EMP RECORDS FOUND FOR THE NAME : " + cuntry);
		}
		return npsEmpList;
	}
	
	
	public List<NPSDomain> getFilesbyName(String name, String countryCode)  {
		
		List<NamePronounciationRecord> records = npsRepository.getFilesbyName(name.toLowerCase(), countryCode);
		
		List<NPSDomain> npsEmpList = records.stream().map(record -> {
			NPSDomain empInfo = new NPSDomain();
			empInfo.setEmpId(record.getEmpid());
			empInfo.setfName(record.getFirst_name());
			empInfo.setlName(record.getLast_name());
			empInfo.setpName(record.getPreferred_name());
			empInfo.setCountry_code(record.getCountry_code());
			empInfo.setCreated_by(record.getCreated_by());
			empInfo.setModified_by(record.getModified_by());
			empInfo.setOptedformat(record.getOpted_format());
			empInfo.setStatus(record.getStatus());
			empInfo.setOverridenStatus(record.getOverriden_Status());
            return empInfo;
        })
        .collect(Collectors.toList());
		
		if(npsEmpList.size() <= 0) {
			throw new ResourceNotFoundException("NO EMP RECORDS FOUND FOR THE NAME : "+ name);
		}
		return npsEmpList;
	}

	public Stream<NamePronounciationRecord> getAllFiles() {
		return npsRepository.findAll().stream();
	}

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
				emprecord.setOverriden_Status("PENDING");
				emprecord.setChannel(empdata.getChannel());
				emprecord.setAudio_url(empdata.getAudio_file_url());
				npsRepository.save(emprecord);
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
		
		if (emprecord.getOverriden_Status() == "PENDING") {
			emprecord.setAudio_file(emprecord.getOverriden_file());
			emprecord.setOverriden_Status("APPROVED");
			emprecord.setOverriden_file(null);
			npsRepository.save(emprecord);
		} else {
			throw new InvalidArgumentException("EMP should be in PENDING state before it needs approval");
			
		}
		//send update to notification table? notification to be received by user
	}
	 
	public void rejectRecord(String empId) throws IOException {
		if (empId == null || empId.isEmpty()) {
			throw new InvalidArgumentException(" EmpID is required to update record.");
		}
		NamePronounciationRecord emprecord = this.getFilebyEmpId(empId);
		emprecord.setOverriden_Status("REJECTED");
		emprecord.setOverriden_file(null);
		npsRepository.save(emprecord);
		//send update to notification table? notification to be received by user
	}
}
