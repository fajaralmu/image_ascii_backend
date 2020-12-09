package com.fajar.livestreaming.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fajar.livestreaming.dto.WebRequest;
import com.fajar.livestreaming.dto.WebResponse;

@Service
public class ImageProcessorService {

	@Autowired
	private ImageCharacterizerService imageCharacterizerService;

	public WebResponse characterize(WebRequest request) {
		try {
			WebResponse response = new WebResponse();
			String imageData = request.getImageData();

			boolean binarized = request.getColorFilters() == null || request.getColorFilters().size() == 0 ||
					request.getColorReducers() == null || request.getColorReducers().isEmpty();
			BufferedImage image = null;

			image = readImage(imageData);

			String resultData = imageCharacterizerService.process(image, request.getColorFilters(),
					request.getColorReducers(), binarized);
			response.setImageData(resultData);
			return response;
		} catch (Exception e) {
			return WebResponse.failed(e);
		}
	}

	private BufferedImage readImage(String data) throws IOException {
		String[] imageData = data.split(",");
		if (imageData == null || imageData.length < 2) {
			return null;
		}
		// create a buffered image
		String imageString = imageData[1];
		BufferedImage image = null;
		byte[] imageByte;

		Base64.Decoder decoder = Base64.getDecoder();
		imageByte = decoder.decode(imageString);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		image = ImageIO.read(bis);
		bis.close();
		return image;
	}
}
