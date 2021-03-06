package io.pivotal.cf.nozzle.doppler;

import io.pivotal.cf.nozzle.netty.NettyFirehose;
import reactor.core.publisher.Flux;

/**
 * Doppler Client that returns events with Envelope around it..
 */
public class NettyDopplerClient implements FirehoseClient {

	private final NettyFirehose nettyFirehose;

	public NettyDopplerClient(NettyFirehose nettyFirehose) {
		this.nettyFirehose = nettyFirehose;
	}

	public Flux<Envelope> firehose() {
		return nettyFirehose.open()
				.map(Envelope::from);
	}

}
