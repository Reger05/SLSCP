package com.eger.slscp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class SLSCPAnalyzer {
	
	HashMap<String, State> states = new HashMap<String, State>();
	HashMap<String, State> zipToState = new HashMap<String, State>();
	static File outputFile = new File("output.csv");
	
	/**
	 * Takes absolute filepath for output file as an argument. Otherwise writes it to output.csv in calling directory
	 * @param args
	 */
	public static void main(String[] args){
		//kick it off
		if(args.length == 1)
			outputFile = new File(args[0]);
		SLSCPAnalyzer analyzer= new SLSCPAnalyzer();
		analyzer.computeSLSCP();
	}

	private void computeSLSCP() {
		try {
			InputStreamReader isr = new InputStreamReader(this.getClass().getResourceAsStream("zip.csv"));
			BufferedReader br = new BufferedReader(isr);
			handleZips(br);
			
			isr = new InputStreamReader(this.getClass().getResourceAsStream("plans.csv"));
			br = new BufferedReader(isr);
			handlePlans(br);
			
			isr = new InputStreamReader(this.getClass().getResourceAsStream("slscp.csv"));
			br = new BufferedReader(isr);
			handleSLSCP(br);
			
		} catch (IOException e) {
			//TODO log it
			e.printStackTrace();
		} catch (Exception e) {
			//TODO log it
			e.printStackTrace();
		}
	}

	private void handleSLSCP(BufferedReader br) throws Exception {
		String line = br.readLine();
		String zip;
		byte[] newLine = System.getProperty("line.separator").getBytes();
		if(!outputFile.exists()){
			outputFile.createNewFile();
		}
		if(!outputFile.canWrite()){
			throw new Exception("Cannot write to file " + outputFile.getAbsolutePath());
		}
		FileOutputStream os = new FileOutputStream(outputFile);
		try{
			os.write(line.getBytes());
			os.write(newLine);
			while((line = br.readLine()) != null){
				String[] split = line.split(",");
					zip = split[0];
					State s = zipToState.get(zip);
					String rate = s.getSLSCP(Integer.parseInt(zip));
					writeLine(zip, rate, os);
			}
		}finally{
			if(os != null){
				os.flush();
				os.close();
			}
		}
		
	}

	private void writeLine(String zip, String rate, FileOutputStream os) throws IOException {
		String toWrite = zip + "," + rate + System.getProperty("line.separator");
		os.write(toWrite.getBytes());
	}

	private void handlePlans(BufferedReader br) throws IOException {
		String line = br.readLine();//first line is info on structure...in this case we know it ahead of time
		String planId = "";
		String state = "";
		String level = "";
		String rate = "";
		String rateArea = "";
		while((line = br.readLine()) != null){
			String[] split = line.split(",");
			if(!(split.length == 4)){
				planId = split[0];
				state = split[1];
				level = split[2];
				rate = split[3];
				rateArea = split[4];
				Plan p  = new Plan(planId, state, level, Double.parseDouble(rate), Integer.parseInt(rateArea));
				addPlanToState(p);
				
			}else{
				//log error, data incomplete or incorrect structure on this line.
			}
		}
	}

	private void addPlanToState(Plan p) {
		State s = states.get(p.getState());
		if(s != null){
			s.addPlan(p);
		}
		
	}

	private void handleZips(BufferedReader br) throws IOException {
		String line = br.readLine();//first line is info on structure...in this case we know it ahead of time
		String zipcode = "";
		String state = "";
		String rateArea = "";
		while((line = br.readLine()) != null){
			String[] split = line.split(",");
			if(!(split.length == 4)){
				zipcode = split[0];
				state = split[1];
				rateArea = split[4];
				State s = states.get(state);
				if(s == null){
					s = new State(state);
					states.put(state, s);
				}
				s.updateRateArea(Integer.parseInt(zipcode), Integer.parseInt(rateArea));
				zipToState.put(zipcode, s);
			}else{
				//TODO log error, data incomplete or incorrect structure on this line.
			}
		}
		
	}
	
	

}
