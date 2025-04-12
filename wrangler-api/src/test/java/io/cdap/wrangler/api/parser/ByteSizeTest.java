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

public class ByteSizeTest {

    @Test
    public void testKB() {
        ByteSize size = new ByteSize("10KB");
        Assert.assertEquals(10 * 1024L, size.getBytes());
    }

    @Test
    public void testMB() {
        ByteSize size = new ByteSize("1.5MB");
        Assert.assertEquals((long) (1.5 * 1024 * 1024), size.getBytes());
    }

    @Test
    public void testGB() {
        ByteSize size = new ByteSize("2GB");
        Assert.assertEquals(2L * 1024 * 1024 * 1024, size.getBytes());
    }
}
