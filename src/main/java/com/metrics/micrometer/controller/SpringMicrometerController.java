package com.metrics.micrometer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

@RestController
@RequestMapping("/rest")
public class SpringMicrometerController {

	@Autowired
	@Qualifier("meterRegistryCustomizer")
	MetricRegistry meterRegistry;

	@RequestMapping("/welcome")
	public String welcome() {
		Timer responses = meterRegistry.timer("/welcome Timer");
		meterRegistry.meter("/welcome Meter").mark();
		try (Timer.Context context = responses.time()) {
			return "Welcome User";
		}
	}

	@RequestMapping("/hello")
	public String hello() {
		Timer responses = meterRegistry.timer("/hello Timer");
		meterRegistry.meter("/hello Meter").mark();
		try (Timer.Context context = responses.time()) {
			return "Hello User";
		}
	}
}
