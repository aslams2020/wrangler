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

import org.junit.Assert;
import org.junit.Test;

public class TimeDurationTest {

    @Test
    public void testMilliseconds() {
        TimeDuration duration = new TimeDuration("100ms");
        Assert.assertEquals(100, duration.getMilliseconds());
    }

    @Test
    public void testSeconds() {
        TimeDuration duration = new TimeDuration("2s");
        Assert.assertEquals(2000, duration.getMilliseconds());
    }

    @Test
    public void testNanoseconds() {
        TimeDuration duration = new TimeDuration("1s");
        Assert.assertEquals(1_000_000_000, duration.getNanoseconds());
    }
}
