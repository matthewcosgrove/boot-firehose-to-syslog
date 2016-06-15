package io.pivotal.cf.nozzle.mapper;

import io.pivotal.cf.nozzle.doppler.Envelope;
import org.cloudfoundry.doppler.Event;

/**
 * Responsible for mapping the envelope to be sent to syslog to a string - json/plain likely to start with
 */
public interface EnvelopeSerializationMapper {

	String serialize(Envelope<? extends Event> envelope);

}
