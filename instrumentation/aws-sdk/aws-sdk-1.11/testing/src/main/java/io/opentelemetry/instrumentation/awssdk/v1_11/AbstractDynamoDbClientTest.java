/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.instrumentation.awssdk.v1_11;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.google.common.collect.ImmutableMap;
import io.opentelemetry.testing.internal.armeria.common.HttpResponse;
import io.opentelemetry.testing.internal.armeria.common.HttpStatus;
import io.opentelemetry.testing.internal.armeria.common.MediaType;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public abstract class AbstractDynamoDbClientTest extends AbstractBaseAwsClientTest {

  public abstract AmazonDynamoDBClientBuilder configureClient(AmazonDynamoDBClientBuilder client);

  @Override
  protected boolean hasRequestId() {
    return false;
  }

  @Test
  public void sendRequestWithMockedResponse() throws Exception {
    AmazonDynamoDBClientBuilder clientBuilder = AmazonDynamoDBClientBuilder.standard();
    AmazonDynamoDB client =
        configureClient(clientBuilder)
            .withEndpointConfiguration(endpoint)
            .withCredentials(credentialsProvider)
            .build();

    server.enqueue(HttpResponse.of(HttpStatus.OK, MediaType.PLAIN_TEXT_UTF_8, ""));

    Object response = client.createTable(new CreateTableRequest("sometable", null));
    assertRequestWithMockedResponse(
        response,
        client,
        "DynamoDBv2",
        "CreateTable",
        "POST",
        ImmutableMap.of("aws.table.name", "sometable"));
  }

  @Test
  public void testGetTableArnWithMockedResponse() {
    AmazonDynamoDBClientBuilder clientBuilder = AmazonDynamoDBClientBuilder.standard();
    AmazonDynamoDB client =
        configureClient(clientBuilder)
            .withEndpointConfiguration(endpoint)
            .withCredentials(credentialsProvider)
            .build();

    String tableName = "MockTable";
    String expectedArn = "arn:aws:dynamodb:us-west-2:123456789012:table/" + tableName;

    String body = "{\n" +
        "  \"Table\": {\n" +
        "    \"TableName\": \"" + tableName + "\",\n" +
        "    \"TableArn\": \"" + expectedArn + "\"\n" +
        "  }\n" +
        "}";

    server.enqueue(HttpResponse.of(HttpStatus.OK, MediaType.JSON_UTF_8, body));

    String actualArn = client.describeTable(new DescribeTableRequest().withTableName(tableName))
        .getTable().getTableArn();

    assertEquals("Table ARN should match expected value", expectedArn, actualArn);
  }
}
