/*
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 0.1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package io.idmc.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.openapitools.jackson.nullable.JsonNullableModule;

import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Configuration and utility class for API clients.
 *
 * <p>This class can be constructed and modified, then used to instantiate the
 * various API classes. The API classes use the settings in this class to
 * configure themselves, but otherwise do not store a link to this class.</p>
 *
 * <p>This class is mutable and not synchronized, so it is not thread-safe.
 * The API classes generated from this are immutable and thread-safe.</p>
 *
 * <p>The setter methods of this class return the current object to facilitate
 * a fluent style of configuration.</p>
 */
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.5.0")
public class ApiClient {

  private HttpClient.Builder builder;
  private ObjectMapper mapper;
  private String scheme;
  private String host;
  private int port;
  private String basePath;
  private Consumer<HttpRequest.Builder> interceptor;
  private Consumer<HttpResponse<InputStream>> responseInterceptor;
  private Consumer<HttpResponse<String>> asyncResponseInterceptor;
  private Duration readTimeout;
  private Duration connectTimeout;

  private static String valueToString(Object value) {
    if (value == null) {
      return "";
    }
    if (value instanceof OffsetDateTime) {
      return ((OffsetDateTime) value).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
    return value.toString();
  }

  /**
   * URL encode a string in the UTF-8 encoding.
   *
   * @param s String to encode.
   * @return URL-encoded representation of the input string.
   */
  public static String urlEncode(String s) {
    return URLEncoder.encode(s, UTF_8).replaceAll("\\+", "%20");
  }

  /**
   * Convert a URL query name/value parameter to a list of encoded {@link Pair}
   * objects.
   *
   * <p>The value can be null, in which case an empty list is returned.</p>
   *
   * @param name The query name parameter.
   * @param value The query value, which may not be a collection but may be
   *              null.
   * @return A singleton list of the {@link Pair} objects representing the input
   * parameters, which is encoded for use in a URL. If the value is null, an
   * empty list is returned.
   */
  public static List<Pair> parameterToPairs(String name, Object value) {
    if (name == null || name.isEmpty() || value == null) {
      return Collections.emptyList();
    }
    return Collections.singletonList(new Pair(urlEncode(name), urlEncode(valueToString(value))));
  }

  /**
   * Convert a URL query name/collection parameter to a list of encoded
   * {@link Pair} objects.
   *
   * @param collectionFormat The swagger collectionFormat string (csv, tsv, etc).
   * @param name The query name parameter.
   * @param values A collection of values for the given query name, which may be
   *               null.
   * @return A list of {@link Pair} objects representing the input parameters,
   * which is encoded for use in a URL. If the values collection is null, an
   * empty list is returned.
   */
  public static List<Pair> parameterToPairs(
      String collectionFormat, String name, Collection<?> values) {
    if (name == null || name.isEmpty() || values == null || values.isEmpty()) {
      return Collections.emptyList();
    }

    // get the collection format (default: csv)
    String format = collectionFormat == null || collectionFormat.isEmpty() ? "csv" : collectionFormat;

    // create the params based on the collection format
    if ("multi".equals(format)) {
      return values.stream()
          .map(value -> new Pair(urlEncode(name), urlEncode(valueToString(value))))
          .collect(Collectors.toList());
    }

    String delimiter;
    switch(format) {
      case "csv":
        delimiter = urlEncode(",");
        break;
      case "ssv":
        delimiter = urlEncode(" ");
        break;
      case "tsv":
        delimiter = urlEncode("\t");
        break;
      case "pipes":
        delimiter = urlEncode("|");
        break;
      default:
        throw new IllegalArgumentException("Illegal collection format: " + collectionFormat);
    }

    StringJoiner joiner = new StringJoiner(delimiter);
    for (Object value : values) {
      joiner.add(urlEncode(valueToString(value)));
    }

    return Collections.singletonList(new Pair(urlEncode(name), joiner.toString()));
  }

  /**
   * Create an instance of ApiClient.
   */
  public ApiClient() {
    this.builder = createDefaultHttpClientBuilder();
    this.mapper = createDefaultObjectMapper();
    updateBaseUri(getDefaultBaseUri());
    interceptor = null;
    readTimeout = null;
    connectTimeout = null;
    responseInterceptor = null;
    asyncResponseInterceptor = null;
  }

  /**
   * Create an instance of ApiClient.
   *
   * @param builder Http client builder.
   * @param mapper Object mapper.
   * @param baseUri Base URI
   */
  public ApiClient(HttpClient.Builder builder, ObjectMapper mapper, String baseUri) {
    this.builder = builder;
    this.mapper = mapper;
    updateBaseUri(baseUri != null ? baseUri : getDefaultBaseUri());
    interceptor = null;
    readTimeout = null;
    connectTimeout = null;
    responseInterceptor = null;
    asyncResponseInterceptor = null;
  }

  protected ObjectMapper createDefaultObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    mapper.registerModule(new JavaTimeModule());
    mapper.registerModule(new JsonNullableModule());
    return mapper;
  }

  protected String getDefaultBaseUri() {
    return "https://na4.dm-us.informaticacloud.com/saas";
  }

  protected HttpClient.Builder createDefaultHttpClientBuilder() {
    return HttpClient.newBuilder();
  }

  public void updateBaseUri(String baseUri) {
    URI uri = URI.create(baseUri);
    scheme = uri.getScheme();
    host = uri.getHost();
    port = uri.getPort();
    basePath = uri.getRawPath();
  }

  /**
   * Set a custom {@link HttpClient.Builder} object to use when creating the
   * {@link HttpClient} that is used by the API client.
   *
   * @param builder Custom client builder.
   * @return This object.
   */
  public ApiClient setHttpClientBuilder(HttpClient.Builder builder) {
    this.builder = builder;
    return this;
  }

  /**
   * Get an {@link HttpClient} based on the current {@link HttpClient.Builder}.
   *
   * <p>The returned object is immutable and thread-safe.</p>
   *
   * @return The HTTP client.
   */
  public HttpClient getHttpClient() {
    return builder.build();
  }

  /**
   * Set a custom {@link ObjectMapper} to serialize and deserialize the request
   * and response bodies.
   *
   * @param mapper Custom object mapper.
   * @return This object.
   */
  public ApiClient setObjectMapper(ObjectMapper mapper) {
    this.mapper = mapper;
    return this;
  }

  /**
   * Get a copy of the current {@link ObjectMapper}.
   *
   * @return A copy of the current object mapper.
   */
  public ObjectMapper getObjectMapper() {
    return mapper.copy();
  }

  /**
   * Set a custom host name for the target service.
   *
   * @param host The host name of the target service.
   * @return This object.
   */
  public ApiClient setHost(String host) {
    this.host = host;
    return this;
  }

  /**
   * Set a custom port number for the target service.
   *
   * @param port The port of the target service. Set this to -1 to reset the
   *             value to the default for the scheme.
   * @return This object.
   */
  public ApiClient setPort(int port) {
    this.port = port;
    return this;
  }

  /**
   * Set a custom base path for the target service, for example '/v2'.
   *
   * @param basePath The base path against which the rest of the path is
   *                 resolved.
   * @return This object.
   */
  public ApiClient setBasePath(String basePath) {
    this.basePath = basePath;
    return this;
  }

  /**
   * Get the base URI to resolve the endpoint paths against.
   *
   * @return The complete base URI that the rest of the API parameters are
   * resolved against.
   */
  public String getBaseUri() {
    return scheme + "://" + host + (port == -1 ? "" : ":" + port) + basePath;
  }

  /**
   * Set a custom scheme for the target service, for example 'https'.
   *
   * @param scheme The scheme of the target service
   * @return This object.
   */
  public ApiClient setScheme(String scheme){
    this.scheme = scheme;
    return this;
  }

  /**
   * Set a custom request interceptor.
   *
   * <p>A request interceptor is a mechanism for altering each request before it
   * is sent. After the request has been fully configured but not yet built, the
   * request builder is passed into this function for further modification,
   * after which it is sent out.</p>
   *
   * <p>This is useful for altering the requests in a custom manner, such as
   * adding headers. It could also be used for logging and monitoring.</p>
   *
   * @param interceptor A function invoked before creating each request. A value
   *                    of null resets the interceptor to a no-op.
   * @return This object.
   */
  public ApiClient setRequestInterceptor(Consumer<HttpRequest.Builder> interceptor) {
    this.interceptor = interceptor;
    return this;
  }

  /**
   * Get the custom interceptor.
   *
   * @return The custom interceptor that was set, or null if there isn't any.
   */
  public Consumer<HttpRequest.Builder> getRequestInterceptor() {
    return interceptor;
  }

  /**
   * Set a custom response interceptor.
   *
   * <p>This is useful for logging, monitoring or extraction of header variables</p>
   *
   * @param interceptor A function invoked before creating each request. A value
   *                    of null resets the interceptor to a no-op.
   * @return This object.
   */
  public ApiClient setResponseInterceptor(Consumer<HttpResponse<InputStream>> interceptor) {
    this.responseInterceptor = interceptor;
    return this;
  }

 /**
   * Get the custom response interceptor.
   *
   * @return The custom interceptor that was set, or null if there isn't any.
   */
  public Consumer<HttpResponse<InputStream>> getResponseInterceptor() {
    return responseInterceptor;
  }

  /**
   * Set a custom async response interceptor. Use this interceptor when asyncNative is set to 'true'.
   *
   * <p>This is useful for logging, monitoring or extraction of header variables</p>
   *
   * @param interceptor A function invoked before creating each request. A value
   *                    of null resets the interceptor to a no-op.
   * @return This object.
   */
  public ApiClient setAsyncResponseInterceptor(Consumer<HttpResponse<String>> interceptor) {
    this.asyncResponseInterceptor = interceptor;
    return this;
  }

 /**
   * Get the custom async response interceptor. Use this interceptor when asyncNative is set to 'true'.
   *
   * @return The custom interceptor that was set, or null if there isn't any.
   */
  public Consumer<HttpResponse<String>> getAsyncResponseInterceptor() {
    return asyncResponseInterceptor;
  }

  /**
   * Set the read timeout for the http client.
   *
   * <p>This is the value used by default for each request, though it can be
   * overridden on a per-request basis with a request interceptor.</p>
   *
   * @param readTimeout The read timeout used by default by the http client.
   *                    Setting this value to null resets the timeout to an
   *                    effectively infinite value.
   * @return This object.
   */
  public ApiClient setReadTimeout(Duration readTimeout) {
    this.readTimeout = readTimeout;
    return this;
  }

  /**
   * Get the read timeout that was set.
   *
   * @return The read timeout, or null if no timeout was set. Null represents
   * an infinite wait time.
   */
  public Duration getReadTimeout() {
    return readTimeout;
  }
  /**
   * Sets the connect timeout (in milliseconds) for the http client.
   *
   * <p> In the case where a new connection needs to be established, if
   * the connection cannot be established within the given {@code
   * duration}, then {@link HttpClient#send(HttpRequest,BodyHandler)
   * HttpClient::send} throws an {@link HttpConnectTimeoutException}, or
   * {@link HttpClient#sendAsync(HttpRequest,BodyHandler)
   * HttpClient::sendAsync} completes exceptionally with an
   * {@code HttpConnectTimeoutException}. If a new connection does not
   * need to be established, for example if a connection can be reused
   * from a previous request, then this timeout duration has no effect.
   *
   * @param connectTimeout connection timeout in milliseconds
   *
   * @return This object.
   */
  public ApiClient setConnectTimeout(Duration connectTimeout) {
    this.connectTimeout = connectTimeout;
    this.builder.connectTimeout(connectTimeout);
    return this;
  }

  /**
   * Get connection timeout (in milliseconds).
   *
   * @return Timeout in milliseconds
   */
  public Duration getConnectTimeout() {
    return connectTimeout;
  }
}
