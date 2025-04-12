/*
 * Copyright © 2024 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */

 package io.cdap.wrangler;

 import io.cdap.wrangler.api.Row;
 import io.cdap.wrangler.api.RecipePipeline;
 import io.cdap.wrangler.api.RecipeParser;
 import io.cdap.wrangler.executor.RecipePipelineExecutor;
 import io.cdap.wrangler.parser.GrammarBasedParser;
 import org.junit.Assert;
 import org.junit.Test;
 
 import java.util.Arrays;
 import java.util.List;
 
 /**
  * Unit test for AggregateStats directive.
  */
 public class AggregateStatsTest {
 
     @Test
     public void testAggregationTotal() throws Exception {
         List<Row> inputRows = Arrays.asList(
            new Row("data_transfer_size", 1048576L).add("response_time", 500L), // 1MB in bytes, 500ms
            new Row("data_transfer_size", 2097152L).add("response_time", 1500L), // 2MB in bytes, 1.5s
            new Row("data_transfer_size", 524288L).add("response_time", 2000L));

         String[] recipe = new String[] {
                 "aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec"
         };
 
         List<Row> results = TestingRig.execute(recipe, inputRows);
 
         // There should be only one output row from aggregate
         Assert.assertEquals(1, results.size());
 
         Row result = results.get(0);
 
         double totalMB = 1 + 2 + 0.5; // 3.5 MB
         double totalSec = 0.5 + 1.5 + 2; // 4.0 sec
 
         double actualMB = (Double) result.getValue("total_size_mb");
         double actualSec = (Double) result.getValue("total_time_sec");
 
         Assert.assertEquals(totalMB, actualMB, 0.001);
         Assert.assertEquals(totalSec, actualSec, 0.001);
     }
 }
