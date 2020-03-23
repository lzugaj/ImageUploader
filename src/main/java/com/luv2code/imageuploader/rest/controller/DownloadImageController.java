package com.luv2code.imageuploader.rest.controller;

import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.service.PostService;
import com.luv2code.imageuploader.utils.MessageError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.*;

/**
 * Created by lzugaj on Saturday, January 2020
 */

@Slf4j
@Controller
@RequestMapping("/download/image")
public class DownloadImageController {

	private final PostService postService;

	@Autowired
	public DownloadImageController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping("/{id}")
	public String showDownloadImageForm(@PathVariable Long id, Model model) {
		Post post = postService.findById(id);
		log.info("Successfully founded Post with id: `{}`", id);

		String selectedPostImage = postService.getSelectedPostImage(post);
		model.addAttribute("selectedPostImage", selectedPostImage);

		model.addAttribute("postId", post.getId());

		model.addAttribute("post", post);
		return "download/download-form";
	}

	@PostMapping("/{id}/post")
	public String download(@PathVariable Long id,
						   HttpServletResponse response) throws IOException, CloneNotSupportedException {

		Post post = postService.findById(id);
		log.info("Successfully founded Post with id: `{}`", id);

		Post clonedPost = (Post) post.clone();

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		outStream.write(clonedPost.getPostImage());
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outStream.toByteArray());

		response.setContentType("images/png");
		response.setContentLength(Math.toIntExact(clonedPost.getImageFileSize()));
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"image_%s.png\"", clonedPost.getId()));

		FileCopyUtils.copy(inputStream, response.getOutputStream());
		response.getOutputStream().flush();
		return "download/download-form";
	}


	public String downloadImage(@PathVariable Long id,
								@RequestParam(value = "width", required = false) double width,
								@RequestParam(value = "height", required = false) double height,
								@RequestParam(value = "sepia", required = false) boolean sepia,
								@RequestParam(value = "blur", required = false) boolean blur,
								RedirectAttributes redirectAttributes,
								HttpServletResponse response) throws IOException, CloneNotSupportedException {
		if (width == 0.0 || height == 0.0) {
			redirectAttributes.addFlashAttribute("errorMessage", MessageError.PLEASE_FILL_ALL_REQUIRED_FIELDS);
			return "redirect:/download/image/{id}";
		}

		Post post = postService.findById(id);
		log.info("Successfully founded Post with id: `{}`", id);

		Post clonePost = (Post) clone();

		BufferedImage bufferedImage = setImageWidthAndHeight(post.getPostImage(), width, height);
		if (!sepia && !blur) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", baos);

			response.setContentType("images/png");
			response.setContentLength(Math.toIntExact(post.getImageFileSize()));
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"image_%s.png\"", post.getId()));

			response.getOutputStream().flush();
		} else if (sepia && !blur) {
			log.info("Sepia yes blur not");
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			outStream.write(post.getPostImage());

			ByteArrayInputStream inputStream = new ByteArrayInputStream(outStream.toByteArray());

			BufferedImage buffered_image= ImageIO.read(inputStream);
			ByteArrayOutputStream output_stream= new ByteArrayOutputStream();
			ImageIO.write(buffered_image, "jpg", output_stream);
			byte [] byte_array = output_stream.toByteArray();
			ByteArrayInputStream input_stream= new ByteArrayInputStream(byte_array);
			BufferedImage final_buffered_image = ImageIO.read(input_stream);
			ImageIO.write(final_buffered_image , "jpg", new File("final_file.jpg") );

			response.setContentType("images/png");
			response.setContentLength(Math.toIntExact(post.getImageFileSize()));
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"image_%s.png\"", post.getId()));

			response.getOutputStream().flush();
			FileCopyUtils.copy(input_stream, response.getOutputStream());
		} else if (!sepia) {
			log.info("Sepia not blue yes");
		}

		return "";
	}

	private BufferedImage setImageWidthAndHeight(byte[] postImage, double width, double height) throws IOException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(postImage);
		BufferedImage buffered_image= ImageIO.read(inputStream);

		BufferedImage bufferedImage = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_RGB);
		bufferedImage.createGraphics().drawImage(buffered_image, 0, 0, (int) width, (int) height, null);
		return bufferedImage;
	}

	private static BufferedImage toSepia(byte[] img, int sepiaIntensity) throws IOException {
		InputStream in = new ByteArrayInputStream(img);
		BufferedImage bImageFromConvert = ImageIO.read(in);

		BufferedImage sepia = new BufferedImage(bImageFromConvert.getWidth(), bImageFromConvert.getHeight(), BufferedImage.TYPE_INT_RGB);
		int sepiaDepth = 20;

		int w = bImageFromConvert.getWidth();
		int h = bImageFromConvert.getHeight();

		WritableRaster raster = sepia.getRaster();

		int[] pixels = new int[w * h * 3];
		// bImageFromConvert.getRaster().getPixels(0, 0, w, h, pixels);
		for (int i = 0; i < pixels.length; i += 3) {
			int r = pixels[i];
			int g = pixels[i + 1];
			int b = pixels[i + 2];

			int gry = (r + g + b) / 3;
			r = g = b = gry;
			r = r + (sepiaDepth * 2);
			g = g + sepiaDepth;

			if (r > 255) {
				r = 255;
			}
			if (g > 255) {
				g = 255;
			}
			if (b > 255) {
				b = 255;
			}

			b -= sepiaIntensity;

			if (b < 0) {
				b = 0;
			}
			if (b > 255) {
				b = 255;
			}

			pixels[i] = r;
			pixels[i + 1] = g;
			pixels[i + 2] = b;
		}

		raster.setPixels(0, 0, w, h, pixels);
		return sepia;
	}
}
