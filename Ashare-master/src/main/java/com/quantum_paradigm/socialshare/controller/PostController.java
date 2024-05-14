package com.quantum_paradigm.socialshare.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.quantum_paradigm.socialshare.dto.MediaPost;
import com.quantum_paradigm.socialshare.response.ResponseStructure;
import com.quantum_paradigm.socialshare.response.ResponseWrapper;
import com.quantum_paradigm.socialshare.service.PostService;

@RestController
@RequestMapping("/quantum-socialshare")
public class PostController {

	@Autowired
	PostService postservice;

	@Autowired
	ResponseStructure<MediaPost> structure;

	@PostMapping(value = "/post-file", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseWrapper postToMedia(@RequestParam MultipartFile mediaFile, MediaPost mediaPost) {
		System.out.println(mediaFile);

		try {
//			String decodedCaption = URLDecoder.decode(mediaPost.getCaption(), "UTF-8");
//			mediaPost.setCaption(decodedCaption);
			System.out.println("controller");
			System.out.println(mediaFile.getContentType());
			System.out.println(mediaPost);
			System.out.println(Arrays.toString(mediaPost.getMediaPlatform()));
			if (mediaPost.getMediaPlatform() == null || mediaPost.getMediaPlatform().length < 0) {
				structure.setCode(HttpStatus.BAD_REQUEST.value());
				structure.setStatus("error");
				structure.setMessage("select social media platforms");
				return new ResponseWrapper(structure, HttpStatus.BAD_REQUEST);
			} else {
				return postservice.postMedia(mediaPost, mediaFile);
			}
		} catch (NullPointerException e) {
			throw new NullPointerException(e.getMessage());
		}
	}
}
