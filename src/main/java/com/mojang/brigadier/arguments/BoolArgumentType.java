// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license.

package com.mojang.brigadier.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BoolArgumentType implements ArgumentType<Boolean> {
    private static final BoolArgumentType INSTANCE = new BoolArgumentType();
    private static final Collection<String> EXAMPLES = List.of("true", "false"); // Solar - immutable

    private BoolArgumentType() {
    }

    public static BoolArgumentType bool() {
        return INSTANCE;
    }

    public static boolean getBool(final CommandContext<?> context, final String name) {
        return context.getArgument(name, Boolean.class);
    }

    @Override
    public Boolean parse(final StringReader reader) throws CommandSyntaxException {
        return reader.readBoolean();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        if ("true".startsWith(builder.getRemainingLowerCase())) {
            builder.suggest("true");
        }
        if ("false".startsWith(builder.getRemainingLowerCase())) {
            builder.suggest("false");
        }
        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
