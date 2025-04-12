/*
 * Copyright © 2024 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */

package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.Locale;

/**
 * Represents a byte size value (e.g., "10KB", "1.5MB", "2GB").
 * This class extracts and stores the numeric value and its unit.
 * It also provides a method to convert the value into canonical bytes.
 */
public class ByteSize implements Token {
    private final double value;
    private final String unit;

    public ByteSize(String input) {
        input = input.trim().toUpperCase(Locale.ENGLISH);
        this.unit = extractUnit(input);
        this.value = extractNumeric(input);
    }

    private String extractUnit(String input) {
        if (input.endsWith("KB"))
            return "KB";
        if (input.endsWith("MB"))
            return "MB";
        if (input.endsWith("GB"))
            return "GB";
        if (input.endsWith("K"))
            return "KB";
        if (input.endsWith("M"))
            return "MB";
        if (input.endsWith("G"))
            return "GB";
        throw new IllegalArgumentException("Unknown byte size unit: " + input);
    }

    private double extractNumeric(String input) {
        return Double.parseDouble(input.replaceAll("[^\\d.]", ""));
    }

    public long getBytes() {
        switch (unit) {
            case "KB":
                return (long) (value * 1024);
            case "MB":
                return (long) (value * 1024 * 1024);
            case "GB":
                return (long) (value * 1024 * 1024 * 1024);
            default:
                throw new IllegalStateException("Unexpected unit: " + unit);
        }
    }

    @Override
    public Object value() {
        return getBytes();
    }

    @Override
    public TokenType type() {
        return TokenType.BYTE_SIZE;
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(getBytes());
    }
}