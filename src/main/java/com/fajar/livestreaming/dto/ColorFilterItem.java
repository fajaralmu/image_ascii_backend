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

public class ColorFilterItem implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 3437405319868427774L;
	private int max;
	private int min;
	private boolean withFilter;
	
	public boolean matchRange(int value) {
		if (!withFilter) {
			return true;
		}
		
		if (value >= min && value <= max) {
			return true;
		}
		
		return false;
	}

}
