/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
import java.util.Date;

public class ProgrammedTweet implements Serializable{
	
	private final String estado;
	private long programmedTime;
	
	public ProgrammedTweet(String estado, long minutos){
		this.estado = estado;
		convertTime(minutos);
	}
	
	private void convertTime(long minutos){
		Date date = new Date();
		programmedTime = date.getTime() + minutos;
	}
	
	public long getTime(){
		return this.programmedTime;
	}
	
	public String getTuit(){
		return this.estado;
	}
}
