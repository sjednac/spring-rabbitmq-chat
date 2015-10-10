package com.mintbeans.chat.model;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import static java.time.ZoneOffset.UTC;
import static lombok.AccessLevel.PACKAGE;

@Value
@AllArgsConstructor(access = PACKAGE)
public class Message {
	String id;
	ZonedDateTime dateTime;
	String content;

	@JsonCreator
	public static Message message(@JsonProperty("id") String id, @JsonProperty("dateTime") ZonedDateTime dateTime, @JsonProperty("content") String content) {
		return new Message(id, dateTime, content);
	}

	public static Message textMessage(@NonNull String content) {
		return new Message(UUID.randomUUID().toString(), ZonedDateTime.now(UTC), content);
	}
}
