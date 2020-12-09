package com.fajar.livestreaming.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColorFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2972042514127731364L;
	private ColorFilterItem redFilter;
	private ColorFilterItem greenFilter;
	private ColorFilterItem blueFilter;
	
	public boolean matchFilter(int red, int green, int blue) {
		
		return redFilter.matchRange(red) && greenFilter.matchRange(green) && blueFilter.matchRange(blue);
	}
	
}
