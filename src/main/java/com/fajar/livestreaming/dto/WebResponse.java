package com.fajar.livestreaming.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data  
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class WebResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8345271799535134609L;
	@Builder.Default
	private Date date = new Date(); 
	@Builder.Default
	private String code = "00";
	@Builder.Default
	private String message = "success";
	
	private String imageData;
	private LinkedList<ColorFilter>  colorFilters = new LinkedList<>();
	private List<ColorComponent> colorReducers;
	private boolean binarized;
	
	public static WebResponse failed(String msg) {
		WebResponse response = new WebResponse();
		response.setMessage(msg);
		response.setCode("-1");
		return response;
	}

	public static WebResponse failed(Exception e) {
		return failed(e.getMessage());
	}
}
