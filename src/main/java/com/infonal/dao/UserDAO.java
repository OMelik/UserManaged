package com.infonal.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.infonal.config.SpringMongoConfig;
import com.infonal.model.SequenceId;
import com.infonal.model.User;
import com.infonal.util.UserManagedConstants;

@Repository
public class UserDAO {

	MongoOperations mongoTemplate;

	public User getUserById(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		User user = getMongoTemplate().findOne(query, (User.class));
		return user;
	}

	public void insertUserWithSequence(User user) {
		String id = String.valueOf(getNextSequenceId(UserManagedConstants.SEQUNCE_KEY_USER));
		user.setId(id);
		getMongoTemplate().insert(user);
	}
	
	public void insertUser(User user) {
		getMongoTemplate().insert(user);
	}

	public void deleteUserFindById(String id) {
		User delUser = getUserById(id);
		deleteUser(delUser);
	}

	public void deleteUser(User user) {
		getMongoTemplate().remove(user);
	}

	public User editUser(User user) {
		Query editQuery = new Query();
		editQuery.addCriteria(Criteria.where("id").is(user.getId()));

		Update update = new Update();
		update.set("name", user.getName());
		update.set("surname", user.getSurname());
		update.set("phone", user.getPhone());

		return getMongoTemplate().findAndModify(editQuery, update, new FindAndModifyOptions().returnNew(true), User.class);
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "id"));
		users = (ArrayList<User>) getMongoTemplate().find(query, (User.class));
		return users;
	}

	public long getNextSequenceId(String key) {
		Query query = new Query(Criteria.where("id").is(key));
		Update update = new Update();
		update.inc("seq", 1);
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);
		SequenceId seqId = getMongoTemplate().findAndModify(query, update, options, SequenceId.class);
		return seqId.getSeq();
	}

	public MongoOperations getMongoTemplate() {

		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoTemplate = (MongoOperations) ctx.getBean("mongoTemplate");

		return mongoTemplate;
	}

	public void setMongoTemplate(MongoOperations mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

}
