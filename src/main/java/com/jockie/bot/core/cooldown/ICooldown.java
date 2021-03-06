package com.jockie.bot.core.cooldown;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface ICooldown {
	
	public enum Scope {
		USER(event -> String.format("u:%s", event.getAuthor().getId())),
		USER_CHANNEL(event -> String.format("u:%s,c:%s", event.getAuthor().getId(), event.getChannel().getId())),
		USER_GUILD(event -> event.getChannelType().isGuild() ? String.format("u:%s,g:%s", event.getAuthor().getId(), event.getGuild().getId()) : USER_CHANNEL.getContextKey(event)),
		USER_SHARD(event -> event.getJDA().getShardInfo() != null ? String.format("u:%s,s:%s", event.getAuthor().getId(), event.getJDA().getShardInfo()) : USER.getContextKey(event)),
		CHANNEL(event -> String.format("c:%s", event.getChannel().getId())),
		GUILD(event -> String.format("g:%s", event.getGuild().getId())),
		SHARD(event -> event.getJDA().getShardInfo() != null ? String.format("s:%s", event.getJDA().getShardInfo()) : ""),
		GLOBAL(event -> "");
		
		private Function<MessageReceivedEvent, String> keyFunction;
		
		private Scope(Function<MessageReceivedEvent, String> function) {
			this.keyFunction = function;
		}
		
		public String getContextKey(MessageReceivedEvent event) {
			return this.keyFunction.apply(event);
		}
	}
	
	public void applyContext(MessageReceivedEvent event);
	
	public Scope getScope();
	
	public String getContextKey();
	
	public Instant getTimeStarted();
	
	public TimeUnit getDurationUnit();
	
	public long getDuration();
	public long getDuration(TimeUnit unit);
	
	public long getTimeRemainingMillis();
	public Duration getTimeRemaining();
	
	public boolean hasExpired();
	
	public void updateDuration(long duration);
	public void updateDuration(long duration, TimeUnit unit);
	
	public void increase(long duration);
	public void increase(long duration, TimeUnit unit);
	
	public void decrease(long duration);
	public void decrease(long duration, TimeUnit unit);
	
	public void start();
	public void reset();
	public void cancel();
	
}