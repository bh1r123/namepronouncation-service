package com.wellsafrgo.model;

import org.springframework.web.client.RestTemplate;

/**
 * @author satpra
 *
 */
public class GetPhonetic {

	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject("https://pronouncenames.com/search?name=Bharath", String.class);
		//String response = ">Phonetic Spelling:</span><span style=\"display:inline-block;color:#BB3333;font-size:17px;\">[ j aw r j ]";
		
		int PhoneticSpelling = response.indexOf("Phonetic Spelling")+17;
		System.out.println("PhoneticSpelling " + PhoneticSpelling);
		int first_Square = response.indexOf("[", PhoneticSpelling);
		System.out.println("first_Square " + first_Square);
		int last_SquareIndexd = response.indexOf("]", first_Square);
		
		System.out.println("last_SquareIndexd " + last_SquareIndexd);
		
		System.out.println("Phonetic for the given name is " + response.substring(first_Square,last_SquareIndexd+1));


	}
	

	
	

}
