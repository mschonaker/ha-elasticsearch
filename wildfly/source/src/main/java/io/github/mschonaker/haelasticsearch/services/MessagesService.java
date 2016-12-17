package io.github.mschonaker.haelasticsearch.services;

public interface MessagesService {

	Message find(String id);

	void update(Message message);

	Results<Message> findAll();

	void insert(String message);

	void delete(String id);

}
