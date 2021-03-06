package example.command.fun;

import java.util.Random;

import com.jockie.bot.core.argument.Argument;
import com.jockie.bot.core.command.impl.CommandImpl;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandDecide extends CommandImpl {
	
	/* No need to create a new one each time someone uses it */
	private Random random = new Random();

	public CommandDecide() {
		super("decide");
		
		super.setDescription("Give me two sentences and I will choose one of them");
	}
	
	public void onCommand(MessageReceivedEvent event, @Argument(name="statement") String statement, @Argument(name="statement 2") String statement2) {
		event.getChannel().sendMessage("**" + (this.random.nextBoolean() ? statement : statement2) + "**" + " seems more reasonable to me!").queue();
	}
}