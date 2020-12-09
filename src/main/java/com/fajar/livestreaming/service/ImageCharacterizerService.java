package com.fajar.livestreaming.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fajar.livestreaming.dto.ColorComponent;
import com.fajar.livestreaming.dto.ColorFilter;

@Service
public class ImageCharacterizerService {

	private static final int PERCENTAGE = 17;
	static Date date = new Date();
	static final String TEMPLATE="#AntiKorupsi";
	private static final boolean BINARIZE = true;

	
	public String process(BufferedImage image, List<ColorFilter> colorFilters, List<ColorComponent> colorReducers, boolean binarized) {
		date = new Date();
		System.out.println("Printing binary image in console");
		System.out.println(StringUtils.repeat("-", 200));
		
		try {
			int scaledWidth = image.getWidth() * PERCENTAGE/100;
			int scaledHeight = image.getHeight() * PERCENTAGE / 100;
			BufferedImage result;
			if (binarized) {
				colorReducers.clear();
				result = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_BYTE_BINARY);
			} else {
				result = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
			}
			
			// scales the input image to the output image
			Graphics2D g2d = result.createGraphics();
			g2d.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
			g2d.dispose();
            
            String resultString = printImageV2(result, colorFilters, colorReducers);
            
            System.out.println(resultString);
            
            System.out.println(StringUtils.repeat("-", 200));
            System.out.println("Duration:"+(System.currentTimeMillis()-date.getTime())+"ms");
            return resultString;
		} catch (Exception e) { 
			e.printStackTrace();
			return null;
		}
		
	}
	private String printImageV2(BufferedImage image, List<ColorFilter> colorFilters, List<ColorComponent> colorReducers) {
		int width = image.getWidth();
		int height = image.getHeight();
		final boolean reducColor = colorReducers != null && colorReducers.size()>0;
		 
		StringBuilder stringBuilder = new StringBuilder();
		int charIndex = 0;
		for (int y = 0; y < height; y++) {
			
			for (int x = 0; x < width; x++) { 
				int pixel = image.getRGB(x, y);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if (reducColor) {
					ColorComponent reducedColor = ColorComponent.adjust(pixel, colorReducers);
					red = reducedColor.getRed();
					green = reducedColor.getGreen();
					blue = reducedColor.getBlue();
				}
				
				boolean matchFilter = false, useTemplateCharacter = true;
				String character = "-";
				if (colorFilters!=null)
					loop: for (int i = 0; i < colorFilters.size(); i++) {
						ColorFilter colorFilter = colorFilters.get(i);
						if (colorFilter.matchFilter(red, green, blue)) {
							character = colorFilter.getCharacter();
							useTemplateCharacter = colorFilter.isUseTemplateCharacter();
							matchFilter = true;
							break loop;
						}
					}
				if(matchFilter) {
					if (useTemplateCharacter) {
						stringBuilder.append(TEMPLATE.charAt(charIndex));
						charIndex++;
						if (charIndex == TEMPLATE.length()) {
							charIndex = 0;
						}
					} else {
						stringBuilder.append(character);
					}
					
				}else{
					stringBuilder.append('-');
				}
			}
			stringBuilder.append('\n');
		}
		return stringBuilder.toString();
	}
}
