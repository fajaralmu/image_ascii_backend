package com.fajar.livestreaming.dto;

import java.io.Serializable;
import java.util.LinkedList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

 
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 110411933791444017L; 
	 
	@Default
	private LinkedList<ColorFilter>  colorFilter = new LinkedList<>();

}