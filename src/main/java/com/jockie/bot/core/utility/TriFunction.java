package com.jockie.bot.core.utility;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface TriFunction<A, B, C, R> {
	
    R apply(A a, B b, C c);
    
    default <V> TriFunction<A, B, C, V> andThen(Function<? super R, ? extends V> after) {
    	return (a, b, c) -> Objects.requireNonNull(after).apply(apply(a, b, c));
    }
}