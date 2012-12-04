package com.suncorp.smashit.repository;

import java.util.List;

import com.suncorp.smashit.model.Faq;

public interface FaqRepository {

	
	public int getFaqCount();
	
	public List<Faq> getFaqs(int page, int limit);
	
	public int saveFaq(List<Faq> faqs);
	
	public int deleteFaqById(List<Integer> faqIds);
}
