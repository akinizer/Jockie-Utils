package com.jockie.bot.core.command;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.jockie.bot.core.argument.IArgument;
import com.jockie.bot.core.argument.IEndlessArgument;
import com.jockie.bot.core.category.ICategory;
import com.jockie.bot.core.command.impl.CommandEvent;
import com.jockie.bot.core.command.impl.CommandListener;
import com.jockie.bot.core.cooldown.ICooldown;
import com.jockie.bot.core.option.IOption;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.utils.tuple.Pair;

public interface ICommand {
	
	/**
	 * @return the command which the Listener should look for.
	 */
	public String getCommand();
	
	/**
	 * @return a boolean that will prove if this command should be able to be triggered by guild messages.
	 */
	public boolean isGuildTriggerable();
	
	/**
	 * @return a boolean that will prove if this command should be able to be triggered by private messages.
	 */
	public boolean isPrivateTriggerable();
	
	/**
	 * @return the argument arguments.
	 */
	public IArgument<?>[] getArguments();
	
	/**
	 * @return a boolean that will prove if this command is hidden and should therefore not be shown in help commands
	 */
	public boolean isHidden();
	
	/**
	 * @return a short description of what the command does, preferable use would be a help command
	 */
	public String getShortDescription();
	
	/**
	 * @return a description of what this command does
	 */
	public String getDescription();
	
	/**
	 * @return a set of examples which can be used in a help command 
	 */
	public String[] getExamples();
	
	/**
	 * @return all the possible aliases for this command
	 */
	public String[] getAliases();
	
	/**
	 * @return a list of functions which will be executed before the actual command is
	 */
	public List<Function<CommandEvent, Object>> getBeforeExecuteFunctions();
	
	/**
	 * @return a list of functions which will be executed after the actual command has been executed
	 */
	public List<Function<CommandEvent, Object>> getAfterExecuteFunctions();
	
	/**
	 * @return all options for this command
	 */
	public IOption[] getOptions();
	
	/**
	 * @return a {@link InvalidOptionPolicy} which is used to determine how the {@link CommandListener} should handle a command when an unknown option is provided
	 */
	public InvalidOptionPolicy getInvalidOptionPolicy();
	
	/**
	 * This is used to determine how the {@link CommandListener} should handle a command when an unknown option is provided
	 */
	public enum InvalidOptionPolicy {
		/** Adds the option which can then be accessed through {@link CommandEvent#getOptionsPresent()}  */
		ADD,
		/** Includes the option content as an argument instead of an option */
		INCLUDE,
		/** Ignores (removes) the option from the message */
		IGNORE,
		/** Fails the command */
		FAIL;
	}
	
	/**
	 * @return a {@link ContentOverflowPolicy} which is used to determine how the {@link CommandListener} should handle a command when a message has more content than the command can take
	 */
	public ContentOverflowPolicy getContentOverflowPolicy();
	
	/**
	 * This is used to determine how the {@link CommandListener} should handle a command when a message has more content than the command can take
	 */
	public enum ContentOverflowPolicy {
		IGNORE,
		FAIL;
	}
	
	/**
	 * @return the discord permissions required for this command to function correctly.
	 */
	public Permission[] getBotDiscordPermissionsNeeded();
	
	/**
	 * @return the discord permissions the author is required to have to trigger this command, if the user does not have these permissions the command will not be visible to them.
	 */
	public Permission[] getAuthorDiscordPermissionsNeeded();
	
	/**
	 * @return a boolean that will prove if this command is a <strong>developer</strong> command, if it is a developer command it can only be triggered by developers/authorised users
	 */
	public boolean isDeveloperCommand();
	
	/**
	 * @return a boolean that will prove if this command can be triggered by a bot {@link net.dv8tion.jda.core.entities.User#isBot() User.isBot()}
	 */
	public boolean isBotTriggerable();
	
	/**
	 * @return a boolean that will prove if this command is case sensitive.<p>
	 * For instance if {@link com.jockie.bot.core.command.ICommand#getCommand() Command.getCommand()} 
	 * is equal to <strong>ping</strong> and {@link com.jockie.bot.core.command.ICommand#isCaseSensitive() Command.isCaseSensitive()} 
	 * is <strong>false</strong> then the command could be triggered by any message that {@link String#toLowerCase()} would be equal to <strong>ping</strong>.<br>
	 * On the other hand if {@link com.jockie.bot.core.command.ICommand#isCaseSensitive() Command.isCaseSensitive()} is <strong>true</strong> and
	 * {@link com.jockie.bot.core.command.ICommand#getCommand() Command.getCommand()} is equal to <strong>PiNg</strong> 
	 * then the command could only be triggered if the message is equal to <strong>PiNg</strong> 
	 */
	public boolean isCaseSensitive();
	
	/**
	 * @return the cooldown duration in milliseconds which will be applied to a user
	 * after they use this command. If the cooldown is less than or equal to 0 no
	 * cooldown will be applied
	 */
	public long getCooldownDuration();
	
	/**
	 * @return the scope of which the cooldown should be applied to, for instance if {@link ICooldown.Scope#USER_GUILD}
	 * is used it will be applied for a user per a guild basis.
	 */
	public ICooldown.Scope getCooldownScope();
	
	/**
	 * @return a boolean that will tell whether the command should be executed on a separate thread or not
	 */
	public boolean isExecuteAsync();
	
	/**
	 * @return the parent of this command, a parent is used to get the full trigger for this command, 
	 * for instance if the parent's command trigger was "mute" and this command's trigger was "all" the whole trigger would be "mute all"
	 */
	public ICommand getParent();
	
	/**
	 * @return a boolean that will tell whether or not this command has a parent
	 * 
	 * @see ICommand#getParent()
	 */
	public default boolean hasParent() {
		return this.getParent() != null;
	}
	
	public ICategory getCategory();
	
	/**
	 * @return a boolean to prove whether this command is passive or not, a passive command will not have any executable method and might for instance only have sub-commands.
	 */
	public boolean isPassive();
	
	/**
	 * @return a boolean to prove whether this command is NSFW or not, NSFW commands will not be usable in non-NSFW channels
	 */
	public boolean isNSFW();
	
	/**
	 * @return all sub-commands for this command
	 */
	public List<ICommand> getSubCommands();
	
	/**
	 * Should only be used by the class that implements this and the class that verifies the commands
	 * 
	 * @return a boolean that will prove if the event has the correct properties for the command to be valid
	 */
	
	/* Should this be renamed to something else, such as isAccessible? Since it checks whether the user has access to the command or not, maybe canAccess? */
	public boolean verify(MessageReceivedEvent event, CommandListener commandListener);
	
	/**
	 * This is what should be executed when this command is considered to be valid.
	 * 
	 * @param event the event which triggered the command.
	 * @param arguments the arguments which triggered the command.
	 */
	public void execute(MessageReceivedEvent event, CommandEvent commandEvent, Object... arguments) throws Throwable;
	
	/**
	 * @param event the context
	 * @param prefix the start of the trigger, used for recursively getting sub-commands
	 * 
	 * @return all commands which are related to this command, sub-commands and dummy commands as well as all the aliases, with the appropriate triggers
	 */
	
	/* Including a default implementation in-case people wants to make their own ICommand implementation */
	public default List<Pair<String, ICommand>> getAllCommandsRecursiveWithTriggers(MessageReceivedEvent event, String prefix) {
		List<Pair<String, ICommand>> commands = new ArrayList<>();
		
		commands.add(Pair.of((prefix + " " + this.getCommand()).trim(), this));
		
		String[] aliases = this.getAliases();
		for(String alias : aliases) {
			commands.add(Pair.of((prefix + " " + alias).trim(), this));
		}
		
		for(ICommand command : this.getSubCommands()) {
			commands.addAll(command.getAllCommandsRecursiveWithTriggers(event, (prefix + " " + this.getCommand()).trim()));
			
			for(String alias : aliases) {
				commands.addAll(command.getAllCommandsRecursiveWithTriggers(event, (prefix + " " + alias).trim()));
			}
		}
		
		return commands;
	}
	
	/** 
	 * @param includeDummyCommands whether or not {@link com.jockie.bot.core.command.impl.DummyCommand DummyCommand}s should be included
	 * 
	 * @return all commands which are related to this command, sub-commands and optional dummy commands
	 */
	
	/* Including a default implementation in-case people wants to make their own ICommand implementation */
	/* Not sure if the includeDummyCommands variable should exist or not, it is more or less an internal thing and is not really supposed to be used */
	public default List<ICommand> getAllCommandsRecursive(boolean includeDummyCommands) {
		List<ICommand> commands = new ArrayList<>();
		
		for(ICommand command : this.getSubCommands()) {
			commands.addAll(command.getAllCommandsRecursive(includeDummyCommands));
		}
		
		return commands;
	}
	
	/**
	 * @return information about the arguments
	 */
	public default String getArgumentInfo() {
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < this.getArguments().length; i++) {
			IArgument<?> argument = this.getArguments()[i];
			
			if(argument.getName() != null) {
				builder.append("<").append(argument.getName()).append(">");
			}else{
				builder.append("<argument ").append(i + 1).append(">");
			}
			
			if(argument instanceof IEndlessArgument) {
				IEndlessArgument<?> endlessArgument = (IEndlessArgument<?>) argument;
				
				builder.append("[").append(endlessArgument.getMinArguments()).append("-").append((endlessArgument.getMaxArguments() != 0) ? endlessArgument.getMaxArguments() + "]" : "...]");
			}
			
			if(!argument.hasDefault()) {
				builder.append("*");			
			}
			
			if(i < this.getArguments().length - 1) {
				builder.append(" ");
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * @return full usage information about the command, prefix, command and {@link #getArgumentInfo()}
	 */
	public default String getUsage(String prefix) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(prefix)
			.append(this.getCommandTrigger())
			.append(" ")
			.append(this.getArgumentInfo());
		
		return builder.toString();
	}
	
	/**
	 * @return full usage information about the command but without a prefix
	 * 
	 * @see ICommand#getUsage(String)
	 */
	public default String getUsage() {
		return this.getUsage("");
	}
	
	/**
	 * @return the actual trigger for this command which is created by recursively getting the parents of this command
	 */
	public default String getCommandTrigger() {
		String command = this.getCommand();
		
		ICommand parent = this;
		while((parent = parent.getParent()) != null) {
			command = (parent.getCommand() + " " + command).trim();
		}
		
		return command;
	}
	
	/**
	 * @return the top parent which was got by recursively getting the parent of the commands
	 */
	public default ICommand getTopParent() {
		if(this.hasParent()) {
			ICommand parent = this.getParent();
			while(parent.hasParent()) {
				parent = parent.getParent();
			}
			
			return parent;
		}
		
		return this;
	}
}