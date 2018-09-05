package com.metrics.micrometer.config;

import java.io.File;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.MetricRegistry;

import io.micrometer.core.instrument.MeterRegistry;

@Configuration
@PropertySource("classpath:config.properties")
public class MicrometerConfig {

	private final MetricRegistry metrics = new MetricRegistry();

	@Value("${FILE_PATH}")
	private String filePath;

	@Bean
	public MetricRegistry meterRegistryCustomizer(MeterRegistry meterRegistry) {

		ConsoleReporter consoleReporter = ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.SECONDS).build();
		//consoleReporter.start(10, TimeUnit.SECONDS);

		CsvReporter csvReporter = CsvReporter.forRegistry(metrics).formatFor(Locale.US).convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.SECONDS).build(new File(filePath));
		csvReporter.start(30, TimeUnit.SECONDS);
		return metrics;

	}
}
