/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.instrumentation.awssdk.v2_2.internal;

import static io.opentelemetry.api.common.AttributeKey.stringKey;

import io.opentelemetry.api.common.AttributeKey;

final class AwsExperimentalAttributes {
  static final AttributeKey<String> AWS_BUCKET_NAME = stringKey("aws.bucket.name");
  static final AttributeKey<String> AWS_QUEUE_URL = stringKey("aws.queue.url");
  static final AttributeKey<String> AWS_QUEUE_NAME = stringKey("aws.queue.name");
  static final AttributeKey<String> AWS_STREAM_NAME = stringKey("aws.stream.name");
  static final AttributeKey<String> AWS_STREAM_ARN = stringKey("aws.stream.arn");
  static final AttributeKey<String> AWS_TABLE_NAME = stringKey("aws.table.name");
  static final AttributeKey<String> AWS_GUARDRAIL_ID = stringKey("aws.bedrock.guardrail.id");
  static final AttributeKey<String> AWS_GUARDRAIL_ARN = stringKey("aws.bedrock.guardrail.arn");
  static final AttributeKey<String> AWS_AGENT_ID = stringKey("aws.bedrock.agent.id");
  static final AttributeKey<String> AWS_DATA_SOURCE_ID = stringKey("aws.bedrock.data_source.id");
  static final AttributeKey<String> AWS_KNOWLEDGE_BASE_ID =
      stringKey("aws.bedrock.knowledge_base.id");

  // TODO: Merge in gen_ai attributes in opentelemetry-semconv-incubating once upgrade to v1.26.0
  static final AttributeKey<String> GEN_AI_MODEL = stringKey("gen_ai.request.model");
  static final AttributeKey<String> GEN_AI_SYSTEM = stringKey("gen_ai.system");

  static final AttributeKey<String> GEN_AI_REQUEST_MAX_TOKENS =
      stringKey("gen_ai.request.max_tokens");

  static final AttributeKey<String> GEN_AI_REQUEST_TEMPERATURE =
      stringKey("gen_ai.request.temperature");

  static final AttributeKey<String> GEN_AI_REQUEST_TOP_P = stringKey("gen_ai.request.top_p");

  static final AttributeKey<String> GEN_AI_RESPONSE_FINISH_REASONS =
      stringKey("gen_ai.response.finish_reasons");

  static final AttributeKey<String> GEN_AI_USAGE_INPUT_TOKENS =
      stringKey("gen_ai.usage.input_tokens");

  static final AttributeKey<String> GEN_AI_USAGE_OUTPUT_TOKENS =
      stringKey("gen_ai.usage.output_tokens");

  static final AttributeKey<String> AWS_STATE_MACHINE_ARN =
      stringKey("aws.stepfunctions.state_machine.arn");

  static final AttributeKey<String> AWS_STEP_FUNCTIONS_ACTIVITY_ARN =
      stringKey("aws.stepfunctions.activity.arn");

  static final AttributeKey<String> AWS_SNS_TOPIC_ARN = stringKey("aws.sns.topic.arn");

  static final AttributeKey<String> AWS_SECRET_ARN = stringKey("aws.secretsmanager.secret.arn");

  static final AttributeKey<String> AWS_LAMBDA_NAME = stringKey("aws.lambda.function.name");

  static final AttributeKey<String> AWS_LAMBDA_ARN = stringKey("aws.lambda.function.arn");

  static final AttributeKey<String> AWS_LAMBDA_RESOURCE_ID =
      stringKey("aws.lambda.resource_mapping.id");

  static final AttributeKey<String> AWS_TABLE_ARN = stringKey("aws.table.arn");

  static final AttributeKey<String> AWS_REMOTE_RESOURCE_ACCESS_KEY = stringKey("aws.remote.resource.account.access_key");

  static final AttributeKey<String> AWS_REMOTE_RESOURCE_REGION = stringKey("aws.remote.resource.region");

  static boolean isGenAiAttribute(String attributeKey) {
    return attributeKey.equals(GEN_AI_REQUEST_MAX_TOKENS.getKey())
        || attributeKey.equals(GEN_AI_REQUEST_TEMPERATURE.getKey())
        || attributeKey.equals(GEN_AI_REQUEST_TOP_P.getKey())
        || attributeKey.equals(GEN_AI_RESPONSE_FINISH_REASONS.getKey())
        || attributeKey.equals(GEN_AI_USAGE_INPUT_TOKENS.getKey())
        || attributeKey.equals(GEN_AI_USAGE_OUTPUT_TOKENS.getKey());
  }

  private AwsExperimentalAttributes() {}
}
