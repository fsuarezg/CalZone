package com.vub.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.security.auth.login.AppConfigurationEntry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

public class ReadCSV {

	public void readRoom() {

		String csvFile = "Leslokalen.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";

		try {
			ApplicationContext appContext = new ClassPathXmlApplicationContext("spring-module.xml");
			Resource resource = appContext.getResource("Leslokalen.csv");
			InputStream is = resource.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			br.readLine(); // Read first line with Header
			
			ArrayList<Room> roomList = new ArrayList<Room>();
			
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] room = line.split(cvsSplitBy);
				Room roomObj = new Room();
				roomObj.setType(RoomType.valueOf(room[0]));
				roomObj.setBuilding(room[1]);
				roomObj.setFloor(Integer.valueOf(room[2]));
				roomObj.setNumber(Integer.valueOf(room[3]));
				roomObj.setPlaces(Integer.valueOf(room[4]));
				roomObj.setHasProjector(room[5].equals(1));
				roomObj.setHasSmartBoard(room[6].equals(1));
				roomObj.setHasRecorder(room[7].equals(1));
				roomObj.setInstitution("VUB"); // TOEGEVOEGD OMDAT AL DEZE LOKALEN VUB ZIJN !!
				roomObj.setName(""); // TOEGEVOEGD OMDAT MOMENTEEL GEEN LOKALEN MET NAAM !!
				
				roomList.add(roomObj);
				
				if (Globals.DEBUG == 1) {System.out.println(roomObj);}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
	}

}
