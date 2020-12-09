package com.fajar.livestreaming.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ImageCharacterizer {

	private static final int PERCENTAGE = 17;
	static Date date = new Date();
	static final String TEMPLATE="#AntiKorupsi";
	
	public static void main(String[] args) {
		process("C:\\Users\\Republic Of Gamers\\Pictures\\hakordia.jpg");
	}

	public static void process(String inputImagePath) {
		date = new Date();
		File inputFile = new File(inputImagePath);

		System.out.println("Printing binary image in console");
		System.out.println(StringUtils.repeat("-", 200));
		
		try {
			BufferedImage image = ImageIO.read(inputFile);
			int scaledWidth = image.getWidth() * PERCENTAGE/100;
			int scaledHeight = image.getHeight() * PERCENTAGE / 100;
			BufferedImage result = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_BYTE_BINARY);

			// scales the input image to the output image
			Graphics2D g2d = result.createGraphics();
			g2d.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
			g2d.dispose();
            
            printImage(result);
            
            System.out.println(StringUtils.repeat("-", 200));
            System.out.println("Duration:"+(System.currentTimeMillis()-date.getTime())+"ms");
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
	}
	
	private static void printImage(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		 
		StringBuilder stringBuilder = new StringBuilder();
		int charIndex = 0;
		for (int y = 0; y < height; y++) {
			
			for (int x = 0; x < width; x++) { 
				int pixel = image.getRGB(x, y);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				boolean black = red == 0 && green == 0 && blue == 0;
				boolean white = red == 255 && green == 255 && blue == 255;
				
				if(black) {
					stringBuilder.append(TEMPLATE.charAt(charIndex));
					charIndex++;
					if (charIndex == TEMPLATE.length()) {
						charIndex = 0;
					}
					
				}else if(white){
					stringBuilder.append('-');
				}
			}
			stringBuilder.append('\n');
		}
		System.out.println(stringBuilder.toString());
	}
}
