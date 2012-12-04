package com.suncorp.smashit;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.suncorp.smashit.model.Faq;
import com.suncorp.smashit.model.QueryRequest;
import com.suncorp.smashit.repository.FaqRepository;
import com.suncorp.smashit.repository.JdbcFaqRepository;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	private JdbcFaqRepository jdbcFaqRepository;


//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String home(Locale locale, Model model) {
//		logger.info("Welcome home! The client locale is {}.", locale);
//
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
//				DateFormat.LONG, locale);
//
//		String formattedDate = dateFormat.format(date);
//
//		model.addAttribute("serverTime", formattedDate);
//
//		return "home";
//	}

	@Transactional(readOnly=true)
	@RequestMapping(value = "/faqData", method = RequestMethod.GET)
	public void listFaq(@RequestParam("callback") String callback,
			@RequestParam("page") int page, @RequestParam("limit") int limit,
			HttpServletResponse response) {

		System.err.println("Callback:" + callback + " page:" + page + " limit:"
				+ limit);

		List<Faq> faqs = this.jdbcFaqRepository.getFaqs(page, limit);
		Gson gson = new Gson();

		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(callback + "(" + gson.toJson(faqs) + ")");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Transactional
	@RequestMapping(value = "/saveFaqData")
	public void addFaq(@RequestParam("callback") String callback,
			@RequestParam("question") String question,
			@RequestParam("answer") String answer, HttpServletResponse response) {
		
		System.err.println("on saveFaq "+question+" "+question);
		Faq f=new Faq();
		f.setQuestion(question);
		f.setAnswer(answer);
		ArrayList<Faq> faqs=new ArrayList<Faq>();
		faqs.add(f);
		this.jdbcFaqRepository.saveFaq(faqs);
		Gson gson = new Gson();

		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(callback + "(" + gson.toJson("1") + ")");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
