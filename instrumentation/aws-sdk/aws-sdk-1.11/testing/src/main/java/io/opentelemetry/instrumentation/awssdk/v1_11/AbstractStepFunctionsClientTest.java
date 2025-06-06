/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.instrumentation.awssdk.v1_11;

import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.DescribeActivityRequest;
import com.amazonaws.services.stepfunctions.model.DescribeStateMachineRequest;
import com.google.common.collect.ImmutableMap;
import io.opentelemetry.testing.internal.armeria.common.HttpResponse;
import io.opentelemetry.testing.internal.armeria.common.HttpStatus;
import io.opentelemetry.testing.internal.armeria.common.MediaType;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public abstract class AbstractStepFunctionsClientTest extends AbstractBaseAwsClientTest {

  public abstract AWSStepFunctionsClientBuilder configureClient(
      AWSStepFunctionsClientBuilder client);

  @Override
  protected boolean hasRequestId() {
    return false;
  }

  @ParameterizedTest
  @MethodSource("provideArguments")
  public void testSendRequestWithMockedResponse(
      String operation,
      Map<String, String> additionalAttributes,
      Function<AWSStepFunctions, Object> call)
      throws Exception {

    AWSStepFunctionsClientBuilder clientBuilder = AWSStepFunctionsClientBuilder.standard();

    AWSStepFunctions client =
        configureClient(clientBuilder)
            .withEndpointConfiguration(endpoint)
            .withCredentials(credentialsProvider)
            .build();

    server.enqueue(HttpResponse.of(HttpStatus.OK, MediaType.PLAIN_TEXT_UTF_8, ""));

    Object response = call.apply(client);
    assertRequestWithMockedResponse(
        response, client, "AWSStepFunctions", operation, "POST", additionalAttributes);
  }

  private static Stream<Arguments> provideArguments() {
    return Stream.of(
        Arguments.of(
            "DescribeStateMachine",
            ImmutableMap.of("aws.stepfunctions.state_machine.arn", "stateMachineArn"),
            (Function<AWSStepFunctions, Object>)
                c ->
                    c.describeStateMachine(
                        new DescribeStateMachineRequest().withStateMachineArn("stateMachineArn"))),
        Arguments.of(
            "DescribeActivity",
            ImmutableMap.of("aws.stepfunctions.activity.arn", "activityArn"),
            (Function<AWSStepFunctions, Object>)
                c ->
                    c.describeActivity(
                        new DescribeActivityRequest().withActivityArn("activityArn"))));
  }
}
