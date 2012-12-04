package com.suncorp.smashit.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.suncorp.smashit.model.Faq;

@Repository
public class JdbcFaqRepository implements FaqRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int getFaqCount() {
		// TODO Auto-generated method stub
		String query = "select count(*) from faq";
		return this.jdbcTemplate.queryForInt(query);
	}

	@Override
	public List<Faq> getFaqs(int page, final int limit) {
		// TODO Auto-generated method stub
		// return null;

		final String queryPage = String.valueOf(page * limit);

		PreparedStatementCreator psc = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				// TODO Auto-generated method stub
				return connection.prepareStatement("select * from faq limit "
						+ queryPage + "," + String.valueOf(limit));
			}
		};

		PreparedStatementSetter pss = new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement preparedStatement)
					throws SQLException {
				// TODO Auto-generated method stub
				//
			}
		};

		ResultSetExtractor<List<Faq>> rse = new ResultSetExtractor<List<Faq>>() {

			@Override
			public List<Faq> extractData(ResultSet resultSet)
					throws SQLException, DataAccessException {
				ArrayList<Faq> faqs = new ArrayList<Faq>();
				while (resultSet.next()) {
					Faq f = new Faq();
					// System.err.println("DDDDD"+resultSet.getString(1)+" "+);
					f.setFaqId(resultSet.getInt(1));
					f.setQuestion(resultSet.getString(2));
					f.setAnswer(resultSet.getString(3));
					faqs.add(f);
				}

				return faqs;

			}
		};

		return jdbcTemplate.query(psc, pss, rse);
	}

	@Override
	public int saveFaq(List<Faq> faqs) {
		// TODO Auto-generated method stub
		for (Faq f : faqs) {
			System.err.println("Save:"+f.getQuestion()+" "+f.getAnswer());
			String query = "insert into faq (question,answer) values(?,?)";
			this.jdbcTemplate.update(query, f.getQuestion(), f.getAnswer());
		}
		return 0;
	}

	@Override
	public int deleteFaqById(List<Integer> faqIds) {
		// TODO Auto-generated method stub
		return 0;
	}

}
