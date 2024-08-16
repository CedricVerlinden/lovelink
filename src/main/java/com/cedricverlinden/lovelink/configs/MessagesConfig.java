package com.cedricverlinden.lovelink.configs;

import com.cedricverlinden.lovelink.LoveLink;
import lombok.Getter;

@Getter
public class MessagesConfig extends BaseConfig {

	private String COMMAND_PROPOSE_SENDER;
	private String COMMAND_PROPOSE_RECEIVER;

	private String COMMAND_ACCEPT_SENDER;
	private String COMMAND_ACCEPT_RECEIVER;

	private String COMMAND_DECLINE_SENDER;
	private String COMMAND_DECLINE_RECEIVER;

	public MessagesConfig(LoveLink plugin) {
		super(plugin, "messages");
	}

	@Override
	protected void loadValues() {
		COMMAND_PROPOSE_SENDER = config.getString("command.propose.to_sender");
		COMMAND_PROPOSE_RECEIVER = config.getString("command.propose.to_receiver");

		COMMAND_ACCEPT_SENDER = config.getString("command.accept.to_sender");
		COMMAND_ACCEPT_RECEIVER = config.getString("command.accept.to_receiver");

		COMMAND_DECLINE_SENDER = config.getString("command.decline.to_sender");
		COMMAND_DECLINE_RECEIVER = config.getString("command.decline.to_receiver");
	}
}
