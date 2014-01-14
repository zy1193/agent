package com.keicei.util;

import com.keicei.agent.domain.entity.Card;

public class PwdUtil {

	public static final String despwd(String password) {
		Card card = new Card();
		card.setPassword(password);
		return card.getDespwd();
	}

}
