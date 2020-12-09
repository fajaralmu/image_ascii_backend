package com.fajar.livestreaming.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

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
	@Default
	private boolean useTemplateCharacter = true;
	@Default
	private String character = "o";
	
	public boolean matchFilter(ColorComponent colorComponent) {
		return matchFilter(colorComponent.getRed(), colorComponent.getGreen(), colorComponent.getBlue());
	}

	public boolean matchFilter(int red, int green, int blue) {

		boolean matchRed = null != redFilter && redFilter.matchRange(red);
//		System.out.println("red: "+ matchRed);
		boolean matchGreen = null != greenFilter && greenFilter.matchRange(green);
//		System.out.println("green: "+ matchGreen);
		boolean matchBlue = null != blueFilter && blueFilter.matchRange(blue);
//		System.out.println("blue: "+ matchBlue);
		
		return matchRed && matchGreen && matchBlue;
	}
	public static ColorFilter createExact(Integer red, Integer green, Integer blue, String character) {
		return createRanged(red, red, green, green, blue, blue, character);
	}
	public static ColorFilter createExactSame(Integer color, String character) {
		return createExact(color, color, color , character);
	}
	public static ColorFilter createRanged(
			Integer redMin,
			Integer redMax,
			Integer greenMin,
			Integer greenMax,
			Integer blueMin,
			Integer blueMax,
			String character
			) {
		
		ColorFilter colorFilter = new ColorFilter();
		if (null != character) {
			colorFilter.setUseTemplateCharacter(false);
			colorFilter.setCharacter(character);
		}
		if (null != redMin && null != redMax) {
			colorFilter.setRedFilter(ColorFilterItem.create(redMin, redMax));
		}
		if (null != greenMin && null != greenMax) {
			colorFilter.setGreenFilter(ColorFilterItem.create(greenMin, greenMax));
		}
		if (null != blueMin && null != blueMax) {
			colorFilter.setBlueFilter(ColorFilterItem.create(blueMin, blueMax));
		}
		return colorFilter ;
	}

}
