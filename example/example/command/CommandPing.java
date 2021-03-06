package example.command;

import com.jockie.bot.core.command.impl.CommandImpl;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandPing extends CommandImpl {

	public CommandPing() {
		super("ping");
		
		super.setDescription("Simple ping command");
	}
	
	public void onCommand(MessageReceivedEvent event) {
		event.getChannel().sendMessage(event.getJDA().getPing() + " ms").queue();
	}
}