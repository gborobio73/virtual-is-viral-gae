package com.leeloo.viv.work.repository;

import java.util.UUID;

public class IdGenerator implements IIdGenerator {

	public String generateId()
	{
		return UUID.randomUUID().toString();
	}
}
