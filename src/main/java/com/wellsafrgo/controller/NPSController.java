package com.wellsafrgo.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wells.constants.OptedFormatEnum;
import com.wellsafrgo.model.CountryData;
import com.wellsafrgo.model.CountryRecord;
import com.wellsafrgo.model.NPSDomain;
import com.wellsafrgo.model.NamePronounciationRecord;
import com.wellsafrgo.model.empUpdateRecord;
import com.wellsafrgo.repository.NPSService;
import com.wellsfargo.response.EmpRecordResponse;
import com.wellsfargo.response.NameSearchResponse;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/npsrecords")
public class NPSController {

	@Autowired
	private NPSService npsService;

	@PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> save(@ModelAttribute NPSDomain npsData)
			throws IOException, SQLException, URISyntaxException {
		String message = "";
		if(npsService.save(npsData)) {
		    message = "Uploaded the file successfully for Emp : " + npsData.getEmpId();
		} else {
			message = "Unable to load the file currently for Emp : " + npsData.getEmpId();
		}
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
	
	@GetMapping("/getAllEmpRecords")
	public ResponseEntity<NameSearchResponse> getAllEmprecords(@RequestParam(required = false) String status) {
		NameSearchResponse searchResponse = npsService.getAllEmpRecords(status);
		System.out.println("record size : " + searchResponse.getSearchNameCount());
		return ResponseEntity.ok(searchResponse);
	}
    
	@GetMapping("/getEmpRecordsByName")
	public ResponseEntity<NameSearchResponse> getEmprecordsByName(@RequestParam String name, @RequestParam String countrycd) {
		NameSearchResponse searchResponse = npsService.getFilesforName(name, countrycd);
		System.out.println("record size : " + searchResponse.getSearchNameCount());
		return ResponseEntity.ok(searchResponse);
	}
	
	@GetMapping("/getEmpRecordsbyCountry")
	public ResponseEntity<List<EmpRecordResponse>> getEmprecordsByCountry( @RequestParam String countrycd) {
		List<EmpRecordResponse> records = npsService.getFilesbyCountry(countrycd);
		System.out.println("record size : " + records.size());
		return ResponseEntity.ok(records);
	}
	
	@GetMapping(value= "/getEmpAudioRecord")
	public ResponseEntity<ByteArrayResource> getEmpCustAudioRecord(@RequestParam String empId,
			@RequestParam String audioFormat) throws IOException, SQLException, URISyntaxException {
		NamePronounciationRecord record = npsService.getFilebyEmpId(empId);

		byte[] data = audioFormat.equalsIgnoreCase(record.getOpted_format()) ? record.getAudio_file()
				: record.getOverriden_file();

		return ResponseEntity.ok().contentLength(data.length)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + record.getEmpid() + ".wav\"")
				.body(new ByteArrayResource(data));

	}
	
	@GetMapping("/getEmpRecord/{empId}")
	public ResponseEntity<NPSDomain> getEmpRecord(@PathVariable String empId)
			throws IOException, SQLException, URISyntaxException {
		NamePronounciationRecord record = npsService.getFilebyEmpId(empId);
		NPSDomain empInfo = new NPSDomain();
		if(record != null) {
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
		}
		return ResponseEntity.ok().body(empInfo);
	}
	
	@DeleteMapping(value = "/deleteEmpRecord/{empId}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteRecord_emp(@PathVariable String empId)
			throws IOException, SQLException, URISyntaxException {
		npsService.deleteRecord(empId);
		String message = "Successfully deleted the recrd for EmpId :  " + empId;
		return ResponseEntity.status(HttpStatus.OK).body(message);

	}
	/*
	 * https://92d2-124-123-173-69.in.ngrok.io/api/v1/npsrecords/updateEmpAudioRecord
	 */
	 
	@PostMapping(value = "/updateEmpAudioRecord", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateRecord_emp(@ModelAttribute empUpdateRecord emprecord)
			throws IOException, SQLException, URISyntaxException {
		npsService.updateRecord(emprecord);
		String message = "Successfully updated the record for EmpId :  " + emprecord.getEmpId();
		return ResponseEntity.status(HttpStatus.OK).body(message);

	}
	
	@PostMapping(value = "/ApproveEmpAudioRecord", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> approveEmpRecord(@RequestParam(required=true) String empId)
			throws IOException, SQLException, URISyntaxException {
		npsService.approveRecord(empId);
		String message = "Successfully approved the custom record for EmpId :  " + empId;
		return ResponseEntity.status(HttpStatus.OK).body(message);

	}
	
	@PostMapping(value = "/OptOut", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> empOptOut(@RequestParam(required=true) String empId) throws Exception {
		npsService.empOptout(empId);
		String message = "Successfully Opted out the Employee  :  " + empId;
		return ResponseEntity.status(HttpStatus.OK).body(message);

	}

	@PostMapping(value = "/OptIn", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> empOptIn(@RequestParam(required=true) String empId) throws Exception {
		String message = npsService.empOptIn(empId);
		return ResponseEntity.status(HttpStatus.OK).body(message);

	}

	
	@PostMapping(value = "/RejectEmpAudioRecord", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> rejectEmpRecord(@RequestParam(required=true) String empId)
			throws IOException, SQLException, URISyntaxException {
		npsService.rejectRecord(empId);
		String message = "Successfully rejected the custom record for EmpId :  " + empId;
		return ResponseEntity.status(HttpStatus.OK).body(message);

	}
	
	@PostMapping(value = "/uploadCountry", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void uploadCountries(@RequestBody String countrylist) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		try {
			List<CountryData> countries = mapper.readValue(countrylist, new TypeReference<List<CountryData>>() {
			});
			List<CountryRecord> records = countries.stream().map(item -> {
				CountryRecord record = new CountryRecord();
				record.setCode(item.getCode());
				record.setName(item.getName());
				return record;
			}).collect(Collectors.toList());
			npsService.uploadCountries(records);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping(value = "/getCountryList")
	public ResponseEntity<List<CountryData>> getCountryList() {
		List<CountryData> data = npsService.getCountries();
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

}
