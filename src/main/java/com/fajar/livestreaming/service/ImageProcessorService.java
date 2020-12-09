package com.fajar.livestreaming.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fajar.livestreaming.dto.WebRequest;
import com.fajar.livestreaming.dto.WebResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
					request.getColorReducers(), binarized, request.getPercentage());
			response.setImageData(resultData);
			response.setColorFilters(request.getColorFilters());
			response.setColorReducers(request.getColorReducers());
			response.setBinarized(binarized);
			return response;
		} catch (Exception e) {
			return WebResponse.failed(e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		String json = "{\"colorFilters\":[{\"index\":1,\"character\":\"Q\",\"useTemplateCharacter\":false,\"red\":{\"min\":0,\"max\":0},\"green\":{\"min\":0,\"max\":0},\"blue\":{\"min\":0,\"max\":0}},{\"index\":2,\"character\":\"_\",\"useTemplateCharacter\":false,\"red\":{\"min\":255,\"max\":255},\"green\":{\"min\":255,\"max\":255},\"blue\":{\"min\":255,\"max\":255},\"hexMin\":\"#ffffff\",\"hexMax\":\"#ffffff\"}],\"colorReducers\":[{\"index\":1,\"red\":0,\"green\":0,\"blue\":0,\"hex\":null,\"resultFontSize\":1},{\"index\":2,\"red\":255,\"green\":255,\"blue\":255,\"hex\":\"#ffffff\",\"resultFontSize\":1}]}";
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		WebRequest req  = objectMapper.readValue(json, WebRequest.class);
		
		ImageCharacterizerService svc = new ImageCharacterizerService();
		BufferedImage image = ImageIO.read(new File("C:\\Users\\Republic Of Gamers\\Pictures\\j.jpg"));
		svc.process(image , req.getColorFilters(), req.getColorReducers(), false, 0);
		System.out.println(req);
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
