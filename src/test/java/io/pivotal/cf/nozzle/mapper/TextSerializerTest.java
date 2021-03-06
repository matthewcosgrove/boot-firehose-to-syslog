package io.pivotal.cf.nozzle.mapper;

import io.pivotal.cf.nozzle.doppler.Envelope;
import io.pivotal.cf.nozzle.doppler.EventType;
import io.pivotal.cf.nozzle.doppler.WrappedEnvelope;
import org.cloudfoundry.doppler.CounterEvent;
import org.cloudfoundry.doppler.HttpStart;
import org.cloudfoundry.doppler.Method;
import org.cloudfoundry.doppler.PeerType;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TextSerializerTest {

	@Test
	public void basicTextSerializer() {
		TextSerializationMapper textSerializationMapper = new TextSerializationMapper();
		Envelope counterEventEnvelope = sampleCounterEvent();
		WrappedEnvelope wrappedEnvelope = new WrappedEnvelope(counterEventEnvelope);

		String text = textSerializationMapper.serialize(wrappedEnvelope);

		assertThat(text).contains("eventType=\"CounterEvent\"");
		assertThat(text).contains(",deployment=\"deployment\"");
		assertThat(text).contains(",origin=\"origin\"");

		assertThat(text).contains(",timestamp=\"123\"");
		assertThat(text).contains(",job=\"job\"");
		assertThat(text).contains(",index=\"index\"");
		assertThat(text).contains(",ip=\"ip\"");
	}

	@Test
	public void testSerializeCounterEvent() {
		TextSerializationMapper textSerializationMapper = new TextSerializationMapper();
		Envelope counterEventEnvelope = sampleCounterEvent();

		WrappedEnvelope wrappedEnvelope = new WrappedEnvelope(counterEventEnvelope);

		String text = textSerializationMapper.serialize(wrappedEnvelope);
		assertThat(text).contains(",CounterEvent.delta=\"1\"");
		assertThat(text).contains(",CounterEvent.name=\"sampleCounter\"");
		assertThat(text).contains(",CounterEvent.total=\"12\"");
	}

	@Test
	public void testSerializeHtpStartEvent() {
		TextSerializationMapper textSerializationMapper = new TextSerializationMapper();
		Envelope httpStartEnvelope = sampleHttpStartEvent();

		WrappedEnvelope wrappedEnvelope = new WrappedEnvelope(httpStartEnvelope);

		String text = textSerializationMapper.serialize(wrappedEnvelope);
		assertThat(text).contains(",HttpStart.applicationId=\"00000000-0000-0000-0000-000000000000\"");
		assertThat(text).contains(",HttpStart.instanceId=\"instanceId\"");
		assertThat(text).contains(",HttpStart.instanceIndex=\"2\"");
		assertThat(text).contains(",HttpStart.method=\"GET\"");
		assertThat(text).contains(",HttpStart.parentRequestId=\"00000000-0000-0000-0000-000000000000\"");
		assertThat(text).contains(",HttpStart.peerType=\"CLIENT\"");
		assertThat(text).contains(",HttpStart.remoteAddress=\"remoteAddress\"");
		assertThat(text).contains(",HttpStart.requestId=\"00000000-0000-0000-0000-000000000000\"");
		assertThat(text).contains(",HttpStart.timestamp=\"123\"");
		assertThat(text).contains(",HttpStart.uri=\"/test\"");
		assertThat(text).contains(",HttpStart.userAgent=\"userAgent\"");
	}

	private Envelope.Builder sampleEnvelopeBuilder(EventType eventType) {
		return Envelope.builder()
				.deployment("deployment")
				.eventType(eventType)
				.index("index")
				.ip("ip")
				.job("job")
				.origin("origin")
				.timestamp(123L);
	}



	private Envelope sampleCounterEvent() {
		CounterEvent counterEvent = CounterEvent.builder()
				.delta(1L)
				.name("sampleCounter")
				.total(12L)
				.build();
		return sampleEnvelopeBuilder(EventType.CounterEvent).counterEvent(counterEvent).build();
	}

	private Envelope sampleHttpStartEvent() {
		HttpStart httpStart = HttpStart.builder()
				.applicationId(UUID.fromString("00000000-0000-0000-0000-000000000000"))
				.instanceId("instanceId")
				.instanceIndex(2)
				.method(Method.GET)
				.parentRequestId(UUID.fromString("00000000-0000-0000-0000-000000000000"))
				.peerType(PeerType.CLIENT)
				.remoteAddress("remoteAddress")
				.requestId(UUID.fromString("00000000-0000-0000-0000-000000000000"))
				.uri("/test")
				.userAgent("userAgent")
				.timestamp(123L)
				.build();
		return sampleEnvelopeBuilder(EventType.HttpStart).httpStart(httpStart).build();
	}
}
